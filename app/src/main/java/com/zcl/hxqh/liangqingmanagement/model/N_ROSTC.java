package com.zcl.hxqh.liangqingmanagement.model;

/**
 * Created by think on 2016/7/1.
 * 风雨雪三查
 */
public class N_ROSTC extends Entity{
    public String BEFOREDATE;//风雨前日期
    public String ISBEFORDAWCLOSE;//门窗关好(前)
    public String ISBFDRAINAGE;//排水通畅 (前)
    public String ISDRAIN;//水沟通畅(中)
    public String ISINGDAWCLOSE;//门窗关好(中)
    public String ISLEAKAGEOFBARN;//
    public String ISLEAKAGEOFBARNLATE;//仓房无渗漏(后)
    public String LATEDATE;//风雨后日期
    public String LOC;//位置
    public String POSITIONING;//发生部位(风雨中)
    public String POSITIONLATE;//发生部位(风雨后)
    public String ROSTCNUM;//编号
    public String TAKEACTIONING;//采取措施(风雨中)
    public String TAKEACTIONLATE;//采取措施(风雨后)
    public String TEMPERATURE;//温度(保留一位小数)
    public String WEATHER;//天气
    public String WET;//湿度(保留整数)
    public String EXAMINER;//检查人
    public String EXAMINERNAME;//检查人名称

    public String getBEFOREDATE() {
        return BEFOREDATE;
    }

    public void setBEFOREDATE(String BEFOREDATE) {
        this.BEFOREDATE = BEFOREDATE;
    }

    public String getISBEFORDAWCLOSE() {
        return ISBEFORDAWCLOSE;
    }

    public void setISBEFORDAWCLOSE(String ISBEFORDAWCLOSE) {
        this.ISBEFORDAWCLOSE = ISBEFORDAWCLOSE;
    }

    public String getISBFDRAINAGE() {
        return ISBFDRAINAGE;
    }

    public void setISBFDRAINAGE(String ISBFDRAINAGE) {
        this.ISBFDRAINAGE = ISBFDRAINAGE;
    }

    public String getISDRAIN() {
        return ISDRAIN;
    }

    public void setISDRAIN(String ISDRAIN) {
        this.ISDRAIN = ISDRAIN;
    }

    public String getISINGDAWCLOSE() {
        return ISINGDAWCLOSE;
    }

    public void setISINGDAWCLOSE(String ISINGDAWCLOSE) {
        this.ISINGDAWCLOSE = ISINGDAWCLOSE;
    }

    public String getISLEAKAGEOFBARN() {
        return ISLEAKAGEOFBARN;
    }

    public void setISLEAKAGEOFBARN(String ISLEAKAGEOFBARN) {
        this.ISLEAKAGEOFBARN = ISLEAKAGEOFBARN;
    }

    public String getISLEAKAGEOFBARNLATE() {
        return ISLEAKAGEOFBARNLATE;
    }

    public void setISLEAKAGEOFBARNLATE(String ISLEAKAGEOFBARNLATE) {
        this.ISLEAKAGEOFBARNLATE = ISLEAKAGEOFBARNLATE;
    }

    public String getLATEDATE() {
        return LATEDATE;
    }

    public void setLATEDATE(String LATEDATE) {
        this.LATEDATE = LATEDATE;
    }

    public String getLOC() {
        return LOC;
    }

    public void setLOC(String LOC) {
        this.LOC = LOC;
    }

    public String getPOSITIONING() {
        return POSITIONING;
    }

    public void setPOSITIONING(String POSITIONING) {
        this.POSITIONING = POSITIONING;
    }

    public String getPOSITIONLATE() {
        return POSITIONLATE;
    }

    public void setPOSITIONLATE(String POSITIONLATE) {
        this.POSITIONLATE = POSITIONLATE;
    }

    public String getROSTCNUM() {
        return ROSTCNUM;
    }

    public void setROSTCNUM(String ROSTCNUM) {
        this.ROSTCNUM = ROSTCNUM;
    }

    public String getTAKEACTIONING() {
        return TAKEACTIONING;
    }

    public void setTAKEACTIONING(String TAKEACTIONING) {
        this.TAKEACTIONING = TAKEACTIONING;
    }

    public String getTAKEACTIONLATE() {
        return TAKEACTIONLATE;
    }

    public void setTAKEACTIONLATE(String TAKEACTIONLATE) {
        this.TAKEACTIONLATE = TAKEACTIONLATE;
    }

    public String getTEMPERATURE() {
        return TEMPERATURE;
    }

    public void setTEMPERATURE(String TEMPERATURE) {
        this.TEMPERATURE = TEMPERATURE;
    }

    public String getWEATHER() {
        return WEATHER;
    }

    public void setWEATHER(String WEATHER) {
        this.WEATHER = WEATHER;
    }

    public String getWET() {
        return WET;
    }

    public void setWET(String WET) {
        this.WET = WET;
    }

    public String getEXAMINER() {
        return EXAMINER;
    }

    public void setEXAMINER(String EXAMINER) {
        this.EXAMINER = EXAMINER;
    }

    public String getEXAMINERNAME() {
        return EXAMINERNAME;
    }

    public void setEXAMINERNAME(String EXAMINERNAME) {
        this.EXAMINERNAME = EXAMINERNAME;
    }
}
