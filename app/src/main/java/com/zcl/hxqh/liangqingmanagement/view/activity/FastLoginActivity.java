/*
 * Copyright (C) 2010 The Android Open Source Project
 * Copyright (C) 2011 Adam Nybäck
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zcl.hxqh.liangqingmanagement.AppManager;
import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.until.AccountUtils;
import com.zcl.hxqh.liangqingmanagement.until.GestureLockViewGroup;
import com.zcl.hxqh.liangqingmanagement.until.GestureLockViewGroup.OnGestureLockViewListener;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 手势登陆
 */
public class FastLoginActivity extends BaseActivity {
    private TextView ConnectSetting;//连接设置
    private GestureLockViewGroup mGestureLockViewGroup;

    private TextView cardloginText;//员工卡登录
    private TextView passwordloginText;//密码登录

    protected FlippingLoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_login);

        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        ConnectSetting = (TextView) findViewById(R.id.connect_setting);
        cardloginText = (TextView) findViewById(R.id.card_login);
        passwordloginText = (TextView) findViewById(R.id.password_login);
    }

    @Override
    protected void initView() {
        ConnectSetting.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        ConnectSetting.getPaint().setAntiAlias(true);//抗锯齿
        ConnectSetting.setOnClickListener(connectsettingOnClickListener);
        cardloginText.setOnClickListener(cardloginOnClickListener);
        passwordloginText.setOnClickListener(passwordOnClickListener);
        mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.id_gestureLockViewGroup);
//        mGestureLockViewGroup.setAnswer(new int[]{1, 2, 3, 4, 5});
        mGestureLockViewGroup
                .setOnGestureLockViewListener(new OnGestureLockViewListener() {

                    @Override
                    public void onUnmatchedExceedBoundary() {
                        Toast.makeText(FastLoginActivity.this, "错误5次...",
                                Toast.LENGTH_SHORT).show();
                        mGestureLockViewGroup.setUnMatchExceedBoundary(5);
                    }

                    @Override
                    public void onChoose(List<Integer> list) {
                        ImageLogin(AccountUtils.getUserName(FastLoginActivity.this),list.toString());
                    }

                    @Override
                    public void onGestureEvent(boolean matched) {
//                        Toast.makeText(FastLoginActivity.this, matched + "",
//                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onBlockSelected(int cId) {
                    }
                });
    }

    /**
     * 手势登录*
     */
    private void ImageLogin(final String username,final String password) {
        getLoadingDialog("正在登陆...").show();
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return AndroidClientService.mobilelogin_ImageLogin(FastLoginActivity.this,username,password);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (isJsonArrary(s)){
                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        if (jsonArray.getJSONObject(0).has("success")){
//                            Toast.makeText(FastLoginActivity.this,jsonArray.getJSONObject(0).getString("success"),Toast.LENGTH_SHORT).show();
                            getUserApp(AccountUtils.getUserName(FastLoginActivity.this));
                        }else {
                            mGestureLockViewGroup.reset();
                            getLoadingDialog("正在登陆...").dismiss();
                            MessageUtils.showErrorMessage(FastLoginActivity.this,"手势密码错误");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    mGestureLockViewGroup.reset();
                    getLoadingDialog("正在登陆...").dismiss();
                    MessageUtils.showErrorMessage(FastLoginActivity.this,"手势密码错误");
                    mGestureLockViewGroup.reset();
                }
            }
        }.execute();
    }

    /**
     * 跳转至主界面*
     */
    private void startIntent(ArrayList<String> list) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.putExtra("appidArray",list);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    /**
     * 获取用户名密码数据*
     */
    private void getUserApp(final String username) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return AndroidClientService.mobilelogin_getUserApp(FastLoginActivity.this,username);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mGestureLockViewGroup.reset();
                getLoadingDialog("正在登陆...").dismiss();
                if (isJsonArrary(s)){
                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        JSONObject jsonObject;
                        ArrayList<String > arrayList = new ArrayList<String>();
                        for (int i =0;i<jsonArray.length();i++){
                            jsonObject = jsonArray.getJSONObject(i);
                            if (jsonObject.has("appid")){
                                arrayList.add(jsonObject.getString("appid"));
                            }
                        }
                        startIntent(arrayList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    MessageUtils.showErrorMessage(FastLoginActivity.this,"获取权限失败");
                }
            }
        }.execute();
    }

    private boolean isJsonArrary(String data){
        try {
            JSONArray jsonArray = new JSONArray(data);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }

    private View.OnClickListener connectsettingOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(FastLoginActivity.this, ConnectSettingActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener cardloginOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(FastLoginActivity.this,CardLoginActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private View.OnClickListener passwordOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(FastLoginActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private long exitTime = 0;

    @Override
    public void onBackPressed() {


        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.AppExit(FastLoginActivity.this);
        }
    }

}
