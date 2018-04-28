package com.hui10.app.model.order;

import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @email mudouyu@aliyun.com
 * @create 2018-04-17 16:25
 **/
public class OrderInfoDto {


    private Date ordertime;
    private String orderno;
    private String outtradeno;
    private int source;
    private String gamecode;
    private Long orderamount;
    private String merchantno;

    private String stationno;

    public String getStationno() {
        return stationno;
    }

    public void setStationno(String stationno) {
        this.stationno = stationno;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getOuttradeno() {
        return outtradeno;
    }

    public void setOuttradeno(String outtradeno) {
        this.outtradeno = outtradeno;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getGamecode() {
        return gamecode;
    }

    public void setGamecode(String gamecode) {
        this.gamecode = gamecode;
    }

    public Long getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(Long orderamount) {
        this.orderamount = orderamount;
    }

    public String getMerchantno() {
        return merchantno;
    }

    public void setMerchantno(String merchantno) {
        this.merchantno = merchantno;
    }
}
