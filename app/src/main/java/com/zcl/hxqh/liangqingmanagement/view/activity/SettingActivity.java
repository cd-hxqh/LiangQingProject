package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    /**设置手势**/
    private RelativeLayout FastLoginLayout;
    /**版本信息**/
    private RelativeLayout versionRelativeLayout;

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
        versionRelativeLayout = (RelativeLayout) findViewById(R.id.version_layout);
    }

    @Override
    protected void initView() {
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleTextView.setText(R.string.setting_title);

        FastLoginLayout.setOnClickListener(FastLoginOnClickListener);
        versionRelativeLayout.setOnClickListener(versionRelativeLayoutOnClickListener);
    }

    /**设置手势密码**/
    private View.OnClickListener FastLoginOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(SettingActivity.this,FastLoginSettingActivity.class);
            startActivity(intent);
        }
    };

    /**版本信息**/

    private View.OnClickListener versionRelativeLayoutOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(SettingActivity.this,VersionActivity.class);
            startActivity(intent);
        }
    };
}
