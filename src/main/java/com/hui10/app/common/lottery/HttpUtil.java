package com.hui10.app.common.lottery;

import com.alibaba.fastjson.JSON;
import com.hui10.app.common.constants.Constants;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.exception.CommonException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author yangcb
 * @ClassName: HttpUtil.java
 * @Description:
 * @date 2017年1月9日 下午5:02:48
 */

class AnyTrustStrategy implements TrustStrategy {

    @Override
    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        // 信任所有需要修改为true ，不信任所有修改false
        return true;
    }

}

/**
 * @author yangcb
 * @ClassName: HttpUtil.java
 * @Description:
 * @date 2017年1月9日 下午5:02:48
 */
public class HttpUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    private static int bufferSize = 1024;

    private static volatile HttpUtil instance;

    private ConnectionConfig connConfig;

    private SocketConfig socketConfig;

    private ConnectionSocketFactory plainSF;

    private KeyStore trustStore;

    private SSLContext sslContext;

    private LayeredConnectionSocketFactory sslSF;

    private Registry<ConnectionSocketFactory> registry;

    private PoolingHttpClientConnectionManager connManager;

    private volatile HttpClient client;

    private volatile BasicCookieStore cookieStore;

    public static String defaultEncoding = "utf-8";

    public static String FILENAME_KEY = "ssl.keystore";

    public static String KEYSTORE_PASSWORD_KEY = "ssl.password";

    public static final int CONNECT_TIMEOUT = StringUtils.isBlank(PropertiesUtils.get("connect_timeout")) ? 30000 : Integer.parseInt(PropertiesUtils.get("connect_timeout").trim());
    public static final int CONNECTION_REQUEST_TIMEOUT = StringUtils.isBlank(PropertiesUtils.get("connection_request_timeout")) ? 30000 : Integer.parseInt(PropertiesUtils.get("connection_request_timeout").trim());
    public static final int SOCKET_TIMEOUT = StringUtils.isBlank(PropertiesUtils.get("socket_timeout")) ? 30000 : Integer.parseInt(PropertiesUtils.get("socket_timeout").trim());
    public static final int DEFAULT_MAX_PER_ROUTE = StringUtils.isBlank(PropertiesUtils.get("default_max_per_route")) ? 100 : Integer.parseInt(PropertiesUtils.get("default_max_per_route").trim());
    public static final int MAX_TOTAL = StringUtils.isBlank(PropertiesUtils.get("max_total")) ? 200 : Integer.parseInt(PropertiesUtils.get("max_total").trim());
    public static final String LOTTERY_MD5_PREFIX = Constants.LOTTERY_MD5_PREFIX;
    public static final String LOTTERY_MD5_SUFFIX = Constants.LOTTERY_MD5_SUFFIX;

    private static List<NameValuePair> paramsConverter(Map<String, String> params) {
        List<NameValuePair> nvps = new LinkedList<NameValuePair>();
        Set<Entry<String, String>> paramsSet = params.entrySet();
        for (Entry<String, String> paramEntry : paramsSet) {
            nvps.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue()));
        }
        return nvps;
    }

    public static String readStream(InputStream in, String encoding) {
        if (in == null) {
            return null;
        }
        try {
            InputStreamReader inReader = null;
            if (encoding == null) {
                inReader = new InputStreamReader(in, defaultEncoding);
            } else {
                inReader = new InputStreamReader(in, encoding);
            }
            char[] buffer = new char[bufferSize];
            int readLen = 0;
            StringBuffer sb = new StringBuffer();
            while ((readLen = inReader.read(buffer)) != -1) {
                sb.append(buffer, 0, readLen);
            }
            inReader.close();
            return sb.toString();
        } catch (IOException e) {
            log.error("读取返回内容出错:{}", e.getMessage());
        }
        return null;
    }

    private HttpUtil() {
        // 设置连接参数
        connConfig = ConnectionConfig.custom().setCharset(Charset.forName(defaultEncoding)).build();
        socketConfig = SocketConfig.custom().setSoTimeout(100000).build();
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
        plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSF);
        // 指定信任密钥存储对象和连接套接字工厂
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            // 2017-03-27 删除信任证书
            String keyfile = PropertiesUtils.getProperty(FILENAME_KEY);
            String password = PropertiesUtils.getProperty(KEYSTORE_PASSWORD_KEY);
            FileInputStream instream = new FileInputStream(new File(keyfile));
            trustStore.load(instream, password.toCharArray());
            sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, new AnyTrustStrategy()).build();
            //按照域名访问
            sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSF);
            instream.close();
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        registry = registryBuilder.build();
        // 设置连接管理器
        connManager = new PoolingHttpClientConnectionManager(registry);
        connManager.setDefaultConnectionConfig(connConfig);
        connManager.setDefaultSocketConfig(socketConfig);

        connManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
        connManager.setMaxTotal(MAX_TOTAL);
        // 指定cookie存储对象
        cookieStore = new BasicCookieStore();
        // 构建客户端
        client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).setConnectionManager(connManager)
                .build();
    }

    public static HttpUtil getInstance() {
        synchronized (HttpUtil.class) {
            if (HttpUtil.instance == null) {
                instance = new HttpUtil();
            }
            return instance;
        }
    }

    public InputStream doGet(String url) throws URISyntaxException, ClientProtocolException, IOException {
        HttpResponse response = this.doGet(url, null);
        return response != null ? response.getEntity().getContent() : null;
    }

    public String doGetForString(String url) throws URISyntaxException, ClientProtocolException, IOException {
        return HttpUtil.readStream(this.doGet(url), null);
    }

    public InputStream doGetForStream(String url, Map<String, String> queryParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        HttpResponse response = this.doGet(url, queryParams);
        return response != null ? response.getEntity().getContent() : null;
    }

    public String doGetForString(String url, Map<String, String> queryParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        return HttpUtil.readStream(this.doGetForStream(url, queryParams), null);
    }

    /**
     * 基本的Get请求
     *
     * @param url         请求url
     * @param queryParams 请求头的查询参数
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws ClientProtocolException
     */
    public HttpResponse doGet(String url, Map<String, String> queryParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        HttpGet gm = new HttpGet();

        RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT).build();

        gm.setConfig(config);

        URIBuilder builder = new URIBuilder(url);
        // 填入查询参数
        if (queryParams != null && !queryParams.isEmpty()) {
            builder.setParameters(HttpUtil.paramsConverter(queryParams));
        }
        gm.setURI(builder.build());
        return client.execute(gm);
    }

    public InputStream doPostForStream(String url, Map<String, String> queryParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        HttpResponse response = this.doPost(url, queryParams, null);
        return response != null ? response.getEntity().getContent() : null;
    }

    public String doPostForString(String url, Map<String, String> queryParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        return HttpUtil.readStream(this.doPostForStream(url, queryParams), null);
    }

    public InputStream doPostForStream(String url, Map<String, String> queryParams, Map<String, String> formParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        HttpResponse response = this.doPost(url, queryParams, formParams);
        return response != null ? response.getEntity().getContent() : null;
    }

    public String doPostRetString(String url, Map<String, String> queryParams, Map<String, String> formParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        return HttpUtil.readStream(this.doPostForStream(url, queryParams, formParams), null);
    }

    /**
     * 基本的Post请求
     *
     * @param url         请求url
     * @param queryParams 请求头的查询参数
     * @param formParams  post表单的参数
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws ClientProtocolException
     */
    public HttpResponse doPost(String url, Map<String, String> queryParams, Map<String, String> formParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        HttpPost pm = new HttpPost();

        RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT).build();

        pm.setConfig(config);

        URIBuilder builder = new URIBuilder(url);
        // 填入查询参数
        if (queryParams != null && !queryParams.isEmpty()) {
            builder.setParameters(HttpUtil.paramsConverter(queryParams));
        }
        pm.setURI(builder.build());
        // 填入表单参数
        if (formParams != null && !formParams.isEmpty()) {
            pm.setEntity(new UrlEncodedFormEntity(HttpUtil.paramsConverter(formParams)));
        }
        return client.execute(pm);
    }

    private String sendpost(String url, String parameters)
            throws IOException, URISyntaxException {

        log.debug("request parameters body-->{}", parameters);

        String body = null;
        HttpPost method = new HttpPost();

        RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT).build();

        method.setConfig(config);

        method.addHeader("Accept", "application/json");
        method.addHeader("Content-Type", "application/json;charset=UTF-8");
        method.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));
        URIBuilder builder = new URIBuilder(url);
        method.setURI(builder.build());
        HttpResponse response = client.execute(method);
        int statusCode = response.getStatusLine().getStatusCode();
        // post(url,parameters);
        if (statusCode != HttpStatus.SC_OK) {
            /**
             * 调用远端失败
             */
            throw new CommonException(102000014, PropertiesUtils.get(102000014));
        }

        if (response.getEntity() != null) {
            body = EntityUtils.toString(response.getEntity());
        }

        return body;
    }

    public <T> String doPost(String transSerialNumber, String url, T data) {

        try {
            HttpParams<String> params = new HttpParams<String>();

            String transData = JSON.toJSONString(data);
            params.setTransData(transData);
            params.setTransSerialNumber(transSerialNumber);
            String signature = Md5Util.getSignature(LOTTERY_MD5_PREFIX, transData, LOTTERY_MD5_SUFFIX);
            params.setSignature(signature);
            String body=JSON.toJSONString(params);
            log.info("请求报文：body={}",body);
            return sendpost(url, body);
        } catch (Exception e) {
            log.error("请求远程服务错误:{}", e.getMessage());
            /**
             * 调用远端失败
             */
            throw new CommonException(102000014, PropertiesUtils.get(102000014));
        }
    }


}
