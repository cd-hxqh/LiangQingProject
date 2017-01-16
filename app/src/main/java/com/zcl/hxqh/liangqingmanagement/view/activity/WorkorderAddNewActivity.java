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
import android.widget.Spinner;
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
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.model.WORKORDER;
import com.zcl.hxqh.liangqingmanagement.until.AccountUtils;
import com.zcl.hxqh.liangqingmanagement.until.DateTimeSelect;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.until.T;
import com.zcl.hxqh.liangqingmanagement.view.widght.ShareBottomDialog;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import org.json.JSONException;
import org.json.JSONObject;

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

    private TextView sb;//设备
    private TextView sbwz;//设备位置
    private ImageView photoImg;//拍照
    private EditText description;//缺陷描述
    private TextView fxbm;//发现部门
    private ImageView fxbm_img;//
    private TextView fxr;//发现人
    private TextView reportdate;//发现时间
    private TextView zrr;//责任人
    private TextView schedfinish;//整改期限
    private EditText n_recreq;//整改要求
    private CheckBox worktype;//是否排查
    private EditText remarkdesc;//备注

    private String ASSETNUM;//设备编号
    private String zrrId;//责任人id

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

        sb = (TextView) findViewById(R.id.workorder_sb);
        sbwz = (TextView) findViewById(R.id.workorder_sbwz);
        photoImg = (ImageView) findViewById(R.id.photo_image);
        description = (EditText) findViewById(R.id.workorder_description);
        fxbm = (TextView) findViewById(R.id.workorder_fxbm);
        fxbm_img = (ImageView) findViewById(R.id.fxbm_img);
        fxr = (TextView) findViewById(R.id.workorder_fxr);
        reportdate = (TextView) findViewById(R.id.workorder_reportdate);
        zrr = (TextView) findViewById(R.id.workorder_zrr);
        schedfinish = (TextView) findViewById(R.id.workorder_schedfinish);
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

        sb.setOnClickListener(sbOnClickListener);
        fxbm_img.setOnClickListener(fxbmOnClickListener);
        fxr.setOnClickListener(new personOnClickListener(1002));
        zrr.setOnClickListener(new personOnClickListener(1003));
        reportdate.setOnClickListener(new TimeOnClickListener(reportdate));
        schedfinish.setOnClickListener(new TimeOnClickListener(schedfinish));
        photoImg.setOnClickListener(photoOnClickListener);
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

    private View.OnClickListener sbOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WorkorderAddNewActivity.this,AssetChooseActivity.class);
            startActivityForResult(intent,0);
        }
    };

    private View.OnClickListener fxbmOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showShareBottomDialog("部门",getResources().getStringArray(R.array.fxbm_array),fxbm);
        }
    };

    private View.OnClickListener photoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private class personOnClickListener implements View.OnClickListener{
        int requestCode;
        private personOnClickListener(int requestCode){
            this.requestCode = requestCode;
        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WorkorderAddNewActivity.this,PersonChooseActivity.class);
            startActivityForResult(intent,requestCode);
        }
    }


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

    /**
     * 显示选项框
     **/
    private void showShareBottomDialog(String title, final String[] typesitem, final TextView textview) {

        final ShareBottomDialog dialog = new ShareBottomDialog(WorkorderAddNewActivity.this, getResources().getStringArray(R.array.fxbm_array), null);


        dialog.title(title)//
                .titleTextSize_SP(14.5f)//
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.showShort(WorkorderAddNewActivity.this, typesitem[position]);
                textview.setText(typesitem[position]);
                dialog.dismiss();
            }
        });
    }

    //时间选择监听
    private class TimeOnClickListener implements View.OnClickListener{
        TextView textView;
        private TimeOnClickListener(TextView textView){
            this.textView = textView;
        }
        @Override
        public void onClick(View view) {
            new DateTimeSelect(WorkorderAddNewActivity.this, textView).showDialog();
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
                        getLoadingDialog("正在提交").show();
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
        final String sbText = ASSETNUM;
        final String sbwzText = sbwz.getText().toString();
        final String descriptionText = description.getText().toString();
        final String fxbmText = fxbm.getText().toString();
        final String fxrText = fxr.getText().toString();
        final String reportdateText = reportdate.getText().toString();
        final String zrrText = zrrId;
        final String schedfinishText = schedfinish.getText().toString();
        final String n_recreqText = n_recreq.getText().toString();
        final String worktypeText = worktype.isChecked()?"HD":"CM";
        final String remarkdescText = remarkdesc.getText().toString();
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return AndroidClientService.saveQX(WorkorderAddNewActivity.this, AccountUtils.getloginUserName(WorkorderAddNewActivity.this),sbText,descriptionText,
                        fxbmText,"",fxrText,reportdateText,remarkdescText,worktypeText,zrrText,n_recreqText,schedfinishText);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
//                MessageUtils.showMiddleToast(WorkorderAddNewActivity.this, s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.has("success")&&jsonObject.getString("success").equals("1")){
                        MessageUtils.showMiddleToast(WorkorderAddNewActivity.this, "新建成功");
                        String workordernum = jsonObject.getString("msg");
                    }else {
                        MessageUtils.showMiddleToast(WorkorderAddNewActivity.this, "新建失败");
                    }
                } catch (JSONException e) {
                    MessageUtils.showMiddleToast(WorkorderAddNewActivity.this, s);
                }
            }
        }.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null) {
            switch (resultCode) {
                case 1001:
                    sb.setText(data.getStringExtra("N_MODELNUM"));
                    sbwz.setText(data.getStringExtra("N_LOCANAME"));
                    ASSETNUM = data.getStringExtra("ASSETNUM");
                    break;
            }
            switch (requestCode) {
                case 1002:
                    fxr.setText(data.getStringExtra("displayname"));
                    break;
                case 1003:
                    zrr.setText(data.getStringExtra("displayname"));
                    zrrId = data.getStringExtra("personid");
                    break;
            }
        }
    }

}
