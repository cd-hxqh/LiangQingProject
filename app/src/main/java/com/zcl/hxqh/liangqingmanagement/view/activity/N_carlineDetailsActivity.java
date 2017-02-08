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
import android.widget.CheckBox;
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
import com.zcl.hxqh.liangqingmanagement.model.N_CARLINE;
import com.zcl.hxqh.liangqingmanagement.until.DateTimeSelect;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.until.T;
import com.zcl.hxqh.liangqingmanagement.view.widght.ShareBottomDialog;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * 货运预报明细
 */
public class N_carlineDetailsActivity extends BaseActivity {
    private static String TAG = "N_carlineDetailsActivity";

    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;

    /**
     * 确定按钮
     **/
    private Button submitBtn;


    /**界面信息**/
    /**
     * 序号
     */
    private TextView snText;
    /**
     * 车号
     */
    private EditText carnoText;
    /**
     * 是否集装箱
     */
    private CheckBox iscontainterText;

    /**
     * 件数（条）
     */
    private EditText numberText;
    /**
     * 发站
     */
    private EditText stationText;
    /**
     * 开始日期
     */
    private TextView startText;
    /**
     * 结束日期
     */
    private TextView endText;
    /**
     * 备注
     */
    private EditText remarkText;
    /**
     * 到库时间
     */
    private TextView intimeText;

    private N_CAR n_car;
    private N_CARLINE n_carline;
    private int position;


    //扦样类型
    private String[] types = null;

    /**
     * 时间选择器
     **/
    private DatePickerDialog datePickerDialog;
    StringBuffer sb;
    private TextView timeView;

    //弹出框
    private ArrayList<DialogMenuItem> mMenuItems = new ArrayList<>();


    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;


    protected FlippingLoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carline_details);
        geiIntentData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void geiIntentData() {
        n_car = (N_CAR) getIntent().getSerializableExtra("n_car");
        n_carline = (N_CARLINE) getIntent().getSerializableExtra("n_carline");
        position = getIntent().getIntExtra("position",0);
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        submitBtn = (Button) findViewById(R.id.sbmit_id);


        snText = (TextView) findViewById(R.id.carline_sn);
        carnoText = (EditText) findViewById(R.id.carline_carno);
        iscontainterText = (CheckBox) findViewById(R.id.carline_iscontainter);
        numberText = (EditText) findViewById(R.id.carline_number);
        stationText = (EditText) findViewById(R.id.carline_station);
        startText = (TextView) findViewById(R.id.carline_start);
        endText = (TextView) findViewById(R.id.carline_end);
        remarkText = (EditText) findViewById(R.id.carline_remark);
        intimeText = (TextView) findViewById(R.id.carline_intime);

        ViewGroup container = (ViewGroup) findViewById(R.id.container);
        LayoutTransition transition = new LayoutTransition();
        container.setLayoutTransition(transition);
    }


    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.carline_detail_title);
        submitBtn.setText("确定");
        submitBtn.setVisibility(View.VISIBLE);
        submitBtn.setOnClickListener(submitBtnBtnOnClickListener);

        if (n_carline != null) {
            snText.setText(n_carline.getSN());
            carnoText.setText(n_carline.getCARNO());
            iscontainterText.setChecked(n_carline.getISCONTAINTER().equals("Y"));
            numberText.setText(n_carline.getNUMBER());
            stationText.setText(n_carline.getSTATION());
            startText.setText(n_carline.getSTART());
            endText.setText(n_carline.getEND());
            remarkText.setText(n_carline.getREMARK());
            intimeText.setText(n_carline.getINTIME());
        }

        setDataListener();
        startText.setOnClickListener(new MydateListener(startText));
        endText.setOnClickListener(new MydateListener(endText));
        intimeText.setOnClickListener(intimeTextOnClickListener);
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
            Intent intent = new Intent();
            intent.putExtra("n_carline",getN_CARLINE());
            intent.putExtra("position",position);
            setResult(1000,intent);
            finish();
//            submitNormalDialog();
        }
    };

    private View.OnClickListener intimeTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new DateTimeSelect(N_carlineDetailsActivity.this, intimeText).showDialog();
        }
    };

    /**
     * 显示选项框
     **/
    private void showShareBottomDialog(String title, final String[] typesitem, final TextView textview) {

        final ShareBottomDialog dialog = new ShareBottomDialog(N_carlineDetailsActivity.this, types, null);


        dialog.title(title)//
                .titleTextSize_SP(14.5f)//
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.showShort(N_carlineDetailsActivity.this, typesitem[position]);
                textview.setText(typesitem[position]);
                dialog.dismiss();
            }
        });
    }


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
        private TextView textView;
        private MydateListener(TextView textView){
            this.textView = textView;
        }

        @Override
        public void onClick(View view) {
            sb = new StringBuffer();
            datePickerDialog.show();
            timeView = textView;
        }
    }


    private class datelistener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            sb = new StringBuffer();
            monthOfYear = monthOfYear + 1;
            if (dayOfMonth < 10) {
                sb.append(year + "-" + monthOfYear + "-" + "0" + dayOfMonth);
            } else {
                sb.append(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
            timeView.setText(sb.toString());
        }
    }

    //提交弹出框

    /**
     * 提交数据*
     */
    private void submitNormalDialog() {

        final NormalDialog dialog = new NormalDialog(N_carlineDetailsActivity.this);
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
//                String reviseresult = AndroidClientService.addAndUpdateN_SAMPLE(N_carlineDetailsActivity.this, JsonUtils.encapsulationN_CAR(getN_CARLINE()));
//                return reviseresult;
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                mLoadingDialog.dismiss();
//                MessageUtils.showMiddleToast(N_carlineDetailsActivity.this, s);
//                if (s.equals("修改成功")) {
//                    finish();
//                }
//            }
//        }.execute();
//    }


    /**
     * 封装数据
     **/
    private N_CARLINE getN_CARLINE() {
        String sn = snText.getText().toString();
        if (!sn.equals(n_carline.getSN())) {
            n_carline.setSN(sn);
        }
        String carno = carnoText.getText().toString();
        if (!carno.equals(n_carline.getCARNO())) {
            n_carline.setCARNO(carno);
        }
        String iscontainter = iscontainterText.isChecked()?"Y":"N";
        if (!iscontainter.equals(n_carline.getISCONTAINTER())) {
            n_carline.setISCONTAINTER(iscontainter);
        }
        String number = numberText.getText().toString();
        if (!number.equals(n_carline.getNUMBER())) {
            n_carline.setNUMBER(number);
        }
        String station = stationText.getText().toString();
        if (!station.equals(n_carline.getSTATION())) {
            n_carline.setSTATION(station);
        }
        String start = startText.getText().toString();
        if (!start.equals(n_carline.getSTART())) {
            n_carline.setSTART(start);
        }
        String end = endText.getText().toString();
        if (!end.equals(n_carline.getEND())) {
            n_carline.setEND(end);
        }
        String remark = remarkText.getText().toString();
        if (!remark.equals(n_carline.getREMARK())) {
            n_carline.setREMARK(remark);
        }
        String intime = intimeText.getText().toString();
        if (!intime.equals(n_carline.getINTIME())) {
            n_carline.setINTIME(intime);
        }
        n_carline.setCARNUM(n_car.CARNUM);
        return n_carline;
    }

}
