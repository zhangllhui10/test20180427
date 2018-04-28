package com.hui10.app.service.prize.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.lottery.FtpUtil;
import com.hui10.app.common.lottery.HttpResult;
import com.hui10.app.common.lottery.Md5Util;
import com.hui10.app.common.utils.UnionPayUtils;
import com.hui10.app.dao.prize.PrizeDao;
import com.hui10.app.model.enums.LotteryStatusEnum;
import com.hui10.app.model.enums.MessageTypeEnum;
import com.hui10.app.model.enums.PrizeFileStatusEnum;
import com.hui10.app.model.enums.PrizeLevelEnum;
import com.hui10.app.model.order.LotteryOrder;
import com.hui10.app.model.order.enums.OrderTypeEnum;
import com.hui10.app.model.prize.LotteryPrizeFileContent;
import com.hui10.app.model.prize.LotteryPrizeFileInfo;
import com.hui10.app.service.message.MessageService;
import com.hui10.app.service.prize.LotteryPrizeService;
import com.hui10.common.mq.sender.HuiQueueSender;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.enums.mpush.AfterOpenAction;
import com.hui10.enums.user.UserInvoketypeEnum;
import com.hui10.exception.CommonException;
import com.hui10.model.mq.HuiUPushMessageBean;
/**
 * @ClassName: PrizeServiceImpl.java
 * @Description:
 * @author zhangll
 * @date 2017年10月18日 上午10:14:27
 */
@Service
public class LotteryPrizeServiceImpl implements LotteryPrizeService {
	private static final Logger logger = LoggerFactory.getLogger(LotteryPrizeServiceImpl.class);
	// 下载文件等待的时间
	private static final String WIN_PRIZE_TITLE = "恭喜您，购买的彩票已中奖～";
	private static final String PRIZE_ACTIVITY = "com.hui10.lottery.ui.lotteryorder.LotteryOrderRequestPrizeActivity";
	private static final String ORDER_ID = "orderid";
	//中奖金额
	private static final String WIN_PRIZE = "winprize";
	private static final String LOTTERY_TYPE = "lotterytype";
	private static final String ISSUE_NUMBER = "issuenumber";
	private static final String PRIZE_LEVEL = "prizelevel";
	@Autowired
	private PrizeDao prizeDao;
	@Autowired
    private HuiQueueSender customizedcastSender;
	@Autowired
	private MessageService messageService;
	
	@Override
	public HttpResult<String> dealWinPrizeFile(Map<String, Object> map) {
		HttpResult<String> httpResult = new HttpResult<String>();
		JSONObject transData = (JSONObject) map.get("postDatas");
		// 彩票类型
		String gameCode = transData.getString("lotterytype");
		// 彩票期号
		String issueNumber = transData.getString("lotteryno");
		// 开奖时间
		Long lotteryTime = transData.getLong("lotterytime");
		// 文件校验码
		String fileCheckCode = transData.getString("filemd5");
		if (null == map.get("flag") || false == (Boolean) map.get("flag")) {
			return getHttpResultFileDownload();
		} else {
			logger.info("****gamecode:" + gameCode + ",issuenumber:" + issueNumber + ",lotterytime:" + lotteryTime + ",filecheckcode:" + fileCheckCode);
			String localPath = Constants.LOCAL_PATH;
			// 文件名字
			String fileName = gameCode + UnionPayUtils.SPLIT + issueNumber + UnionPayUtils.FILE_SUFFIX;
			File file = new File(localPath + fileName);
			// 判断文件是否存在
			if (file.isFile() && file.exists()) {
				// 计算文件MD5值
				String fileMD5 = getMD5(file);
				if (!fileMD5.equals(fileCheckCode)) {
					// 文件MD5值不正确，删除文件
					file.delete();
					httpResult.setTransData("Validate the MD5 of file failure !!!");
					logger.info("*******get the MD5 of the file =" + fileCheckCode + "*******me calculate the MD5 of the file =" + fileMD5 + "******* Local "
							+ fileName + " deleted!!!");
				} else {
					logger.info("********download the file,localPath：" + (localPath + fileName));
					httpResult.setTransData("1");
					try {
						//文件校验成功，处理中奖订单。
						treatePrize_Order(gameCode, issueNumber, lotteryTime, fileCheckCode,fileName, localPath,OrderTypeEnum.ORDINARY.getCode(),null);

					} catch (Exception e) {
						logger.error("the exception when access the queue!!!");
					}
				}
			} else {
				logger.error("******** The file doesn't exsit !!!");
				httpResult.setTransData("null");
			}
			httpResult.setSignature(Md5Util.getSignature(Constants.LOTTERY_MD5_PREFIX, JSON.toJSONString(httpResult), Constants.LOTTERY_MD5_SUFFIX));
			return httpResult;

		}

	}
	
	@Override
	public void treatePrize_Order(String gameCode, String issueNumber, Long lotteryTime, String fileCheckCode, String fileName, String localPath, String market,
			String promotionId) {
		String SEPARATOR = ",";
		File file = new File(localPath + fileName);
		// 判断文件是否存在
		if (file.isFile() && file.exists()) {

			InputStreamReader read = null;
			BufferedReader bufferedReader = null;
			List<LotteryPrizeFileContent> lotteryPrizeList = null;
			try {
				read = new InputStreamReader(new FileInputStream(file), "UTF-8");
				// 编码格式
				bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				lotteryPrizeList = new ArrayList<LotteryPrizeFileContent>();
				int num = 0;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					String[] txt = lineTxt.split(SEPARATOR);
					if (num > 0) {
						LotteryPrizeFileContent lotteryPrize = new LotteryPrizeFileContent();
						lotteryPrize.setOrderNo(txt[0]);
						if (PrizeLevelEnum.POSLOT_MEDIUM.getCode().equals(txt[1])) {
							lotteryPrize.setPrizelevel(PrizeLevelEnum.MEDIUM.getCode());
						} else if (PrizeLevelEnum.POSLOT_BIG.getCode().equals(txt[1])) {
							lotteryPrize.setPrizelevel(PrizeLevelEnum.BIG.getCode());
						} else {
							lotteryPrize.setPrizelevel(PrizeLevelEnum.SMALL.getCode());
						}
						lotteryPrize.setWinPrize(Long.parseLong(txt[2]));
						lotteryPrizeList.add(lotteryPrize);
					}
					num++;
				}
				logger.info("彩种 ： " + gameCode + " ,期号 ：  " + issueNumber + " ,中奖数 ：" + lotteryPrizeList.size());
				if (lotteryPrizeList.size() > 0) {
					// 更新中奖的订单
					Integer prizeNum = prizeDao.updateLotteryOrder(lotteryPrizeList);
					// 处理那些不是汇拾的订单
					if (prizeNum < lotteryPrizeList.size()) {
						List<String> updateDate = prizeDao.queryOrderNoByOrderNoList(lotteryPrizeList);
						int i = 0;
						for (LotteryPrizeFileContent lpc : lotteryPrizeList) {
							Boolean flag = true;
							for (String s : updateDate) {
								if (lpc.getOrderNo().equals(s)) {
									flag = false;
									break;
								}
							}
							if (flag) {
								logger.info("****** The lottery order has not been updated,orderno=******[" + i + "]:" + lpc.getOrderNo());
								i++;
							}
						}
					}
					// 更新用户累计中奖金额
					prizeDao.updateUserWinamount(lotteryPrizeList);
				}

				// 该彩种该期的订单更新为已开奖
				Integer open = prizeDao.updateLotteryStatus(gameCode, issueNumber, LotteryStatusEnum.HAVE_OPE.getCode(), market);
				logger.info("彩种： " + gameCode + " ,期号：  " + issueNumber + " ,已开奖数 :  " + open);
				LotteryPrizeFileInfo lotteryPrizeFileInfo = new LotteryPrizeFileInfo();
				lotteryPrizeFileInfo.setStatus(PrizeFileStatusEnum.TREATED.getCode());
				lotteryPrizeFileInfo.setGamecode(gameCode);
				lotteryPrizeFileInfo.setIssuenumber(issueNumber);
				lotteryPrizeFileInfo.setFilecheckcode(fileCheckCode);
				lotteryPrizeFileInfo.setUpdatetime(new Date());
				if (market.equals(OrderTypeEnum.GIVING.getCode())) {
					lotteryPrizeFileInfo.setPromotionid(promotionId);
				}
				// 开奖文件更新为已经处理
				prizeDao.updatePrizeFile(lotteryPrizeFileInfo);
			} catch (FileNotFoundException e) {
				logger.error("[FileNotFoundException] message:" + e.getMessage());
			} catch (IOException e) {
				logger.error("[IOException] message:" + e.getMessage());
			} catch (Exception e) {
				logger.error("[异常] message:" + e.getMessage());
			} finally {
				try {
					if (bufferedReader != null) {
						bufferedReader.close();
					}
					if (read != null) {
						read.close();
					}
				} catch (IOException e) {
					logger.error("message:" + e.getMessage());
				}
			}
			try {
				pushMessageToUser(lotteryPrizeList, market);
			} catch (Exception e) {
				logger.error("推送消息给用户，失败了。" + e.getMessage());
			}
			 

		} else {
			logger.info("文件不存在." + localPath +" "+ fileName);
		}

	}

	@Override
	public LotteryOrder selectPrizeOrder(String orderid, String uid) {
		LotteryOrder order = prizeDao.selectPrizeOrder(orderid, uid);
		if (null == order) {
			throw new CommonException(ICommon.ORDER_NOTFOND_ERROR, PropertiesUtils.get(ICommon.ORDER_NOTFOND_ERROR));
		}
		return order;
	}

	@Override
	public LotteryOrder selectAdutingOrder(String orderid) {
		LotteryOrder order = prizeDao.selectAuditingOrder(orderid);
		if (null == order) {
			throw new CommonException(ICommon.ORDER_NOTFOND_ERROR, PropertiesUtils.get(ICommon.ORDER_NOTFOND_ERROR));
		}
		return order;
	}

	private static String getMD5(File file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			String md5 = DigestUtils.md5DigestAsHex(fis);
			return md5;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (null != fis) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void winPrizeNotifyJob() {
		boolean download = true;
		String host = Constants.FTP_HOST;
		int port = Constants.FTP_PORT;
		String username = Constants.FTP_USERNAME;
		String password = Constants.FTP_PASSWORD;
		String remotePath = Constants.FTP_PRIZE_REMOTEPATH;
		String localPath = Constants.LOCAL_PATH;
		File dirFile = new File(localPath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		List<LotteryPrizeFileInfo> list = prizeDao.queryPrizeFileList(PrizeFileStatusEnum.UNTREATED.getCode());
		for (LotteryPrizeFileInfo file : list) {
			String fileName = null;
			String market = null;
			if (StringUtils.isBlank(file.getPromotionid())) {
				fileName = file.getGamecode() + UnionPayUtils.SPLIT + file.getIssuenumber() + UnionPayUtils.FILE_SUFFIX;
				market = OrderTypeEnum.ORDINARY.getCode();
			} else {
				fileName = UnionPayUtils.MARKET + UnionPayUtils.SPLIT + file.getGamecode() + UnionPayUtils.SPLIT + file.getIssuenumber() + UnionPayUtils.SPLIT
						+ file.getPromotionid() + UnionPayUtils.FILE_SUFFIX;
				market = OrderTypeEnum.GIVING.getCode();
			}

			File local_file = new File(localPath + fileName);
			if (local_file.isFile() && local_file.exists()) {
				dealWithFile(localPath, fileName, file, market, file.getPromotionid());
			} else {
				download = FtpUtil.sshDownloadFtp(host, username, password, port, fileName, remotePath, localPath);
				if (true == download) {
					dealWithFile(localPath, fileName, file, market, file.getPromotionid());
				} else {
					logger.info("******** The file download failure !!!,localPath " + localPath + " ,fileName " + fileName + " ,file " + file + " ,market "
							+ market);
				}
			}
		}

	}
	
	private void dealWithFile(String localPath, String fileName, LotteryPrizeFileInfo pf,String market,String promotionId) {

		File file = new File(localPath + fileName);
		// 计算文件MD5值
		String fileMD5 = getMD5(file);
		if (!fileMD5.equals(pf.getFilecheckcode())) {
			// 删除文件
			file.delete();
			logger.info("*******get the MD5 of the file =" + pf.getFilecheckcode() + "*******me calculate the MD5 of the file =" + fileMD5 + "******* Local "
					+ fileName + " deleted!!!");
		} else {
			try {
				
				this.treatePrize_Order(pf.getGamecode(), pf.getIssuenumber(), pf.getLotterytime().getTime(), pf.getFilecheckcode(), fileName, localPath,market,promotionId);
			} catch (Exception e) {
				logger.error("the exception when access the queue!!!"+e.getMessage());
			}
		}

	}
	
	private HttpResult<String> getHttpResultFileDownload() {
		HttpResult<String> httpResult = new HttpResult<String>();
		httpResult.setResult("103");
		httpResult.setResultDesc("文件下载失败");
		httpResult.setTransData("null");
		httpResult.setSignature(Md5Util.getSignature(Constants.LOTTERY_MD5_PREFIX, JSON.toJSONString(httpResult), Constants.LOTTERY_MD5_SUFFIX));
		return httpResult;
	}
	
	/**
	 * 推消息
	 * @param list
	 * @user zhangll
	 * @date 2018年4月9日 上午11:19:49
	 */
	@Async
	private void pushMessageToUser(List<LotteryPrizeFileContent> list, String market) {
		if (list != null && list.size() > 0) {
			for (LotteryPrizeFileContent content : list) {
				Map<String, String> order = prizeDao.queryInfoByOrderno(content.getOrderNo());
				if (null != order) {
					String uid = order.get("uid");
					String orderid = order.get("orderid");
					if (market.equals(OrderTypeEnum.GIVING.getCode())) {
						logger.debug("*** 518彩票节,推送消息   *** uid = " + uid + " ,orderid" + orderid);
						messageService.addPushMessage(uid, orderid, MessageTypeEnum.WIN_NOTIFY.getCode());
					} else {
						logger.debug("*** 中奖,推送信息   *** uid = " + uid + " ,orderid " + orderid);
						HuiUPushMessageBean bean = new HuiUPushMessageBean();
						Map<String, Object> messageContentMap = new HashMap<String, Object>();
						messageContentMap.put(ORDER_ID, orderid);
						messageContentMap.put(WIN_PRIZE, content.getWinPrize());
						messageContentMap.put(LOTTERY_TYPE, order.get("gamecode"));
						messageContentMap.put(ISSUE_NUMBER, order.get("issuenumber"));
						messageContentMap.put(PRIZE_LEVEL, content.getPrizelevel());
						bean.setAppType(UserInvoketypeEnum.HUI_CAIBAO.getState());
						bean.setTitle(WIN_PRIZE_TITLE);
						bean.setText(WIN_PRIZE_TITLE);
						bean.setTicker(WIN_PRIZE_TITLE);
						bean.setUid(uid);
						bean.setMessageContent(messageContentMap);
						bean.setAfterOpenDestination(PRIZE_ACTIVITY);
						bean.setAfterOpenAction(AfterOpenAction.go_custom.getState());
						// bean.setMode(false);
						customizedcastSender.sendMessage(bean);
					}

				}
			}
		}

	}

	@Override
	public HttpResult<String> dealWinPrizeFileMarket(Map<String, Object> map) {
		HttpResult<String> httpResult = new HttpResult<String>();
		JSONObject transData = (JSONObject) map.get("postDatas");
		// 彩票类型
		String lotteryType = transData.getString("lotterytype");
		// 彩票期号
		String lotteryNo = transData.getString("lotteryno");
		// 开奖时间
		Long lotteryTime = transData.getLong("lotterytime");
		// 文件校验码
		String fileCheckCode = transData.getString("filemd5");
		//营销开奖文件编号
		String promotionId = transData.getString("promotionid");
		if (null == map.get("flag") || false == (Boolean) map.get("flag")) {
			return getHttpResultFileDownload();
		} else {
			logger.info("****gamecode:" + lotteryType + ",issuenumber:" + lotteryNo + ",promotionid:" + promotionId+ ",lotterytime:" + lotteryTime + ",filecheckcode:" + fileCheckCode);
			String localPath = Constants.LOCAL_PATH;
			// 文件名字
			String fileName =UnionPayUtils.MARKET + UnionPayUtils.SPLIT + lotteryType + UnionPayUtils.SPLIT + lotteryNo +UnionPayUtils.SPLIT +promotionId+ UnionPayUtils.FILE_SUFFIX;
			File file = new File(localPath + fileName);
			// 判断文件是否存在
			if (file.isFile() && file.exists()) {
				// 计算文件MD5值
				String fileMD5 = getMD5(file);
				if (!fileMD5.equals(fileCheckCode)) {
					// 文件MD5值不正确，删除文件
					file.delete();
					httpResult.setTransData("Validate the MD5 of file failure !!!");
					logger.info("*******get the MD5 of the file =" + fileCheckCode + "*******me calculate the MD5 of the file =" + fileMD5 + "******* Local "
							+ fileName + " deleted!!!");
				} else {
					logger.info("********Validate the MD5 of file success.the file:" + (localPath + fileName));
					httpResult.setTransData("1");
					try {
						//文件校验成功，处理中奖订单。
						treatePrize_Order(lotteryType, lotteryNo, lotteryTime, fileCheckCode,fileName, localPath,OrderTypeEnum.GIVING.getCode(),promotionId);

					} catch (Exception e) {
						logger.error("the exception when access the queue!!!");
					}
				}
			} else {
				logger.error("******** The file doesn't exsit !!!");
				httpResult.setTransData("null");
			}
			httpResult.setSignature(Md5Util.getSignature(Constants.LOTTERY_MD5_PREFIX, JSON.toJSONString(httpResult), Constants.LOTTERY_MD5_SUFFIX));
			return httpResult;

		}

	}
}
