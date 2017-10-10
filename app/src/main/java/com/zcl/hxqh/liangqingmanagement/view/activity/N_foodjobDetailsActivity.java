package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.model.N_FOODJOB;
import com.zcl.hxqh.liangqingmanagement.until.AccountUtils;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 出入仓告知记录
 */
public class N_foodjobDetailsActivity extends BaseActivity {
    private static String TAG = "N_foodjobDetailsActivity";
    private static final int TELL_CODE = 1000; //告知人
    private static final int SAFER_CODE = 1001; //安全员

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


    protected FlippingLoadingDialog mLoadingDialog;

    private N_FOODJOB n_foodjob;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_foodjob_details);
        ButterKnife.bind(this);
        geiIntentData();
        initView();
        findViewById();
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void geiIntentData() {
        n_foodjob = (N_FOODJOB) getIntent().getSerializableExtra("n_foodjob");
    }

    @Override
    protected void findViewById() {
        setDataListener();
    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.jlxq_text);
        submitBtn.setVisibility(View.VISIBLE);
        locTextView.setText(n_foodjob.getLOC());
        reportdateText.setText(n_foodjob.getREPORTDATE());
        tellTextView.setText(n_foodjob.getTELLDISPLAYNAME());
        aqxmTextView.setText(n_foodjob.getAQXM());
        saferTextView.setText(n_foodjob.getSAFERDISPLAYNAME());
        remarkTextView.setText(n_foodjob.getREMARK());
        tellerTextView.setText(n_foodjob.getTELLER());
        contentTextView.setText(n_foodjob.getCONTENT());
        holderTextView.setText(n_foodjob.getHOLDERDISPLAYNAME());
        chiefTextView.setText(n_foodjob.getCHIEFDISPLAYNAME());


    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageView() {
        finish();
    }

    //提交按钮
    @OnClick(R.id.sbmittext_id)
    void setSubmitBtn() {
//        getLoadingDialog(getResources().getString(R.string.seaching_text)).show();
//        SubmitData(JsonUtils.encapsulationN_ROSTC(getN_rostc()));
    }


    //选择当前人的货位号
    @OnClick(R.id.loc_text_id)
    void setLocTextView() {
        //跳转至选项值界面
        Intent intent = new Intent(N_foodjobDetailsActivity.this, ChooseActivity.class);
        intent.putExtra("HOLDER", AccountUtils.getloginUserName(N_foodjobDetailsActivity.this));
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
        Intent intent = new Intent(N_foodjobDetailsActivity.this, PersonChooseActivity.class);
        intent.putExtra("condition", "'DEPARTMENT':'=仓储管理科,=监督检查科,=副主任办公室','STATUS':'=活动'");
        startActivityForResult(intent, TELL_CODE);

    }

    //安全员
    @OnClick(R.id.safer_text_id)
    void setSaferTextView() {
        Intent intent = new Intent(N_foodjobDetailsActivity.this, PersonChooseActivity.class);
        intent.putExtra("condition", "'DEPARTMENT':'=监督检查科','STATUS':'=活动'");
        startActivityForResult(intent, SAFER_CODE);

    }

    //安全告知项目
    @OnClick(R.id.aqxm_text_id)
    void setAqxmTextView() {
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
        datePickerDialog = new DatePickerDialog(this, new N_foodjobDetailsActivity.datelistener(), iYear, iMonth, iDay);
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
                    String personid = data.getExtras().getString("personid");
                    tellTextView.setText(displayname);

                } else if (requestCode == SAFER_CODE) {//安全员
                    String displayname = data.getExtras().getString("displayname");
                    String personid = data.getExtras().getString("personid");
                    saferTextView.setText(displayname);

                }
        }
    }


    //提交新建的信息
    private void SubmitData(final String json) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String reviseresult = AndroidClientService.postN_rostc(N_foodjobDetailsActivity.this, json);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(N_foodjobDetailsActivity.this, s);
                finish();
            }
        }.execute();

    }


}
