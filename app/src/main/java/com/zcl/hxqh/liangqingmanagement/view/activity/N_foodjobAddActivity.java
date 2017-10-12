package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.api.HttpManager;
import com.zcl.hxqh.liangqingmanagement.api.HttpRequestHandler;
import com.zcl.hxqh.liangqingmanagement.api.JsonUtils;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.constants.Constants;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.model.ALNDOMAIN;
import com.zcl.hxqh.liangqingmanagement.model.N_FOODJOB;
import com.zcl.hxqh.liangqingmanagement.until.AccountUtils;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.view.widght.ShareBottomDialog;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 出入仓告知记录
 */
public class N_foodjobAddActivity extends BaseActivity {
    private static String TAG = "N_foodjobAddActivity";
    private static final int TELL_CODE = 1000; //告知人
    private static final int SAFER_CODE = 1001; //安全员
    private static final int CHIEF_CODE = 1002; //带班负责人
    private static final int TELLER_CODE = 1003; //被告知人
    private static final int AQXM_CODE = 1004; //安全告知项目
    private static final int REMARK_CODE = 1005; //安全检查项目

    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮
    @Bind(R.id.title_name)
    TextView titleTextView;//标题
    @Bind(R.id.sbmittext_id)
    ImageButton submitBtn; //提交
    @Bind(R.id.loc_text_id)
    TextView locTextView; //货位号


    @Bind(R.id.reportdate_text_id) //作业日期
            TextView reportdateText;
    @Bind(R.id.tell_text_id)
    TextView tellTextView;//告知人
    @Bind(R.id.aqxm_text_id) //安全告知项目
            TextView aqxmTextView;
    @Bind(R.id.safer_text_id) //安全员
            TextView saferTextView;
    @Bind(R.id.remark_text_id) //安全检查项目
            TextView remarkTextView;
    @Bind(R.id.teller_text_id) //被告知人
            TextView tellerTextView;
    @Bind(R.id.content_text_id) //作业内容
            TextView contentTextView;
    @Bind(R.id.holder_text_id) //现场保管员
            TextView holderTextView;
    @Bind(R.id.chief_text_id) //带班负责人
            TextView chiefTextView;
    @Bind(R.id.yfxz_checkbox_id)
    CheckBox yfxzCheckBox; //用户须知


    protected FlippingLoadingDialog mLoadingDialog;


    /**
     * 日期选择器
     **/
    private DatePickerDialog datePickerDialog;
    StringBuffer sb;
    private int layoutnum;


    /**
     * 选项值
     **/
    private String[] types = null;

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private ShareBottomDialog shareBottomDialog;


    private String tell = ""; //告知人
    private String safer = ""; //安全员
    private String chief = "";//带班负责人


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_foodjob_details);
        ButterKnife.bind(this);
        initView();
        findViewById();
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }


    @Override
    protected void findViewById() {
        setDataListener();
    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.add_jl_text);
        submitBtn.setVisibility(View.VISIBLE);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String newDate = df.format(new Date());
        reportdateText.setText(newDate);
        holderTextView.setText(AccountUtils.getdisplayName(N_foodjobAddActivity.this));
    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageView() {
        finish();
    }


    //用户须知
    @OnClick(R.id.yfxz_text_id)
    void setyhxzOnClickListener() {
        Intent intent = new Intent(N_foodjobAddActivity.this, User_instructionsActivity.class);
        startActivityForResult(intent, 0);

    }


    //提交按钮
    @OnClick(R.id.sbmittext_id)
    void setSubmitBtn() {

        if (yfxzCheckBox.isChecked()) {
            getLoadingDialog(getResources().getString(R.string.seaching_text)).show();
            SubmitData(JsonUtils.encapsulationN_FOODJOB(getN_FOODJOB()));
        } else {
            MessageUtils.showMiddleToast(N_foodjobAddActivity.this, "请阅读用户须知");
        }
    }


    //选择当前人的货位号
    @OnClick(R.id.loc_text_id)
    void setLocTextView() {
        //跳转至选项值界面
        Intent intent = new Intent(N_foodjobAddActivity.this, ChooseActivity.class);
        intent.putExtra("HOLDER", AccountUtils.getloginUserName(N_foodjobAddActivity.this));
        startActivityForResult(intent, 0);
    }


    //作业日期
    @OnClick(R.id.reportdate_text_id)
    void setReportdateTextOnClickListener() {
        sb = new StringBuffer();
        layoutnum = R.id.reportdate_text_id;
        datePickerDialog.show();
    }

    //告知人
    @OnClick(R.id.tell_text_id)
    void setTellTextView() {
        Intent intent = new Intent(N_foodjobAddActivity.this, N_FoodPersonActivity.class);
        intent.putExtra("type", "TELL");
        startActivityForResult(intent, TELL_CODE);


    }

    //安全员
    @OnClick(R.id.safer_text_id)
    void setSaferTextView() {
        Intent intent = new Intent(N_foodjobAddActivity.this, N_FoodPersonActivity.class);
        intent.putExtra("type", "SAFER");
        startActivityForResult(intent, SAFER_CODE);

    }

    //安全告知项目
    @OnClick(R.id.aqxm_text_id)
    void setAqxmTextView() {
        Intent intent = new Intent(N_foodjobAddActivity.this, AlndomainChooseActivity.class);
        intent.putExtra("type", "AQXM");
        intent.putExtra("title", getString(R.string.axgzxm_text));
        startActivityForResult(intent, AQXM_CODE);
    }

    //安全检查项目
    @OnClick(R.id.remark_text_id)
    void setRemarkTextView() {
        Intent intent = new Intent(N_foodjobAddActivity.this, AlndomainChooseActivity.class);
        intent.putExtra("type", "AQXM");
        intent.putExtra("title", getString(R.string.axjcxm_text));
        startActivityForResult(intent, REMARK_CODE);
    }

    //被告知人
    @OnClick(R.id.teller_text_id)
    void setTellerTextView() {
        Intent intent = new Intent(N_foodjobAddActivity.this, N_FoodPersonActivity.class);
        intent.putExtra("type", "TELLER");
        startActivityForResult(intent, TELLER_CODE);

    }

    //作业内容
    @OnClick(R.id.content_text_id)
    void setContentTextView() {
        getOptionsValue(getString(R.string.zynr_text), Constants.CONTENT, contentTextView);
    }

    //带班负责人
    @OnClick(R.id.chief_text_id)
    void setChiefTextView() {
        Intent intent = new Intent(N_foodjobAddActivity.this, N_FoodPersonActivity.class);
        intent.putExtra("type", "CHIEF");
        startActivityForResult(intent, CHIEF_CODE);
    }

    //获取作业内容的选项值
    private void getOptionsValue(final String title, String url, final TextView textview) {
        {
            HttpManager.getData(this, HttpManager.getALNDOMAIN(url), new HttpRequestHandler<Results>() {


                @Override
                public void onSuccess(Results data) {

                }

                @Override
                public void onSuccess(Results data, int totalPages, int currentPage) {
                    ArrayList<ALNDOMAIN> item = JsonUtils.parsingALNDOMAIN(N_foodjobAddActivity.this, data.getResultlist());
                    if (item == null && item.isEmpty()) {
                        MessageUtils.showMiddleToast(N_foodjobAddActivity.this, getString(R.string.qiangyang_type_text));
                    } else {
                        types = new String[item.size()];
                        for (int i = 0; i < item.size(); i++) {
                            types[i] = item.get(i).getDESCRIPTION();
                        }
                        if (types != null && types.length != 0) {

                            showShareBottomDialog(title, types, textview);

                        } else {
                            MessageUtils.showMiddleToast(N_foodjobAddActivity.this, getString(R.string.qiangyang_type_text));
                        }

                    }

                }

                @Override
                public void onFailure(String error) {

                }
            });

        }
    }


    /**
     * 显示选项框
     **/
    private void showShareBottomDialog(String title, final String[] typesitem, final TextView textview) {
        if (null != shareBottomDialog) {
            shareBottomDialog.cancel();
            shareBottomDialog = null;
        }
        shareBottomDialog = new ShareBottomDialog(N_foodjobAddActivity.this, typesitem, null);


        shareBottomDialog.title(title)//
                .titleTextSize_SP(14.5f)//
                .show();

        shareBottomDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                {
                    textview.setText(typesitem[position]);

                    shareBottomDialog.dismiss();
                }
            }
        });


    }


    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }


    /**
     * 设置日期选择器*
     */
    private void setDataListener() {
        final Calendar objTime = Calendar.getInstance();
        int iYear = objTime.get(Calendar.YEAR);
        int iMonth = objTime.get(Calendar.MONTH);
        int iDay = objTime.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, new N_foodjobAddActivity.datelistener(), iYear, iMonth, iDay);
    }


    /**
     * 日期选择器
     **/
    private class datelistener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            sb = new StringBuffer();
            monthOfYear = monthOfYear + 1;
            String month = null;
            String day = null;
            if (monthOfYear < 10) {
                month = "0" + monthOfYear;
            } else {
                month = monthOfYear + "";
            }
            if (dayOfMonth < 10) {
                day = "0" + dayOfMonth;
            } else {
                day = dayOfMonth + "";
            }
            sb.append(year + "-" + month + "-" + day);

            if (layoutnum == R.id.reportdate_text_id) {
                reportdateText.setText(sb);

            }

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1001://货柜
                String loc = data.getExtras().getString("loc");
                locTextView.setText(loc);
                break;
            case 1000://人员
                if (requestCode == TELL_CODE) {//告知人
                    String displayname = data.getExtras().getString("displayname");
                    tell = data.getExtras().getString("personid");
                    tellTextView.setText(displayname);

                } else if (requestCode == SAFER_CODE) {//安全员
                    String displayname = data.getExtras().getString("displayname");
                    safer = data.getExtras().getString("personid");
                    saferTextView.setText(displayname);

                } else if (requestCode == CHIEF_CODE) {//带班负责人
                    String displayname = data.getExtras().getString("displayname");
                    chief = data.getExtras().getString("personid");
                    chiefTextView.setText(displayname);

                } else if (requestCode == TELLER_CODE) {//被告知人
                    String displayname = data.getExtras().getString("displayname");
                    tellerTextView.setText(displayname);

                } else if (requestCode == AQXM_CODE) {//安全告知项目
                    String description = data.getExtras().getString("description");
                    aqxmTextView.setText(description);

                } else if (requestCode == REMARK_CODE) {//安全检查项目
                    String description = data.getExtras().getString("description");
                    remarkTextView.setText(description);

                }
        }
    }


    //提交新建的信息
    private void SubmitData(final String json) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String reviseresult = AndroidClientService.postN_foodjob(N_foodjobAddActivity.this, json);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(N_foodjobAddActivity.this, s);
                finish();
            }
        }.execute();

    }


    //封装填写的数据
    private N_FOODJOB getN_FOODJOB() {
        N_FOODJOB n_foodjob = new N_FOODJOB();
        n_foodjob.setLOC(locTextView.getText().toString()); //货位号
        n_foodjob.setREPORTDATE(reportdateText.getText().toString()); //作业日期
        n_foodjob.setTELL(tell); //告知人
        n_foodjob.setAQXM(aqxmTextView.getText().toString()); //安全告知项目
        n_foodjob.setSAFER(safer); //安全员
        n_foodjob.setREMARK(remarkTextView.getText().toString()); //安全检查项目
        n_foodjob.setTELLER(tellerTextView.getText().toString()); //被告知人
        n_foodjob.setCONTENT(contentTextView.getText().toString()); //作业内容
        n_foodjob.setHOLDER(AccountUtils.getloginUserName(N_foodjobAddActivity.this)); //作业内容
        n_foodjob.setCHIEF(chief); //代办负责人
        n_foodjob.setFOODJOBNUM("");//编号
        return n_foodjob;
    }


}
