package com.hui10.app.common.lottery.dto;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-10-18 11:23
 **/
public class BetRequest {
    /**
     * 选码⽅式（1机选；2⾃选）
     */
    private int selectType;
    /**
     * 投注⽅式
     */
    private String betType;
    /**
     * 投注倍数
     */
    private int multiple;
    /**
     * 双⾊球：单/复式投注：按红球
     * 区|蓝球区给出；红球区、蓝球区
     * 内各号码⽤“，”分割，球号采⽤
     * 2位数球号；胆拖投注：按红球
     * 胆码|红球拖码|蓝球拖码给出，
     * 各区内号码之间⽤“,”分割，球号
     * 采⽤2位数球号。
     */
    private String codeDetail;


    public int getSelectType() {
        return selectType;
    }

    public void setSelectType(int selectType) {
        this.selectType = selectType;
    }

    public String getBetType() {
        return betType;
    }

    public void setBetType(String betType) {
        this.betType = betType;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public String getCodeDetail() {
        return codeDetail;
    }

    public void setCodeDetail(String codeDetail) {
        this.codeDetail = codeDetail;
    }
}
