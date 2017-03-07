package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.api.JsonUtils;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.model.N_LABAPY;
import com.zcl.hxqh.liangqingmanagement.model.N_LABAPYRULE;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import java.util.List;


/**
 * 用工验收
 */
public class N_labapysDetailsActivity extends BaseActivity {
    private static String TAG = "N_labapysDetailsActivity";

    public static final int SCORE_RESULTCODE = 1013;

    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;
    /**
     * 菜单
     */
    private ImageView menuImageView;

    /**
     * 提交
     **/
    private Button sumbitBtn;


    //编号
    private TextView n_labapynumText;
    //工作地点
    private TextView placeText;
    //工作内容
    private TextView detailsText;
    //班组
    private TextView amcrewbzText;
    //工作量
    private EditText workloadText;
    //用工人
    private TextView linkmanText;
    //实际开始时间
    private TextView ysstartText;
    //实际结束时间
    private TextView ysendText;

    private N_LABAPY n_labapy;

    private PopupWindow popupWindow;

    private LinearLayout labapyruleLayout;

    protected FlippingLoadingDialog mLoadingDialog;

    //评分json
    private String scoreJson = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labapys_details);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        n_labapy = (N_LABAPY) getIntent().getSerializableExtra("n_labapy");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        menuImageView = (ImageView) findViewById(R.id.title_add);

        sumbitBtn = (Button) findViewById(R.id.sbmit_btn_id);

        n_labapynumText = (TextView) findViewById(R.id.n_labapynum_text_id);
        placeText = (TextView) findViewById(R.id.place_text_id);
        detailsText = (TextView) findViewById(R.id.details_text_id);
        amcrewbzText = (TextView) findViewById(R.id.amcrew_bz_id);
        workloadText = (EditText) findViewById(R.id.workload_text_id);
        linkmanText = (TextView) findViewById(R.id.linkman_text_id);
        ysstartText = (TextView) findViewById(R.id.ysstart_text_id);
        ysendText = (TextView) findViewById(R.id.ysend_text_id);
    }


    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.n_labapy_text);
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setImageResource(R.drawable.ic_more);
        menuImageView.setOnClickListener(menuImageViewOnClickListener);

        n_labapynumText.setText(n_labapy.getN_LABAPYNUM());
        placeText.setText(n_labapy.getPLACE());
        detailsText.setText(n_labapy.getDETAILS());
        amcrewbzText.setText(n_labapy.getAMCREWBZ());
        workloadText.setText(n_labapy.getWORKLOAD());
        linkmanText.setText(n_labapy.getLINKMAN());
        ysstartText.setText(n_labapy.getYSSTART());
        ysendText.setText(n_labapy.getYSEND());

        sumbitBtn.setOnClickListener(sumbitBtnOnClickListener);


    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private View.OnClickListener menuImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupWindow(menuImageView);
        }
    };


    private View.OnClickListener sumbitBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getLoadingDialog("正在提交数据...请稍后").show();
            startAsyncTask();
        }
    };


    private void showPopupWindow(View view) {

        View contentView = LayoutInflater.from(N_labapysDetailsActivity.this).inflate(
                R.layout.popup_item_window, null);


        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
            }
        });

        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.popup_background_mtrl_mult));

        popupWindow.showAsDropDown(view);
        labapyruleLayout = (LinearLayout) contentView.findViewById(R.id.add_linearlayout_id);


        labapyruleLayout.setOnClickListener(labapyruleLayoutOnClickListener);

    }


    private View.OnClickListener labapyruleLayoutOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            popupWindow.dismiss();
            Intent intent = new Intent(N_labapysDetailsActivity.this, N_labapyruleActivity.class);
            intent.putExtra("LABAPYNUM", n_labapy.getN_LABAPYNUM());
            startActivityForResult(intent, 0);
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case SCORE_RESULTCODE:
                List<N_LABAPYRULE> n_labapyrules = (List<N_LABAPYRULE>) data.getSerializableExtra("n_labapyrules");
                for (N_LABAPYRULE n_labapyrule : n_labapyrules) {
                    scoreJson += JsonUtils.encapsulationN_labapys(n_labapyrule.getSN(), n_labapyrule.getSCORE()) + ",";

                }
                scoreJson = scoreJson.substring(0, scoreJson.length() - 1);
                break;
        }
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
        final String workload = workloadText.getText().toString();
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String reviseresult = AndroidClientService.updateN_LABAPY(N_labapysDetailsActivity.this, n_labapy.getN_LABAPYNUM(), workload, "[" + scoreJson + "]");
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                MessageUtils.showMiddleToast(N_labapysDetailsActivity.this, s);
                finish();


            }
        }.execute();


    }


}
