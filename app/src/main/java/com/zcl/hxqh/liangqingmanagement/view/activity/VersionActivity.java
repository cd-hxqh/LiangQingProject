package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;


/**
 * 版本信息
 */
public class VersionActivity extends BaseActivity {
    private static String TAG = "VersionActivity";

    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;

    /**
     * 版本
     **/
    private TextView versionText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        findViewById();
        initView();

    }


    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        versionText = (TextView) findViewById(R.id.version_text_id);

    }


    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.version_text);

        versionText.setText(getVersion());


    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    /**
     * 获取当前版本信息
     **/
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return this.getString(R.string.version_name) + version;
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.can_not_find_version_name);
        }
    }

}
