package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 选项值
 */
public class ALNDOMAIN extends Entity{
    public String DESCRIPTION;//描述
    public String DOMAINID;//类型
    public String VALUE;//值

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getDOMAINID() {
        return DOMAINID;
    }

    public void setDOMAINID(String DOMAINID) {
        this.DOMAINID = DOMAINID;
    }

    public String getVALUE() {
        return VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }
}
