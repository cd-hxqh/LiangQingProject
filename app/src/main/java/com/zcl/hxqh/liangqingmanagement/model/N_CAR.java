package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 货运预报
 */
public class N_CAR extends Entity{
    public String CARNUM;//单号
    public String PLANNUM;//仓储作业计划编号
    public String DESCRIPTION;//描述
    public String FROMSTATION;//收货/提货单位
    public String PROVINCE;//省份
    public String FOODTYPES;//品种
    public String THEAMOUNTOFMONEY;//量衡
    public String CARTYPE;//车辆类型
    public String PLANTOTAL;//计划总量
    public String PLANSTATUS;//作业类型
    public String ENTERDATE;//创建日期
    public String ENTERBY;//创建人
    public String STATUS;//状态

    public String getCARNUM() {
        return CARNUM;
    }

    public void setCARNUM(String CARNUM) {
        this.CARNUM = CARNUM;
    }

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

    public String getFROMSTATION() {
        return FROMSTATION;
    }

    public void setFROMSTATION(String FROMSTATION) {
        this.FROMSTATION = FROMSTATION;
    }

    public String getPROVINCE() {
        return PROVINCE;
    }

    public void setPROVINCE(String PROVINCE) {
        this.PROVINCE = PROVINCE;
    }

    public String getFOODTYPES() {
        return FOODTYPES;
    }

    public void setFOODTYPES(String FOODTYPES) {
        this.FOODTYPES = FOODTYPES;
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

    public String getPLANTOTAL() {
        return PLANTOTAL;
    }

    public void setPLANTOTAL(String PLANTOTAL) {
        this.PLANTOTAL = PLANTOTAL;
    }

    public String getPLANSTATUS() {
        return PLANSTATUS;
    }

    public void setPLANSTATUS(String PLANSTATUS) {
        this.PLANSTATUS = PLANSTATUS;
    }

    public String getENTERDATE() {
        return ENTERDATE;
    }

    public void setENTERDATE(String ENTERDATE) {
        this.ENTERDATE = ENTERDATE;
    }

    public String getENTERBY() {
        return ENTERBY;
    }

    public void setENTERBY(String ENTERBY) {
        this.ENTERBY = ENTERBY;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
}
