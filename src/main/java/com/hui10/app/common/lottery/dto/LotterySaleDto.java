package com.hui10.app.common.lottery.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-10-19 10:31
 **/
public class LotterySaleDto implements Serializable{

    private String issuenumber;
    private String gamecode;
    private Date lotterystarttime;
    private Date lotteryendtime;


    public String getIssuenumber() {
        return issuenumber;
    }

    public void setIssuenumber(String issuenumber) {
        this.issuenumber = issuenumber;
    }

    public String getGamecode() {
        return gamecode;
    }

    public void setGamecode(String gamecode) {
        this.gamecode = gamecode;
    }

    public Date getLotterystarttime() {
        return lotterystarttime;
    }

    public void setLotterystarttime(Date lotterystarttime) {
        this.lotterystarttime = lotterystarttime;
    }

    public Date getLotteryendtime() {
        return lotteryendtime;
    }

    public void setLotteryendtime(Date lotteryendtime) {
        this.lotteryendtime = lotteryendtime;
    }
}
