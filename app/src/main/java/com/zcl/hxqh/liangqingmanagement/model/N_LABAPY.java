package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 用工验收
 */
public class N_LABAPY extends Entity{
    public String N_LABAPYNUM;//编号
    public String PLACE;//工作地点
    public String DETAILS;//工作内容
    public String AMCREWBZ;//班组
    public String WORKLOAD;//工作量
    public String LINKMAN;//用工人
    public String YSSTART;//实际开始时间
    public String YSEND;//实际结束时间
    public String STATUS;//状态

    public String getN_LABAPYNUM() {
        return N_LABAPYNUM;
    }

    public void setN_LABAPYNUM(String n_LABAPYNUM) {
        N_LABAPYNUM = n_LABAPYNUM;
    }

    public String getPLACE() {
        return PLACE;
    }

    public void setPLACE(String PLACE) {
        this.PLACE = PLACE;
    }

    public String getDETAILS() {
        return DETAILS;
    }

    public void setDETAILS(String DETAILS) {
        this.DETAILS = DETAILS;
    }

    public String getAMCREWBZ() {
        return AMCREWBZ;
    }

    public void setAMCREWBZ(String AMCREWBZ) {
        this.AMCREWBZ = AMCREWBZ;
    }

    public String getWORKLOAD() {
        return WORKLOAD;
    }

    public void setWORKLOAD(String WORKLOAD) {
        this.WORKLOAD = WORKLOAD;
    }

    public String getLINKMAN() {
        return LINKMAN;
    }

    public void setLINKMAN(String LINKMAN) {
        this.LINKMAN = LINKMAN;
    }

    public String getYSSTART() {
        return YSSTART;
    }

    public void setYSSTART(String YSSTART) {
        this.YSSTART = YSSTART;
    }

    public String getYSEND() {
        return YSEND;
    }

    public void setYSEND(String YSEND) {
        this.YSEND = YSEND;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
}
