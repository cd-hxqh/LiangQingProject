package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.adapter.N_instructionsListAdapter;
import com.zcl.hxqh.liangqingmanagement.api.HttpManager;
import com.zcl.hxqh.liangqingmanagement.api.HttpRequestHandler;
import com.zcl.hxqh.liangqingmanagement.api.JsonUtils;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.model.N_INSTRUCSTASK;
import com.zcl.hxqh.liangqingmanagement.model.N_INSTRUCTIONS;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.view.widght.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 调车作业指令单
 */
public class N_instrucstaskDetailsActivity extends BaseActivity {
    private static String TAG = "N_instrucstaskDetailsActivity";

    @Bind(R.id.title_back_id)
    ImageView backImageView;//返回按钮
    @Bind(R.id.title_name)
    TextView titleTextView;//标题

    @Bind(R.id.instrucnum_text_id)
    TextView instrucnumextView;//指令单编号
    @Bind(R.id.entryby_text_id)
    TextView entrybyTextView; //录入人
    @Bind(R.id.status_text_id)
    TextView statusText; //状态
    @Bind(R.id.entrydate_text_id)
    TextView entrydateText; //录入时间

    @Bind(R.id.zk_imageview_id)
    ImageView zlImageView;//指令
    @Bind(R.id.listview_bj_id)
    LinearLayout listviewLinearLayout;


    LinearLayoutManager layoutManager;
    @Bind(R.id.recyclerView_id)
    RecyclerView recyclerView;

    @Bind(R.id.have_not_data_id)
    LinearLayout nodatalayout; //暂无数据

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout refresh_layout = null; //界面刷新


    private N_instructionsListAdapter n_instructionsListAdapter;

    private Animation rotate;

    /**
     * N_CARTASK
     **/
    private N_INSTRUCSTASK n_instrucstask;


    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_instrucstask_details);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        n_instrucstask = (N_INSTRUCSTASK) getIntent().getSerializableExtra("n_instrucstask");
    }

    @Override
    protected void findViewById() {
        titleTextView.setText(R.string.zldxq_text);
    }

    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }


    @Override
    protected void initView() {
        rotate = AnimationUtils.loadAnimation(this, R.anim.arrow_rotate);//创建动画
        instrucnumextView.setText(n_instrucstask.getINSTRUCNUM());
        entrybyTextView.setText(n_instrucstask.getENTRYBY());
        statusText.setText(n_instrucstask.getSTATUS());
        entrydateText.setText(n_instrucstask.getENTRYDATE());

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        refresh_layout.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refresh_layout.setRefreshing(true);
        refresh_layout.setOnRefreshListener(refreshListener);
        refresh_layout.setOnLoadListener(loadListener);
        initAdapter(new ArrayList<N_INSTRUCTIONS>());
        getData();

    }

    @OnClick(R.id.title_back_id)
    void setBackImageView() {
        finish();
    }

//    @OnClick(R.id.zk_imageview_id)
//    void setZlOnClickListener() {
//        if (startAnaim()) {
//            listviewLinearLayout.setVisibility(View.GONE);
//        } else {
//            listviewLinearLayout.setVisibility(View.VISIBLE);
//        }
//
//    }


    //启动动画
    private boolean startAnaim() {

        rotate.setInterpolator(new LinearInterpolator());//设置为线性旋转
        rotate.setFillAfter(!rotate.getFillAfter());//每次都取相反值，使得可以不恢复原位的旋转
        zlImageView.startAnimation(rotate);
        return rotate.getFillAfter();
    }


    //刷新
    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {

        @Override
        public void onRefresh() {
            page = 1;
            getData();
        }
    };


    //加载
    private SwipeRefreshLayout.OnLoadListener loadListener = new SwipeRefreshLayout.OnLoadListener() {
        @Override
        public void onLoad() {
            page++;
            getData();
        }
    };


    /**
     * 获取数据*
     */
    private void getData() {
        HttpManager.getDataPagingInfo(N_instrucstaskDetailsActivity.this, HttpManager.getN_INSTRUCTIONS(n_instrucstask.getINSTRUCNUM(), page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<N_INSTRUCTIONS> item = JsonUtils.parsingN_INSTRUCTIONS(results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
//                    listviewLinearLayout.setVisibility(View.GONE);
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            initAdapter(new ArrayList<N_INSTRUCTIONS>());
                        }
                        if (page > totalPages) {
                            MessageUtils.showMiddleToast(N_instrucstaskDetailsActivity.this, getString(R.string.have_all_data_text));
                        } else {
                            addData(item);
                        }
                    }
                    nodatalayout.setVisibility(View.GONE);
//                    listviewLinearLayout.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(String error) {
//                listviewLinearLayout.setVisibility(View.GONE);
                refresh_layout.setRefreshing(false);
                nodatalayout.setVisibility(View.VISIBLE);
            }
        });

    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<N_INSTRUCTIONS> list) {
        n_instructionsListAdapter = new N_instructionsListAdapter(N_instrucstaskDetailsActivity.this, R.layout.list_instructions_item, list);
        recyclerView.setAdapter(n_instructionsListAdapter);
    }

    /**
     * 添加数据*
     */
    private void addData(final List<N_INSTRUCTIONS> list) {
        n_instructionsListAdapter.addData(list);
    }


}
