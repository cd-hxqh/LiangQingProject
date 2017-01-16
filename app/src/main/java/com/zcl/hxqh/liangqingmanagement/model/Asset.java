package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 设备
 */
public class Asset extends Entity{
    public String N_MODELNUM;//设备
    public String ASSETNUM;//设备编号
    public String N_LOCANAME;//设备位置

    public String getN_MODELNUM() {
        return N_MODELNUM;
    }

    public void setN_MODELNUM(String n_MODELNUM) {
        N_MODELNUM = n_MODELNUM;
    }

    public String getN_LOCANAME() {
        return N_LOCANAME;
    }

    public void setN_LOCANAME(String n_LOCANAME) {
        N_LOCANAME = n_LOCANAME;
    }

    public String getASSETNUM() {
        return ASSETNUM;
    }

    public void setASSETNUM(String ASSETNUM) {
        this.ASSETNUM = ASSETNUM;
    }
}
