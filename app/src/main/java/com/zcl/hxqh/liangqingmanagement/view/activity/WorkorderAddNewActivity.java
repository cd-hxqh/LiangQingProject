package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.animation.LayoutTransition;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.api.HttpManager;
import com.zcl.hxqh.liangqingmanagement.api.HttpRequestHandler;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.model.WORKORDER;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * 缺陷工单详情
 */
public class WorkorderAddNewActivity extends BaseActivity {
    private static String TAG = "WorkorderAddNewActivity";

    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;

    /**
     * 提交按钮
     **/
    private Button submitBtn;

    private EditText sb;//设备
    private EditText sbwz;//设备位置
    private ImageView photoImg;//拍照
    private EditText description;//缺陷描述
    private Spinner fxbm;//发现部门
    private Spinner fxr;//发现人
    private EditText reportdate;//发现时间
    private EditText zrr;//责任人
    private EditText schedfinish;//整改期限
    private EditText n_recreq;//整改要求
    private CheckBox worktype;//是否排查
    private EditText remarkdesc;//备注


    private WORKORDER workorder;


    /**界面信息**/

    /**
     * 时间选择器
     **/
    private DatePickerDialog datePickerDialog;
    StringBuffer stringBuffer;
    private int layoutnum;


    //弹出框
    private ArrayList<DialogMenuItem> mMenuItems = new ArrayList<>();


    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;


    protected FlippingLoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workorder_details);
        geiIntentData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void geiIntentData() {
        workorder = (WORKORDER) getIntent().getSerializableExtra("workorder");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        submitBtn = (Button) findViewById(R.id.sbmit_id);

        sb = (EditText) findViewById(R.id.workorder_sb);
        sbwz = (EditText) findViewById(R.id.workorder_sbwz);
        photoImg = (ImageView) findViewById(R.id.photo_image);
        description = (EditText) findViewById(R.id.workorder_description);
        fxbm = (Spinner) findViewById(R.id.Spinner_fxbm);
        fxr = (Spinner) findViewById(R.id.Spinner_fxr);
        reportdate = (EditText) findViewById(R.id.workorder_reportdate);
        zrr = (EditText) findViewById(R.id.workorder_zrr);
        schedfinish = (EditText) findViewById(R.id.workorder_schedfinish);
        n_recreq = (EditText) findViewById(R.id.workorder_n_recreq);
        worktype = (CheckBox) findViewById(R.id.workorder_worktype);
        remarkdesc = (EditText) findViewById(R.id.workorder_remarkdesc);

        ViewGroup container = (ViewGroup) findViewById(R.id.container);
        LayoutTransition transition = new LayoutTransition();
        container.setLayoutTransition(transition);
    }


    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.workorder_addnew_title);
        submitBtn.setVisibility(View.VISIBLE);
        submitBtn.setOnClickListener(submitBtnBtnOnClickListener);


        setDataListener();
    }

    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    private View.OnClickListener submitBtnBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            submitNormalDialog();
        }
    };


    //获取选项值
    private void getOptionsValue(final String title, String url, final TextView textview) {
        {
            HttpManager.getData(this, url, new HttpRequestHandler<Results>() {
                @Override
                public void onSuccess(Results data) {

                }

                @Override
                public void onSuccess(Results data, int totalPages, int currentPage) {
//                    ArrayList<ALNDOMAIN> item = JsonUtils.parsingALNDOMAIN(WorkorderDetailsActivity.this, data.getResultlist());
//                    if (item == null || item.isEmpty()) {
//                        MessageUtils.showMiddleToast(WorkorderDetailsActivity.this, getString(R.string.qiangyang_type_text));
//                    } else {
//                        types = new String[item.size()];
//                        for (int i = 0; i < item.size(); i++) {
//                            types[i] = item.get(i).getDESCRIPTION();
//                        }
//                        if (types != null && types.length != 0) {
//                            showShareBottomDialog(title, types, textview);
//                        } else {
//                            MessageUtils.showMiddleToast(WorkorderDetailsActivity.this, getString(R.string.qiangyang_type_text));
//                        }
//
//                    }

                }

                @Override
                public void onFailure(String error) {

                }
            });

        }
    }

//    /**
//     * 显示选项框
//     **/
//    private void showShareBottomDialog(String title, final String[] typesitem, final TextView textview) {
//
//        final ShareBottomDialog dialog = new ShareBottomDialog(WorkorderDetailsActivity.this, types, null);
//
//
//        dialog.title(title)//
//                .titleTextSize_SP(14.5f)//
//                .show();
//
//        dialog.setOnOperItemClickL(new OnOperItemClickL() {
//            @Override
//            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                T.showShort(WorkorderDetailsActivity.this, typesitem[position]);
//                textview.setText(typesitem[position]);
//                dialog.dismiss();
//            }
//        });
//    }


    /**
     * 设置时间选择器*
     */
    private void setDataListener() {

        final Calendar objTime = Calendar.getInstance();
        int iYear = objTime.get(Calendar.YEAR);
        int iMonth = objTime.get(Calendar.MONTH);
        int iDay = objTime.get(Calendar.DAY_OF_MONTH);
        int hour = objTime.get(Calendar.HOUR_OF_DAY);

        int minute = objTime.get(Calendar.MINUTE);


        datePickerDialog = new DatePickerDialog(this, new datelistener(), iYear, iMonth, iDay);
    }


    /**
     * 日期选择器
     **/
    private class MydateListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            layoutnum = 0;
            stringBuffer = new StringBuffer();
            layoutnum = view.getId();
            datePickerDialog.show();
        }
    }


    private class datelistener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            stringBuffer = new StringBuffer();
            monthOfYear = monthOfYear + 1;
            if (dayOfMonth < 10) {
                stringBuffer.append(year % 100 + "-" + monthOfYear + "-" + "0" + dayOfMonth);
            } else {
                stringBuffer.append(year % 100 + "-" + monthOfYear + "-" + dayOfMonth);
            }

//            enterdateText.setText(sb.toString());
        }
    }

    //提交弹出框

    /**
     * 提交数据*
     */
    private void submitNormalDialog() {

        final NormalDialog dialog = new NormalDialog(WorkorderAddNewActivity.this);
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
//                        startAsyncTask();

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
//    private void startAsyncTask() {
//        new AsyncTask<String, String, String>() {
//            @Override
//            protected String doInBackground(String... strings) {
//                return AndroidClientService.addAndUpdateN_CAR(WorkorderDetailsActivity.this, JsonUtils.encapsulationN_CAR(getN_CAR(), null));
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                mLoadingDialog.dismiss();
//                MessageUtils.showMiddleToast(WorkorderDetailsActivity.this, s);
////                if (s.equals("修改成功")) {
////                    finish();
////                }
//            }
//        }.execute();
//
//
//    }


//    /**
//     * 封装数据
//     **/
//    private N_CAR getN_CAR() {
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1001:
                break;
        }
    }

}
