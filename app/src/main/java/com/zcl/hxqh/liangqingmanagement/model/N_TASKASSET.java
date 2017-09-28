package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 计划领用
 */
public class N_TASKASSET extends Entity{
    public String ITEMNUM;//设备
    public String MEASUREUNIT;//单位
    public String REMARK;//备注
    public String SUM;//数量
    public String TASKNUM;//领用编号

    public String getITEMNUM() {
        return ITEMNUM;
    }

    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
    }

    public String getMEASUREUNIT() {
        return MEASUREUNIT;
    }

    public void setMEASUREUNIT(String MEASUREUNIT) {
        this.MEASUREUNIT = MEASUREUNIT;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getSUM() {
        return SUM;
    }

    public void setSUM(String SUM) {
        this.SUM = SUM;
    }

    public String getTASKNUM() {
        return TASKNUM;
    }

    public void setTASKNUM(String TASKNUM) {
        this.TASKNUM = TASKNUM;
    }
}
