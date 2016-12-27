package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.animation.LayoutTransition;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.flyco.dialog.widget.NormalListDialog;
import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.api.HttpManager;
import com.zcl.hxqh.liangqingmanagement.api.HttpRequestHandler;
import com.zcl.hxqh.liangqingmanagement.api.JsonUtils;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.constants.Constants;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.model.ALNDOMAIN;
import com.zcl.hxqh.liangqingmanagement.model.N_CARTASK;
import com.zcl.hxqh.liangqingmanagement.model.N_QCTASKLINE;
import com.zcl.hxqh.liangqingmanagement.model.N_SAMPLE;
import com.zcl.hxqh.liangqingmanagement.model.N_WAGONS;
import com.zcl.hxqh.liangqingmanagement.until.AccountUtils;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.until.T;
import com.zcl.hxqh.liangqingmanagement.view.widght.ShareBottomDialog;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * 扦样单
 */
public class N_sampleAddActivity extends BaseActivity {
    private static String TAG = "N_sampleDetailsActivity";

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
     * 样品编号
     */
    private TextView samplenumText;

    /**
     * 条码扫描
     **/
    private ImageView codeImageView;


    /**
     * 描述
     */
    private EditText descriptionText;

    /**
     * 扦样类型
     */
    private TextView typeText;

    /**
     * 货位号
     **/
    private EditText locText;

    private ImageView locImageView;

    /**
     * 检验任务编号
     */
    private EditText n_qctasklinenumText;
    private ImageView linenumImageView;
    /**
     * 扦样对象
     */
    private TextView objText;
    /**
     * 车辆作业单号
     */
    private TextView cartasknumText;
    private LinearLayout cartasknumLayout;
    private View cartasknum;

    /**
     * 是否火车
     */
    private CheckBox trainynCheckBox;
    private RelativeLayout isLayout;
    private View isView;
    /**
     * 一卡通号
     **/
    private TextView tagIdText;
    /**
     * 选择图标
     **/
    private ImageView tagImageView;

    /**
     * 车号
     */
    private EditText carnoText;
    /**
     * 选择图标
     **/
    private ImageView carnoImageView;
    /**
     * 取样位置
     */
    private EditText fromlocText;
    /**
     * 取样人
     */
    private TextView enterbyText;
    /**
     * 取样日期
     */
    private TextView enterdateText;

    private N_SAMPLE n_sample;


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
        setContentView(R.layout.activity_qyd_details);
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

        samplenumText = (TextView) findViewById(R.id.samplenum_text_id);
        codeImageView = (ImageView) findViewById(R.id.imagecode_id);
        descriptionText = (EditText) findViewById(R.id.description_text_id);
        typeText = (TextView) findViewById(R.id.type_text_id);
        locText = (EditText) findViewById(R.id.loc_text_id);
        locImageView = (ImageView) findViewById(R.id.loc_image_id);
        n_qctasklinenumText = (EditText) findViewById(R.id.n_qctasklinenum_text_id);
        linenumImageView = (ImageView) findViewById(R.id.n_qctasklinenum_image_id);
        objText = (TextView) findViewById(R.id.obj_text_id);
        cartasknumText = (TextView) findViewById(R.id.cartasknum_text_id);
        cartasknumLayout = (LinearLayout) findViewById(R.id.cartasknum_layout_id);
        cartasknum = (View) findViewById(R.id.cartasknum_view_id);
        isLayout = (RelativeLayout) findViewById(R.id.is_huoche_id);
        isView = (View) findViewById(R.id.trainyn_view_id);
        trainynCheckBox = (CheckBox) findViewById(R.id.trainyn_text_id);
        tagIdText = (TextView) findViewById(R.id.tagid_text_id);
        tagImageView = (ImageView) findViewById(R.id.carno_image_id);
        carnoText = (EditText) findViewById(R.id.carno_text_id);
        carnoImageView = (ImageView) findViewById(R.id.carno_imageview_id);

        fromlocText = (EditText) findViewById(R.id.fromloc_text_id);
        enterbyText = (TextView) findViewById(R.id.enterby_text_id);
        enterdateText = (TextView) findViewById(R.id.enterdate_text_id);

        linenumImageView.setVisibility(View.GONE);


        ViewGroup container = (ViewGroup) findViewById(R.id.container);
        LayoutTransition transition = new LayoutTransition();
        container.setLayoutTransition(transition);


        enterbyText.setText(AccountUtils.getloginUserName(N_sampleAddActivity.this));
        enterdateText.setText(getCurrentTime("yyyy-MM-dd"));
        setDataListener();
    }


    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.add_text);
        submitBtn.setVisibility(View.VISIBLE);
        submitBtn.setOnClickListener(submitBtnBtnOnClickListener);
        codeImageView.setVisibility(View.VISIBLE);
        codeImageView.setOnClickListener(codeImageViewOnClickListener);
        typeText.setOnClickListener(typeTextOnClickListner);
        locImageView.setVisibility(View.VISIBLE);
        locImageView.setOnClickListener(locTextOnClickListener);
//        linenumImageView.setVisibility(View.VISIBLE);
//        linenumImageView.setOnClickListener(n_qctasklinenumTextOnClickListner);
//        objText.setOnClickListener(objTextOnClickListner);


        enterdateText.setOnClickListener(new MydateListener());


        trainynCheckBox.setOnCheckedChangeListener(trainynCheckBoxOnCheckedChangeListener);
        tagImageView.setVisibility(View.VISIBLE);
        tagImageView.setOnClickListener(imageViewOnClickListener);

        carnoImageView.setOnClickListener(carnoimageViewOnClickListener);


    }

    /**
     * 是否火车
     **/
    private CompoundButton.OnCheckedChangeListener trainynCheckBoxOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                carnoImageView.setVisibility(View.VISIBLE);
            } else {
                carnoImageView.setVisibility(View.GONE);
            }
        }
    };


    /**
     * 选择值车皮跟踪
     **/
    private View.OnClickListener imageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            getN_WAGONSValue(HttpManager.getN_WAGONS("已到库并确认"),carnoText);

            //跳转至NFC扫描界面
            Intent intent = new Intent(N_sampleAddActivity.this, Nfc_Activity.class);
            startActivityForResult(intent, 0);

        }
    };
    /**
     * 选择值车号
     **/
    private View.OnClickListener carnoimageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getN_WAGONSValue(HttpManager.getN_WAGONS("已到库并确认"), carnoText);


        }
    };


    /**
     * 获取系统当前时间
     **/
    private String getCurrentTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
        String time = df.format(new Date());
        return time;
    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener codeImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(N_sampleAddActivity.this, MipcaActivityCapture.class);
            startActivityForResult(intent, 0);
        }
    };


    private View.OnClickListener submitBtnBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            if (isSubmit()) {
            submitNormalDialog();
//            }
        }
    };


    /**
     * 判断必填选项
     **/
    private boolean isSubmit() {
        if (carnoText.getText().toString().isEmpty()) {
            MessageUtils.showMiddleToast(N_sampleAddActivity.this, "车号不能为空");
            return false;

        }
        return true;
    }

    //扦样类型
    private View.OnClickListener typeTextOnClickListner = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            getOptionsValue("扦样类型", HttpManager.getALNDOMAIN(Constants.N_TYPES), typeText);


        }
    };

    private View.OnClickListener locTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //跳转至选项值界面
            Intent intent = new Intent(N_sampleAddActivity.this, ChooseActivity.class);
            intent.putExtra("HOLDER", "");
            startActivityForResult(intent, 0);
        }
    };


    //任务编号
    private View.OnClickListener n_qctasklinenumTextOnClickListner = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            getN_QCTASKLINEValue(HttpManager.getN_QCTASKLINE(AccountUtils.getloginUserName(N_sampleAddActivity.this)), n_qctasklinenumText);

        }
    };
    //车辆作业
    private View.OnClickListener cartasknumTextOnClickListner = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            getN_CARTASKValue(HttpManager.getN_CARTASK("入库"), cartasknumText);

        }
    };
    //扦样对象
    private View.OnClickListener objTextOnClickListner = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            getOptionsValue("扦样对象", HttpManager.getALNDOMAIN(Constants.CROPSTYPE), objText);


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
                    ArrayList<ALNDOMAIN> item = JsonUtils.parsingALNDOMAIN(N_sampleAddActivity.this, data.getResultlist());
                    if (item == null || item.isEmpty()) {
                        MessageUtils.showMiddleToast(N_sampleAddActivity.this, getString(R.string.qiangyang_type_text));
                    } else {
                        types = new String[item.size()];
                        for (int i = 0; i < item.size(); i++) {
                            types[i] = item.get(i).getDESCRIPTION();
                        }
                        if (types != null && types.length != 0) {
                            showShareBottomDialog(title, types, textview);
                        } else {
                            MessageUtils.showMiddleToast(N_sampleAddActivity.this, getString(R.string.qiangyang_type_text));
                        }

                    }

                }

                @Override
                public void onFailure(String error) {

                }
            });

        }
    }


    //任务编号
    private void getN_QCTASKLINEValue(String url, final TextView textView) {
        {
            HttpManager.getData(this, url, new HttpRequestHandler<Results>() {


                @Override
                public void onSuccess(Results data) {

                }

                @Override
                public void onSuccess(Results data, int totalPages, int currentPage) {
                    ArrayList<N_QCTASKLINE> item = JsonUtils.parsingN_QCTASKLINE(N_sampleAddActivity.this, data.getResultlist());
                    if (item == null || item.isEmpty()) {
                        MessageUtils.showMiddleToast(N_sampleAddActivity.this, getString(R.string.qiangyang_type_text));
                    } else {
                        mMenuItems = new ArrayList<DialogMenuItem>();
                        for (N_QCTASKLINE n : item) {
                            String numAnddesc = "编号:" + n.getN_QCTASKLINENUM() + " , 描述:" + n.getDESCRIPTION();
                            mMenuItems.add(new DialogMenuItem(numAnddesc, 0));
                        }

                        NormalListDialogCustomAttr(textView, item);

                    }

                }

                @Override
                public void onFailure(String error) {

                }
            });

        }
    }

    //车辆作业
    private void getN_CARTASKValue(String url, final TextView textView) {
        {
            HttpManager.getData(this, url, new HttpRequestHandler<Results>() {


                @Override
                public void onSuccess(Results data) {

                }

                @Override
                public void onSuccess(Results data, int totalPages, int currentPage) {
                    ArrayList<N_CARTASK> item = JsonUtils.parsingN_CARTASK(N_sampleAddActivity.this, data.getResultlist());
                    if (item == null || item.isEmpty()) {
                        MessageUtils.showMiddleToast(N_sampleAddActivity.this, getString(R.string.qiangyang_type_text));
                    } else {
                        mMenuItems = new ArrayList<DialogMenuItem>();
                        for (N_CARTASK n : item) {
                            String numAnddesc = "单号:" + n.getCARTASKNUM() + " , 车号:" + n.getCARNO();
                            mMenuItems.add(new DialogMenuItem(numAnddesc, 0));
                        }

                        NormalListDialogCustomAttr1(textView, item);

                    }

                }

                @Override
                public void onFailure(String error) {

                }
            });

        }
    }


    //车皮跟踪
    private void getN_WAGONSValue(String url, final TextView textView) {
        {
            HttpManager.getData(this, url, new HttpRequestHandler<Results>() {


                @Override
                public void onSuccess(Results data) {

                }

                @Override
                public void onSuccess(Results data, int totalPages, int currentPage) {
                    ArrayList<N_WAGONS> item = JsonUtils.parsingN_WAGONS(N_sampleAddActivity.this, data.getResultlist());
                    if (item == null || item.isEmpty()) {
                        MessageUtils.showMiddleToast(N_sampleAddActivity.this, getString(R.string.qiangyang_type_text));
                    } else {
                        mMenuItems = new ArrayList<DialogMenuItem>();
                        for (N_WAGONS n : item) {
                            String numAnddesc = " 车号:" + n.getWAGONNUM();
                            mMenuItems.add(new DialogMenuItem(numAnddesc, 0));
                        }

                        NormalListDialogCustomAttr2(textView, item);

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

        final ShareBottomDialog dialog = new ShareBottomDialog(N_sampleAddActivity.this, types, null);


        dialog.title(title)//
                .titleTextSize_SP(14.5f)//
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.showShort(N_sampleAddActivity.this, typesitem[position]);
                textview.setText(typesitem[position]);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
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
            sb.append(year + "-" + monthOfYear + "-" + "0" + dayOfMonth);

            enterdateText.setText(sb.toString());
        }
    }


    private void NormalListDialogCustomAttr(final TextView textView, final ArrayList<N_QCTASKLINE> item) {
        final NormalListDialog dialog = new NormalListDialog(N_sampleAddActivity.this, mMenuItems);
        dialog.title("任务编号")//
                .titleTextSize_SP(18)//
                .titleBgColor(Color.parseColor("#2da861"))//
                .itemPressColor(Color.parseColor("#41c378"))//
                .itemTextColor(Color.parseColor("#393d3f"))//
                .itemTextSize(14)//
                .cornerRadius(0)//
                .widthScale(0.8f)//
                .show(R.style.myDialogAnim);

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.showShort(N_sampleAddActivity.this, mMenuItems.get(position).mOperName);
                textView.setText(item.get(position).getN_QCTASKLINENUM());
                dialog.dismiss();
            }
        });
    }


    private void NormalListDialogCustomAttr1(final TextView textView, final ArrayList<N_CARTASK> item) {
        final NormalListDialog dialog = new NormalListDialog(N_sampleAddActivity.this, mMenuItems);
        dialog.title("车辆作业")//
                .titleTextSize_SP(18)//
                .titleBgColor(Color.parseColor("#2da861"))//
                .itemPressColor(Color.parseColor("#41c378"))//
                .itemTextColor(Color.parseColor("#393d3f"))//
                .itemTextSize(14)//
                .cornerRadius(0)//
                .widthScale(0.8f)//
                .show(R.style.myDialogAnim);

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.showShort(N_sampleAddActivity.this, mMenuItems.get(position).mOperName);
                textView.setText(item.get(position).getCARTASKNUM());
                carnoText.setText(item.get(position).getCARNO());
                dialog.dismiss();
            }
        });
    }

    private void NormalListDialogCustomAttr2(final TextView textView, final ArrayList<N_WAGONS> item) {
        final NormalListDialog dialog = new NormalListDialog(N_sampleAddActivity.this, mMenuItems);
        dialog.title("车号选项")//
                .titleTextSize_SP(18)//
                .titleBgColor(Color.parseColor("#2da861"))//
                .itemPressColor(Color.parseColor("#41c378"))//
                .itemTextColor(Color.parseColor("#393d3f"))//
                .itemTextSize(14)//
                .cornerRadius(0)//
                .widthScale(0.8f)//
                .show(R.style.myDialogAnim);

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.showShort(N_sampleAddActivity.this, mMenuItems.get(position).mOperName);
                carnoText.setText(item.get(position).getWAGONNUM());
                dialog.dismiss();
            }
        });
    }


    //提交弹出框

    /**
     * 提交数据*
     */
    private void submitNormalDialog() {

        final NormalDialog dialog = new NormalDialog(N_sampleAddActivity.this);
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
                String reviseresult = AndroidClientService.addAndUpdateN_SAMPLE(N_sampleAddActivity.this, JsonUtils.encapsulationN_SAMPLE(getN_sample()));
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(N_sampleAddActivity.this, s);
                finish();


            }
        }.execute();


    }


    /**
     * 封装数据
     **/
    private N_SAMPLE getN_sample() {

        n_sample = new N_SAMPLE();
        String samplenum = samplenumText.getText().toString();
        n_sample.setSAMPLENUM(samplenum);
        String description = descriptionText.getText().toString();
        n_sample.setDESCRIPTION(description);
        String type = typeText.getText().toString();
        n_sample.setTYPE(type);
        String n_qctasklinenum = n_qctasklinenumText.getText().toString();
        n_sample.setN_QCTASKLINENUM(n_qctasklinenum);
        String obj = objText.getText().toString();
        n_sample.setOBJ(obj);
        String loc = locText.getText().toString();
        n_sample.setLOC(loc);
        String cartasknum = cartasknumText.getText().toString();
        n_sample.setCARTASKNUM(cartasknum);
        boolean trainyn = trainynCheckBox.isChecked();
        if (trainyn) {
            n_sample.setTRAINYN("Y");
        } else {
            n_sample.setTRAINYN("N");
        }

        String carno = carnoText.getText().toString();
        n_sample.setCARNO(carno);
        String tagId = tagIdText.getText().toString();
        n_sample.setTAGID(tagId);
        String fromloc = fromlocText.getText().toString();
        n_sample.setFROMLOC(fromloc);
        String enterby = enterbyText.getText().toString();
        n_sample.setENTERBY(enterby);
        String enterdate = enterdateText.getText().toString();
        n_sample.setENTERDATE(enterdate);


        return n_sample;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                String samplenum = data.getExtras().getString("result");
                samplenumText.setText(samplenum);
                break;
            case 1002:
                String tagId = data.getExtras().getString("tagId");
                tagIdText.setText(tagId);
                getCarInfo(tagId);
                break;
            case 1001:
                String loc = data.getExtras().getString("loc");
                //根据货位号获取任务检查单号

                getN_QCTASKLINENUMInfo(loc);

                locText.setText(loc);
                break;
        }
    }

    /**
     * 根据N_QCTASKLINENUM**获取车辆信息
     **/

    private void getN_QCTASKLINENUMInfo(final String loc) {
        getLoadingDialog("请稍后...");

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String reviseresult = AndroidClientService.addN_SAMPLE1LOC(N_sampleAddActivity.this, loc);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                showN_QCTASKLINENUMInfo(s);
                mLoadingDialog.dismiss();


            }
        }.execute();

    }

    /**
     * 获取任务检查单号
     **/

    private void showN_QCTASKLINENUMInfo(String info) {
        if (!info.equals("")) {
            try {
                JSONArray jsonArray = new JSONArray(info);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                String n_qctasklinenum = jsonObject.getString("n_qctasklinenum");
                String foodtype = jsonObject.getString("foodtype");

                n_qctasklinenumText.setText(n_qctasklinenum);
                objText.setText(foodtype);

            } catch (JSONException e) {
                MessageUtils.showMiddleToast(N_sampleAddActivity.this, "请核实检验任务编号");
                e.printStackTrace();
            }
        }
    }


    /**
     * 根据tagId**获取车辆信息
     **/

    private void getCarInfo(final String tagId) {
        getLoadingDialog("请稍后...");

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String reviseresult = AndroidClientService.addAndUpdateN_SAMPLE1(N_sampleAddActivity.this, tagId);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                showCarInfo(s);
                mLoadingDialog.dismiss();


            }
        }.execute();

    }


    /**
     * 解析获取的值
     **/

    private void showCarInfo(String info) {
        if (!info.equals("")) {
            try {
                JSONArray jsonArray = new JSONArray(info);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                String cartasknum = jsonObject.getString("cartasknum");
                String carno = jsonObject.getString("carno");
                String foodtype = jsonObject.getString("foodtype");
                String trainyn = jsonObject.getString("trainyn");

                cartasknumText.setText(cartasknum);
                carnoText.setText(carno);
                objText.setText(foodtype);
                isLayout.setVisibility(View.GONE);
                isView.setVisibility(View.GONE);

            } catch (JSONException e) {
                MessageUtils.showMiddleToast(N_sampleAddActivity.this, "没有相关的车辆信息");
                e.printStackTrace();
            }
        }
    }


}
