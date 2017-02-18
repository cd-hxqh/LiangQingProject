package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 送检编号选择
 */
public class N_QCLSAMP extends Entity{
    public String SAMPNUM;//编号
    public String FOODTYPE;//粮食品种
    public String DESCRIPTION;//描述

    public String getSAMPNUM() {
        return SAMPNUM;
    }

    public void setSAMPNUM(String SAMPNUM) {
        this.SAMPNUM = SAMPNUM;
    }

    public String getFOODTYPE() {
        return FOODTYPE;
    }

    public void setFOODTYPE(String FOODTYPE) {
        this.FOODTYPE = FOODTYPE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }
}
