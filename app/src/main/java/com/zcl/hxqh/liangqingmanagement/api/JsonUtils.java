package com.zcl.hxqh.liangqingmanagement.api;

import android.content.Context;
import android.util.Log;

import com.zcl.hxqh.liangqingmanagement.bean.LoginResults;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.bean.TemPERSON;
import com.zcl.hxqh.liangqingmanagement.constants.Constants;
import com.zcl.hxqh.liangqingmanagement.model.ALNDOMAIN;
import com.zcl.hxqh.liangqingmanagement.model.Asset;
import com.zcl.hxqh.liangqingmanagement.model.Doclinks;
import com.zcl.hxqh.liangqingmanagement.model.INVUSE;
import com.zcl.hxqh.liangqingmanagement.model.INVUSELINE;
import com.zcl.hxqh.liangqingmanagement.model.N_CAR;
import com.zcl.hxqh.liangqingmanagement.model.N_CARLINE;
import com.zcl.hxqh.liangqingmanagement.model.N_CARTASK;
import com.zcl.hxqh.liangqingmanagement.model.N_FOODJOB;
import com.zcl.hxqh.liangqingmanagement.model.N_GRAINJC;
import com.zcl.hxqh.liangqingmanagement.model.N_INSTRUCSTASK;
import com.zcl.hxqh.liangqingmanagement.model.N_INSTRUCTIONS;
import com.zcl.hxqh.liangqingmanagement.model.N_LABAPY;
import com.zcl.hxqh.liangqingmanagement.model.N_LABAPYRULE;
import com.zcl.hxqh.liangqingmanagement.model.N_PRODUCTIONPLANS;
import com.zcl.hxqh.liangqingmanagement.model.N_QCLSAMP;
import com.zcl.hxqh.liangqingmanagement.model.N_QCTASKLINE;
import com.zcl.hxqh.liangqingmanagement.model.N_ROSTC;
import com.zcl.hxqh.liangqingmanagement.model.N_SAMPLE;
import com.zcl.hxqh.liangqingmanagement.model.N_STOREINFO;
import com.zcl.hxqh.liangqingmanagement.model.N_TASKASSET;
import com.zcl.hxqh.liangqingmanagement.model.N_TASKPLAN;
import com.zcl.hxqh.liangqingmanagement.model.N_WAGONS;
import com.zcl.hxqh.liangqingmanagement.model.N_WTLINE;
import com.zcl.hxqh.liangqingmanagement.model.PERSON;
import com.zcl.hxqh.liangqingmanagement.model.WFASSIGNMENT;
import com.zcl.hxqh.liangqingmanagement.model.WORKORDER;
import com.zcl.hxqh.liangqingmanagement.model.WTLABOR;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Json数据解析类
 */
public class JsonUtils {
    private static final String TAG = "JsonUtils";


    /**
     * 解析登录信息*
     */
    public static LoginResults parsingAuthStr(final Context cxt, String data) {
        Log.i(TAG, "data=" + data);
        LoginResults loginResults = new LoginResults();
        try {
            JSONObject json = new JSONObject(data);
            String errcode = json.getString("errcode");
            String errmsg = json.getString("errmsg");
            loginResults.setErrcode(errcode);
            loginResults.setErrmsg(errmsg);
            if (errcode.equals(Constants.LOGINSUCCESS) || errcode.equals(Constants.CHANGEIMEI)) {
                loginResults.setResult(json.getString("result"));
            }


            return loginResults;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 分页解析返回的结果*
     */
    public static Results parsingResults(Context ctx, String data) {

        String result = null;
        Results results = null;
        try {
            JSONObject json = new JSONObject(data);
            String jsonString = json.getString("errcode");
            if (jsonString.equals(Constants.GETDATASUCCESS)) {
                result = json.getString("result");
                JSONObject rJson = new JSONObject(result);
                String curpage = rJson.getString("curpage");
                String totalresult = rJson.getString("totalresult");
                String resultlist = rJson.getString("resultlist");
                String totalpage = rJson.getString("totalpage");
                String showcount = rJson.getString("showcount");
                results = new Results();
                results.setCurpage(Integer.valueOf(curpage));
                results.setTotalresult(totalresult);
                results.setResultlist(resultlist);
                results.setTotalpage(totalpage);
                results.setShowcount(Integer.valueOf(showcount));
            }

            return results;


        } catch (JSONException e) {
            e.printStackTrace();
            return results;
        }

    }

    /**
     * 不分页解析返回的结果*
     */
    public static Results parsingResults1(Context ctx, String data) {

        String result = null;
        Results results = null;
        try {
            JSONObject json = new JSONObject(data);
            String jsonString = json.getString("errcode");
            if (jsonString.equals(Constants.GETDATASUCCESS)) {
                result = json.getString("result");
                Log.i(TAG, "result=" + result);
                results = new Results();
                results.setResultlist(result);
            }

            return results;


        } catch (JSONException e) {
            e.printStackTrace();
            return results;
        }

    }

    /**
     * 收件箱
     */
    public static ArrayList<WFASSIGNMENT> parsingWFASSIGNMENT(Context ctx, String data) {
        ArrayList<WFASSIGNMENT> list = null;
        WFASSIGNMENT wfassignment = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<WFASSIGNMENT>();
            for (int i = 0; i < jsonArray.length(); i++) {
                wfassignment = new WFASSIGNMENT();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = wfassignment.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = wfassignment.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(wfassignment);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = wfassignment.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(wfassignment, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(wfassignment);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 仓储粮情检查单
     */
    public static ArrayList<N_GRAINJC> parsingN_GRAINJC(Context ctx, String data) {
        ArrayList<N_GRAINJC> list = null;
        N_GRAINJC n_grainjc = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_GRAINJC>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_grainjc = new N_GRAINJC();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_grainjc.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_grainjc.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_grainjc);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_grainjc.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_grainjc, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_grainjc);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 扦样单
     */
    public static ArrayList<N_SAMPLE> parsingN_SAMPLE(Context ctx, String data) {
        ArrayList<N_SAMPLE> list = null;
        N_SAMPLE n_sample = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_SAMPLE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_sample = new N_SAMPLE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_sample.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_sample.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_sample);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_sample.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_sample, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_sample);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 考勤管理
     */
    public static ArrayList<N_WTLINE> parsingN_WTLINE(Context ctx, String data) {
        ArrayList<N_WTLINE> list = null;
        N_WTLINE n_wtline = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_WTLINE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_wtline = new N_WTLINE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_wtline.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_wtline.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_wtline);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_wtline.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_wtline, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_wtline);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 用工记录
     */
    public static ArrayList<WTLABOR> parsingWTLABOR(Context ctx, String data) {
        ArrayList<WTLABOR> list = null;
        WTLABOR wtlabor = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<WTLABOR>();
            for (int i = 0; i < jsonArray.length(); i++) {
                wtlabor = new WTLABOR();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = wtlabor.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = wtlabor.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(wtlabor);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = wtlabor.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(wtlabor, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(wtlabor);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 货运预报
     */
    public static ArrayList<N_CAR> parsingN_CAR(Context ctx, String data) {
        ArrayList<N_CAR> list = null;
        N_CAR n_car = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_CAR>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_car = new N_CAR();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_car.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_car.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_car);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_car.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_car, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_car);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 货运预报
     */
    public static ArrayList<N_CARLINE> parsingN_CARLINE(Context ctx, String data) {
        ArrayList<N_CARLINE> list = null;
        N_CARLINE n_car = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_CARLINE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_car = new N_CARLINE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_car.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_car.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_car);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_car.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_car, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_car);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 移动设备借用
     */
    public static ArrayList<INVUSE> parsingINVUSE(Context ctx, String data) {
        ArrayList<INVUSE> list = null;
        INVUSE invuse = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<INVUSE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                invuse = new INVUSE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = invuse.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = invuse.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(invuse);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = invuse.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(invuse, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(invuse);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 调车作业指令单
     */
    public static ArrayList<N_INSTRUCSTASK> parsingN_INSTRUCSTASK(Context ctx, String data) {
        ArrayList<N_INSTRUCSTASK> list = null;
        N_INSTRUCSTASK n_instrucstask = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_INSTRUCSTASK>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_instrucstask = new N_INSTRUCSTASK();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_instrucstask.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_instrucstask.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_instrucstask);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_instrucstask.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_instrucstask, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_instrucstask);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 调车作业指令单行表
     */
    public static ArrayList<N_INSTRUCTIONS> parsingN_INSTRUCTIONS(String data) {
        ArrayList<N_INSTRUCTIONS> list = null;
        N_INSTRUCTIONS n_instructions = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_INSTRUCTIONS>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_instructions = new N_INSTRUCTIONS();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_instructions.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_instructions.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_instructions);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_instructions.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_instructions, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_instructions);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 实际领用
     */
    public static ArrayList<INVUSELINE> parsingINVUSELINE(Context ctx, String data) {
        ArrayList<INVUSELINE> list = null;
        INVUSELINE invuseline = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<INVUSELINE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                invuseline = new INVUSELINE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = invuseline.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = invuseline.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(invuseline);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = invuseline.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(invuseline, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(invuseline);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 计划领用
     */
    public static ArrayList<N_PRODUCTIONPLANS> parsingN_PRODUCTIONPLANS(String data) {
        ArrayList<N_PRODUCTIONPLANS> list = null;
        N_PRODUCTIONPLANS n_productionplans = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_PRODUCTIONPLANS>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_productionplans = new N_PRODUCTIONPLANS();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_productionplans.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_productionplans.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_productionplans);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_productionplans.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_productionplans, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_productionplans);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 计划领用
     */
    public static ArrayList<N_TASKASSET> parsingN_TASKASSET(String data) {
        ArrayList<N_TASKASSET> list = null;
        N_TASKASSET n_taskasset = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_TASKASSET>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_taskasset = new N_TASKASSET();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_taskasset.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_taskasset.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_taskasset);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_taskasset.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_taskasset, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_taskasset);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 缺陷工单
     */
    public static ArrayList<WORKORDER> parsingWORKORDER(Context ctx, String data) {
        ArrayList<WORKORDER> list = null;
        WORKORDER n_car = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<WORKORDER>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_car = new WORKORDER();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_car.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_car.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_car);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_car.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_car, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_car);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 用工验收
     */
    public static ArrayList<N_LABAPY> parsingN_LABAPY(Context ctx, String data) {
        ArrayList<N_LABAPY> list = null;
        N_LABAPY n_labapy = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_LABAPY>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_labapy = new N_LABAPY();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_labapy.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_labapy.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_labapy);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_labapy.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_labapy, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_labapy);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 验收标准与评分
     */
    public static ArrayList<N_LABAPYRULE> parsingN_LABAPYRULE(Context ctx, String data) {
        ArrayList<N_LABAPYRULE> list = null;
        N_LABAPYRULE n_labapyrule = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_LABAPYRULE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_labapyrule = new N_LABAPYRULE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_labapyrule.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_labapyrule.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_labapyrule);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_labapyrule.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_labapyrule, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_labapyrule);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 选项值
     */
    public static ArrayList<ALNDOMAIN> parsingALNDOMAIN(Context ctx, String data) {
        ArrayList<ALNDOMAIN> list = null;
        ALNDOMAIN alndomain = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<ALNDOMAIN>();
            for (int i = 0; i < jsonArray.length(); i++) {
                alndomain = new ALNDOMAIN();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = alndomain.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = alndomain.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(alndomain);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = alndomain.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(alndomain, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(alndomain);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 任务编号
     */
    public static ArrayList<N_QCTASKLINE> parsingN_QCTASKLINE(Context ctx, String data) {
        ArrayList<N_QCTASKLINE> list = null;
        N_QCTASKLINE n_qctaskline = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_QCTASKLINE>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_qctaskline = new N_QCTASKLINE();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_qctaskline.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_qctaskline.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_qctaskline);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_qctaskline.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_qctaskline, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_qctaskline);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 车辆作业
     */
    public static ArrayList<N_CARTASK> parsingN_CARTASK(Context ctx, String data) {
        ArrayList<N_CARTASK> list = null;
        N_CARTASK n_cartask = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_CARTASK>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_cartask = new N_CARTASK();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_cartask.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_cartask.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_cartask);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_cartask.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_cartask, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_cartask);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 车皮跟踪
     */
    public static ArrayList<N_WAGONS> parsingN_WAGONS(Context ctx, String data) {
        ArrayList<N_WAGONS> list = null;
        N_WAGONS n_cartask = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_WAGONS>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_cartask = new N_WAGONS();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_cartask.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_cartask.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_cartask);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_cartask.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_cartask, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_cartask);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 人员查询
     */
    public static ArrayList<PERSON> parsingPERSON(String data) {
        ArrayList<PERSON> list = null;
        PERSON n_car = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<PERSON>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_car = new PERSON();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_car.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_car.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_car);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_car.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_car, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_car);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 封装N_GRAINJC
     **/

    public static String encapsulationN_GRAINJC(N_GRAINJC n_grainjc) {
        JSONObject jsonObject = new JSONObject();
        try {
            Field[] field = n_grainjc.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
            for (int j = 0; j < field.length; j++) {
                field[j].setAccessible(true);
                String name = field[j].getName();//获取属性的名字
                Method getOrSet = null;
                try {
                    getOrSet = n_grainjc.getClass().getMethod("get" + name);
                    Object value = null;
                    value = getOrSet.invoke(n_grainjc);
                    if (value != null) {
                        jsonObject.put(name, value + "");
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "jsonObject=" + "[" + jsonObject.toString() + "]");
        return "[" + jsonObject.toString() + "]";
    }

    /**
     * 封装扦样单
     **/

    public static String encapsulationN_SAMPLE(N_SAMPLE n_sample) {
        JSONObject jsonObject = new JSONObject();
        try {
            Field[] field = n_sample.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
            for (int j = 0; j < field.length; j++) {
                field[j].setAccessible(true);
                String name = field[j].getName();//获取属性的名字
                Method getOrSet = null;
                try {
                    getOrSet = n_sample.getClass().getMethod("get" + name);
                    Object value = null;
                    value = getOrSet.invoke(n_sample);
                    if (value != null) {
                        if (name.equals("SAMPNUM")) {
                            jsonObject.put("sampnum", value + "");
                        } else {
                            jsonObject.put(name, value + "");
                        }
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "jsonObject=" + "[" + jsonObject.toString() + "]");
        return "[" + jsonObject.toString() + "]";
    }

    /**
     * 封装货运预报
     **/

    public static String encapsulationN_CAR(N_CAR n_car, ArrayList<N_CARLINE> carlines) {
        JSONObject jsonObject = new JSONObject();
        try {
            Field[] field = n_car.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
            for (int j = 0; j < field.length; j++) {
                field[j].setAccessible(true);
                String name = field[j].getName();//获取属性的名字
                Method getOrSet = null;
                try {
                    getOrSet = n_car.getClass().getMethod("get" + name);
                    Object value = null;
                    value = getOrSet.invoke(n_car);
                    if (value != null) {
                        jsonObject.put(name, value + "");
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (carlines == null || carlines.size() == 0) {
            try {
                jsonObject.put("CarLine", new JSONArray());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            JSONObject object = new JSONObject();
            JSONArray carlineArray = new JSONArray();
            JSONObject carlineObj;
            for (int i = 0; i < carlines.size(); i++) {
                carlineObj = new JSONObject();
                Field[] field1 = carlines.get(i).getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field1.length; j++) {
                    field1[j].setAccessible(true);
                    String name = field1[j].getName();//获取属性的名字
                    Method getOrSet = null;
                    try {
                        getOrSet = carlines.get(i).getClass().getMethod("get" + name);
                        Object value = null;
                        value = getOrSet.invoke(carlines.get(i));
                        if (value != null) {
                            carlineObj.put(name, value + "");

                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                carlineArray.put(carlineObj);
            }
            try {
                jsonObject.put("CarLine", carlineArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, "jsonObject=" + "[" + jsonObject.toString() + "]");
        return "[" + jsonObject.toString() + "]";
    }


    /**
     * 保管员货位对照表
     **/
    public static ArrayList<N_STOREINFO> parsingN_STOREINFO(Context ctx, String data) {
        ArrayList<N_STOREINFO> list = null;
        N_STOREINFO n_storeinfo = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_STOREINFO>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_storeinfo = new N_STOREINFO();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_storeinfo.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_storeinfo.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_storeinfo);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_storeinfo.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_storeinfo, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_storeinfo);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 送检编号
     **/
    public static ArrayList<N_QCLSAMP> parsingN_QCLSAMP(Context ctx, String data) {
        ArrayList<N_QCLSAMP> list = null;
        N_QCLSAMP n_qclsamp = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_QCLSAMP>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_qclsamp = new N_QCLSAMP();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_qclsamp.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_qclsamp.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_qclsamp);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_qclsamp.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_qclsamp, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_qclsamp);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 仓储作业计划表
     **/
    public static ArrayList<N_TASKPLAN> parsingN_TASKPLAN(Context ctx, String data) {
        ArrayList<N_TASKPLAN> list = null;
        N_TASKPLAN n_taskplan = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_TASKPLAN>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_taskplan = new N_TASKPLAN();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_taskplan.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_taskplan.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_taskplan);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_taskplan.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_taskplan, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_taskplan);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 设备表
     **/
    public static ArrayList<Asset> parsingAsset(Context ctx, String data) {
        ArrayList<Asset> list = null;
        Asset n_storeinfo = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<Asset>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_storeinfo = new Asset();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_storeinfo.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_storeinfo.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_storeinfo);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_storeinfo.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_storeinfo, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_storeinfo);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 风雨雪三查
     **/
    public static ArrayList<N_ROSTC> parsingN_ROSTC(String data) {
        ArrayList<N_ROSTC> list = null;
        N_ROSTC n_rostc = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_ROSTC>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_rostc = new N_ROSTC();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_rostc.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_rostc.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_rostc);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_rostc.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_rostc, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_rostc);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 出入仓告知记录
     **/
    public static ArrayList<N_FOODJOB> parsingN_FOODJOB(String data) {
        ArrayList<N_FOODJOB> list = null;
        N_FOODJOB n_foodjob = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<N_FOODJOB>();
            for (int i = 0; i < jsonArray.length(); i++) {
                n_foodjob = new N_FOODJOB();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = n_foodjob.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = n_foodjob.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(n_foodjob);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = n_foodjob.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(n_foodjob, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(n_foodjob);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 附件类*
     */
    public static ArrayList<Doclinks> parsingDoclinks(Context ctx, String data) {
        Log.i(TAG, "ddddata=" + data);
        ArrayList<Doclinks> list = null;
        Doclinks doclinks = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<Doclinks>();
            for (int i = 0; i < jsonArray.length(); i++) {
                doclinks = new Doclinks();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = doclinks.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.i(TAG, "name=" + name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = doclinks.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(doclinks);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = doclinks.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(doclinks, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(doclinks);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 封装扦样单
     **/

    public static String encapsulationN_labapys(String sn, String scroe) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SN", sn);
            jsonObject.put("SCORE", scroe);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "jsonObject=" + "[" + jsonObject.toString() + "]");
        return jsonObject.toString();
    }

    /**
     * 封装N_ROSTC风雨雪三查
     **/
    public static String encapsulationN_ROSTC(N_ROSTC n_rostc) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (n_rostc.getISINGDAWCLOSE().equals("Y")) { //门窗关好(中)
                jsonObject.put("ISINGDAWCLOSE", true);
            } else {
                jsonObject.put("ISINGDAWCLOSE", false);
            }

            jsonObject.put("TAKEACTIONING", n_rostc.getTAKEACTIONING()); //采取措施(风雨中)
            jsonObject.put("TAKEACTIONLATE", n_rostc.getTAKEACTIONLATE());//采取措施(风雨后)
            if (n_rostc.getISBEFORDAWCLOSE().equals("Y")) {//门窗关好(前)
                jsonObject.put("ISBEFORDAWCLOSE", true);
            } else {
                jsonObject.put("ISBEFORDAWCLOSE", false);
            }
            jsonObject.put("BEFOREDATE", n_rostc.getBEFOREDATE()); //风雨前日期
            if (n_rostc.getISLEAKAGEOFBARNLATE().equals("Y")) {//仓房无渗漏(后)
                jsonObject.put("ISLEAKAGEOFBARNLATE", true);
            } else {
                jsonObject.put("ISLEAKAGEOFBARNLATE", false);
            }

            jsonObject.put("POSITIONLATE", n_rostc.getPOSITIONLATE());//发生部位(风雨后)
            jsonObject.put("LATEDATE", n_rostc.getLATEDATE());//风雨后日期
            if (n_rostc.getISLEAKAGEOFBARN().equals("Y")) {//仓房渗漏?(中)
                jsonObject.put("ISLEAKAGEOFBARN", true);
            } else {
                jsonObject.put("ISLEAKAGEOFBARN", false);
            }

            jsonObject.put("LOC", n_rostc.getLOC()); //货位号
            jsonObject.put("WEATHER", n_rostc.getWEATHER());//天气
            if (n_rostc.getISDRAIN().equals("Y")) {//水沟通畅(中)
                jsonObject.put("ISDRAIN", true);
            } else {
                jsonObject.put("ISDRAIN", false);
            }

            jsonObject.put("POSITIONING", n_rostc.getPOSITIONING());//发生部位(风雨中)
            jsonObject.put("WET", n_rostc.getWET());////湿度(保留整数)
            if (n_rostc.getISBFDRAINAGE().equals("Y")) {//水沟通畅(中)
                jsonObject.put("ISBFDRAINAGE", true);
            } else {
                jsonObject.put("ISBFDRAINAGE", false);
            }
            jsonObject.put("ROSTCNUM", n_rostc.getROSTCNUM());
            jsonObject.put("EXAMINER", n_rostc.getEXAMINER());
            jsonObject.put("TEMPERATURE", n_rostc.getTEMPERATURE());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "jsonObject=" + "[" + jsonObject.toString() + "]");
        return "[" + jsonObject.toString() + "]";
    }


    /**
     * 封装出入仓记录
     **/
    public static String encapsulationN_FOODJOB(N_FOODJOB n_foodjob) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("CHIEF", n_foodjob.getCHIEF()); //带班负责人
            jsonObject.put("CONTENT", n_foodjob.getCONTENT());//作业内容
            jsonObject.put("REMARK", n_foodjob.getREMARK()); //安全检查项目
            jsonObject.put("AQXM", n_foodjob.getAQXM());//安全告知项目
            jsonObject.put("REPORTDATE", n_foodjob.getREPORTDATE());//作业日期
            jsonObject.put("FOODJOBNUM", n_foodjob.getFOODJOBNUM()); //编号
            jsonObject.put("TELLER", n_foodjob.getTELLER());//被告知人
            jsonObject.put("SAFER", n_foodjob.getSAFER());//安全员
            jsonObject.put("HOLDER", n_foodjob.getHOLDER());//现场保管员
            jsonObject.put("TELL", n_foodjob.getTELL());//告知人
            jsonObject.put("LOC", n_foodjob.getLOC());//货位号
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "jsonObject=" + "[" + jsonObject.toString() + "]");
        return "[" + jsonObject.toString() + "]";
    }


    /**
     * 人员查询
     */
    public static ArrayList<TemPERSON> parsingTemPERSON(String data) {
        Log.e(TAG,"data="+data);
        ArrayList<TemPERSON> list = null;
        TemPERSON temperson = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<TemPERSON>();
            for (int i = 0; i < jsonArray.length(); i++) {
                temperson = new TemPERSON();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = temperson.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    Log.e(TAG,"name="+name);
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = temperson.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(temperson);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = temperson.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(temperson, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(temperson);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}