package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 人员
 */
public class PERSON extends Entity{
    public String DISPLAYNAME;//姓名
    public String N_CARDNUM;//一卡通编号
    public String N_IDNUM;//身份证
    public String N_PHONE;//电话
    public String N_WORKTYPE;//工种
    public String TITLE;//头衔
    public String DEPARTMENT;//部门

    public String getDISPLAYNAME() {
        return DISPLAYNAME;
    }

    public void setDISPLAYNAME(String DISPLAYNAME) {
        this.DISPLAYNAME = DISPLAYNAME;
    }

    public String getN_CARDNUM() {
        return N_CARDNUM;
    }

    public void setN_CARDNUM(String n_CARDNUM) {
        N_CARDNUM = n_CARDNUM;
    }

    public String getN_IDNUM() {
        return N_IDNUM;
    }

    public void setN_IDNUM(String n_IDNUM) {
        N_IDNUM = n_IDNUM;
    }

    public String getN_PHONE() {
        return N_PHONE;
    }

    public void setN_PHONE(String n_PHONE) {
        N_PHONE = n_PHONE;
    }

    public String getN_WORKTYPE() {
        return N_WORKTYPE;
    }

    public void setN_WORKTYPE(String n_WORKTYPE) {
        N_WORKTYPE = n_WORKTYPE;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getDEPARTMENT() {
        return DEPARTMENT;
    }

    public void setDEPARTMENT(String DEPARTMENT) {
        this.DEPARTMENT = DEPARTMENT;
    }
}
