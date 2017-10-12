package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 出入仓告知记录
 */
public class User_instructionsActivity extends BaseActivity {
    private static String TAG = "N_foodjobDetailsActivity";
    private static final int TELL_CODE = 1000; //告知人
    private static final int SAFER_CODE = 1001; //安全员
    private static final int CHIEF_CODE = 1002; //带班负责人

    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮
    @Bind(R.id.title_name)
    TextView titleTextView;//标题


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_instructions);
        ButterKnife.bind(this);
        findViewById();
    }

    @Override
    protected void findViewById() {
        titleTextView.setText(R.string.yhxz_text);
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.title_back_id)
    void setBackImageView() {
        finish();
    }
}
