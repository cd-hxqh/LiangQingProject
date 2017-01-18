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
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.until.GestureLockViewGroup;
import com.zcl.hxqh.liangqingmanagement.until.GestureLockViewGroup.OnGestureLockViewListener;


/**
 * 手势登陆
 */
public class FastLoginActivity extends BaseActivity {
    private TextView ConnectSetting;//连接设置
    private GestureLockViewGroup mGestureLockViewGroup;

    private TextView cardloginText;//员工卡登录
    private TextView passwordloginText;//密码登录

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
        mGestureLockViewGroup.setAnswer(new int[]{1, 2, 3, 4, 5});
        mGestureLockViewGroup
                .setOnGestureLockViewListener(new OnGestureLockViewListener() {

                    @Override
                    public void onUnmatchedExceedBoundary() {
                        Toast.makeText(FastLoginActivity.this, "错误5次...",
                                Toast.LENGTH_SHORT).show();
                        mGestureLockViewGroup.setUnMatchExceedBoundary(5);
                    }

                    @Override
                    public void onGestureEvent(boolean matched) {
                        Toast.makeText(FastLoginActivity.this, matched + "",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onBlockSelected(int cId) {
                    }
                });
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

}
