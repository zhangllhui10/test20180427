package com.hui10.app.model.order;

import java.io.Serializable;
import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-10-17 15:41
 **/
public class LotteryOrderDetail implements Serializable {

    private String tradesn;
    /**
     * 订单表ID
     */
    private String orderid;
    /**
     * 投注流⽔号(英泰)
     */
    private String orderno;
    /**
     * 投注成功⾦额（单位：分）
     */
    private int orderamount;
    /**
     * 彩票玩法代码
     */
    private String gamecode;
    /**
     * 投注期号
     */
    private String issuenumber;
    /**
     * 订单投注时间
     */
    private Date ordertime;
    /**
     * 开奖时间
     */
    private Date lotterytime;
    /**
     * 选码⽅式（1机选;2⾃选）
     */
    private int selecttype;
    /**
     * 投注⽅式（乐透型：100单式；101复式；102胆拖。）
     */
    private String bettype;
    /**
     * 倍数
     */
    private int multipl;
    /**
     * 投注号码
     */
    private String codedetail;
    /**
     * 中奖金额
     */
    private int bonus;
    /**
     * 出票状态
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 更新时间
     */
    private Date updatetime;


    public String getTradesn() {
        return tradesn;
    }

    public void setTradesn(String tradesn) {
        this.tradesn = tradesn;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public int getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(int orderamount) {
        this.orderamount = orderamount;
    }

    public String getGamecode() {
        return gamecode;
    }

    public void setGamecode(String gamecode) {
        this.gamecode = gamecode;
    }

    public String getIssuenumber() {
        return issuenumber;
    }

    public void setIssuenumber(String issuenumber) {
        this.issuenumber = issuenumber;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public Date getLotterytime() {
        return lotterytime;
    }

    public void setLotterytime(Date lotterytime) {
        this.lotterytime = lotterytime;
    }

    public int getSelecttype() {
        return selecttype;
    }

    public void setSelecttype(int selecttype) {
        this.selecttype = selecttype;
    }

    public String getBettype() {
        return bettype;
    }

    public void setBettype(String bettype) {
        this.bettype = bettype;
    }

    public int getMultipl() {
        return multipl;
    }

    public void setMultipl(int multipl) {
        this.multipl = multipl;
    }

    public String getCodedetail() {
        return codedetail;
    }

    public void setCodedetail(String codedetail) {
        this.codedetail = codedetail;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
