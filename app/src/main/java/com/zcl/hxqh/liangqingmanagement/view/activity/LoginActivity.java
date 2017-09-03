package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    @Bind(R.id.login_username_edit)
    EditText mUsername;
    @Bind(R.id.login_password_edit)
    EditText mPassword;
    @Bind(R.id.btn_login)
    Button loginBtn;
    @Bind(R.id.checkBox)
    CheckBox checkBox; //记住密码
    @Bind(R.id.connect_setting)
    TextView connectsetting;//连接设置
    @Bind(R.id.card_login)
    TextView cardloginText;//员工卡登录
    @Bind(R.id.fast_login)
    TextView fastloginText;//快速登录


    private boolean isRemember; //是否记住密码

    String imei; //imei

    private long exitTime = 0;

    protected FlippingLoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Bugly.init(getApplicationContext(), "2d01706f1a", true);

        imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
                .getDeviceId();

        if (AccountUtils.getIpAddress(LoginActivity.this).equals("")) {
            AccountUtils.setIpAddress(LoginActivity.this, Constants.HTTP_API_IP);
        }
        findViewById();
        initView();


    }

    @Override
    protected void findViewById() {


        boolean isChecked = AccountUtils.getIsChecked(LoginActivity.this);
        if (isChecked) {
            checkBox.setChecked(isChecked);
            mUsername.setText(AccountUtils.getUserName(LoginActivity.this));
            mPassword.setText(AccountUtils.getUserPassword(LoginActivity.this));
        }


    }


    //记住密码
    @OnCheckedChanged(R.id.checkBox)
    void onChecked(boolean isChecked) {
        isRemember = isChecked;
    }

    @OnClick(R.id.connect_setting)
    void setConnectsettingOnClickListener() {
        Intent intent = new Intent(LoginActivity.this, ConnectSettingActivity.class);
        startActivityForResult(intent, 0);
    }

    @OnClick(R.id.card_login)
    void cardloginOnClickListener() {
        Intent intent = new Intent(LoginActivity.this, CardLoginActivity.class);
        startActivityForResult(intent, 0);
    }

    @OnClick(R.id.fast_login)
    void fastloginOnClickListener() {
        Intent intent = new Intent(LoginActivity.this, FastLoginActivity.class);
        startActivityForResult(intent, 0);
    }

    @OnClick(R.id.btn_login)
    void setLoginBtn() {
        if (mUsername.getText().length() == 0) {
            mUsername.setError(getString(R.string.login_error_empty_user));
            mUsername.requestFocus();
        } else if (mPassword.getText().length() == 0) {
            mPassword.setError(getString(R.string.login_error_empty_passwd));
            mPassword.requestFocus();
        } else {
            login();
        }
    }


    @Override
    protected void initView() {

        if (AccountUtils.getIpAddress(LoginActivity.this) == null
                || AccountUtils.getIpAddress(LoginActivity.this).equals("")) {//初始化地址
            AccountUtils.setIpAddress(LoginActivity.this, Constants.HTTP_API_IP);
        }

    }


    /**
     * 跳转至主界面*
     */
    private void startIntent(ArrayList<String> list) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.putExtra("appidArray", list);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    /**
     * 获取用户名显示权限数据*
     */
    private void getUserApp(final String username) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return AndroidClientService.mobilelogin_getUserApp(LoginActivity.this, username);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e(TAG, "s=" + s);
                if (isJsonArrary(s)) {
                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        JSONObject jsonObject;
                        ArrayList<String> arrayList = new ArrayList<String>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            if (jsonObject.has("appid")) {
                                arrayList.add(jsonObject.getString("appid"));
                            }
                        }
                        startIntent(arrayList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    MessageUtils.showErrorMessage(LoginActivity.this, "获取权限失败");
                }
            }
        }.execute();
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
                                        LoginDetails.getString("personId"), object.getString("userName"), LoginDetails.getString("displayName"), LoginDetails.getString("loginUserName"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            getUserApp(mUsername.getText().toString());
                        }
                    }

                    @Override
                    public void onSuccess(String data, int totalPages, int currentPage) {
                        if (data != null) {
                            MessageUtils.showMiddleToast(LoginActivity.this, getString(R.string.login_successful_hint));

                            getUserApp(mUsername.getText().toString());
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

    private boolean isJsonArrary(String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }


}
