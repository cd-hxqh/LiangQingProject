package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.adapter.AlndomainListAdapter;
import com.zcl.hxqh.liangqingmanagement.api.HttpManager;
import com.zcl.hxqh.liangqingmanagement.api.HttpRequestHandler;
import com.zcl.hxqh.liangqingmanagement.api.JsonUtils;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.model.ALNDOMAIN;
import com.zcl.hxqh.liangqingmanagement.view.widght.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 安全告知项目
 **/

public class AlndomainChooseActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {

    private static final String TAG = "AlndomainChooseActivity";

    @Bind(R.id.title_name)
    TextView titleTextView;//标题
    @Bind(R.id.title_back_id)
    ImageView backImageView;//返回按钮
    @Bind(R.id.sbmittext_id)
    ImageButton submitBtn; //提交

    LinearLayoutManager layoutManager;


    @Bind(R.id.recyclerView_id)
    RecyclerView recyclerView;//RecyclerView

    @Bind(R.id.have_not_data_id)
    LinearLayout nodatalayout;//暂无数据

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout refresh_layout = null;//界面刷新
    /**
     * 适配器*
     */
    private AlndomainListAdapter alndomainListAdapter;
    @Bind(R.id.search_edit)
    EditText search;//编辑框


    ArrayList<ALNDOMAIN> items = new ArrayList<ALNDOMAIN>();

    private String type;
    private String title;

    private String choosedescription="";//多选值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xxz_list);
        ButterKnife.bind(this);
        initData();
        findViewById();
        initView();

    }

    private void initData() {
        if (getIntent().hasExtra("type")) {
            type = getIntent().getExtras().getString("type");
        }
        title = getIntent().getExtras().getString("title");
    }


    @Override
    protected void findViewById() {


    }

    @Override
    protected void initView() {
        titleTextView.setText(title);
        search.setVisibility(View.GONE);
        submitBtn.setVisibility(View.VISIBLE);
        layoutManager = new LinearLayoutManager(AlndomainChooseActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        refresh_layout.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refresh_layout.setRefreshing(true);

        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setOnLoadListener(this);

        initAdapter(new ArrayList<ALNDOMAIN>());
        items = new ArrayList<>();
        getData();
    }


    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageView() {
        finish();
    }

    //确认事件
    @OnClick(R.id.sbmittext_id)
    void setSubmitBtn() {
        choosedescription = choosedescription.substring(0, choosedescription.length() - 1);
        Intent intent = getIntent();
        intent.putExtra("description", choosedescription);
        setResult(1000, intent);
        finish();
    }


    @Override
    public void onLoad() {

        refresh_layout.setLoading(false);
    }

    @Override
    public void onRefresh() {
        refresh_layout.setRefreshing(true);

    }


    /**
     * 获取数据*
     */
    private void getData() {

        HttpManager.getData(AlndomainChooseActivity.this, HttpManager.getALNDOMAIN1(type), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<ALNDOMAIN> item = JsonUtils.parsingALNDOMAIN(AlndomainChooseActivity.this, results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {
                    addData(item);
                    nodatalayout.setVisibility(View.GONE);


                }
            }

            @Override
            public void onFailure(String error) {
                refresh_layout.setRefreshing(false);
                nodatalayout.setVisibility(View.VISIBLE);
            }
        });

    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<ALNDOMAIN> list) {
        alndomainListAdapter = new AlndomainListAdapter(AlndomainChooseActivity.this, R.layout.list_alnaomain_item, list);
        recyclerView.setAdapter(alndomainListAdapter);
        alndomainListAdapter.setmCheckBoxListener(new AlndomainListAdapter.CheckBoxListener() {
            @Override
            public void getCheckTemPERSON(boolean b, ALNDOMAIN item) {
                Log.e(TAG, "b=" + b + "," + "choosedescription=" + choosedescription);
                if (b) {
                    choosedescription = choosedescription + item.DESCRIPTION + "、";
                } else {
                    choosedescription.replace(item.DESCRIPTION + "、", "");
                }
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<ALNDOMAIN> list) {
        alndomainListAdapter.addData(list);
    }
}