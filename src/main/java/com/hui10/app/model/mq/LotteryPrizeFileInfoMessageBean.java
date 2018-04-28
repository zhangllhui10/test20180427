package com.hui10.app.model.mq;

import com.hui10.model.mq.MessageBean;

/**
 * @ClassName: LotteryPrizeFileInfoMessageBean.java
 * @Description:
 * @author zhangll
 * @date 2017年10月19日 上午10:29:22
 */
public class LotteryPrizeFileInfoMessageBean extends MessageBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5814686831011549132L;
	private String gamecode;
	private String issuenumber;
	private Long lotterytime;
	private String filecheckcode;
	private String host;
	private int port;
	private String username;
	private String password;
	private String remotePath;
	private String filename;
	private String localPath;
	/**
	 * @return the gamecode
	 */
	public String getGamecode() {
		return gamecode;
	}
	/**
	 * @param gamecode the gamecode to set
	 */
	public void setGamecode(String gamecode) {
		this.gamecode = gamecode;
	}
	/**
	 * @return the issuenumber
	 */
	public String getIssuenumber() {
		return issuenumber;
	}
	/**
	 * @param issuenumber the issuenumber to set
	 */
	public void setIssuenumber(String issuenumber) {
		this.issuenumber = issuenumber;
	}
	/**
	 * @return the lotterytime
	 */
	public Long getLotterytime() {
		return lotterytime;
	}
	/**
	 * @param lotterytime the lotterytime to set
	 */
	public void setLotterytime(Long lotterytime) {
		this.lotterytime = lotterytime;
	}
	/**
	 * @return the filecheckcode
	 */
	public String getFilecheckcode() {
		return filecheckcode;
	}
	/**
	 * @param filecheckcode the filecheckcode to set
	 */
	public void setFilecheckcode(String filecheckcode) {
		this.filecheckcode = filecheckcode;
	}
	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}
	
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the remotePath
	 */
	public String getRemotePath() {
		return remotePath;
	}
	/**
	 * @param remotePath the remotePath to set
	 */
	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/**
	 * @return the localPath
	 */
	public String getLocalPath() {
		return localPath;
	}
	/**
	 * @param localPath the localPath to set
	 */
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
}
