package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 车辆作业单
 */
public class N_CARTASK extends Entity{
    public String CARTASKNUM;//作业单号
    public String DESCRIPTION;//描述
    public String CARNO;//车号
    public String TASKTYPE;//作业性质

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
}
