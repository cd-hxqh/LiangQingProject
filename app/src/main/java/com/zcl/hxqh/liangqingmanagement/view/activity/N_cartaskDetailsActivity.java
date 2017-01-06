package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.N_CARTASK;


/**
 * 车辆查询详情
 */
public class N_cartaskDetailsActivity extends BaseActivity {
    private static String TAG = "N_cartaskDetailsActivity";

    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;

    /**
     * 进门时间
     **/
    private TextView intimeTextView;
    /**
     * 出门时间
     **/
    private TextView outtimeTextView;
    /**
     * 姓名
     **/
    private TextView pname;
    /**
     * 作业性质
     **/
    private TextView tasktypeTextView;
    /**
     * 品种
     **/
    private TextView foodtypesTextView;
    /**
     * 作业货位号
     **/
    private TextView locTextView;
    /**
     * 收货/提货单位
     **/
    private TextView fromstationTextView;


    /**
     * N_CARTASK
     **/
    private N_CARTASK n_cartask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartask_details);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        n_cartask = (N_CARTASK) getIntent().getSerializableExtra("n_cartask");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);

        intimeTextView = (TextView) findViewById(R.id.intime_text_id);
        outtimeTextView = (TextView) findViewById(R.id.outtime_text_id);
        pname = (TextView) findViewById(R.id.pname_text_id);
        tasktypeTextView = (TextView) findViewById(R.id.tasktype_text_id);
        foodtypesTextView = (TextView) findViewById(R.id.foodtypes_text_id);
        locTextView = (TextView) findViewById(R.id.loc_text_id);
        fromstationTextView = (TextView) findViewById(R.id.fromstation_text_id);
    }


    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.cljl_text);

        intimeTextView.setText(n_cartask.getINTIME());
        outtimeTextView.setText(n_cartask.getOUTTIME());
        pname.setText(n_cartask.getDRIVER());
        tasktypeTextView.setText(n_cartask.getTASKTYPE());
        foodtypesTextView.setText(n_cartask.getFOODTYPES());
        locTextView.setText(n_cartask.getLOC());
        fromstationTextView.setText(n_cartask.getN_CAR_THDW());


    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

}
