package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 指令单行表
 */
public class N_INSTRUCTIONS extends Entity {
    public String ORDERNUM;//顺号
    public String THROUGH;//经由
    public String LINE;//线别
    public String WAGONSG;//车数(挂)
    public String WAGONSZ;//车数(摘)
    public String MEMO;//记事

    public String getORDERNUM() {
        return ORDERNUM;
    }

    public void setORDERNUM(String ORDERNUM) {
        this.ORDERNUM = ORDERNUM;
    }

    public String getTHROUGH() {
        return THROUGH;
    }

    public void setTHROUGH(String THROUGH) {
        this.THROUGH = THROUGH;
    }

    public String getLINE() {
        return LINE;
    }

    public void setLINE(String LINE) {
        this.LINE = LINE;
    }

    public String getWAGONSG() {
        return WAGONSG;
    }

    public void setWAGONSG(String WAGONSG) {
        this.WAGONSG = WAGONSG;
    }

    public String getWAGONSZ() {
        return WAGONSZ;
    }

    public void setWAGONSZ(String WAGONSZ) {
        this.WAGONSZ = WAGONSZ;
    }

    public String getMEMO() {
        return MEMO;
    }

    public void setMEMO(String MEMO) {
        this.MEMO = MEMO;
    }
}
