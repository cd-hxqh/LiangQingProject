package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 保管员货位对照表
 */
public class N_STOREINFO extends Entity{
    public String LOC;//新货位号
    public String HOLDERNAME;//名称
    public String OLDLOC;//原货位号
    public String HOLDER;//保管员编号


    public String getLOC() {
        return LOC;
    }

    public void setLOC(String LOC) {
        this.LOC = LOC;
    }

    public String getHOLDERNAME() {
        return HOLDERNAME;
    }

    public void setHOLDERNAME(String HOLDERNAME) {
        this.HOLDERNAME = HOLDERNAME;
    }

    public String getOLDLOC() {
        return OLDLOC;
    }

    public void setOLDLOC(String OLDLOC) {
        this.OLDLOC = OLDLOC;
    }

    public String getHOLDER() {
        return HOLDER;
    }

    public void setHOLDER(String HOLDER) {
        this.HOLDER = HOLDER;
    }
}
