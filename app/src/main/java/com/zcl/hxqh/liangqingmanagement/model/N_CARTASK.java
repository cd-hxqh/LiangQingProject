package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 车辆作业单
 */
public class N_CARTASK extends Entity{
    public String CARTASKNUM;//作业单号
    public String DESCRIPTION;//描述
    public String CARNO;//车号
    public String TAGID;//一卡通ID
    public String IDCARDNUM;//身份证
    public String INTIME;//进门时间
    public String OUTTIME;//出门时间
    public String TASKTYPE;//作业性质
    public String FOODTYPES;//品种
    public String LOC;//作业货位号
    public String N_CAR_THDW;//收货/提货单位

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

    public String getCARNO() {
        return CARNO;
    }

    public void setCARNO(String CARNO) {
        this.CARNO = CARNO;
    }

    public String getTASKTYPE() {
        return TASKTYPE;
    }

    public void setTASKTYPE(String TASKTYPE) {
        this.TASKTYPE = TASKTYPE;
    }

    public String getIDCARDNUM() {
        return IDCARDNUM;
    }

    public void setIDCARDNUM(String IDCARDNUM) {
        this.IDCARDNUM = IDCARDNUM;
    }

    public String getINTIME() {
        return INTIME;
    }

    public void setINTIME(String INTIME) {
        this.INTIME = INTIME;
    }

    public String getOUTTIME() {
        return OUTTIME;
    }

    public void setOUTTIME(String OUTTIME) {
        this.OUTTIME = OUTTIME;
    }

    public String getFOODTYPES() {
        return FOODTYPES;
    }

    public void setFOODTYPES(String FOODTYPES) {
        this.FOODTYPES = FOODTYPES;
    }

    public String getLOC() {
        return LOC;
    }

    public void setLOC(String LOC) {
        this.LOC = LOC;
    }

    public String getN_CAR_THDW() {
        return N_CAR_THDW;
    }

    public void setN_CAR_THDW(String n_CAR_THDW) {
        N_CAR_THDW = n_CAR_THDW;
    }

    public String getTAGID() {
        return TAGID;
    }

    public void setTAGID(String TAGID) {
        this.TAGID = TAGID;
    }
}
