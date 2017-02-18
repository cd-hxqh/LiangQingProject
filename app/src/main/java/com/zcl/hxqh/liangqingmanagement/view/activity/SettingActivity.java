package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;

/**
 * Created by Administrator on 2017/2/15.
 * 其他页面
 */

public class SettingActivity extends BaseActivity {
    private static final String TAG = "SettingActivity";
    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;

    private RelativeLayout FastLoginLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);

        FastLoginLayout = (RelativeLayout) findViewById(R.id.fast_login_layout);
    }

    @Override
    protected void initView() {
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleTextView.setText("设置");

        FastLoginLayout.setOnClickListener(FastLoginOnClickListener);
    }

    private View.OnClickListener FastLoginOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(SettingActivity.this,FastLoginSettingActivity.class);
            startActivity(intent);
        }
    };
}
