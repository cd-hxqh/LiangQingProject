package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 车皮跟踪
 */
public class N_WAGONS extends Entity{
    public String STATUS;//状态
    public String WAGONNUM;//车号

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getWAGONNUM() {
        return WAGONNUM;
    }

    public void setWAGONNUM(String WAGONNUM) {
        this.WAGONNUM = WAGONNUM;
    }
}
