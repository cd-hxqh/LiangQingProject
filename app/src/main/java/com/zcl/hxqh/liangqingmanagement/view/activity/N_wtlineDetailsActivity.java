package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.N_WTLINE;


/**
 * 考勤管理
 */
public class N_wtlineDetailsActivity extends BaseActivity {
    private static String TAG = "N_wtlineDetailsActivity";

    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;

    //姓名/车号
    private TextView nameText;
    //作业开始时间
    private TextView startText;
    //作业结束时间
    private TextView endText;
    //状态
    private TextView statusText;
    //一卡通编号
    private TextView cardidText;
    //IP
    private TextView ipText;

    private N_WTLINE n_wtline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wtline_details);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        n_wtline = (N_WTLINE) getIntent().getSerializableExtra("n_wtline");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);


        nameText = (TextView) findViewById(R.id.kq_name_text_id);
        startText = (TextView) findViewById(R.id.start_text_id);
        endText = (TextView) findViewById(R.id.end_text_id);
        statusText = (TextView) findViewById(R.id.status_id);
        cardidText = (TextView) findViewById(R.id.cardid_text_id);
        ipText = (TextView) findViewById(R.id.ip_text_id);
    }


    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.wiline_text);

        nameText.setText(n_wtline.getNAME());
        startText.setText(n_wtline.getSTART());
        endText.setText(n_wtline.getEND());
        statusText.setText(n_wtline.getSTATUS());
        cardidText.setText(n_wtline.getCARDID());
        ipText.setText(n_wtline.getIP());


    }


    private View.OnClickListener backImageViewOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

}
