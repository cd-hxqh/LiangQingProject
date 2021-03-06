package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;

/**
 * Created by Administrator on 2017/2/15.
 * 车辆查询页面
 */

public class N_cartaskActivity extends BaseActivity {
    private static final String TAG = "N_cartaskActivity";
    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;
    private static final int N_CARTASK_MARK=1002;

    private Button n_cartaskBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_cartask);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        n_cartaskBtn = (Button) findViewById(R.id.cartask_btn_id);
    }

    @Override
    protected void initView() {
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleTextView.setText("车辆查询");
        n_cartaskBtn.setOnClickListener(n_cartaskBtnOnClickListener);
    }

    private View.OnClickListener n_cartaskBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(N_cartaskActivity.this, Nfc_Activity.class);
            intent.putExtra("mark",N_CARTASK_MARK);
            startActivityForResult(intent, 0);
        }
    };
}
