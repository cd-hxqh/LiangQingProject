package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/15.
 * 粮情管理页面
 */

public class CclqjcdActivity extends BaseActivity {
    private static final String TAG = "CclqjcdActivity";

    @Bind(R.id.title_name)
    TextView titleTextView;//标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lqjcd);
        ButterKnife.bind(this);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
    }

    @Override
    protected void initView() {
        titleTextView.setText(getString(R.string.ccjcgz_text));
    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageView() {
        finish();
    }

    //粮情检查
    @OnClick(R.id.lqjc_id)
    void setLqjcTextOnClickListener() {
        Intent intent = new Intent(CclqjcdActivity.this, N_grainjcListActivity.class);
        intent.putExtra("worktype", getString(R.string.lqjc_text));
        startActivityForResult(intent, 0);
    }

    //设备设施安全卫生检查
    @OnClick(R.id.sbwsjc_id)
    void setSbwsjcTextOnClickListener() {
        Intent intent = new Intent(CclqjcdActivity.this, N_grainjcListActivity.class);
        intent.putExtra("worktype", getString(R.string.sbssjc_text));
        startActivityForResult(intent, 0);
    }

    //作业现场检查
    @OnClick(R.id.zyxcjc_id)
    void setZyxcjcTextOnClickListener() {
        Intent intent = new Intent(CclqjcdActivity.this, N_grainjcListActivity.class);
        intent.putExtra("worktype", getString(R.string.aqjc_text));
        startActivityForResult(intent, 0);
    }

    //风雨雪三查
    @OnClick(R.id.fyx_text_id)
    void setFyxTextOnClickListener() {
        Intent intent = new Intent(CclqjcdActivity.this, N_rostcActivity.class);
        startActivityForResult(intent, 0);
    }

}
