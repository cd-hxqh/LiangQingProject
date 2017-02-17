package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;

/**
 * Created by Administrator on 2017/2/15.
 * 粮情管理页面
 */

public class CclqjcdActivity extends BaseActivity {
    private static final String TAG = "CclqjcdActivity";
    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;
    private TextView lqjcText;//粮情检查
    private TextView sbssaqwsjc_text;//设备设施安全卫生检查
    private TextView zyxcjcText;//作业现场检查

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lqjcd);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        lqjcText = (TextView) findViewById(R.id.lqjc_id);
        sbssaqwsjc_text = (TextView) findViewById(R.id.sbwsjc_id);
        zyxcjcText = (TextView) findViewById(R.id.zyxcjc_id);
    }

    @Override
    protected void initView() {
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleTextView.setText("仓储基础工作");
        lqjcText.setOnClickListener(OnClickListener);
        sbssaqwsjc_text.setOnClickListener(OnClickListener);
        zyxcjcText.setOnClickListener(OnClickListener);
    }

    private View.OnClickListener OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(CclqjcdActivity.this, N_grainjcListActivity.class);
            switch (v.getId()) {
                case R.id.lqjc_id:
                    intent.putExtra("worktype","粮情检查");
                    startActivityForResult(intent, 0);
                    break;
                case R.id.sbwsjc_id:
                    intent.putExtra("worktype","设备设施安全卫生检查");
                    startActivityForResult(intent, 0);
                    break;
                case R.id.zyxcjc_id:
                    intent.putExtra("worktype","作业现场检查");
                    startActivityForResult(intent, 0);
                    break;
            }

        }
    };
}
