package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.model.INVUSELINE;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 实际领用
 */
public class JyAddActivity extends BaseActivity {
    private static String TAG = "JyAddActivity";
    public static int NFC_RESULT_CODE = 1002;
    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;


    /**
     * 界面信息
     **/

    private TextView numTitle; //编号
    private TextView rotassetnumText; //移动设备编号

    private ImageView codeImageView; //二维码扫描

    private TextView issuetoText; //归还人

    private ImageView tagImageView; //员工卡扫描

    private Button nextButton; //下一条

    private Button submitBtton;//完成提交

    protected FlippingLoadingDialog mLoadingDialog;

    private List<INVUSELINE> invuselines = new ArrayList<INVUSELINE>();
    private String invusenum;

    private int mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jy_add);
        initData();
        findViewById();
        initView();

    }

    private void initData() {
        mark = getIntent().getExtras().getInt("mark");
        Log.i(TAG, "mark=" + mark);
        invusenum = getIntent().getExtras().getString("invusenum");
    }


    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);


        numTitle = (TextView) findViewById(R.id.num_title_id);
        rotassetnumText = (TextView) findViewById(R.id.rotassetnum_text_id);
        codeImageView = (ImageView) findViewById(R.id.imagecode_id);
        issuetoText = (TextView) findViewById(R.id.issueto_text_id);
        tagImageView = (ImageView) findViewById(R.id.carno_image_id);
        nextButton = (Button) findViewById(R.id.next_btn_id);
        submitBtton = (Button) findViewById(R.id.sbumit_btn_id);
    }


    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        if (mark == MainActivity.ITEMNUM_CODE) {
            numTitle.setText(R.string.gj_num);
        }
        titleTextView.setText(R.string.invuseline1_text);
        codeImageView.setOnClickListener(codeImageViewOnClickListener);
        tagImageView.setOnClickListener(tagImageViewOnClickListener);
        nextButton.setOnClickListener(nextButtonOnClickListener);
        submitBtton.setOnClickListener(submitBttonOnClickListener);
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


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    private View.OnClickListener nextButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            INVUSELINE invuseline = new INVUSELINE();
            if (!rotassetnumText.getText().toString().equals("") && !issuetoText.getText().toString().equals("")) {
                invuseline.setROTASSETNUM(rotassetnumText.getText().toString());
                invuseline.setISSUETO(issuetoText.getText().toString());
                invuselines.add(invuseline);
                rotassetnumText.setText("");
                issuetoText.setText("");
            } else {
                MessageUtils.showMiddleToast(JyAddActivity.this, "移动设备编号或借用人为必选项");
            }

        }
    };
    private View.OnClickListener submitBttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (invuselines == null || invuselines.size() == 0) {
                INVUSELINE invuseline = new INVUSELINE();
                if (!rotassetnumText.getText().toString().equals("") && !issuetoText.getText().toString().equals("")) {
                    if (mark == MainActivity.ROTASSETNUM_CODE) {
                        invuseline.setROTASSETNUM(rotassetnumText.getText().toString());
                    } else if (mark == MainActivity.ITEMNUM_CODE) {
                        invuseline.setITEMNUM(rotassetnumText.getText().toString());
                    }
                    invuseline.setISSUETO(issuetoText.getText().toString());
                    invuselines.add(invuseline);


                } else {
                    MessageUtils.showMiddleToast(JyAddActivity.this, "设备编号或借用人为必选项");
                }
            }
            getLoadingDialog("正在提交数据...");
            startAsyncTask();
        }
    };


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
                Log.i(TAG, "222222222=" + mark);
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
        Log.i(TAG, "jsonArray.toString()=" + jsonArray.toString());
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
                String tagId = data.getExtras().getString("tagId");
                issuetoText.setText(tagId);
                break;
        }
    }
}
