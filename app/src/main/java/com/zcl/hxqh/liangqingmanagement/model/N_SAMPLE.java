package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 扦样单
 */
public class N_SAMPLE extends Entity{
    public String CARNO;//车号
    public String CARTASKNUM;//车辆作业单号
    public String DESCRIPTION;//样品名称
    public String ENTERBY;//取样人
    public String ENTERDATE;//取样日期
    public String FROMLOC;//取样位置
    public String TRAINYN;//是否火车
    public String N_QCTASKLINENUM;//检验任务编号
    public String OBJ;//扦样对象
    public String LOC;//货位号
    public String SAMPNUM;//送检编号
    public String SAMPLENUM;//样品编号
    public String TYPE;//扦样类型
    public String TAGID;//一卡通号


    public String getLOC() {
        return LOC;
    }

    public void setLOC(String LOC) {
        this.LOC = LOC;
    }

    public String getTAGID() {
        return TAGID;
    }

    public void setTAGID(String TAGID) {
        this.TAGID = TAGID;
    }

    public String getTRAINYN() {
        return TRAINYN;
    }

    public void setTRAINYN(String TRAINYN) {
        this.TRAINYN = TRAINYN;
    }

    public String getCARNO() {
        return CARNO;
    }

    public void setCARNO(String CARNO) {
        this.CARNO = CARNO;
    }

    public String getCARTASKNUM() {
        return CARTASKNUM;
    }

    public void setCARTASKNUM(String CARTASKNUM) {
        this.CARTASKNUM = CARTASKNUM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getENTERBY() {
        return ENTERBY;
    }

    public void setENTERBY(String ENTERBY) {
        this.ENTERBY = ENTERBY;
    }

    public String getENTERDATE() {
        return ENTERDATE;
    }

    public void setENTERDATE(String ENTERDATE) {
        this.ENTERDATE = ENTERDATE;
    }

    public String getFROMLOC() {
        return FROMLOC;
    }

    public void setFROMLOC(String FROMLOC) {
        this.FROMLOC = FROMLOC;
    }

    public String getN_QCTASKLINENUM() {
        return N_QCTASKLINENUM;
    }

    public void setN_QCTASKLINENUM(String n_QCTASKLINENUM) {
        N_QCTASKLINENUM = n_QCTASKLINENUM;
    }

    public String getOBJ() {
        return OBJ;
    }

    public void setOBJ(String OBJ) {
        this.OBJ = OBJ;
    }

    public String getSAMPLENUM() {
        return SAMPLENUM;
    }

    public void setSAMPLENUM(String SAMPLENUM) {
        this.SAMPLENUM = SAMPLENUM;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getSAMPNUM() {
        return SAMPNUM;
    }

    public void setSAMPNUM(String SAMPNUM) {
        this.SAMPNUM = SAMPNUM;
    }
}
