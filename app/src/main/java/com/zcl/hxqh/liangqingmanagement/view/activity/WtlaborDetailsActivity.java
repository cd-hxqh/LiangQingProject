package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.WTLABOR;


/**
 * 用工记录详情
 */
public class WtlaborDetailsActivity extends BaseActivity {
    private static String TAG = "WtlaborDetailsActivity";

    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;

    /**
     * 用工申请编号
     **/
    private TextView applynumTextView;
    /**
     * 工作地点
     **/
    private TextView placeTextView;
    /**
     * 作业类型
     **/
    private TextView tasktypeTextView;
    /**
     * 开始时间
     **/
    private TextView starttimeTextView;
    /**
     * 结束时间
     **/
    private TextView endtimeTextView;
    /**
     * 状态
     **/
    private TextView statusTextView;


    /**
     * WTLABOR
     **/
    private WTLABOR wtlabor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wtlabor_details);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        wtlabor = (WTLABOR) getIntent().getSerializableExtra("wtlabor");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);

        applynumTextView = (TextView) findViewById(R.id.applynum_text_id);
        placeTextView = (TextView) findViewById(R.id.place_text_id);
        tasktypeTextView = (TextView) findViewById(R.id.tasktype_text_id);
        starttimeTextView = (TextView) findViewById(R.id.starttime_text_id);
        endtimeTextView = (TextView) findViewById(R.id.endtime_text_id);
        statusTextView = (TextView) findViewById(R.id.status_text_id);
    }


    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.wtlabor_detail_text);

        applynumTextView.setText(wtlabor.getAPPLYNUM());
        placeTextView.setText(wtlabor.getPLACE());
        tasktypeTextView.setText(wtlabor.getTASKTYPE());
        starttimeTextView.setText(wtlabor.getSTARTTIME());
        endtimeTextView.setText(wtlabor.getENDTIME());
        statusTextView.setText(wtlabor.getSTATUS());


    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

}
