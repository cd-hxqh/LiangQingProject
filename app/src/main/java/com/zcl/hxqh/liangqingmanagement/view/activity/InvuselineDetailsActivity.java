package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 移动设备查询
 */
public class InvuselineDetailsActivity extends BaseActivity {
    private static String TAG = "InvuselineDetailsActivity";

    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;


    /**
     * 界面信息
     **/

    private TextView invusenumText; //申请单编号

    private TextView usetypeText; //类型

    private TextView numTitle; //设备编号标题
    private TextView rotassetnumText; //移动设备编号

    private TextView descTitle; //设备描述标题
    private TextView ydsbmsText; //移动设备描述

    private TextView locationText; //位置

    private TextView jyghxmText; //借用、归还人姓名

    private TextView carlineText; //实际日期

    private String rotassetnum; //设备编号

    protected FlippingLoadingDialog mLoadingDialog;

    private int mark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invuseline_details);
        geiIntentData();
        findViewById();
        initView();
        getLoadingDialog(getString(R.string.seaching_text)).show();
        startAsyncTask();

    }

    private void geiIntentData() {

        mark = getIntent().getExtras().getInt("mark");
        rotassetnum = getIntent().getExtras().getString("result");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);


        invusenumText = (TextView) findViewById(R.id.invusenum_text_id);
        usetypeText = (TextView) findViewById(R.id.usetype_text_id);
        numTitle = (TextView) findViewById(R.id.numtitle_id);
        rotassetnumText = (TextView) findViewById(R.id.rotassetnum_text_id);
        descTitle = (TextView) findViewById(R.id.desctitle_id);
        ydsbmsText = (TextView) findViewById(R.id.ydsbms_id);
        locationText = (TextView) findViewById(R.id.location_text_id);
        jyghxmText = (TextView) findViewById(R.id.jyghxm_text_id);
        carlineText = (TextView) findViewById(R.id.actualdate_text_id);
    }


    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        if (mark == MainActivity.ROTASSETNUM_CODE) {
            titleTextView.setText(R.string.ydsbcx_text);
        } else if (mark == MainActivity.ITEMNUM_CODE) {
            numTitle.setText(R.string.gj_num);
            descTitle.setText(R.string.gjms_text);
            titleTextView.setText(R.string.gjcx_text);
        }
    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }


    /**
     * 查询数据*
     */
    private void startAsyncTask() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String reviseresult = null;
                if (mark == MainActivity.ROTASSETNUM_CODE) { //移动设备查询
                    reviseresult = AndroidClientService.getmobileinfo(InvuselineDetailsActivity.this, rotassetnum);
                }else if(mark == MainActivity.ITEMNUM_CODE){ //工具查询
                    reviseresult = AndroidClientService.getIteminfo(InvuselineDetailsActivity.this, rotassetnum);
                }
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                showInfo(s);


            }
        }.execute();


    }


    /**
     * 解析返回的数据
     **/
    private void showInfo(String result) {
        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject json = jsonArray.getJSONObject(0);
            String actualdate = json.getString("actualdate");
            String usetype = json.getString("usetype");
            String description = json.getString("description");
            String invusenum = json.getString("invusenum");
            String num=null;
            if (mark==MainActivity.ROTASSETNUM_CODE){

                num = json.getString("rotassetnum");
            }else if(mark==MainActivity.ITEMNUM_CODE){
                num = json.getString("itemnum");
            }
            String location = json.getString("location");
            String displayname = json.getString("displayname");
            invusenumText.setText(invusenum);
            usetypeText.setText(usetype);
            rotassetnumText.setText(num);
            ydsbmsText.setText(description);
            locationText.setText(location);
            jyghxmText.setText(displayname);
            carlineText.setText(actualdate);


        } catch (JSONException e) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String actualdate = jsonObject.getString("actualdate");
                String usetype = jsonObject.getString("usetype");
                String description = jsonObject.getString("description");
                String invusenum = jsonObject.getString("invusenum");
                String num=null;
                if (mark==MainActivity.ROTASSETNUM_CODE){

                    num = jsonObject.getString("rotassetnum");
                }else if(mark==MainActivity.ITEMNUM_CODE){
                    num = jsonObject.getString("itemnum");
                }
                String location = jsonObject.getString("location");
                String displayname = jsonObject.getString("displayname");
                invusenumText.setText(invusenum);
                usetypeText.setText(usetype);
                rotassetnumText.setText(num);
                ydsbmsText.setText(description);
                locationText.setText(location);
                jyghxmText.setText(displayname);
                carlineText.setText(actualdate);
                MessageUtils.showMiddleToast(InvuselineDetailsActivity.this, jsonObject.getString("msg"));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

        }


    }

}
