package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 仓储作业计划
 */
public class N_TASKPLAN extends Entity{
    public String PLANNUM;//计划编号
    public String DESCRIPTION;//描述
    public String STATION;//收货/提货单位
    public String TYPE;//品种
    public String THEAMOUNTOFMONEY;//量衡
    public String CARTYPE;//车辆类型

    public String getPLANNUM() {
        return PLANNUM;
    }

    public void setPLANNUM(String PLANNUM) {
        this.PLANNUM = PLANNUM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getSTATION() {
        return STATION;
    }

    public void setSTATION(String STATION) {
        this.STATION = STATION;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getTHEAMOUNTOFMONEY() {
        return THEAMOUNTOFMONEY;
    }

    public void setTHEAMOUNTOFMONEY(String THEAMOUNTOFMONEY) {
        this.THEAMOUNTOFMONEY = THEAMOUNTOFMONEY;
    }

    public String getCARTYPE() {
        return CARTYPE;
    }

    public void setCARTYPE(String CARTYPE) {
        this.CARTYPE = CARTYPE;
    }
}
