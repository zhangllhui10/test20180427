package com.hui10.app.model.main;

import java.util.Date;

/**
 * @author fanht
 * @ClassName:
 * @Description:
 * @date 2018年03月06日 16:20
 */
public class HomeLottery {

    /**
     * 注数
     */
    private int betnumber;
    /**
     * 注码详情
     */
    private String codedetail;
    /**
     * 未领取的彩票失效时间
     */
    private Date expiretime;
    /**
     * 期号
     */
    private String issuenumber;
    /**
     * 彩票类型
     */
    private String lotterytype;
    /**
     * 投注截至时间
     */
    private Date lotterytime;
    /**
     * 开奖时间
     */
    private Date opentime;
    /**
     * 订单号
     */
    private String orderid;
    /**
     * 投注时间
     */
    private Date ordertime;
    /**
     * 状态：0待领取 1待支付 2待开奖 3已中奖
     */
    private int status;
    /**
     * 中奖金额
     */
    private long winprize;
    /**
     * 是否中奖：1未中奖，2中奖
     */
    private String winstatus;
    /**
     * 领奖银行卡号
     */
    private String bankcardno;
    /**
     * 开奖状态：0--未开奖，1--已开奖
     */
    private String lotterystatus;
    /**
     * 订单类型：0--正常，1--赠送
     */
    private String ordertype;
    /**
     * 彩金领取：1-未领取 2-已领取  3-领奖处理中   4-领取失败    5-证件审核中   6-证件审核失败 7- 证件审核成功 8-线下领奖
     */
    private String bonusstatus;
    /**
     * 投注json
     */
    private String remark;
    /**
     * 活动名称
     */
    private String marketingname;
    /**
     * 1小奖 2中奖 3大奖
     */
    private String prizelevel;

    public int getBetnumber() {
        return betnumber;
    }

    public void setBetnumber(int betnumber) {
        this.betnumber = betnumber;
    }

    public String getCodedetail() {
        return codedetail;
    }

    public void setCodedetail(String codedetail) {
        this.codedetail = codedetail;
    }

    public Date getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(Date expiretime) {
        this.expiretime = expiretime;
    }

    public String getIssuenumber() {
        return issuenumber;
    }

    public void setIssuenumber(String issuenumber) {
        this.issuenumber = issuenumber;
    }

    public String getLotterytype() {
        return lotterytype;
    }

    public void setLotterytype(String lotterytype) {
        this.lotterytype = lotterytype;
    }

    public Date getLotterytime() {
        return lotterytime;
    }

    public void setLotterytime(Date lotterytime) {
        this.lotterytime = lotterytime;
    }

    public Date getOpentime() {
        return opentime;
    }

    public void setOpentime(Date opentime) {
        this.opentime = opentime;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getWinprize() {
        return winprize;
    }

    public void setWinprize(long winprize) {
        this.winprize = winprize;
    }

    public String getWinstatus() {
        return winstatus;
    }

    public void setWinstatus(String winstatus) {
        this.winstatus = winstatus;
    }

    public String getBankcardno() {
        return bankcardno;
    }

    public void setBankcardno(String bankcardno) {
        this.bankcardno = bankcardno;
    }

    public String getLotterystatus() {
        return lotterystatus;
    }

    public void setLotterystatus(String lotterystatus) {
        this.lotterystatus = lotterystatus;
    }

    public String getBonusstatus() {
        return bonusstatus;
    }

    public void setBonusstatus(String bonusstatus) {
        this.bonusstatus = bonusstatus;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMarketingname() {
        return marketingname;
    }

    public void setMarketingname(String marketingname) {
        this.marketingname = marketingname;
    }

    public String getPrizelevel() {
        return prizelevel;
    }

    public void setPrizelevel(String prizelevel) {
        this.prizelevel = prizelevel;
    }
}
