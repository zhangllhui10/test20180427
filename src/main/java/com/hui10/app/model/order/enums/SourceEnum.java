package com.hui10.app.model.order.enums;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-10-22 10:17
 **/
public enum SourceEnum {


    POS(1,"POS机订单"),APP(2,"APP订单"),H5(3,"H5订单");


    private int state;
    private String desc;
    SourceEnum(int state, String desc) {
        this.state = state;
        this.desc = desc;

    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
