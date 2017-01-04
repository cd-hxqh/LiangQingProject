package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 用工记录
 */
public class WTLABOR extends Entity{
    public String APPLYNUM;//用工申请编号
    public String CARDNUM;//一卡通ID
    public String STARTTIME;//开始时间
    public String ENDTIME;//结束时间
    public String LABORCODE;//人员编号
    public String PLACE;//工作地点
    public String STATUS;//状态
    public String TASKTYPE;//作业类型

    public String getAPPLYNUM() {
        return APPLYNUM;
    }

    public void setAPPLYNUM(String APPLYNUM) {
        this.APPLYNUM = APPLYNUM;
    }

    public String getCARDNUM() {
        return CARDNUM;
    }

    public void setCARDNUM(String CARDNUM) {
        this.CARDNUM = CARDNUM;
    }

    public String getSTARTTIME() {
        return STARTTIME;
    }

    public void setSTARTTIME(String STARTTIME) {
        this.STARTTIME = STARTTIME;
    }

    public String getENDTIME() {
        return ENDTIME;
    }

    public void setENDTIME(String ENDTIME) {
        this.ENDTIME = ENDTIME;
    }

    public String getLABORCODE() {
        return LABORCODE;
    }

    public void setLABORCODE(String LABORCODE) {
        this.LABORCODE = LABORCODE;
    }

    public String getPLACE() {
        return PLACE;
    }

    public void setPLACE(String PLACE) {
        this.PLACE = PLACE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getTASKTYPE() {
        return TASKTYPE;
    }

    public void setTASKTYPE(String TASKTYPE) {
        this.TASKTYPE = TASKTYPE;
    }
}
