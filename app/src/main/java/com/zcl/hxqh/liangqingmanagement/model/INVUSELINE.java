package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 实际领用
 */
public class INVUSELINE extends Entity {
    public String INVUSENUM;//编号
    public String ITEMNUM;//项目
    public String ROTASSETNUM;//移动设备编号
    public String ROTASSETNUMDESC;//移动设备描述
    public String ISSUETO;//借用人编号
    public String ISSUETONAME;//借用人名称
    public String LOCATION;//位置编号
    public String LOCATIONDESC;//位置描述
    public String ACTUALDATE;//实际日期

    public String getINVUSENUM() {
        return INVUSENUM;
    }

    public void setINVUSENUM(String INVUSENUM) {
        this.INVUSENUM = INVUSENUM;
    }

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getROTASSETNUM() {
        return ROTASSETNUM;
    }

    public void setROTASSETNUM(String ROTASSETNUM) {
        this.ROTASSETNUM = ROTASSETNUM;
    }

    public String getROTASSETNUMDESC() {
        return ROTASSETNUMDESC;
    }

    public void setROTASSETNUMDESC(String ROTASSETNUMDESC) {
        this.ROTASSETNUMDESC = ROTASSETNUMDESC;
    }

    public String getISSUETO() {
        return ISSUETO;
    }

    public void setISSUETO(String ISSUETO) {
        this.ISSUETO = ISSUETO;
    }

    public String getISSUETONAME() {
        return ISSUETONAME;
    }

    public void setISSUETONAME(String ISSUETONAME) {
        this.ISSUETONAME = ISSUETONAME;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getLOCATIONDESC() {
        return LOCATIONDESC;
    }

    public void setLOCATIONDESC(String LOCATIONDESC) {
        this.LOCATIONDESC = LOCATIONDESC;
    }

    public String getACTUALDATE() {
        return ACTUALDATE;
    }

    public void setACTUALDATE(String ACTUALDATE) {
        this.ACTUALDATE = ACTUALDATE;
    }
}
