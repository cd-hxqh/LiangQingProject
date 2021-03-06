package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/12/1.
 * 消缺工单
 */
public class WORKORDER extends Entity{
    public String SB;//设备
    public String SBWZ;//设备位置
    public String DESCRIPTION;//缺陷描述
    public String FXBM;//发现部门
    public String FXR;//发现人
    public String REPORTDATE;//发现时间
    public String ZRR;//责任人
    public String SCHEDFINISH;//整改期限
    public String N_RECREQ;//整改要求
    public String WORKTYPE;//是否排查
    public String REMARKDESC;//备注

    public String WONUM;//工单编号
    public String N_REGION;//区域
    public String REPORTEDBY;//报告人
    public String STATUS;//状态
    public String WORKORDERID;//唯一编号


    public String getSB() {
        return SB;
    }

    public void setSB(String SB) {
        this.SB = SB;
    }

    public String getSBWZ() {
        return SBWZ;
    }

    public void setSBWZ(String SBWZ) {
        this.SBWZ = SBWZ;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getFXBM() {
        return FXBM;
    }

    public void setFXBM(String FXBM) {
        this.FXBM = FXBM;
    }

    public String getFXR() {
        return FXR;
    }

    public void setFXR(String FXR) {
        this.FXR = FXR;
    }

    public String getREPORTDATE() {
        return REPORTDATE;
    }

    public void setREPORTDATE(String REPORTDATE) {
        this.REPORTDATE = REPORTDATE;
    }

    public String getZRR() {
        return ZRR;
    }

    public void setZRR(String ZRR) {
        this.ZRR = ZRR;
    }

    public String getSCHEDFINISH() {
        return SCHEDFINISH;
    }

    public void setSCHEDFINISH(String SCHEDFINISH) {
        this.SCHEDFINISH = SCHEDFINISH;
    }

    public String getN_RECREQ() {
        return N_RECREQ;
    }

    public void setN_RECREQ(String n_RECREQ) {
        N_RECREQ = n_RECREQ;
    }

    public String getWORKTYPE() {
        return WORKTYPE;
    }

    public void setWORKTYPE(String WORKTYPE) {
        this.WORKTYPE = WORKTYPE;
    }

    public String getREMARKDESC() {
        return REMARKDESC;
    }

    public void setREMARKDESC(String REMARKDESC) {
        this.REMARKDESC = REMARKDESC;
    }

    public String getWONUM() {
        return WONUM;
    }

    public void setWONUM(String WONUM) {
        this.WONUM = WONUM;
    }

    public String getN_REGION() {
        return N_REGION;
    }

    public void setN_REGION(String n_REGION) {
        N_REGION = n_REGION;
    }

    public String getREPORTEDBY() {
        return REPORTEDBY;
    }

    public void setREPORTEDBY(String REPORTEDBY) {
        this.REPORTEDBY = REPORTEDBY;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getWORKORDERID() {
        return WORKORDERID;
    }

    public void setWORKORDERID(String WORKORDERID) {
        this.WORKORDERID = WORKORDERID;
    }
}
