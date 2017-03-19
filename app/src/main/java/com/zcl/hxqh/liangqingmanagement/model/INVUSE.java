package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 移动设备借用
 */
public class INVUSE extends Entity{
    public String INVUSENUM;//编号
    public String DESCRIPTION;//描述
    public String FROMSTORELOC;//库房
    public String LOCATIONSDESC;//库房描述
    public String PRODUCTIONPLANSNUM;//计划编号
    public String PLANDESC;//计划描述
    public String ENTERBY;//录入人
    public String ENTERDATE;//录入时间
    public String STATUS;//状态

    public String getINVUSENUM() {
        return INVUSENUM;
    }

    public void setINVUSENUM(String INVUSENUM) {
        this.INVUSENUM = INVUSENUM;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getFROMSTORELOC() {
        return FROMSTORELOC;
    }

    public void setFROMSTORELOC(String FROMSTORELOC) {
        this.FROMSTORELOC = FROMSTORELOC;
    }

    public String getLOCATIONSDESC() {
        return LOCATIONSDESC;
    }

    public void setLOCATIONSDESC(String LOCATIONSDESC) {
        this.LOCATIONSDESC = LOCATIONSDESC;
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

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getPRODUCTIONPLANSNUM() {
        return PRODUCTIONPLANSNUM;
    }

    public void setPRODUCTIONPLANSNUM(String PRODUCTIONPLANSNUM) {
        this.PRODUCTIONPLANSNUM = PRODUCTIONPLANSNUM;
    }

    public String getPLANDESC() {
        return PLANDESC;
    }

    public void setPLANDESC(String PLANDESC) {
        this.PLANDESC = PLANDESC;
    }
}
