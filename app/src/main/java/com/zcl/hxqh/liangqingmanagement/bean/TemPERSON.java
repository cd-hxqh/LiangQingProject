package com.zcl.hxqh.liangqingmanagement.bean;

import com.zcl.hxqh.liangqingmanagement.model.Entity;

/**
 * Created by think on 2016/7/1.
 * 人员
 */
public class TemPERSON extends Entity {
    public String personid;//编号
    public String displayname;//姓名

    public String getpersonid() {
        return personid;
    }

    public void setpersonid(String personid) {
        this.personid = personid;
    }

    public String getdisplayname() {
        return displayname;
    }

    public void setdisplayname(String displayname) {
        this.displayname = displayname;
    }
}
