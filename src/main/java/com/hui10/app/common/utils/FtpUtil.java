package com.hui10.app.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-11-01 9:51
 **/
public class FtpUtil extends com.hui10.app.common.lottery.FtpUtil {


    /**
     * 密码方式登录
     *
     * @param ip
     * @param user
     * @param psw
     * @param port
     * @param sPath
     * @param dPath
     */
    public static void sshSftp(String ip, String user, String psw, int port,
                               String sPath, String dPath) {
        System.out.println("password login");
        Session session = null;
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(user, ip, port);
            if (session == null) {
                throw new Exception("session is null");
            }
            session.setPassword(psw);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(300000);
            upLoadFile(session, sPath, dPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("success");
    }


    private static void upLoadFile(Session session, String sPath, String dPath) {

        Channel channel = null;
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
            File file = new File(dPath);
            copyFile(sftp, file, sftp.pwd());
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            session.disconnect();
            channel.disconnect();
        }
    }

    private static void copyFile(ChannelSftp sftp, File file, String pwd) {
        try {
            sftp.cd(pwd);
        } catch (SftpException e1) {
            e1.printStackTrace();
        }
        InputStream instream = null;
        OutputStream outstream = null;
        try {
            outstream = sftp.put(file.getName());
            instream = new FileInputStream(file);

            byte b[] = new byte[1024];
            int n;
            try {
                while ((n = instream.read(b)) != -1) {
                    outstream.write(b, 0, n);
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        } catch (SftpException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                outstream.flush();
                outstream.close();
                instream.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


}
