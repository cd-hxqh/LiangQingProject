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
public class FastLoginSettingActivity extends BaseActivity {
    private TextView hintText;
    private GestureLockViewGroup mGestureLockViewGroup;

    private List<Integer> chooseList;

    protected FlippingLoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_login_setting);

        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        hintText = (TextView) findViewById(R.id.hint_text);
        mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.id_gestureLockViewGroup);
    }

    @Override
    protected void initView() {
//        mGestureLockViewGroup.setAnswer(new int[]{1, 2, 3, 4, 5});
        mGestureLockViewGroup.setIsFirst(true);
        mGestureLockViewGroup
                .setOnGestureLockViewListener(new OnGestureLockViewListener() {

                    @Override
                    public void onUnmatchedExceedBoundary() {
//                        Toast.makeText(FastLoginSettingActivity.this, "错误5次...",
//                                Toast.LENGTH_SHORT).show();
//                        mGestureLockViewGroup.setUnMatchExceedBoundary(5);
                    }

                    @Override
                    public void onChoose(List<Integer> list) {
                        if (chooseList==null||chooseList.size()==0){
                            chooseList = list;
                            hintText.setText("请再次绘制手势密码");
                            mGestureLockViewGroup.reset();
                        }else {
                            if (chooseList == list){//两次绘制相同
//                                Toast.makeText(FastLoginSettingActivity.this,"两次绘制手势相同!",Toast.LENGTH_SHORT).show();
                                setImagepassword(AccountUtils.getUserName(FastLoginSettingActivity.this),list.toString());
                            }else {//两次绘制不同
                               Toast.makeText(FastLoginSettingActivity.this,"两次绘制手势不同!",Toast.LENGTH_SHORT).show();
                                hintText.setText("请重新绘制手势密码");
                                chooseList = null;
                            }
                        }
                    }

                    @Override
                    public void onGestureEvent(boolean matched) {
//                        Toast.makeText(FastLoginSettingActivity.this, matched + "",
//                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onBlockSelected(int cId) {
                    }
                });
    }

    /**
     * 设置手势密码数据*
     */
    private void setImagepassword(final String username,final String password) {
        getLoadingDialog("正在设置...").show();
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return AndroidClientService.mobilelogin_Imagepassword(FastLoginSettingActivity.this,username,password);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (isJsonArrary(s)){
                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        if (jsonArray.getJSONObject(0).has("success")){
                            Toast.makeText(FastLoginSettingActivity.this,jsonArray.getJSONObject(0).getString("success"),Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    MessageUtils.showErrorMessage(FastLoginSettingActivity.this,"保存手势密码失败");
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
}
