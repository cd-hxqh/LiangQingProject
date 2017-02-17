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
 * 人员查询页面
 */

public class PersonActivity extends BaseActivity {
    private static final String TAG = "PersonActivity";
    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;
    private static final int PERSION_MARK=1001;

    private Button personBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        personBtn = (Button) findViewById(R.id.person_btn_id);
    }

    @Override
    protected void initView() {
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleTextView.setText("人员查询");
        personBtn.setOnClickListener(personBtnOnClickListener);
    }

    private View.OnClickListener personBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(PersonActivity.this, Nfc_Activity.class);
            intent.putExtra("mark",PERSION_MARK);
            startActivityForResult(intent, 0);
        }
    };
}
