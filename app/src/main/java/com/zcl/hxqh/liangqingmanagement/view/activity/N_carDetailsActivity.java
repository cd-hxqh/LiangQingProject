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
import android.widget.LinearLayout;
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
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.until.T;
import com.zcl.hxqh.liangqingmanagement.view.widght.ShareBottomDialog;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * 货运预报单
 */
public class N_carDetailsActivity extends BaseActivity {
    private static String TAG = "N_carDetailsActivity";

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


    /**界面信息**/
    /**
     * 编号
     */
    private TextView carnumText;
    /**
     * 仓储作业计划编号
     */
    private TextView plannumText;
    /**
     * 描述
     */
    private EditText descriptionText;

    /**
     * 收货/提货单位
     */
    private TextView fromstationText;
    /**
     * 省份
     */
    private EditText provinceText;
    /**
     * 品种
     */
    private TextView foodtypesText;
    /**
     * 量衡
     */
    private TextView theamountofmoneyText;
    /**
     * 车辆类型
     */
    private TextView cartypeText;
    /**
     * 水分%
     */

    private LinearLayout actualqc1Layout;
    private TextView actualqc1Text;
    private View actualqc1View;
    /**
     * 数量
     */
    private LinearLayout actualqtyLayout;
    private TextView actualqtyText;
    private View actualqtyView;

    /**
     * 杂质含量%
     */
    private LinearLayout actualqc2Layout;
    private TextView actualqc2Text;
    private View actualqc2View;
    /**
     * 不完善含量%
     */
    private LinearLayout actualqc3Layout;
    private TextView actualqc3Text;
    private View actualqc3View;
    /**
     * 容重g/l%
     */
    private LinearLayout actualqc4Layout;
    private TextView actualqc4Text;
    private View actualqc4View;
    /**
     * 生霉含量%
     */
    private LinearLayout actualqc5Layout;
    private TextView actualqc5Text;
    private View actualqc5View;
    /**
     * 脂肪酸值
     */
    private LinearLayout actualqc6Layout;
    private TextView actualqc6Text;
    private View actualqc6View;


    /**
     * 计划总量
     **/
    private EditText plantotalText;

    /**
     * 作业类型
     */
    private EditText planstatusText;
    /**
     * 创建日期
     */
    private TextView enterdateText;
    /**
     * 创建人
     */
    private TextView enterbyText;
    /**
     * 状态
     */
    private TextView statusText;

    private N_CAR n_car;

    private RelativeLayout carlineLayout;//货运预报明细
    private Button carlineButton;//查看货运预报明细按钮


    //扦样类型
    private String[] types = null;

    /**
     * 时间选择器
     **/
    private DatePickerDialog datePickerDialog;
    StringBuffer sb;
    private int layoutnum;


    //弹出框
    private ArrayList<DialogMenuItem> mMenuItems = new ArrayList<>();


    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;


    protected FlippingLoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyyb_details);
        geiIntentData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void geiIntentData() {
        n_car = (N_CAR) getIntent().getSerializableExtra("n_car");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        submitBtn = (Button) findViewById(R.id.sbmit_id);


        carnumText = (TextView) findViewById(R.id.hyyb_carnum);
        plannumText = (TextView) findViewById(R.id.hyyb_plannum);
        descriptionText = (EditText) findViewById(R.id.hyyb_description);
        fromstationText = (TextView) findViewById(R.id.hyyb_fromstation);
        provinceText = (EditText) findViewById(R.id.hyyb_province);
        foodtypesText = (TextView) findViewById(R.id.hyyb_foodtypes);
        theamountofmoneyText = (TextView) findViewById(R.id.hyyb_theamountofmoney);
        cartypeText = (TextView) findViewById(R.id.hyyb_cartype);

        actualqc1Text = (TextView) findViewById(R.id.actualqc1_text_id);
        actualqc1Layout = (LinearLayout) findViewById(R.id.actualqc1_linearlayout_id);
        actualqc1View = (View) findViewById(R.id.actualqc1_view_id);

        actualqtyText = (TextView) findViewById(R.id.actualqty_text_id);
        actualqtyLayout = (LinearLayout) findViewById(R.id.actualqty_linearlayout_id);
        actualqtyView = (View) findViewById(R.id.actualqty_view_id);

        actualqc2Text = (TextView) findViewById(R.id.actualqc2_text_id);
        actualqc2Layout = (LinearLayout) findViewById(R.id.actualqc2_linearlayout_id);
        actualqc2View = (View) findViewById(R.id.actualqc2_view_id);

        actualqc3Text = (TextView) findViewById(R.id.actualqc3_text_id);
        actualqc3Layout = (LinearLayout) findViewById(R.id.actualqc3_linearlayout_id);
        actualqc3View = (View) findViewById(R.id.actualqc3_view_id);

        actualqc4Text = (TextView) findViewById(R.id.actualqc4_text_id);
        actualqc4Layout = (LinearLayout) findViewById(R.id.actualqc4_linearlayout_id);
        actualqc4View = (View) findViewById(R.id.actualqc4_view_id);

        actualqc5Text = (TextView) findViewById(R.id.actualqc5_text_id);
        actualqc5Layout = (LinearLayout) findViewById(R.id.actualqc5_linearlayout_id);
        actualqc5View = (View) findViewById(R.id.actualqc5_view_id);

        actualqc6Text = (TextView) findViewById(R.id.actualqc6_text_id);
        actualqc6Layout = (LinearLayout) findViewById(R.id.actualqc6_linearlayout_id);
        actualqc6View = (View) findViewById(R.id.actualqc6_view_id);

        plantotalText = (EditText) findViewById(R.id.hyyb_plantotal);
        planstatusText = (EditText) findViewById(R.id.hyyb_planstatus);
        enterdateText = (TextView) findViewById(R.id.hyyb_enterdate);
        enterbyText = (TextView) findViewById(R.id.hyyb_enterby);
        statusText = (TextView) findViewById(R.id.hyyb_status);

        carlineLayout = (RelativeLayout) findViewById(R.id.carline_layout);
        carlineButton = (Button) findViewById(R.id.carline_button);

        ViewGroup container = (ViewGroup) findViewById(R.id.container);
        LayoutTransition transition = new LayoutTransition();
        container.setLayoutTransition(transition);
    }


    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.hyyb_text);
        submitBtn.setVisibility(View.VISIBLE);
        submitBtn.setOnClickListener(submitBtnBtnOnClickListener);

        plannumText.setOnClickListener(plannumTextOnClickListener);
        foodtypesText.setOnClickListener(foodtypesTextOnClickListner);
        carlineButton.setOnClickListener(carlineOnClickListener);

        if (n_car != null) {
            carnumText.setText(n_car.getCARNUM());
            plannumText.setText(n_car.getPLANNUM());
            descriptionText.setText(n_car.getDESCRIPTION());
            fromstationText.setText(n_car.getFROMSTATION());
            provinceText.setText(n_car.getPROVINCE());
            foodtypesText.setText(n_car.getFOODTYPES());
            theamountofmoneyText.setText(n_car.getTHEAMOUNTOFMONEY());
            cartypeText.setText(n_car.getCARTYPE());

            if (n_car.getFOODTYPES().equals("玉米")) {
                actualqc1Text.setText(n_car.getACTUALQC1());
                actualqtyText.setText(n_car.getACTUALQTY());
                actualqc2Text.setText(n_car.getACTUALQC2());

                actualqc3Text.setText(n_car.getACTUALQC3());
                actualqc4Text.setText(n_car.getACTUALQC4());
                actualqc5Text.setText(n_car.getACTUALQC5());
                actualqc6Text.setText(n_car.getACTUALQC6());
            } else if (n_car.getFOODTYPES().equals("大豆") || n_car.getFOODTYPES().equals("稻谷")) {

                actualqc1Text.setText(n_car.getACTUALQC1());
                actualqtyText.setText(n_car.getACTUALQTY());
                actualqc2Text.setText(n_car.getACTUALQC2());

                actualqc3Layout.setVisibility(View.GONE);
                actualqc3View.setVisibility(View.GONE);
                actualqc4Layout.setVisibility(View.GONE);
                actualqc4View.setVisibility(View.GONE);
                actualqc5Layout.setVisibility(View.GONE);
                actualqc5View.setVisibility(View.GONE);
                actualqc6Layout.setVisibility(View.GONE);
                actualqc6View.setVisibility(View.GONE);
            } else if (n_car.getFOODTYPES().equals("小麦")) {
                actualqc1Text.setText(n_car.getACTUALQC1());
                actualqtyText.setText(n_car.getACTUALQTY());
                actualqc2Text.setText(n_car.getACTUALQC2());

                actualqc3Text.setText(n_car.getACTUALQC3());
                actualqc4Text.setText(n_car.getACTUALQC4());

                actualqc5Layout.setVisibility(View.GONE);
                actualqc5View.setVisibility(View.GONE);
                actualqc6Layout.setVisibility(View.GONE);
                actualqc6View.setVisibility(View.GONE);
            }
            plantotalText.setText(n_car.getPLANTOTAL());
            planstatusText.setText(n_car.getPLANSTATUS());
            enterdateText.setText(n_car.getENTERDATE());
            enterbyText.setText(n_car.getENTERBY());
            statusText.setText(n_car.getSTATUS());
        }

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


    //仓储作业计划编号
    private View.OnClickListener plannumTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //跳转至选项值界面
            Intent intent = new Intent(N_carDetailsActivity.this, CarPlanChooseActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    //品种
    private View.OnClickListener foodtypesTextOnClickListner = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            getOptionsValue("品种", HttpManager.getALNDOMAIN(Constants.CROPSTYPE), foodtypesText);
        }
    };

    private View.OnClickListener carlineOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(N_carDetailsActivity.this, N_carlineListActivity.class);
            intent.putExtra("n_car", getN_CAR());
            startActivity(intent);
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
                    ArrayList<ALNDOMAIN> item = JsonUtils.parsingALNDOMAIN(N_carDetailsActivity.this, data.getResultlist());
                    if (item == null || item.isEmpty()) {
                        MessageUtils.showMiddleToast(N_carDetailsActivity.this, getString(R.string.qiangyang_type_text));
                    } else {
                        types = new String[item.size()];
                        for (int i = 0; i < item.size(); i++) {
                            types[i] = item.get(i).getDESCRIPTION();
                        }
                        if (types != null && types.length != 0) {
                            showShareBottomDialog(title, types, textview);
                        } else {
                            MessageUtils.showMiddleToast(N_carDetailsActivity.this, getString(R.string.qiangyang_type_text));
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

        final ShareBottomDialog dialog = new ShareBottomDialog(N_carDetailsActivity.this, types, null);


        dialog.title(title)//
                .titleTextSize_SP(14.5f)//
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.showShort(N_carDetailsActivity.this, typesitem[position]);
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

        @Override
        public void onClick(View view) {
            layoutnum = 0;
            sb = new StringBuffer();
            layoutnum = view.getId();
            datePickerDialog.show();
        }
    }


    private class datelistener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            sb = new StringBuffer();
            monthOfYear = monthOfYear + 1;
            if (dayOfMonth < 10) {
                sb.append(year % 100 + "-" + monthOfYear + "-" + "0" + dayOfMonth);
            } else {
                sb.append(year % 100 + "-" + monthOfYear + "-" + dayOfMonth);
            }

            enterdateText.setText(sb.toString());
        }
    }

    //提交弹出框

    /**
     * 提交数据*
     */
    private void submitNormalDialog() {

        final NormalDialog dialog = new NormalDialog(N_carDetailsActivity.this);
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
                        startAsyncTask();

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
    private void startAsyncTask() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return AndroidClientService.addAndUpdateN_CAR(N_carDetailsActivity.this, JsonUtils.encapsulationN_CAR(getN_CAR(), null));
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(N_carDetailsActivity.this, s);
//                if (s.equals("修改成功")) {
//                    finish();
//                }
            }
        }.execute();


    }


    /**
     * 封装数据
     **/
    private N_CAR getN_CAR() {
        String carnum = carnumText.getText().toString();
        if (!carnum.equals(n_car.getCARNUM())) {
            n_car.setCARNUM(carnum);
        }
        String description = descriptionText.getText().toString();
        if (!description.equals(n_car.getDESCRIPTION())) {
            n_car.setDESCRIPTION(description);
        }
        String plannum = plannumText.getText().toString();
        if (!plannum.equals(n_car.getPLANNUM())) {
            n_car.setPLANNUM(plannum);
        }
        String fromstation = fromstationText.getText().toString();
        if (!fromstation.equals(n_car.getFROMSTATION())) {
            n_car.setFROMSTATION(fromstation);
        }
        String province = provinceText.getText().toString();
        if (!province.equals(n_car.getPROVINCE())) {
            n_car.setPROVINCE(province);
        }
        String foodtypes = foodtypesText.getText().toString();
        if (!foodtypes.equals(n_car.getFOODTYPES())) {
            n_car.setFOODTYPES(foodtypes);
        }
        String theamountofmoney = theamountofmoneyText.getText().toString();
        if (!theamountofmoney.equals(n_car.getTHEAMOUNTOFMONEY())) {
            n_car.setTHEAMOUNTOFMONEY(theamountofmoney);
        }
        String cartype = cartypeText.getText().toString();
        if (!cartype.equals(n_car.getCARTYPE())) {
            n_car.setCARTYPE(cartype);
        }
        String plantotal = plantotalText.getText().toString();
        if (!plantotal.equals(n_car.getPLANTOTAL())) {
            n_car.setPLANTOTAL(plantotal);
        }
        String planstatus = planstatusText.getText().toString();
        if (!planstatus.equals(n_car.getPLANSTATUS())) {
            n_car.setPLANSTATUS(planstatus);
        }
        String enterdate = enterdateText.getText().toString();
        if (!enterdate.equals(n_car.getENTERDATE())) {
            n_car.setENTERDATE(enterdate);
        }
        String enterby = enterbyText.getText().toString();
        if (!enterby.equals(n_car.getENTERBY())) {
            n_car.setENTERBY(enterby);
        }
        String status = statusText.getText().toString();
        if (!status.equals(n_car.getSTATUS())) {
            n_car.setSTATUS(status);
        }
        return n_car;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1001:
                N_TASKPLAN n_taskplan = (N_TASKPLAN) data.getSerializableExtra("n_taskplan");
                plannumText.setText(n_taskplan.getPLANNUM());
                descriptionText.setText(n_taskplan.getDESCRIPTION());
                fromstationText.setText(n_taskplan.getSTATION());
                foodtypesText.setText(n_taskplan.getTYPE());
                theamountofmoneyText.setText(n_taskplan.getTHEAMOUNTOFMONEY());
                cartypeText.setText(n_taskplan.getCARTYPE());
                break;
        }
    }

}
