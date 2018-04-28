package com.hui10.app.common.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.lottery.FtpUtil;
import com.hui10.app.common.lottery.Md5Util;
import com.hui10.app.model.acquirer.SettlementInfo;
import com.hui10.common.utils.HttpUtil;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.exception.CommonException;
import com.pay.platform.trunk.pay.sdk.MD5SignAndValidate;

/**
 * @ClassName: UnionPayUtils.java
 * @Description:
 * @author zhangll
 * @date 2017年10月30日 下午4:21:27
 */
public class UnionPayUtils {
	// 下载文件等待的时间
	private static final Integer WAITTIME = 10;

	private static final Logger logger = LoggerFactory.getLogger(UnionPayUtils.class);
	
	public static final String SPLIT = "_";
	
	public static final String FILE_SUFFIX = ".txt";
	
	public static final String MARKET = "market";

	/**
	 * 校验数据
	 *
	 * @param auth
	 * @param body
	 */
	public static void check(String auth, String body) {
		String sig = Md5Util.getSignature(Constants.UNIONPAY_MD5_PREFIX, body, Constants.UNIONPAY_MD5_SUFFIX);
		if (!auth.equals(sig)) {
			throw new CommonException(ICommon.PARAM_SIG_ERROE, PropertiesUtils.get(ICommon.PARAM_SIG_ERROE));
		}
	}

	/**
	 * 
	 * @param appId
	 * @param token
	 * @param time
	 * @return
	 * @user zhangll
	 * @date 2017年10月31日 下午12:50:43
	 */
	public static String getSigParameter(String appId, String token, String time) {
		String md5 = appId + token + time;
		return DigestUtils.md5Hex(md5);
	}

	/**
	 * 
	 * @param appId
	 * @param times
	 * @return
	 * @user zhangll
	 * @date 2017年10月31日 下午12:50:37
	 */
	public static String getAuthorization(String appId, String times) {
		String base64 = appId + ":" + times;
		return Base64Utils.encodeToString(base64.getBytes());
	}

	/**
	 * 
	 * @param url
	 * @param parameters
	 * @param authorization
	 * @return
	 * @user zhangll
	 * @date 2017年10月31日 下午12:51:09
	 */
	public static String getResponseBody(String url, String parameters, String authorization) {
		HttpUtil httpUtil = HttpUtil.getInstance();

		try {
			return httpUtil.postBody(url, parameters, authorization);
		} catch (IOException | URISyntaxException e) {
			logger.error("请求聚合支付错误", e);
		}
		return null;
	}

	public static void settlementInfoInsert(SettlementInfo settlementInfo) {
		// 访问支付平台
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = sdf.format(new Date());
		Map<String, String> map = new HashMap<String, String>();
		map.put("merc_id", Constants.MERC_ID);
		map.put("salt", MD5SignAndValidate.getRandomString(32));
		map.put("req_time", time);
		map.put("signature", "");
		map.put("profit_participant_id", settlementInfo.getProfit_participant_id());// 参与分润方ID
		map.put("profit_participant_name", settlementInfo.getProfit_participant_name());// 参与分润方名称
		map.put("profit_participant_contact", settlementInfo.getProfit_participant_contact());// 商户联系人
		map.put("profit_participant_phone", settlementInfo.getProfit_participant_phone());// 商户联系电话
		map.put("merc_bank_head_name", settlementInfo.getMerc_bank_head_name());// 商户开户行总行名称
		map.put("merc_bank_head_code", settlementInfo.getMerc_bank_head_code());// 商户开户行总行联行号
		map.put("merc_bank_branch_name", settlementInfo.getMerc_bank_branch_name());// 商户开户行具体支行名称
		map.put("merc_bank_branch_code", settlementInfo.getMerc_bank_branch_code());// 商户开户行具体支行联行号
		map.put("merc_account_open_type", settlementInfo.getMerc_account_open_type());// 商户在银行账户开户类型:1-对公账户;2-对私账户;
		map.put("merc_account_name", settlementInfo.getMerc_account_name());// 商户在银行的账户名称
		map.put("merc_account_no", settlementInfo.getMerc_account_no());// 商户银行账户号
		map.put("single_fee_rate", "0");// 单笔结算费率
		map.put("single_fee_upper_limit", "10");// 单笔手续费的上限额
		map.put("single_fee_lower_limit", "0");// 单笔手续费的下限额
		map.put("profit_participant_financial_contact", settlementInfo.getProfit_participant_financial_contact());// 商户财务联系人
		map.put("profit_participant_financial_phone", settlementInfo.getProfit_participant_phone());// 商户财务联系电话
		map.put("profit_settle_date", settlementInfo.getProfit_settle_date());
		map.put("profit_floor_amount", settlementInfo.getProfit_floor_amount());
		map.put("chl_fee_settle_date", settlementInfo.getChl_fee_settle_date());
		map.put("chl_fee_floor_amount", settlementInfo.getChl_fee_floor_amount());
		logger.debug("**** 结算信息录入 **** " + JSONObject.toJSONString(map));
		String body = UnionPayUtils.getResponseBody(
				String.format(Constants.BASE_URL, Constants.VERSION, Constants.APPID, Constants.MERCENTRY_UPLSETTLEINF) + "?sig="
						+ UnionPayUtils.getSigParameter(Constants.APPID, Constants.TOKEN, time),
				JSON.toJSONString(MD5SignAndValidate.signData(map, Constants.KEY)), UnionPayUtils.getAuthorization(Constants.APPID, time));
		logger.debug("***********get the response from payment platform =" + body);

	}

	public static Map<String, Object> downloadedFile(JSONObject transData) {
		Map<String, Object> map = new HashMap<String, Object>();
		String gamecode = transData.getString("lotterytype");// 彩票类型
		String issuenumber = transData.getString("lotteryno");// 彩票期号
		// 登录ftp服务器的参数
		String host = Constants.FTP_HOST;
		int port = Constants.FTP_PORT;
		String username = Constants.FTP_USERNAME;
		String password = Constants.FTP_PASSWORD;
		// 放置文件的路径
		String remotePath = Constants.FTP_PRIZE_REMOTEPATH;
		String localPath = Constants.LOCAL_PATH;
		// 文件名字
		String fileName = gamecode + SPLIT + issuenumber + FILE_SUFFIX;
		File dirFile = new File(localPath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		boolean download = sshDownloadFtp(host, username, password, port, fileName, remotePath, localPath, gamecode, issuenumber);

		map.put("flag", download);
		map.put("postDatas", transData);

		return map;
	}
	
	
	public static Map<String, Object> downloadedFileMarket(JSONObject transData) {
		Map<String, Object> map = new HashMap<String, Object>();
		String gameCode = transData.getString("lotterytype");// 彩票类型
		String issueNumber = transData.getString("lotteryno");// 彩票期号
		String promotionId = transData.getString("promotionid");
		// 登录ftp服务器的参数
		String host = Constants.FTP_HOST;
		int port = Constants.FTP_PORT;
		String username = Constants.FTP_USERNAME;
		String password = Constants.FTP_PASSWORD;
		// 放置文件的路径
		String remotePath = Constants.FTP_PRIZE_REMOTEPATH;
		String localPath = Constants.LOCAL_PATH;
		// 文件名字
		String fileName = MARKET + SPLIT + gameCode + SPLIT + issueNumber + SPLIT + promotionId + FILE_SUFFIX;
		File dirFile = new File(localPath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		boolean download = sshDownloadFtp(host, username, password, port, fileName, remotePath, localPath, gameCode, issueNumber);
		map.put("flag", download);
		map.put("postDatas", transData);
		return map;
	}
	
	private static boolean sshDownloadFtp(String host,String username,String password,int port,String fileName,String remotePath,String localPath,String gameCode,String issueNumber){
		boolean download = FtpUtil.sshDownloadFtp(host, username, password, port, fileName, remotePath, localPath);
		if (download == false) {
			int time = WAITTIME;
			while (time > 0) {
				time--;
				try {
					Thread.sleep(60 * 1000);// 休眠一分钟
					logger.info("****Left minutes = " + time + ", " + gameCode + " " + issueNumber);
					download = FtpUtil.sshDownloadFtp(host, username, password, port, fileName, remotePath, localPath);
					if (download == true) {
						break;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		return download;
	}
}
