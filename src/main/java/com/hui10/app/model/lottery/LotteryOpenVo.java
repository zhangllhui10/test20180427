package com.hui10.app.model.lottery;

import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-11-09 10:50
 **/
public class LotteryOpenVo {

    /**
     * 彩票代号
     */
    private String gamecode;

    /**
     * 开奖日期
     */
    private Date opendate;
    /**
     * 周几
     */
    private String week;
    /**
     *true :是 false ：不是
     */
    private boolean flag;


    public String getGamecode() {
        return gamecode;
    }

    public void setGamecode(String gamecode) {
        this.gamecode = gamecode;
    }

    public Date getOpendate() {
        return opendate;
    }

    public void setOpendate(Date opendate) {
        this.opendate = opendate;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
