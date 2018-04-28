package com.hui10.app.common.lottery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class FtpUtil {
	
	private static Logger LOG = Logger.getLogger(FtpUtil.class);
	
	public static int time = 5;
	
	/**
	 * Description: 从FTP服务器下载文件
	 * 
	 * @param host
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 */
	public static boolean downFile(String host, int port, String username, String password, String remotePath, String fileName, String localPath) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			LOG.info("Ftputil downFile  : " + fileName);
			int reply;
			ftp.setDefaultTimeout(60000);
			ftp.setConnectTimeout(60000);
			ftp.connect(host, port);
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			LOG.info("Ftputil connect reply : " + reply + " " + ftp.getReplyString() + ", " + fileName);
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				LOG.info("Ftputil connect failed : " + fileName);
				return success;
			}
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					File localFile = new File(localPath + "/" + ff.getName());

					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
					success = true;
					LOG.info("Ftputil downFile success : " + fileName);
					break;
				}
			}

			ftp.logout();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Ftputil downFile error : " + fileName , e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

	/** 
	 * Description: 向FTP服务器上传文件 
	 * @param host FTP服务器hostname 
	 * @param port FTP服务器端口 
	 * @param username FTP登录账号 
	 * @param password FTP登录密码 
	 * @param path FTP服务器保存目录 
	 * @param filename 上传到FTP服务器上的文件名 
	 * @param input 输入流 
	 * @return 成功返回true，否则返回false 
	 */  
	public static boolean uploadFile(String host,int port,String username, String password, String path, String filename, InputStream input) {  
	    boolean success = false;  
	    FTPClient ftp = new FTPClient();  
	    try {  
	        int reply;  
	        ftp.connect(host, port);//连接FTP服务器  
	        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
	        ftp.login(username, password);//登录  
	        reply = ftp.getReplyCode();  
	        if (!FTPReply.isPositiveCompletion(reply)) {  
	            ftp.disconnect();  
	            return success;  
	        }  
	        ftp.changeWorkingDirectory(path);  
	        ftp.storeFile(filename, input);           
	          
	        input.close();  
	        ftp.logout();  
	        success = true;  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (ftp.isConnected()) {  
	            try {  
	                ftp.disconnect();  
	            } catch (IOException ioe) {  
	            }  
	        }  
	    }  
	    return success;  
	}

	public static boolean sshDownloadFtp(String ip, String user, String psw, int port, String sname, String sPath, String dPath) {
		//LOG.info("password login");
		Session session = null;
		JSch jsch = new JSch();
		boolean flag = true;
		try {
			session = jsch.getSession(user, ip, port);
			if (session == null) {
				throw new Exception("session is null");
			}
			session.setPassword(psw);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect(time *60 *1000);
			flag =	downloadFileFromRemoteToLocal(session, sname, sPath, dPath);
		} catch (Exception e) {
			flag = false;
			LOG.error("message:"+e.getMessage());
		}
		return flag;
	}

	private static boolean downloadFileFromRemoteToLocal(Session session, String sname, String sPath, String dPath) {

		Channel channel = null;
		boolean flag = true;
		try {
			channel = (Channel) session.openChannel("sftp");
			channel.connect(10000000);
			ChannelSftp sftp = (ChannelSftp) channel;
			try {
				sftp.cd(sPath);
			} catch (SftpException e) {
				sftp.mkdir(dPath);
				sftp.cd(dPath);
			}
			File file = new File(dPath + sname);
			flag =	copyFileFromRemoteToLocal(sftp, sname, file, sftp.pwd());
		} catch (Exception e) {
			flag = false;
			LOG.error("message:"+e.getMessage());
		} finally {
			if (null != session) {
				session.disconnect();
			}
			if (null != channel) {
				channel.disconnect();
			}
		}
		return flag;
	}

	private static boolean copyFileFromRemoteToLocal(ChannelSftp sftp, String sname, File file, String pwd) {
		boolean flag = true;
		try {
			sftp.cd(pwd);
			//LOG.info(pwd);
		} catch (SftpException e1) {
			flag = false;
			e1.printStackTrace();
		}
		InputStream instream = null;
		OutputStream outstream = null;
		try {
			instream = sftp.get(sname);
			outstream = new FileOutputStream(file);

			byte b[] = new byte[1024];
			int n;
			// outstream.write(b);
			try {
				while ((n = instream.read(b)) != -1) {
					outstream.write(b, 0, n);
				}
			} catch (IOException e) {
				flag = false;
			}
		} catch (SftpException e) {
			flag = false;
			LOG.error("message:"+e.getMessage());
		} catch (IOException e) {
			flag = false;
			LOG.error("message:"+e.getMessage());
		} finally {
			try {
				if (null != outstream) {
					outstream.flush();
					outstream.close();
				}
				if (null != instream) {
					instream.close();
				}

			} catch (Exception e2) {
				flag = false;
				LOG.error("message:"+e2.getMessage());
			}
		}

		return flag;
	}

}
