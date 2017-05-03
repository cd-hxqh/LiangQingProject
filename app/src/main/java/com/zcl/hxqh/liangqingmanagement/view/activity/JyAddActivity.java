package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.api.HttpManager;
import com.zcl.hxqh.liangqingmanagement.api.HttpRequestHandler;
import com.zcl.hxqh.liangqingmanagement.api.JsonUtils;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.model.INVUSELINE;
import com.zcl.hxqh.liangqingmanagement.model.PERSON;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 实际领用
 */
public class JyAddActivity extends BaseActivity {
    private static String TAG = "JyAddActivity";
    public static int NFC_RESULT_CODE = 1002;
    @Bind(R.id.title_back_id)
    ImageView backImageView; //返回按钮
    @Bind(R.id.title_name)
    TextView titleTextView;//标题


    /**
     * 界面信息
     **/
    @Bind(R.id.num_title_id)
    TextView numTitle; //编号
    @Bind(R.id.rotassetnum_text_id)
    TextView rotassetnumText; //移动设备编号
    @Bind(R.id.imagecode_id)
    ImageView codeImageView; //二维码扫描
    @Bind(R.id.issueto_text_id)
    TextView issuetoText; //归还人
    @Bind(R.id.carno_image_id)
    ImageView tagImageView; //员工卡扫描
    @Bind(R.id.next_btn_id)
    Button nextButton; //下一条
    @Bind(R.id.sbumit_btn_id)
    Button submitBtton;//完成提交

    protected FlippingLoadingDialog mLoadingDialog;

    private List<INVUSELINE> invuselines = new ArrayList<INVUSELINE>();
    private String invusenum;

    private int mark;

    private String cadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jy_add);
        ButterKnife.bind(this);
        initData();
        findViewById();
        initView();

    }

    private void initData() {
        mark = getIntent().getExtras().getInt("mark");
        invusenum = getIntent().getExtras().getString("invusenum");
    }


    @Override
    protected void findViewById() {

    }


    @Override
    protected void initView() {
        if (mark == MainActivity.ITEMNUM_CODE) {
            numTitle.setText(R.string.gj_num);
        }
        titleTextView.setText(R.string.invuseline1_text);
        codeImageView.setOnClickListener(codeImageViewOnClickListener);
        tagImageView.setOnClickListener(tagImageViewOnClickListener);
    }

    /**
     * 二维码扫描
     **/
    private View.OnClickListener codeImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(JyAddActivity.this, MipcaActivityCapture.class);
            intent.putExtra("mark", 0);
            startActivityForResult(intent, 0);
        }
    };

    /**
     * 员工卡感应
     **/
    private View.OnClickListener tagImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //跳转至NFC扫描界面
            Intent intent = new Intent(JyAddActivity.this, Nfc_Activity.class);
            startActivityForResult(intent, 0);

        }
    };
    //返回事件
    @OnClick(R.id.title_back_id)

    void setBackImageViewOnClickListener() {
        finish();
    }
    //下一条
    @OnClick(R.id.next_btn_id)

    void setNextButtonOnClickListener() {
        INVUSELINE invuseline = new INVUSELINE();
        if (!rotassetnumText.getText().toString().equals("") && !issuetoText.getText().toString().equals("")) {
            if (mark == MainActivity.ROTASSETNUM_CODE) {
                invuseline.setROTASSETNUM(rotassetnumText.getText().toString());
            } else if (mark == MainActivity.ITEMNUM_CODE) {
                invuseline.setITEMNUM(rotassetnumText.getText().toString());
            }
            invuseline.setISSUETO(cadId);
            invuselines.add(invuseline);
            rotassetnumText.setText("");
            issuetoText.setText("");
        } else {
            MessageUtils.showMiddleToast(JyAddActivity.this, "移动设备编号或借用人为必选项");
        }
    }
    //提交
    @OnClick(R.id.sbumit_btn_id)
    void setSubmitBttonOnClickListener() {
        if (invuselines != null || invuselines.size() != 0) {
            INVUSELINE invuseline = new INVUSELINE();
            if (!rotassetnumText.getText().toString().equals("") && !issuetoText.getText().toString().equals("")) {
                if (mark == MainActivity.ROTASSETNUM_CODE) {
                    invuseline.setROTASSETNUM(rotassetnumText.getText().toString());
                } else if (mark == MainActivity.ITEMNUM_CODE) {
                    invuseline.setITEMNUM(rotassetnumText.getText().toString());
                }
                invuseline.setISSUETO(cadId);
                invuselines.add(invuseline);


            } else {
                MessageUtils.showMiddleToast(JyAddActivity.this, "设备编号或借用人为必选项");
            }
        }
        getLoadingDialog("正在提交数据...");
        startAsyncTask();
    }


    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }


    /**
     * 查询数据*
     */
    private void startAsyncTask() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String reviseresult = null;
                if (mark == MainActivity.ROTASSETNUM_CODE) {
                    reviseresult = AndroidClientService.postInvuseLine(JyAddActivity.this, toJson());
                } else if (mark == MainActivity.ITEMNUM_CODE) {
                    reviseresult = AndroidClientService.postItemJy(JyAddActivity.this, toJson());
                }
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(JyAddActivity.this, inJson(s));
                finish();


            }
        }.execute();


    }

    /**
     * 封装 json字符串
     **/
    private String toJson() {
        JSONArray jsonArray = new JSONArray();
        try {
            for (INVUSELINE i : invuselines) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("INVUSENUM", invusenum);
                if (mark == MainActivity.ROTASSETNUM_CODE) {
                    jsonObject.put("ROTASSETNUM", i.getROTASSETNUM());
                } else if (mark == MainActivity.ITEMNUM_CODE) {
                    jsonObject.put("ITEMNUM", i.getITEMNUM());
                }
                jsonObject.put("CARDID", i.getISSUETO());
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            return "";
        }
        return jsonArray.toString();
    }


    /**
     * 解析返回数据
     **/
    private String inJson(String json) {
        String reslut = null;
        try {
            JSONArray array = new JSONArray(json);
            JSONObject jsonobject = new JSONObject(array.get(0).toString());
            if (jsonobject.has("failed")) {
                reslut = jsonobject.get("failed").toString();
                return reslut;
            } else if (jsonobject.has("success")) {
                reslut = jsonobject.get("success").toString();
                return reslut;
            }

        } catch (JSONException e) {
            return "借用失败";
        }
        return reslut;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK: //移动设备编号
                String rotassetnum = data.getExtras().getString("result");
                rotassetnumText.setText(rotassetnum);
                break;
            case 1002: //归还人
                cadId = data.getExtras().getString("tagId");
                getPerson(cadId);
                break;
        }
    }


    /**
     * 获取库存项目信息*
     */

    private void getPerson(String cardnum) {
        HttpManager.getData(this, HttpManager.setPersonUrl(cardnum), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<PERSON> items = new ArrayList<PERSON>();
                items = JsonUtils.parsingPERSON(results.getResultlist());
                if (items.size() == 0) {
                    Toast.makeText(JyAddActivity.this, R.string.wcxdcr_text, Toast.LENGTH_SHORT).show();
                } else if (items.size() == 1) {
                    issuetoText.setText(items.get(0).getDISPLAYNAME());
                } else {
                    Toast.makeText(JyAddActivity.this, R.string.cxcw_text, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(JyAddActivity.this, R.string.cxcw_text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
