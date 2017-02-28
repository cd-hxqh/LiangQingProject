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
    public String ACTUALQTY;//数量
    public String ACTUALQC1;//水分
    public String ACTUALQC2;//杂质含量
    public String ACTUALQC3;//不完善含量%
    public String ACTUALQC4;//容重g/l%
    public String ACTUALQC5;//生霉含量%
    public String ACTUALQC6;//脂肪酸值
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

    public String getACTUALQTY() {
        return ACTUALQTY;
    }

    public void setACTUALQTY(String ACTUALQTY) {
        this.ACTUALQTY = ACTUALQTY;
    }

    public String getACTUALQC1() {
        return ACTUALQC1;
    }

    public void setACTUALQC1(String ACTUALQC1) {
        this.ACTUALQC1 = ACTUALQC1;
    }

    public String getACTUALQC2() {
        return ACTUALQC2;
    }

    public void setACTUALQC2(String ACTUALQC2) {
        this.ACTUALQC2 = ACTUALQC2;
    }

    public String getACTUALQC3() {
        return ACTUALQC3;
    }

    public void setACTUALQC3(String ACTUALQC3) {
        this.ACTUALQC3 = ACTUALQC3;
    }

    public String getACTUALQC4() {
        return ACTUALQC4;
    }

    public void setACTUALQC4(String ACTUALQC4) {
        this.ACTUALQC4 = ACTUALQC4;
    }

    public String getACTUALQC5() {
        return ACTUALQC5;
    }

    public void setACTUALQC5(String ACTUALQC5) {
        this.ACTUALQC5 = ACTUALQC5;
    }

    public String getACTUALQC6() {
        return ACTUALQC6;
    }

    public void setACTUALQC6(String ACTUALQC6) {
        this.ACTUALQC6 = ACTUALQC6;
    }
}
