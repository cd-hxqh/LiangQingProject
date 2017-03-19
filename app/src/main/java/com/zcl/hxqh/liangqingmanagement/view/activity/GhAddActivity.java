package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 归还
 */
public class GhAddActivity extends BaseActivity {
    private static String TAG = "GhAddActivity";
    public static int NFC_RESULT_CODE = 1002;
    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;

    private ImageButton submitImageButton;


    /**
     * 界面信息
     **/

    private TextView numTitle; //设备标题
    private TextView rotassetnumText; //移动设备编号

    private ImageView codeImageView; //二维码扫描

    private TextView issuetoText; //归还人

    private ImageView tagImageView; //员工卡扫描

    private TextView usetypeText; //类型

    private EditText memoText; //备注


    protected FlippingLoadingDialog mLoadingDialog;

    private int mark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gh_add);
        initData();
        findViewById();
        initView();

    }

    private void initData() {
        mark = getIntent().getExtras().getInt("mark");
    }


    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        submitImageButton = (ImageButton) findViewById(R.id.sbmittext_id);

        numTitle = (TextView) findViewById(R.id.num_title_id);
        rotassetnumText = (TextView) findViewById(R.id.rotassetnum_text_id);
        codeImageView = (ImageView) findViewById(R.id.imagecode_id);
        issuetoText = (TextView) findViewById(R.id.issueto_text_id);
        tagImageView = (ImageView) findViewById(R.id.carno_image_id);
        usetypeText = (TextView) findViewById(R.id.usetype_text_id);
        memoText = (EditText) findViewById(R.id.memo_text_id);
    }


    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        if (mark == MainActivity.ROTASSETNUM_CODE) {
            titleTextView.setText(R.string.ydsbgh_text);
        } else if (mark == MainActivity.ITEMNUM_CODE) {
            titleTextView.setText(R.string.gjgh_text);
            numTitle.setText(R.string.gj_num);
        }
        submitImageButton.setVisibility(View.VISIBLE);
        submitImageButton.setOnClickListener(submitImageButtonOnClickListener);
        codeImageView.setOnClickListener(codeImageViewOnClickListener);
        tagImageView.setOnClickListener(tagImageViewOnClickListener);
    }

    /**
     * 提交
     **/
    private View.OnClickListener submitImageButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getLoadingDialog("正在提交数据...");
            startAsyncTask();
        }
    };
    /**
     * 二维码扫描
     **/
    private View.OnClickListener codeImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(GhAddActivity.this, MipcaActivityCapture.class);
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
            Intent intent = new Intent(GhAddActivity.this, Nfc_Activity.class);
            startActivityForResult(intent, 0);

        }
    };


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
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
                    reviseresult = AndroidClientService.postGuiHuai(GhAddActivity.this, toJson());
                } else if (mark == MainActivity.ITEMNUM_CODE) {
                    reviseresult = AndroidClientService.postGJGuiHuai(GhAddActivity.this, toJson());
                }
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(GhAddActivity.this, s);
                finish();


            }
        }.execute();


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


    /**
     * 封装 json字符串
     **/
    private String toJson() {
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject();
            if (mark==MainActivity.ROTASSETNUM_CODE) {
                jsonObject.put("ROTASSETNUM", rotassetnumText.getText().toString());
            }else if(mark==MainActivity.ITEMNUM_CODE){
                jsonObject.put("ITEMNUM", rotassetnumText.getText().toString());
            }
            jsonObject.put("CARDID", issuetoText.getText().toString());
            jsonObject.put("REMARK", memoText.getText().toString());
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            return "";
        }
        Log.i(TAG, "jsonArray.toString()=" + jsonArray.toString());
        return jsonArray.toString();
    }


}
