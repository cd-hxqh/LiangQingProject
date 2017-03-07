package com.zcl.hxqh.liangqingmanagement.api;


import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.bean.LoginResults;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.constants.Constants;
import com.zcl.hxqh.liangqingmanagement.until.AccountUtils;


/**
 * Created by apple on 15/5/27.
 */
public class HttpManager {

    private static AsyncHttpClient sClient = null;
    private static final String TAG = "HttpManager";

    /**
     * 设置收件箱的接口
     */
    public static String getWFASSIGNMENT(String persionid, int curpage, int showcount) {
            return "{'appid':'" + Constants.WFASSIGNMENT_APPID + "','objectname':'" + Constants.WFASSIGNMENT_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WFASSIGNMENTID DESC','condition':{'ASSIGNCODE':'" + persionid + "','ASSIGNSTATUS':'=活动'}}";

    }

    /**
     * 设置仓储粮情检查单的接口
     */
    public static String getN_GRAINJC(String value, String worktype,int curpage, int showcount) {
        if (value.equals("")) {
            return "{'appid':'" + Constants.N_GRAINJC_APPID + "','objectname':'" + Constants.N_GRAINJC_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'GRAINSNUN DESC','condition':{'WORKTYPE':'=" + worktype + "'}}";
        }
        return "{'appid':'" + Constants.N_GRAINJC_APPID + "','objectname':'" + Constants.N_GRAINJC_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'GRAINSNUN DESC','condition':{'WORKTYPE':'=" + worktype + "'},'sinorsearch':{'GRAINSNUN':'" + value + "','DESCRIPTION':'" + value + "'}}";
    }

    /**
     * 设置扦样单的接口
     */
    public static String getN_SAMPLE(String value, String enterby, int curpage, int showcount) {
        if (value.equals("")) {
            return "{'appid':'" + Constants.N_SAMPLE_APPID + "','objectname':'" + Constants.N_SAMPLE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'SAMPLENUM DESC','condition':{'ENTERBY':'=" + enterby + "'}}";
        }
        return "{'appid':'" + Constants.N_SAMPLE_APPID + "','objectname':'" + Constants.N_SAMPLE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'SAMPLENUM DESC','condition':{'ENTERBY':'=" + enterby + "'},'sinorsearch':{'SAMPLENUM':'" + value + "','DESCRIPTION':'" + value + "'}}";
    }

    /**
     * 设置货运预报的接口
     */
    public static String getN_CAR(String value, String enterby, int curpage, int showcount) {
        if (value.equals("")) {
            return "{'appid':'" + Constants.N_CAR_APPID + "','objectname':'" + Constants.N_CAR_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'CARNUM DESC','condition':{'ENTERBY':'=" + enterby + "'}}";
        }
        return "{'appid':'" + Constants.N_CAR_APPID + "','objectname':'" + Constants.N_CAR_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'CARNUM DESC','condition':{'ENTERBY':'=" + enterby + "'},'sinorsearch':{'CARNUM':'" + value + "','DESCRIPTION':'" + value + "'}}";
    }

    /**
     * 设置货运预报明细的接口
     */
    public static String getN_CARLINE_NAME(String value, String carnum, int curpage, int showcount) {
        if (value.equals("")) {
            return "{'appid':'" + Constants.N_CAR_APPID + "','objectname':'" + Constants.N_CARLINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'CARNUM','condition':{'CARNUM':'=" + carnum + "'}}";
        }
        return "{'appid':'" + Constants.N_CAR_APPID + "','objectname':'" + Constants.N_CARLINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'CARNUM','condition':{'CARNUM':'=" + carnum + "'},'sinorsearch':{'CARNUM':'" + value + "','CARNO':'" + value + "'}}";
    }


    /**
     * 设置用工验收的接口
     */
    public static String getN_LABAPY(String value, String linkman, int curpage, int showcount) {

        if (value.equals("")) {
            return "{'appid':'" + Constants.N_LABAPYS_APPID + "','objectname':'" + Constants.N_LABAPYS_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'N_LABAPYNUM DESC','condition':{'LINKMAN':'=" + linkman  + "','STATUS':'=已初审'}}";
        }
        return "{'appid':'" + Constants.N_LABAPYS_APPID + "','objectname':'" + Constants.N_LABAPYS_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'N_LABAPYNUM DESC','condition':{'LINKMAN':'=" + linkman + ",'STATUS':'=已初审'},'sinorsearch':{'CARNUM':'" + value + "','DESCRIPTION':'" + value + "'}}";

    }


    /**
     * 验收标准与评分
     */
    public static String getN_LABAPYRULE(String value,String labapynum, int curpage, int showcount) {

        if (value.equals("")) {
            return "{'appid':'" + Constants.N_LABAPYS_APPID + "','objectname':'" + Constants.N_LABAPYRULE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'SN ASC','condition':{'LABAPYNUM':'=" + labapynum+"'}}";
        }
        return "{'appid':'" + Constants.N_LABAPYS_APPID + "','objectname':'" + Constants.N_LABAPYRULE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'SN ASC','condition':{'LABAPYNUM':'=" + labapynum + "'},'sinorsearch':{'SN':'" + value + "','ITEM':'" + value + "','SCORE':'" + value + "'}}";

    }










    /**
     * 设置考勤管理的接口
     */
    public static String getN_WTLINE(String value, String start, String yesterday, String ip, int curpage, int showcount) {
        if (value.equals("")) {
            return "{'appid':'" + Constants.N_WTLINE_APPID + "','objectname':'" + Constants.N_WTLINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'N_WTLINEID DESC','condition':{'START':'<=" + start + "','START':'>=" + yesterday + "','IP':'=" + ip + "'}}";
        }
        return "{'appid':'" + Constants.N_WTLINE_APPID + "','objectname':'" + Constants.N_WTLINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'N_WTLINEID DESC','condition':{'START':'=" + start + "','IP':'=" + ip + "'},'sinorsearch':{'NAME':'" + value + "'}}";
    }

    /**
     * 设置人员查询的接口
     */
    public static String getPERSON(String n_cardnum, int curpage, int showcount) {
        return "{'appid':'" + Constants.PERSON_APPID + "','objectname':'" + Constants.PERSON_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'N_CARDNUM':'" + n_cardnum + "'}}";
    }

    /**
     * 设置考勤记录的接口
     */
    public static String getN_WTLINE2(String n_cardnum, int curpage, int showcount) {
        return "{'appid':'" + Constants.N_WTLINE_APPID + "','objectname':'" + Constants.N_WTLINE_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'N_WTLINEID DESC','condition':{'CARDID':'=" + n_cardnum + "'}}";
    }

    /**
     * 设置用工记录的接口
     */
    public static String getWTLABOR(String cardnum, int curpage, int showcount) {
        return "{'appid':'" + Constants.WTLABORVIEW_APPID + "','objectname':'" + Constants.WTLABORVIEW_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'STARTTIME DESC','condition':{'CARDNUM':'=" + cardnum + "'}}";
//        return "{'appid':'" + Constants.WTLABORVIEW_APPID + "','objectname':'" + Constants.WTLABORVIEW_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'STARTTIME DESC'}";
    }

    /**
     * 设置缺陷工单的接口
     */
    public static String getWORKORDER(String value, int curpage, int showcount) {
        if (value.equals("")) {
            return "{'appid':'" + Constants.WORKORDER_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WORKORDERID DESC'}";
        }
        return "{'appid':'" + Constants.WORKORDER_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WORKORDERID DESC','sinorsearch':{'SB':'" + value + "','DESCRIPTION':'" + value + "'}}";
    }

    /**
     * 设置紧急工单的接口
     */
    public static String getJinJiWORKORDER(String value, String reportedby, int curpage, int showcount) {
        if (value.equals("")) {
            return "{'appid':'" + Constants.WORKORDER_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WORKORDERID DESC','condition':{'WORKTYPE':'EM','REPORTEDBY':'" + reportedby + "'}}";
        }
        return "{'appid':'" + Constants.WORKORDER_APPID + "','objectname':'" + Constants.WORKORDER_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WORKORDERID DESC','condition':{'WORKTYPE':'EM','REPORTEDBY':'" + reportedby + "'},'sinorsearch':{'WONUM':'" + value + "','DESCRIPTION':'" + value + "'}}";
    }

    /**
     * 选项值
     */
    public static String getALNDOMAIN(String DOMAINID) {
        return "{'appid':'" + Constants.ALNDOMAIN_APPID + "','objectname':'" + Constants.ALNDOMAIN_NAME + "','option':'read','condition':{'DOMAINID':'=" + DOMAINID + "'}}";
    }

    /**
     * 任务编号
     */
    public static String getN_QCTASKLINE(String checker) {
        return "{'appid':'" + Constants.N_SAMPLE_APPID + "','objectname':'" + Constants.N_QCTASKLINE_NAME + "','option':'read','condition':{'CHECKER':'=" + checker + "'}}";
    }

    /**
     * 车辆作业
     */
    public static String getN_CARTASK(String tasktype) {
        return "{'appid':'" + Constants.N_SAMPLE_APPID + "','objectname':'" + Constants.N_CARTASK_NAME + "','option':'read','condition':{'TASKTYPE':'=" + tasktype + "'}}";
    }

    /**
     * 车皮跟踪
     */
    public static String getN_WAGONS(String value, String status, int curpage, int showcount) {
        if (value.equals("")) {
            return "{'appid':'" + Constants.N_SAMPLE_APPID + "','objectname':'" + Constants.N_WAGONS_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','condition':{'STATUS':'" + status + "'}}";
        }
        return "{'appid':'" + Constants.N_SAMPLE_APPID + "','objectname':'" + Constants.N_WAGONS_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'WAGONNUM DESC','condition':{'STATUS':'" + status + "'},'sinorsearch':{'WAGONNUM':'" + value + "'}}";


    }

    /**
     * 车辆作业单
     */
    public static String getN_CARTASK(String tagId, int curpage, int showcount) {
        return "{'appid':'" + Constants.N_CARTASK_NAME + "','objectname':'" + Constants.N_CARTASK_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'INTIME DESC','condition':{'TAGID':'=" + tagId + "'}}";
    }


    /**
     * 设置保管员货位的接口
     */
    public static String getN_STOREINFO(String value, String holder, int curpage, int showcount) {
        if (value.equals("")) {
            if (holder.equals("")) {
                return "{'appid':'" + Constants.N_STOREINFO_APPID + "','objectname':'" + Constants.N_STOREINFO_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'LOC DESC'}";
            }
            return "{'appid':'" + Constants.N_STOREINFO_APPID + "','objectname':'" + Constants.N_STOREINFO_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'LOC DESC','condition':{'HOLDER':'=" + holder + "'}}";
        } else {
            if (holder.equals("")) {
                return "{'appid':'" + Constants.N_STOREINFO_APPID + "','objectname':'" + Constants.N_STOREINFO_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'LOC DESC','sinorsearch':{'LOC':'" + value + "','OLDLOC':'" + value + "'}}";
            }
            return "{'appid':'" + Constants.N_STOREINFO_APPID + "','objectname':'" + Constants.N_STOREINFO_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'LOC DESC','condition':{'HOLDER':'=" + holder + "'},'sinorsearch':{'LOC':'" + value + "','OLDLOC':'" + value + "'}}";

        }


    }

    /**
     * 设置送检编号的接口
     */
    public static String getN_QCLSAMP(String value, int curpage, int showcount) {
        if (value.equals("")) {
            return "{'appid':'" + Constants.N_QCLSAMP_APPID + "','objectname':'" + Constants.N_QCLSAMP_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'SAMPNUM'}";
        }
        return "{'appid':'" + Constants.N_QCLSAMP_APPID + "','objectname':'" + Constants.N_QCLSAMP_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'SAMPNUM','sinorsearch':{'SAMPNUM':'" + value + "','DESCRIPTION':'" + value + "'}}";
    }

    /**
     * 设置保管员货位的接口
     */
    public static String getN_TASKPLAN(String value, String nowtime, int curpage, int showcount) {
        if (value.equals("")) {
            return "{'appid':'" + Constants.N_CAR_APPID + "','objectname':'" + Constants.N_TASKPLAN + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'PLANNUM DESC','condition':{'STATUS':'已修改,已发布,已取消,已完成,待修改,待发布,等待核准,草稿','OVERTIME':'>" + nowtime + "'}}";
        }
        return "{'appid':'" + Constants.N_CAR_APPID + "','objectname':'" + Constants.N_TASKPLAN + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'PLANNUM DESC','condition':{'STATUS':'已修改,已发布,已取消,已完成,待修改,待发布,等待核准,草稿','OVERTIME':'>" + nowtime + "'},'sinorsearch':{'PLANNUM':'" + value + "','DESCRIPTION':'" + value + "'}}";
    }

    /**
     * 设置设备的接口
     */
    public static String getAsset(String value, int curpage, int showcount) {
        if (value.equals("")) {
            return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ASSETNUM DESC','condition':{'STATUS':'活动'}}";
        }
        return "{'appid':'" + Constants.ASSET_APPID + "','objectname':'" + Constants.ASSET_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'ASSETNUM DESC','condition':{'STATUS':'活动'},'sinorsearch':{'N_MODELNUM':'" + value + "','N_LOCANAME':'" + value + "'}}";
    }

    /**
     * 设置人员的接口
     */
    public static String getPerson(String value, int curpage, int showcount) {
        if (value.equals("")) {
            return "{'appid':'" + Constants.PERSON_APPID + "','objectname':'" + Constants.PERSON_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'PERSONID DESC'}";
        }
        return "{'appid':'" + Constants.PERSON_APPID + "','objectname':'" + Constants.PERSON_NAME + "','curpage':" + curpage + ",'showcount':" + showcount + ",'option':'read','orderby':'PERSONID DESC','sinorsearch':{'PERSONID':'" + value + "','DISPLAYNAME':'" + value + "','TITLE':'" + value + "','DEPARTMENT':'" + value + "'}}";
    }

    /**
     * 查询附件的接口
     */
    public static String getDoclinks(String ownertable, String ownerid) {
        return "{'appid':'" + Constants.DOCLINKS_APPID + "','objectname':'" + Constants.DOCLINKS_NAME + "','option':'read','condition':{'OWNERTABLE':'=" + ownertable + "','OWNERID':'=" + ownerid + "'}}";
    }


    /**
     * 使用用户名密码登录
     *
     * @param cxt
     * @param username 用户名
     * @param password 密码
     * @param imei     密码
     * @param handler  返回结果处理
     */
    public static void loginWithUsername(final Context cxt, final String username, final String password, String imei,
                                         final HttpRequestHandler<String> handler) {
        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.SIGN_IN_URL;
        Log.i(TAG, "ip_adress=" + ip_adress);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("loginid", username);
        params.put("password", password);
        params.put("imei", imei);
        client.post(ip_adress, params, new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.i(TAG, "SstatusCode=" + statusCode + "responseString=" + responseString);
                SafeHandler.onFailure(handler, IMErrorType.errorMessage(cxt, IMErrorType.ErrorLoginFailure));
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.i(TAG, "SstatusCode=" + statusCode + "responseString=" + responseString);
                if (statusCode == 200) {
                    LoginResults loginResults = JsonUtils.parsingAuthStr(cxt, responseString);
                    if (loginResults != null) {
                        if (loginResults.getErrcode().equals(Constants.LOGINSUCCESS) || loginResults.getErrcode().equals(Constants.CHANGEIMEI)) {
                            SafeHandler.onSuccess(handler, loginResults.getResult());
                        } else if (loginResults.getErrcode().equals(Constants.USERNAMEERROR)) {
                            SafeHandler.onFailure(handler, loginResults.getErrmsg());
                        }
                    }

                }
            }

        });


    }


    /**
     * 不分页获取信息方法*
     */
    public static void getData(final Context cxt, String data, final HttpRequestHandler<Results> handler) {
        Log.i(TAG, "data=" + data);
        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.BASE_URL;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("data", data);
        client.get(ip_adress, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {

                Results result = JsonUtils.parsingResults1(cxt, responseString);

                SafeHandler.onSuccess(handler, result, result.getCurpage(), result.getShowcount());
            }

        });
    }


    /**
     * 解析返回的结果--分页*
     */
    public static void getDataPagingInfo(final Context cxt, String data, final HttpRequestHandler<Results> handler) {
        Log.i(TAG, "data=" + data);
        String ip_adress = AccountUtils.getIpAddress(cxt) + Constants.BASE_URL;
        Log.i(TAG, "ip_adress=" + ip_adress);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("data", data);
        client.get(ip_adress, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }


            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Results result = JsonUtils.parsingResults(cxt, responseString);

                SafeHandler.onSuccess(handler, result, result.getCurpage(), result.getShowcount());
            }

        });
    }


    /**
     * 生成物资编码*
     *
     * @ cxt 上下问
     * useruid 用户唯一ID
     * itemreqid 编码申请单唯一标识
     */
    public static void setItemNumber(final Context cxt, final String useruid, final String itemreqid,
                                     final HttpRequestHandler<String> handler) {

        Log.i(TAG, "itemreqid=" + itemreqid);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("useruid", useruid);
        params.put("itemreqid", itemreqid);
        client.post(Constants.ITEM_GENERATE_URL, params, new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, IMErrorType.errorMessage(cxt, IMErrorType.ErrorLoginFailure));
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.i(TAG, "SstatusCode=" + statusCode + "responseString=" + responseString);
            }

        });


    }


    /**
     * 发送工作流
     *
     * @cxt 上下文
     * @ownertable 工作流对应的主表名称
     * @ownerid 工作流对应的主表主键
     * @processname 工作流名称
     * @useruid 当前登录人的唯一标识
     */
    public static void startFlow(final Context cxt, final String ownertable, final String ownerid, final String processname, final String useruid,
                                 final HttpRequestHandler<String> handler) {


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("ownertable", ownertable);
        params.put("ownerid", ownerid);
        params.put("processname", processname);
        params.put("useruid", useruid);
        client.post(Constants.START_FLOW_URL, params, new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, IMErrorType.errorMessage(cxt, IMErrorType.ErrorLoginFailure));
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.i(TAG, "SstatusCode=" + statusCode + "responseString=" + responseString);

            }

        });


    }

    /**
     * 审批工作流
     *
     * @ownertable 工作流对应的主表名称
     * @ownerid 工作流对应的主表主键
     * @memo 审批意见
     * @selectWhat 是否接受：true/false
     * useruid 当前登录人的唯一标识
     */
    public static void approvalFlow(final Context cxt, final String ownertable, final String ownerid, final String memo, final String selectWhat, final String useruid,
                                    final HttpRequestHandler<String> handler) {


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("ownertable", ownertable);
        params.put("ownerid", ownerid);
        params.put("memo", memo);
        params.put("selectWhat", selectWhat);
        params.put("useruid", useruid);
        client.post(Constants.APPROVAL_FLOW_URL, params, new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, IMErrorType.errorMessage(cxt, IMErrorType.ErrorLoginFailure));
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.i(TAG, "SstatusCode=" + statusCode + "responseString=" + responseString);
            }

        });


    }


}
