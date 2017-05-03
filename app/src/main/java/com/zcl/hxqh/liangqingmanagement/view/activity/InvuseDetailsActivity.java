package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.model.INVUSE;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 移动设备借用详情
 */
public class InvuseDetailsActivity extends BaseActivity {
    private static String TAG = "InvuseDetailsActivity";

    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;

    /**
     * 菜单
     */
    private ImageView menuImageView;


    /**
     * 界面信息
     **/

    private TextView invusenumText; //编号

    private TextView descriptionText; //描述

    private TextView productionplansnumText; //计划编号

    private TextView plandescText; //计划描述

    private TextView enterbyText; //录入人

    private TextView enterdateText; //录入时间


    private INVUSE invuse;

    private PopupWindow popupWindow;

    private int mark;

    protected FlippingLoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invuse_details);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        mark = getIntent().getExtras().getInt("mark");
        invuse = (INVUSE) getIntent().getSerializableExtra("invuse");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        menuImageView = (ImageView) findViewById(R.id.title_add);


        invusenumText = (TextView) findViewById(R.id.invusenum_text_id);
        descriptionText = (TextView) findViewById(R.id.item_desc_text_id);
        productionplansnumText = (TextView) findViewById(R.id.productionplansnum_text_id);
        plandescText = (TextView) findViewById(R.id.plandesc_text_id);
        enterbyText = (TextView) findViewById(R.id.enterby_text_id);
        enterdateText = (TextView) findViewById(R.id.enterdate_text_id);
    }


    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        if (mark == MainActivity.ROTASSETNUM_CODE) {

            titleTextView.setText(R.string.ydsbjy_text);
        } else if (mark == MainActivity.ITEMNUM_CODE) {
            titleTextView.setText(R.string.gjjy_text);
        }
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setImageResource(R.drawable.ic_more);
        menuImageView.setOnClickListener(menuImageViewOnClickListener);

        if (invuse != null) {
            invusenumText.setText(invuse.getINVUSENUM());
            descriptionText.setText(invuse.getDESCRIPTION());
            productionplansnumText.setText(invuse.getPRODUCTIONPLANSNUM());
            plandescText.setText(invuse.getPLANDESC());
            enterbyText.setText(invuse.getENTERBY());
            enterdateText.setText(invuse.getENTERDATE());
        }
    }


    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }


    @OnClick(R.id.status_btn_id)
    void setUpdateStatusOnClickListener() {
        getLoadingDialog("正在修改").show();
        startAsyncTask();
    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener menuImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupWindow(menuImageView);
        }
    };


    private void showPopupWindow(View view) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(InvuseDetailsActivity.this).inflate(
                R.layout.invuse_powindow, null);


        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.popup_background_mtrl_mult));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

        TextView actualTextView = (TextView) contentView.findViewById(R.id.invuseline_text_id);
//        planTextView.setOnClickListener(planTextViewOnClickListener);
        actualTextView.setOnClickListener(actualTextViewOnClickListener);

    }


    private View.OnClickListener planTextViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MessageUtils.showMiddleToast(InvuseDetailsActivity.this, "计划领用尚在开发中...");
            popupWindow.dismiss();
        }
    };

    private View.OnClickListener actualTextViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            intent.setClass(InvuseDetailsActivity.this, InvuseLineActivity.class);
            intent.putExtra("invusenum", invuse.getINVUSENUM());
            startActivityForResult(intent, 0);
            popupWindow.dismiss();
        }
    };


    /**
     * 修改状态*
     */
    private void startAsyncTask() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String reviseresult = null;
                reviseresult = AndroidClientService.MobileBorrowChangeStatus(InvuseDetailsActivity.this, invuse.getINVUSENUM());
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                if (s.equals("")) {
                    MessageUtils.showMiddleToast(InvuseDetailsActivity.this, "修改失败");
                } else {
                    MessageUtils.showMiddleToast(InvuseDetailsActivity.this, s);
                }


            }
        }.execute();


    }


}
