package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 生产计划
 */
public class N_PRODUCTIONPLANS extends Entity{
    public String PRODUCTIONPLANSNUM;//编号
    public String TASKNUM;//任务编号

    public String getPRODUCTIONPLANSNUM() {
        return PRODUCTIONPLANSNUM;
    }

    public void setPRODUCTIONPLANSNUM(String PRODUCTIONPLANSNUM) {
        this.PRODUCTIONPLANSNUM = PRODUCTIONPLANSNUM;
    }

    public String getTASKNUM() {
        return TASKNUM;
    }

    public void setTASKNUM(String TASKNUM) {
        this.TASKNUM = TASKNUM;
    }
}
