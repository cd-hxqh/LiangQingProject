package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.INVUSELINE;


/**
 * 实际领用
 */
public class InvuseLine1DetailsActivity extends BaseActivity {
    private static String TAG = "InvuseLine1DetailsActivity";

    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;


    /**
     * 界面信息
     **/

    private TextView itemnumText; //项目

    private TextView numTitle; //编号
    private TextView rotassetnumText; //移动设备编号

    private TextView descTitle; //描述
    private TextView rotassetnumdescText; //移动设备描述

    private TextView issuetoText; //借用人

    private TextView issuetonameText; //借用人名称

    private TextView locationdescText; //位置

    private TextView actualdateText; //实际日期


    private INVUSELINE invuseline;
    private int mark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invuseline1_details);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        mark = getIntent().getExtras().getInt("mark");
        invuseline = (INVUSELINE) getIntent().getSerializableExtra("invuseline");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);


        itemnumText = (TextView) findViewById(R.id.itemnum_text_id);
        numTitle = (TextView) findViewById(R.id.num_title_id);
        rotassetnumText = (TextView) findViewById(R.id.rotassetnum_text_id);
        rotassetnumdescText = (TextView) findViewById(R.id.rotassetnumdesc_text_id);
        descTitle = (TextView) findViewById(R.id.desc_title_id);
        issuetoText = (TextView) findViewById(R.id.jyr_text_id);
        issuetonameText = (TextView) findViewById(R.id.issuetoname_text_id);
        locationdescText = (TextView) findViewById(R.id.loctation_text_id);
        actualdateText = (TextView) findViewById(R.id.actualdate_text_id);
    }


    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.invuseline1_text);
        if (mark == MainActivity.ITEMNUM_CODE) {
            numTitle.setText(R.string.gj_num);
            descTitle.setText(R.string.gjms_text);
        }
        if (invuseline != null) {
            itemnumText.setText(invuseline.getITEMNUM());
            rotassetnumText.setText(invuseline.getROTASSETNUM());
            rotassetnumdescText.setText(invuseline.getROTASSETNUMDESC());
            issuetoText.setText(invuseline.getISSUETO());
            issuetonameText.setText(invuseline.getISSUETONAME());
            locationdescText.setText(invuseline.getLOCATIONDESC());
            actualdateText.setText(invuseline.getACTUALDATE());
        }
    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

}
