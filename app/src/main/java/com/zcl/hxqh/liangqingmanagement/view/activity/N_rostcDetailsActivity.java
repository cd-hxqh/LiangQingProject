package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.model.N_ROSTC;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 风雨雪三查详情
 */
public class N_rostcDetailsActivity extends BaseActivity {
    private static String TAG = "N_rostcDetailsActivity";

    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮
    @Bind(R.id.title_name)
    TextView titleTextView;//标题
    //风雨雪-前
    @Bind(R.id.beforedate_text_id) //日期
            TextView beforedateText;
    @Bind(R.id.isbfdrainage_chckbox_id) //排水通畅?
            CheckBox isbfdrainageCheckBox;
    @Bind(R.id.isbefordawclose_checkbox_id) //门窗关好?
            CheckBox isbefordawcloseCheckBox;
    //风雨雪-中
    @Bind(R.id.weather_text_id) //天气情况
            EditText weatherTextView;
    @Bind(R.id.positioning_text_id) //发生部位
            EditText positioningTextView;
    @Bind(R.id.takeactioning_text_id) //采取措施
            EditText takeactioningTextView;
    @Bind(R.id.isingdawclose_checkbox_id) //门窗关好?
            CheckBox isingdawcloseCheckBox;
    @Bind(R.id.isleakageofbarn_checkbox_id) //仓房渗漏?
            CheckBox isleakageofbarnCheckBox;
    @Bind(R.id.isdrain_check_id) //水沟通畅?
            CheckBox isdrainCheckBox;

    //风雨雪-后
    @Bind(R.id.latedate_text_id) //日期
            TextView latedateTextView;
    @Bind(R.id.positionlate_text_id) //发生部位
            EditText positionlateTextView;
    @Bind(R.id.takeactionlate_text_id) //采取措施
            EditText takeactionlateTextView;
    @Bind(R.id.temperature_text_id) //仓温
            EditText temperatureTextView;
    @Bind(R.id.wet_text_id) //仓湿
            EditText wetTextView;
    @Bind(R.id.isleakageofbarnlate_check_id) //仓房无渗漏?
            CheckBox isleakageofbarnlateCheckBox;


    protected FlippingLoadingDialog mLoadingDialog;

    private N_ROSTC n_rostc;

    /**
     * 日期选择器
     **/
    private DatePickerDialog datePickerDialog;
    StringBuffer sb;

    private int layoutnum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_rostc_details);
        ButterKnife.bind(this);
        geiIntentData();
        initView();
        findViewById();
    }

    private void geiIntentData() {
        n_rostc = (N_ROSTC) getIntent().getSerializableExtra("n_rostc");
    }

    @Override
    protected void findViewById() {
        setDataListener();
    }


    @Override
    protected void initView() {
        titleTextView.setText(R.string.scjl_text);

        beforedateText.setText(n_rostc.getBEFOREDATE());
        if (n_rostc.getISBFDRAINAGE().equals("Y")) {
            isbfdrainageCheckBox.setChecked(true);
        }
        if (n_rostc.getISBEFORDAWCLOSE().equals("Y")) {
            isbefordawcloseCheckBox.setChecked(true);
        }

        weatherTextView.setText(n_rostc.getWEATHER());
        positioningTextView.setText(n_rostc.getPOSITIONING());
        takeactioningTextView.setText(n_rostc.getTAKEACTIONING());
        if (n_rostc.getISINGDAWCLOSE().equals("Y")) {
            isingdawcloseCheckBox.setChecked(true);
        }
        if (n_rostc.getISLEAKAGEOFBARN().equals("Y")) {
            isleakageofbarnCheckBox.setChecked(true);
        }
        if (n_rostc.getISDRAIN().equals("Y")) {
            isdrainCheckBox.setChecked(true);
        }
        latedateTextView.setText(n_rostc.getLATEDATE());
        positionlateTextView.setText(n_rostc.getPOSITIONLATE());
        takeactionlateTextView.setText(n_rostc.getTAKEACTIONLATE());
        temperatureTextView.setText(n_rostc.getTEMPERATURE());
        wetTextView.setText(n_rostc.getWEATHER());
        if (n_rostc.getISLEAKAGEOFBARNLATE().equals("Y")) {
            isleakageofbarnlateCheckBox.setChecked(true);
        }


    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageView() {
        finish();
    }

    //风雨前日期选择
    @OnClick(R.id.beforedate_text_id)
    void setBeforedateTextOnClickListener() {
        sb = new StringBuffer();
        layoutnum = R.id.beforedate_text_id;
        datePickerDialog.show();
    }

    //风雨后日期选择
    @OnClick(R.id.latedate_text_id)
    void setLatedateTextOnClickListener() {
        sb = new StringBuffer();
        layoutnum = R.id.latedate_text_id;
        datePickerDialog.show();
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
        datePickerDialog = new DatePickerDialog(this, new N_rostcDetailsActivity.datelistener(), iYear, iMonth, iDay);
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

            if (layoutnum == R.id.beforedate_text_id) {
                beforedateText.setText(sb);
            }
            if (layoutnum == R.id.latedate_text_id) {
                latedateTextView.setText(sb);
            }
        }
    }


}
