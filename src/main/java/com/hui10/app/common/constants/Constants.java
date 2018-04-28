package com.hui10.app.common.constants;

import com.hui10.common.utils.PropertiesUtils;

/**
 * @author wengf
 * @ClassName: Constants.java
 * @Description:
 * @date 2017年10月17日 下午3:59:43
 */
public interface Constants {

    /**
     * 和Pubservice通信的secret
     */
    public static String PUBSERVICESECRET = PropertiesUtils.get("pubservice.secret");

    /**
     * 网银互联联行号
     */
    public static String CMBC = "cmbc.xml";

    /**
     * 加密算子前缀
     */
    public static final String LOTTERY_MD5_PREFIX = PropertiesUtils.get("LOTTERY_MD5_PREFIX");
    /**
     * 加密算子后缀
     */
    public static final String LOTTERY_MD5_SUFFIX = PropertiesUtils.get("LOTTERY_MD5_SUFFIX");
    /**
     * 渠道商ID
     */
    public static final String CHANNEL_ID = PropertiesUtils.get("LOTTERY_CHANNEL_ID");
    /**
     * 加密算子前缀
     */
    public static final String PAYPROXY_MD5_PREFIX = PropertiesUtils.get("PAYPROXY_MD5_PREFIX"); //"c1661665505a3f81"
    /**
     * 加密算子后缀
     */
    public static final String PAYPROXY_MD5_SUFFIX = PropertiesUtils.get("PAYPROXY_MD5_SUFFIX"); //"a04ad239d983388d"


    /**
     * 聚合支付地址
     */
    String BASE_URL = PropertiesUtils.get("pay_base_url");

    /**
     * 支付接口版本号
     */
    String VERSION = PropertiesUtils.get("pay_version");

    /**
     * 商户号 支付平台分配的商户号
     */
    String MERC_ID = PropertiesUtils.get("pay_merc_id");

    /**
     * 二级商户
     */
    String SUB_MERC_ID = PropertiesUtils.get("pay_sub_merc_id");

    /**
     * 业务应用ID，由支付系统提供
     */
    String APPID = PropertiesUtils.get("pay_appid");

    /***
     * token
     */
    String TOKEN = PropertiesUtils.get("pay_appid_token");

    /***
     * 密钥
     */
    String KEY = PropertiesUtils.get("pay_scan_code_key");

    /**
     * 统一下单
     */
    String UNIFIED_ORDER = PropertiesUtils.get("pay_unified_order");

    /**
     * 预交易订单
     */
    String TRANSFER_UNIFIED = PropertiesUtils.get("pay_transfer_unified");

    /**
     * 提现
     */
    String TRANSFER_PAY = PropertiesUtils.get("transfer_pay");

    /**
     * 收款
     */
    String CHARGE_ORDER = PropertiesUtils.get("charge_order");

    /**
     * 查询提现状态
     */
    String QUERY_ORDER = PropertiesUtils.get("transfer_query");

    /**
     * 关闭订单
     */
    String CLOSE_ORDER = PropertiesUtils.get("close_order");

    /**
     * 回调地址
     */
    String PAY_SERVER_NOTIFY_URL = PropertiesUtils.get("meeto.url") + PropertiesUtils.get("pay_server_notify_url");


    /**
     * 出票结果通知
     */
    String UPLNOTIFY = PropertiesUtils.get("uplNotify");


    /**
     * 提现费用
     */
    public static final double WITHDRAW_FEE = 0d;

    /**
     * 1W小奖
     */
    public static final long LITTLE_MONEY = 1000000;
    /**
     * 10W大奖
     */
    public static final long BIG_MONEY = 10000000;

    public final static String APP_IDENTITY = "app_identity:";

    public final static int PCCODE_EXPIRED = 60 * 2;

    public static String DEFUALT_LOTTERY_TYPE_CODE = "10001";

    /**
     * 加密算子
     */
    public static String UNIONPAY_MD5_PREFIX = PropertiesUtils.get("UNIONPAY_MD5_PREFIX");
    public static String UNIONPAY_MD5_SUFFIX = PropertiesUtils.get("UNIONPAY_MD5_SUFFIX");

    public static String UNIONPAY_URL = PropertiesUtils.get("unionpay.presys.host");
    
    public final static int DEFAULT_PAGESIZE = 10;
    
    public final static int DEFAULT_PAGENO = 1;


    /**
     * FTP文件配置
     */
    String FTP_HOST = PropertiesUtils.get("ftp.host");
    int FTP_PORT = Integer.parseInt(PropertiesUtils.get("ftp.port"));
    String FTP_USERNAME = PropertiesUtils.get("ftp.username");
    String FTP_PASSWORD = PropertiesUtils.get("ftp.password");
    String FTP_REMOTEPATH = PropertiesUtils.get("ftp.remotepath");
    String LOCAL_PATH = PropertiesUtils.get("ftp.local.path");
    
    String FTP_UNION_HOST = PropertiesUtils.get("ftp.union.host");
    int FTP_UNION_PORT = Integer.parseInt(PropertiesUtils.get("ftp.union.port"));
    String FTP_UNION_USERNAME = PropertiesUtils.get("ftp.union.username");
    String FTP_UNION_PASSWORD = PropertiesUtils.get("ftp.union.password");
    String FTP_UNION_REMOTEPATH = PropertiesUtils.get("ftp.union.remotepath");
    String UNION_LOCAL_PATH = PropertiesUtils.get("ftp.union.local.path");
    String FTP_UNION_FILE_NAME = PropertiesUtils.get("ftp.union.file.name");

    public static String UNIONPAY_MERCHANT_LOTTERY_NOTIFY  = PropertiesUtils.get("merchant.lottery.notify");

    String MERCENTRY_UPLSETTLEINF=PropertiesUtils.get("mercentry_uplsettleinf");

    String FTP_PRIZE_REMOTEPATH = PropertiesUtils.get("ftp.prize.remotepath");
    
    
    public static final String AES_KEY = PropertiesUtils.get("aes_key").trim();
    /**
     * 前置分配给汇拾的SPID
     */
    String HUI10_SPID=PropertiesUtils.get("HUI10_SPID");
    public static String STATION_NO_PREFIX = PropertiesUtils.get("huiapp.merchant.stationno");
    
    public static String LOTTERY_RECEIVE = PropertiesUtils.get("lottery.receive") ;

    int HOME_LIST_SIZE = Integer.parseInt(PropertiesUtils.get("home.list.size"));
    String AUDIT_FAIL_MESSAGE = PropertiesUtils.get("AUDIT_FAIL_MESSAGE");
    String SEND_PRIZE_FAIL_MESSAGE = PropertiesUtils.get("SEND_PRIZE_FAIL_MESSAGE");
    
    public final static Boolean test_code = null == PropertiesUtils.get("smscode.powerful") ? false : Boolean.parseBoolean(PropertiesUtils.get("smscode.powerful"));

}
