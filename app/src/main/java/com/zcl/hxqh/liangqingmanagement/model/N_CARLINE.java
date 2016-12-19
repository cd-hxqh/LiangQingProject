package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 货运预报
 */
public class N_CARLINE extends Entity{
    public String SN;//序号
    public String CARNO;//车号
    public String ISCONTAINTER;//是否集装箱
    public String NUMBER;//件数（条）
    public String STATION;//发站
    public String START;//开始日期
    public String END;//结束日期
    public String REMARK;//备注
    public String CARNUM;//货运预报编号

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public String getCARNO() {
        return CARNO;
    }

    public void setCARNO(String CARNO) {
        this.CARNO = CARNO;
    }

    public String getISCONTAINTER() {
        return ISCONTAINTER;
    }

    public void setISCONTAINTER(String ISCONTAINTER) {
        this.ISCONTAINTER = ISCONTAINTER;
    }

    public String getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(String NUMBER) {
        this.NUMBER = NUMBER;
    }

    public String getSTATION() {
        return STATION;
    }

    public void setSTATION(String STATION) {
        this.STATION = STATION;
    }

    public String getSTART() {
        return START;
    }

    public void setSTART(String START) {
        this.START = START;
    }

    public String getEND() {
        return END;
    }

    public void setEND(String END) {
        this.END = END;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getCARNUM() {
        return CARNUM;
    }

    public void setCARNUM(String CARNUM) {
        this.CARNUM = CARNUM;
    }
}
