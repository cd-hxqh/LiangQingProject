package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 调车作业指令单
 */
public class N_INSTRUCSTASK extends Entity {
    public String ENTRYBY;//录入人
    public String ENTRYDATE;//录入时间
    public String INSTRUCNUM;//指令单编号
    public String STATUS;//状态

    public String getENTRYBY() {
        return ENTRYBY;
    }

    public void setENTRYBY(String ENTRYBY) {
        this.ENTRYBY = ENTRYBY;
    }

    public String getENTRYDATE() {
        return ENTRYDATE;
    }

    public void setENTRYDATE(String ENTRYDATE) {
        this.ENTRYDATE = ENTRYDATE;
    }

    public String getINSTRUCNUM() {
        return INSTRUCNUM;
    }

    public void setINSTRUCNUM(String INSTRUCNUM) {
        this.INSTRUCNUM = INSTRUCNUM;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
}
