package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 出入仓告知记录
 */
public class N_FOODJOB extends Entity{
    public String AQXM;//安全告知项目
    public String CHIEF;//带班负责人
    public String CHIEFDISPLAYNAME;//带班负责人名称
    public String CONTENT;//作业内容
    public String FOODJOBNUM;//编号
    public String HOLDER;//现场保管员
    public String HOLDERDISPLAYNAME;//现场保管员名称
    public String LOC;//货位号
    public String REPORTDATE;//作业日期
    public String SAFER;//安全员
    public String SAFERDISPLAYNAME;//安全员名称
    public String TELL;//告知人
    public String TELLDISPLAYNAME;//被告知人名称
    public String TELLER;//被告知人
    public String REMARK;//安全检查项目

    public String getAQXM() {
        return AQXM;
    }

    public void setAQXM(String AQXM) {
        this.AQXM = AQXM;
    }

    public String getCHIEF() {
        return CHIEF;
    }

    public void setCHIEF(String CHIEF) {
        this.CHIEF = CHIEF;
    }

    public String getCHIEFDISPLAYNAME() {
        return CHIEFDISPLAYNAME;
    }

    public void setCHIEFDISPLAYNAME(String CHIEFDISPLAYNAME) {
        this.CHIEFDISPLAYNAME = CHIEFDISPLAYNAME;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getFOODJOBNUM() {
        return FOODJOBNUM;
    }

    public void setFOODJOBNUM(String FOODJOBNUM) {
        this.FOODJOBNUM = FOODJOBNUM;
    }

    public String getHOLDER() {
        return HOLDER;
    }

    public void setHOLDER(String HOLDER) {
        this.HOLDER = HOLDER;
    }

    public String getHOLDERDISPLAYNAME() {
        return HOLDERDISPLAYNAME;
    }

    public void setHOLDERDISPLAYNAME(String HOLDERDISPLAYNAME) {
        this.HOLDERDISPLAYNAME = HOLDERDISPLAYNAME;
    }

    public String getLOC() {
        return LOC;
    }

    public void setLOC(String LOC) {
        this.LOC = LOC;
    }

    public String getREPORTDATE() {
        return REPORTDATE;
    }

    public void setREPORTDATE(String REPORTDATE) {
        this.REPORTDATE = REPORTDATE;
    }

    public String getSAFER() {
        return SAFER;
    }

    public void setSAFER(String SAFER) {
        this.SAFER = SAFER;
    }

    public String getSAFERDISPLAYNAME() {
        return SAFERDISPLAYNAME;
    }

    public void setSAFERDISPLAYNAME(String SAFERDISPLAYNAME) {
        this.SAFERDISPLAYNAME = SAFERDISPLAYNAME;
    }

    public String getTELL() {
        return TELL;
    }

    public void setTELL(String TELL) {
        this.TELL = TELL;
    }

    public String getTELLDISPLAYNAME() {
        return TELLDISPLAYNAME;
    }

    public void setTELLDISPLAYNAME(String TELLDISPLAYNAME) {
        this.TELLDISPLAYNAME = TELLDISPLAYNAME;
    }

    public String getTELLER() {
        return TELLER;
    }

    public void setTELLER(String TELLER) {
        this.TELLER = TELLER;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }
}
