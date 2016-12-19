package com.zcl.hxqh.liangqingmanagement.api;

import android.content.Context;
import android.util.Log;

import com.zcl.hxqh.liangqingmanagement.bean.LoginResults;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.constants.Constants;
import com.zcl.hxqh.liangqingmanagement.model.ALNDOMAIN;
import com.zcl.hxqh.liangqingmanagement.model.N_CAR;
import com.zcl.hxqh.liangqingmanagement.model.N_CARLINE;
import com.zcl.hxqh.liangqingmanagement.model.N_CARTASK;
import com.zcl.hxqh.liangqingmanagement.model.N_GRAINJC;
import com.zcl.hxqh.liangqingmanagement.model.N_QCTASKLINE;
import com.zcl.hxqh.liangqingmanagement.model.N_SAMPLE;
import com.zcl.hxqh.liangqingmanagement.model.N_STOREINFO;
import com.zcl.hxqh.liangqingmanagement.model.N_TASKPLAN;
import com.zcl.hxqh.liangqingmanagement.model.N_WAGONS;

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

        Log.i(TAG, "jsonObject=" + "["+jsonObject.toString()+"]");
        return "["+jsonObject.toString()+"]";
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

        Log.i(TAG, "jsonObject=" + "["+jsonObject.toString()+"]");
        return "["+jsonObject.toString()+"]";
    }

    /**
     * 封装货运预报
     **/

    public static String encapsulationN_CAR(N_CAR n_car,ArrayList<N_CARLINE> carlines) {
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
        if (carlines==null||carlines.size()==0) {
            try {
                jsonObject.put("CarLine", new JSONArray());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
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
        Log.i(TAG, "jsonObject=" + "["+jsonObject.toString()+"]");
        return "["+jsonObject.toString()+"]";
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


}