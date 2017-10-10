package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.zcl.hxqh.liangqingmanagement.model.N_ROSTC;
import com.zcl.hxqh.liangqingmanagement.until.AccountUtils;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.view.widght.ShareBottomDialog;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 风雨雪三查新增
 */
public class N_rostcAddActivity extends BaseActivity {
    private static String TAG = "N_rostcAddActivity";

    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮
    @Bind(R.id.title_name)
    TextView titleTextView;//标题
    @Bind(R.id.sbmittext_id)
    ImageButton submitBtn; //提交
    @Bind(R.id.loc_text_id)
    TextView locTextView; //货位号

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


    /**
     * 选项值
     **/
    private String[] types = null;

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;


    private ShareBottomDialog shareBottomDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_rostc_details);
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


    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageView() {
        finish();
    }

    //提交按钮
    @OnClick(R.id.sbmittext_id)
    void setSubmitBtn() {
        if (isBoolean()) {
            getLoadingDialog(getResources().getString(R.string.seaching_text)).show();
            SubmitData(JsonUtils.encapsulationN_ROSTC(getN_rostc()));
        }

    }

    //选择当前人的货位号
    @OnClick(R.id.loc_text_id)
    void setLocTextView() {
        //跳转至选项值界面
        Intent intent = new Intent(N_rostcAddActivity.this, ChooseActivity.class);
        intent.putExtra("HOLDER", AccountUtils.getloginUserName(N_rostcAddActivity.this));
        startActivityForResult(intent, 0);
    }


    //风雨前日期选择
    @OnClick(R.id.beforedate_text_id)
    void setBeforedateTextOnClickListener() {
        sb = new StringBuffer();
        layoutnum = R.id.beforedate_text_id;
        datePickerDialog.show();
    }

    //发生部位(中)
    @OnClick(R.id.positioning_image_id)
    void setPositioningTextView() {
        getOptionsValue(getResources().getString(R.string.positioning_text_1), Constants.POSITIONING, positioningTextView);
    }

    //采取措施(中)
    @OnClick(R.id.takeactioning_image_id)
    void setTakeactioningTextView() {
        getOptionsValue(getResources().getString(R.string.takeactioning_text_1), Constants.TAKEACTIONING, takeactioningTextView);
    }

    //风雨后日期选择
    @OnClick(R.id.latedate_text_id)
    void setLatedateTextOnClickListener() {
        sb = new StringBuffer();
        layoutnum = R.id.latedate_text_id;
        datePickerDialog.show();
    }


    //发生部位(后)
    @OnClick(R.id.positionlate_image_id)
    void setpositionlateTextView() {
        getOptionsValue(getResources().getString(R.string.positioning_text_1), Constants.POSITIONING, positionlateTextView);
    }

    //采取措施(后)
    @OnClick(R.id.takeactionlate_image_id)
    void settakeactionlateTextView() {
        getOptionsValue(getResources().getString(R.string.takeactioning_text_1), Constants.TAKEACTIONING, takeactionlateTextView);
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
        datePickerDialog = new DatePickerDialog(this, new N_rostcAddActivity.datelistener(), iYear, iMonth, iDay);
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
                getLoadingDialog(getResources().getString(R.string.seaching_text)).show();
                setWeather(sb.toString());
            }
            if (layoutnum == R.id.latedate_text_id) {
                latedateTextView.setText(sb);
                getLoadingDialog(getResources().getString(R.string.seaching_text)).show();
                setAWeather(sb.toString(), locTextView.getText().toString());
            }
        }
    }


    //根据风雨前日期获取天气的信息
    private void setWeather(final String beforedate) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String reviseresult = AndroidClientService.postWEATHER(N_rostcAddActivity.this, beforedate);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                weatherTextView.setText(s);

            }
        }.execute();


    }


    //根据风雨后日期与货位号获取仓温仓湿的信息

    private void setAWeather(final String beforedate, final String loc) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String reviseresult = AndroidClientService.postAweather(N_rostcAddActivity.this, beforedate, loc);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                Log.e(TAG, "s=" + s);

                temperatureTextView.setText(jxwetData(s)[0]);
                wetTextView.setText(jxwetData(s)[1]);

            }
        }.execute();


    }


    //获取发生部位与采取措施的选项值
    private void getOptionsValue(final String title, String url, final TextView textview) {
        {
            HttpManager.getData(this, HttpManager.getALNDOMAIN(url), new HttpRequestHandler<Results>() {


                @Override
                public void onSuccess(Results data) {

                }

                @Override
                public void onSuccess(Results data, int totalPages, int currentPage) {
                    ArrayList<ALNDOMAIN> item = JsonUtils.parsingALNDOMAIN(N_rostcAddActivity.this, data.getResultlist());
                    if (item == null && item.isEmpty()) {
                        MessageUtils.showMiddleToast(N_rostcAddActivity.this, getString(R.string.qiangyang_type_text));
                    } else {
                        types = new String[item.size()];
                        for (int i = 0; i < item.size(); i++) {
                            types[i] = item.get(i).getDESCRIPTION();
                        }
                        if (types != null && types.length != 0) {

                            showShareBottomDialog(title, types, textview);

                        } else {
                            MessageUtils.showMiddleToast(N_rostcAddActivity.this, getString(R.string.qiangyang_type_text));
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
        shareBottomDialog = new ShareBottomDialog(N_rostcAddActivity.this, typesitem, null);


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


    /**
     * 解析仓温仓湿
     **/
    private String[] jxwetData(String s) {
        String[] data = new String[2];
        if (null != s) {
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    if (jsonArray.get(i).toString().equals("暂无数据")) {
                        data[i] = "";
                    } else {
                        data[i] = jsonArray.get(i).toString();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return data;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1001:
                String loc = data.getExtras().getString("loc");
                locTextView.setText(loc);
                break;
        }
    }


    //提交新建的信息
    private void SubmitData(final String json) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String reviseresult = AndroidClientService.postN_rostc(N_rostcAddActivity.this, json);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(N_rostcAddActivity.this, s);
                finish();
            }
        }.execute();


    }


    //封装获取到的数据
    private N_ROSTC getN_rostc() {
        N_ROSTC n_rostc = new N_ROSTC();
        n_rostc.setLOC(locTextView.getText().toString());//货位号
        n_rostc.setBEFOREDATE(beforedateText.getText().toString());//风雨前（日期）
        if (isbfdrainageCheckBox.isChecked()) {
            n_rostc.setISBFDRAINAGE("Y");//排水通畅?
        } else {
            n_rostc.setISBFDRAINAGE("N");
        }
        if (isbefordawcloseCheckBox.isChecked()) {
            n_rostc.setISBEFORDAWCLOSE("Y");//排水通畅?
        } else {
            n_rostc.setISBEFORDAWCLOSE("N");
        }
        n_rostc.setWEATHER(weatherTextView.getText().toString());//天气
        n_rostc.setPOSITIONING(positioningTextView.getText().toString());//发生部位
        n_rostc.setTAKEACTIONING(takeactioningTextView.getText().toString()); //采取措施
        if (isingdawcloseCheckBox.isChecked()) {
            n_rostc.setISINGDAWCLOSE("Y");//门窗关好?
        } else {
            n_rostc.setISINGDAWCLOSE("N");
        }
        if (isleakageofbarnCheckBox.isChecked()) {
            n_rostc.setISLEAKAGEOFBARN("Y"); //仓房渗漏?
        } else {
            n_rostc.setISLEAKAGEOFBARN("N");
        }
        if (isdrainCheckBox.isChecked()) {
            n_rostc.setISDRAIN("Y"); ////水沟通畅??
        } else {
            n_rostc.setISDRAIN("N");
        }
        n_rostc.setLATEDATE(latedateTextView.getText().toString());//风雨后(日期)
        n_rostc.setPOSITIONLATE(positionlateTextView.getText().toString());//发生部位
        n_rostc.setTAKEACTIONLATE(takeactionlateTextView.getText().toString());//采取措施
        n_rostc.setTEMPERATURE(temperatureTextView.getText().toString());//仓温
        n_rostc.setWET(wetTextView.getText().toString());//仓湿
        if (isleakageofbarnlateCheckBox.isChecked()) {
            n_rostc.setISLEAKAGEOFBARNLATE("Y"); //仓房无渗漏?
        } else {
            n_rostc.setISLEAKAGEOFBARNLATE("N");
        }
        n_rostc.setEXAMINER(AccountUtils.getloginUserName(N_rostcAddActivity.this)); //检查人
        n_rostc.setROSTCNUM("");//编号
        return n_rostc;
    }

    //判断必填项
    private boolean isBoolean() {
        if (locTextView.getText().toString().isEmpty()) {
            MessageUtils.showErrorMessage(N_rostcAddActivity.this, "货位号");
            return false;
        } else if (beforedateText.getText().toString().isEmpty()) {
            MessageUtils.showErrorMessage(N_rostcAddActivity.this, "日期");
            return false;
        } else if (isingdawcloseCheckBox.isChecked() || isleakageofbarnCheckBox.isChecked() || isdrainCheckBox.isChecked()) {
            if (positioningTextView.getText().toString().isEmpty()) {
                MessageUtils.showErrorMessage(N_rostcAddActivity.this, "发放部位");
                return false;
            }
            if (takeactioningTextView.getText().toString().isEmpty()) {
                MessageUtils.showErrorMessage(N_rostcAddActivity.this, "采取措施");
                return false;
            }
        } else if (latedateTextView.getText().toString().isEmpty()) {
            MessageUtils.showErrorMessage(N_rostcAddActivity.this, "日期");
            return false;
        } else if (isleakageofbarnlateCheckBox.isChecked()) {
            if (positionlateTextView.getText().toString().isEmpty()) {
                MessageUtils.showErrorMessage(N_rostcAddActivity.this, "发放部位");
                return false;
            }
            if (takeactionlateTextView.getText().toString().isEmpty()) {
                MessageUtils.showErrorMessage(N_rostcAddActivity.this, "采取措施");
                return false;
            }

        }
        return true;


    }
}
