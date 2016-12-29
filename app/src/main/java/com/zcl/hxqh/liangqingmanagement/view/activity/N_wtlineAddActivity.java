package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

/**
 * 考勤管理新增
 **/
public class N_wtlineAddActivity extends BaseActivity {


    /**
     * 返回按钮
     **/
    private ImageView backImageView;
    /**
     * 标题
     **/
    private TextView titleTextView;
    /**
     * 提交按钮
     **/
    private Button submitBtn;


    /**
     * 一卡通编号
     **/
    private TextView cardIdTextView;
    /**
     * nfc扫描
     **/
    private ImageView nfcImageView;
    /**
     * 考勤机IP
     **/
    private TextView ipTextView;

    private String imei;


    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;


    protected FlippingLoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n_wtline_add);
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();

    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        submitBtn = (Button) findViewById(R.id.sbmit_id);


        cardIdTextView = (TextView) findViewById(R.id.cardid_text_id);
        nfcImageView = (ImageView) findViewById(R.id.nfc_image_id);
        ipTextView = (TextView) findViewById(R.id.ip_text_id);
    }

    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.add_xinjian);
        submitBtn.setVisibility(View.VISIBLE);
        nfcImageView.setOnClickListener(nfcImageViewOnClickListener);

        imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
                .getDeviceId();

        ipTextView.setText(imei);
        submitBtn.setOnClickListener(submitBtnOnClickListener);
    }


    /**
     * 返回
     **/
    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };


    /**
     * nfc
     **/
    private View.OnClickListener nfcImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //跳转至NFC扫描界面
            Intent intent = new Intent(N_wtlineAddActivity.this, Nfc_Activity.class);
            startActivityForResult(intent, 0);
        }
    };


    /****/
    private View.OnClickListener submitBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            submitNormalDialog();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {

            case 1002:
                String tagId = data.getExtras().getString("tagId");
                cardIdTextView.setText(tagId);
                break;

        }
    }


    /**
     * 提交数据*
     */
    private void submitNormalDialog() {

        final NormalDialog dialog = new NormalDialog(N_wtlineAddActivity.this);
        dialog.content("确定提交数据吗？")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {


                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        getLoadingDialog("正在提交");

                        startAsyncTask(cardIdTextView.getText().toString(), ipTextView.getText().toString());

                        dialog.dismiss();
                    }
                });
    }


    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }


    /**
     * 提交数据*
     */
    private void startAsyncTask(final String cardId, final String id) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {

                String reviseresult = AndroidClientService.addN_WTLINE(N_wtlineAddActivity.this, cardId, id);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(N_wtlineAddActivity.this, s);
                finish();


            }
        }.execute();


    }


}





