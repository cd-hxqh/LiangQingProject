package com.zcl.hxqh.liangqingmanagement.webserviceclient;


import android.content.Context;
import android.util.Log;

import com.zcl.hxqh.liangqingmanagement.constants.Constants;
import com.zcl.hxqh.liangqingmanagement.until.AccountUtils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by think on 2015/8/11.
 * webservice方法
 */
public class AndroidClientService {
    private static final String TAG = "AndroidClientService";
    public static String NAMESPACE = "http://www.ibm.com/maximo";
    public String url = null;
    public static  int timeOut = 1200000;



    /**
     * 粮情检查单新增及修改
     */
    public static String addAndUpdateN_grainjc(final Context cxt,String json) {

        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.lqwebserviceURL;

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "n_grainsaddAccount");
        soapReq.addProperty("json", json);//封装数据
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }
    /**
     * 扦样单新增及修改
     */
    public static String addAndUpdateN_SAMPLE(final Context cxt,String json) {

        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.qywebserviceURL;

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "n_sampleaddAccount");
        soapReq.addProperty("json", json);//封装数据
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }


    /**
     * 根据TAGID获取车号
     */
    public static String addAndUpdateN_SAMPLE1(final Context cxt,String tagId,String enterby) {

        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.qywebservice1URL;

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "n_sample1addAccoun");
        soapReq.addProperty("tagid", tagId);//封装数据
        soapReq.addProperty("enterby", enterby);//封装数据
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            Log.i(TAG,"obj="+obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }


    /**
     * 根据货位号获取任务检查单号
     */
    public static String addN_SAMPLE1LOC(final Context cxt,String loc,String enterby) {

        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.qywebservice1URL;

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "n_sample1loc");
        soapReq.addProperty("loc", loc);//封装数据
        soapReq.addProperty("enterby", enterby);//封装数据
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            Log.i(TAG,"obj="+obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }

    /**
     * 根据是否火车与车号获取任务检查单号
     */
    public static String addN_SAMPLE2LOC(final Context cxt,String carno,String train,String enterby) {

        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.qywebservice1URL;

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "n_sample1carno");
        soapReq.addProperty("carno", carno);//封装数据
        soapReq.addProperty("train", train);//封装数据
        soapReq.addProperty("enterby", enterby);//封装数据
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            Log.i(TAG,"obj="+obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }


    /**
     *根据创建时间与货位号获取相关信息
     */
    public static String getN_GRAINS(final Context cxt,String loc,String reportdate) {

        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.lqwebserviceURL;

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "n_grainsloc");
        soapReq.addProperty("LOC", loc);//货位号
        soapReq.addProperty("REPORTDATE", reportdate);//创建时间
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            Log.i(TAG,"obj="+obj);
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }

    /**
     * 货运预报新增
     */
    public static String addAndUpdateN_CAR(final Context cxt,String json) {

        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.carwebserviceURL;

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "n_caradd ");
        soapReq.addProperty("json", json);//封装数据
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }



    /**
     * 考勤管理
     */
    public static String addN_WTLINE(final Context cxt,String cardid,String ip) {

        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.carwebserviceURL;

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "n_carAttendDance");
        soapReq.addProperty("cardid", cardid);//封装数据
        soapReq.addProperty("ip", ip);//封装数据
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }

    /**
     * 考勤管理
     */
    public static String saveQX(final Context cxt,String personid,String elc_no,String lim_sht,String pla_no,
                                String fun_crw,String fun_per,String fum_dtm,String lim_not,String worktype,String supervisor,
                                String recreq,String schedfinish) {

        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.qxwebserviceURL;

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mobileroutessaveQx");
        soapReq.addProperty("personid", personid);//登陆人
        soapReq.addProperty("ELC_NO", elc_no);//设备
        soapReq.addProperty("LIM_SHT", lim_sht);//缺陷描述
        soapReq.addProperty("PLA_NO", pla_no);//发现部门
        soapReq.addProperty("FUN_CRW", fun_crw);//发现班组
        soapReq.addProperty("FUN_PER", fun_per);//发现人
        soapReq.addProperty("FUM_DTM", fum_dtm);//发现时间
        soapReq.addProperty("LIM_NOT", lim_not);//备注
        soapReq.addProperty("WORKTYPE", worktype);//是否排查
        soapReq.addProperty("SUPERVISOR", supervisor);//责任人
        soapReq.addProperty("RECREQ", recreq);//整改要求
        soapReq.addProperty("SCHEDFINISH", schedfinish);//整改期限
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(ip_adress, timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }

}
