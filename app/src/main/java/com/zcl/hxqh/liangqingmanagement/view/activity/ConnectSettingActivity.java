package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.animation.LayoutTransition;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.api.HttpManager;
import com.zcl.hxqh.liangqingmanagement.api.HttpRequestHandler;
import com.zcl.hxqh.liangqingmanagement.api.JsonUtils;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.constants.Constants;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.model.ALNDOMAIN;
import com.zcl.hxqh.liangqingmanagement.model.N_CAR;
import com.zcl.hxqh.liangqingmanagement.model.N_TASKPLAN;
import com.zcl.hxqh.liangqingmanagement.until.AccountUtils;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.until.T;
import com.zcl.hxqh.liangqingmanagement.view.widght.ShareBottomDialog;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * 连接设置
 */
public class ConnectSettingActivity extends BaseActivity {
    private static String TAG = "ConnectSettingActivity";

    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;

    private EditText ipadressEditView;//地址输入框
    private ImageView qrCodeImageView;//二维码扫描
    private TextView internetTextView;//网络设置
    private Button update_adressBtn;//更新地址
    private Button connect_testBtn;//连接测试


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectsetting_details);
        findViewById();
        initView();

    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);

        ipadressEditView = (EditText) findViewById(R.id.ipadress);
        qrCodeImageView = (ImageView) findViewById(R.id.code_img);
        internetTextView = (TextView) findViewById(R.id.internet_setting);
        update_adressBtn = (Button) findViewById(R.id.update_adress);
        connect_testBtn = (Button) findViewById(R.id.connect_test);
    }


    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.connect_setting_title);

        ipadressEditView.setText(AccountUtils.getIpAddress(ConnectSettingActivity.this));

        update_adressBtn.setOnClickListener(updateOnClickListener);
        connect_testBtn.setOnClickListener(connectOnClickListener);
    }

    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener connectOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ipadressEditView.getText().toString().equals(Constants.HTTP_API_IP)
                    ||ipadressEditView.getText().toString().equals(Constants.HTTPZHENGSHI_API_IP)){
                MessageUtils.showMiddleToast(ConnectSettingActivity.this,"测试通过!");
            }else {
                MessageUtils.showErrorMessage(ConnectSettingActivity.this,"测试未通过!");
            }
        }
    };

    private View.OnClickListener updateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ipadressEditView.getText().toString().equals(Constants.HTTP_API_IP)
                    ||ipadressEditView.getText().toString().equals(Constants.HTTPZHENGSHI_API_IP)) {
                AccountUtils.setIpAddress(ConnectSettingActivity.this, ipadressEditView.getText().toString());
                MessageUtils.showMiddleToast(ConnectSettingActivity.this,"保存成功!");
            }else {
                MessageUtils.showErrorMessage(ConnectSettingActivity.this,"请输入正确地址!");
            }
        }
    };

}
