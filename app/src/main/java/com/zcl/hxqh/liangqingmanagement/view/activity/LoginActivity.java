package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.bugly.Bugly;
import com.zcl.hxqh.liangqingmanagement.AppManager;
import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.api.HttpManager;
import com.zcl.hxqh.liangqingmanagement.api.HttpRequestHandler;
import com.zcl.hxqh.liangqingmanagement.constants.Constants;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.until.AccountUtils;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private EditText mUsername;
    private EditText mPassword;

    private Button loginBtn;

    private CheckBox checkBox; //记住密码

    private TextView connectsetting;//连接设置

    private TextView cardloginText;//员工卡登录

    private TextView fastloginText;//快速登录

//    private RadioGroup radioGroup;
//    /**
//     * 内网*
//     */
//    private RadioButton neiwangRadio;
//    /**
//     * 外网*
//     */
//    private RadioButton waiwangRadio;

    private boolean isRemember; //是否记住密码

    String userName; //用户名
    String userPassWorld; //密码
    String imei; //imei

    private long exitTime = 0;

    protected FlippingLoadingDialog mLoadingDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bugly.init(getApplicationContext(), "2d01706f1a", true);

        imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
                .getDeviceId();

        if (AccountUtils.getIpAddress(LoginActivity.this).equals("")) {
            AccountUtils.setIpAddress(LoginActivity.this, Constants.HTTP_API_IP);
        }
        findViewById();
        initView();
        setEvent();


    }

    @Override
    protected void findViewById() {

        mUsername = (EditText) findViewById(R.id.login_username_edit);
        mPassword = (EditText) findViewById(R.id.login_password_edit);

        loginBtn = (Button) findViewById(R.id.btn_login);

        boolean isChecked = AccountUtils.getIsChecked(LoginActivity.this);
        if (isChecked) {
            mUsername.setText(AccountUtils.getUserName(LoginActivity.this));
            mPassword.setText(AccountUtils.getUserPassword(LoginActivity.this));
        }

//        radioGroup = (RadioGroup) findViewById(R.id.radiogroup_id);
//        waiwangRadio = (RadioButton) findViewById(R.id.waiwang_id);
//        neiwangRadio = (RadioButton) findViewById(R.id.neiwang_id);


        checkBox = (CheckBox) findViewById(R.id.checkBox);

        connectsetting = (TextView) findViewById(R.id.connect_setting);
        cardloginText = (TextView) findViewById(R.id.card_login);
        fastloginText = (TextView) findViewById(R.id.fast_login);
    }

    /**
     * 设置事件监听*
     */
    private void setEvent() {
        checkBox.setOnCheckedChangeListener(cheBoxOnCheckedChangListener);
        connectsetting.setOnClickListener(connectsettingOnClickListener);
        cardloginText.setOnClickListener(cardloginOnClickListener);
        fastloginText.setOnClickListener(fastloginOnClickListener);
    }


    private CompoundButton.OnCheckedChangeListener cheBoxOnCheckedChangListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            isRemember = isChecked;
        }
    };

    private View.OnClickListener connectsettingOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this,ConnectSettingActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener cardloginOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this,CardLoginActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private View.OnClickListener fastloginOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this,FastLoginActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void initView() {
        loginBtn.setOnClickListener(this);

//        waiwangRadio.setChecked(true);
//        radioGroup.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener);
    }


//    private RadioGroup.OnCheckedChangeListener radioGroupOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(RadioGroup group, int checkedId) {
//            if (checkedId == neiwangRadio.getId()) {
//                AccountUtils.setIpAddress(LoginActivity.this, Constants.HTTPZHENGSHI_API_IP);
//            } else if (checkedId == waiwangRadio.getId()) {
//                AccountUtils.setIpAddress(LoginActivity.this, Constants.HTTP_API_IP);
//            }
//        }
//    };


    /**
     * 跳转至主界面*
     */
    private void startIntent() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (mUsername.getText().length() == 0) {
                    mUsername.setError(getString(R.string.login_error_empty_user));
                    mUsername.requestFocus();
                } else if (mPassword.getText().length() == 0) {
                    mPassword.setError(getString(R.string.login_error_empty_passwd));
                    mPassword.requestFocus();
                } else {
                    login();
                }
                break;

        }
    }


    /**
     * 登陆*
     */
    private void login() {
        getLoadingDialog("正在登陆...").show();

        HttpManager.loginWithUsername(LoginActivity.this,
                mUsername.getText().toString(),
                mPassword.getText().toString(), imei,
                new HttpRequestHandler<String>() {
                    @Override
                    public void onSuccess(String data) {

                        getLoadingDialog("正在登陆...").dismiss();
                        if (data != null) {
                            getBaseApplication().setUsername(mUsername.getText().toString());
                            if (isRemember) {
                                AccountUtils.setChecked(LoginActivity.this, isRemember);
                                //记住密码
                                AccountUtils.setUserNameAndPassWord(LoginActivity.this, mUsername.getText().toString(), mPassword.getText().toString());
                            }
                            try {//保存登录返回信息
                                JSONObject object = new JSONObject(data);
                                JSONObject LoginDetails = object.getJSONObject("userLoginDetails");
                                AccountUtils.setLoginDetails(LoginActivity.this, LoginDetails.getString("insertOrg"), LoginDetails.getString("insertSite"),
                                        LoginDetails.getString("personId"), object.getString("userName"), LoginDetails.getString("displayName"),LoginDetails.getString("loginUserName"));
//                            findByDepartment(LoginDetails.getString("personId"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            startIntent();
                        }
                    }

                    @Override
                    public void onSuccess(String data, int totalPages, int currentPage) {
                        if (data != null) {
                            MessageUtils.showMiddleToast(LoginActivity.this, getString(R.string.login_successful_hint));

                            startIntent();
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        MessageUtils.showErrorMessage(LoginActivity.this, error);
                        getLoadingDialog("正在登陆...").dismiss();
                    }
                });
    }


    @Override
    public void onBackPressed() {


        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.AppExit(LoginActivity.this);
        }
    }

    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }




}
