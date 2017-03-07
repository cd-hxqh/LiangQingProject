package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 验收标准与评分
 */
public class N_LABAPYRULE extends Entity{
    public String SN;//序号
    public String ITEM;//项目
    public String SCORE;//评分
    public String RULE;//参考标准
    public String LABAPYNUM;//用工编号

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public String getITEM() {
        return ITEM;
    }

    public void setITEM(String ITEM) {
        this.ITEM = ITEM;
    }

    public String getSCORE() {
        return SCORE;
    }

    public void setSCORE(String SCORE) {
        this.SCORE = SCORE;
    }

    public String getRULE() {
        return RULE;
    }

    public void setRULE(String RULE) {
        this.RULE = RULE;
    }

    public String getLABAPYNUM() {
        return LABAPYNUM;
    }

    public void setLABAPYNUM(String LABAPYNUM) {
        this.LABAPYNUM = LABAPYNUM;
    }
}
