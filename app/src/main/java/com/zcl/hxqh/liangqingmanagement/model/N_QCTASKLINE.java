package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 检验任务编号
 */
public class N_QCTASKLINE extends Entity{
    public String CHECKER;//检验人
    public String DESCRIPTION;//描述
    public String N_QCTASKLINENUM;//编号
    public String PRIORTY;//

    public String getCHECKER() {
        return CHECKER;
    }

    public void setCHECKER(String CHECKER) {
        this.CHECKER = CHECKER;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getN_QCTASKLINENUM() {
        return N_QCTASKLINENUM;
    }

    public void setN_QCTASKLINENUM(String n_QCTASKLINENUM) {
        N_QCTASKLINENUM = n_QCTASKLINENUM;
    }

    public String getPRIORTY() {
        return PRIORTY;
    }

    public void setPRIORTY(String PRIORTY) {
        this.PRIORTY = PRIORTY;
    }
}
