package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;

/**
 * Created by Administrator on 2017/2/15.
 * 工单管理页面
 */

public class WorkOrderActivity extends BaseActivity {
    private static final String TAG = "WorkOrderActivity";
    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;
    private TextView qxWorkorder;//缺陷工单
    private TextView jjWorkorder;//紧急工单

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workorder);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        qxWorkorder = (TextView) findViewById(R.id.workorder_qx_id);
        jjWorkorder = (TextView) findViewById(R.id.workorder_jj_id);
    }

    @Override
    protected void initView() {
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleTextView.setText("工单管理");
        qxWorkorder.setOnClickListener(qxWorkorderOnClickListener);
        jjWorkorder.setOnClickListener(jjWorkorderOnClickListener);
    }

    private View.OnClickListener qxWorkorderOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WorkOrderActivity.this, WorkorderListActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener jjWorkorderOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WorkOrderActivity.this, WorkorderJinJiActivity.class);
            startActivityForResult(intent, 0);
        }
    };
}
