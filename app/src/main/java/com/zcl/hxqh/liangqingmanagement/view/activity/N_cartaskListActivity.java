package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.adapter.BaseQuickAdapter;
import com.zcl.hxqh.liangqingmanagement.adapter.N_cartaskListAdapter;
import com.zcl.hxqh.liangqingmanagement.api.HttpManager;
import com.zcl.hxqh.liangqingmanagement.api.HttpRequestHandler;
import com.zcl.hxqh.liangqingmanagement.api.JsonUtils;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.model.N_CARTASK;
import com.zcl.hxqh.liangqingmanagement.view.widght.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 车辆查询
 */
public class N_cartaskListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {


    private static final String TAG = "N_cartaskFragment";


    /**
     * 标题
     **/
    private TextView titleTextView;

    /**
     * 返回
     **/
    private ImageView backImageView;


    /**
     * 一卡通卡号
     **/
    private TextView tagidTextView;

    /**
     * 车号
     **/
    private TextView carnoTextView;
    /**
     * 身份证
     **/
    private TextView idcardnumTextView;

    LinearLayoutManager layoutManager;
    /**
     * RecyclerView*
     */
    public RecyclerView recyclerView;

    /**
     * 暂无数据*
     */
    private LinearLayout nodatalayout;

    /**
     * 界面刷新*
     */
    private SwipeRefreshLayout refresh_layout = null;
    /**
     * 适配器*
     */
    private N_cartaskListAdapter n_cartaskListAdapter;

    ArrayList<N_CARTASK> items = new ArrayList<N_CARTASK>();

    private int page = 1;

    /**
     * 一卡通ID
     **/
    private String tagId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n_cartask_details);
        initData();
        findViewById();
        initView();
        getData(tagId);
    }


    private void initData() {
        tagId = getIntent().getExtras().getString("tagId");
    }

    @Override
    protected void findViewById() {
        titleTextView = (TextView) findViewById(R.id.title_name);
        backImageView = (ImageView) findViewById(R.id.title_back_id);


        tagidTextView = (TextView) findViewById(R.id.cardid_text_id);

        carnoTextView = (TextView) findViewById(R.id.carline_carno_id);
        idcardnumTextView = (TextView) findViewById(R.id.idcardnum_text_id);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_id);
        refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        nodatalayout = (LinearLayout) findViewById(R.id.have_not_data_id);

    }


    /**
     * 设置事件监听*
     */
    protected void initView() {

        titleTextView.setText("车辆查询结果");
        backImageView.setOnClickListener(backImageViewOnClickListener);

        tagidTextView.setText(tagId);
        layoutManager = new LinearLayoutManager(N_cartaskListActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        refresh_layout.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refresh_layout.setRefreshing(false);

        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setOnLoadListener(this);

        initAdapter(items);
    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };


    /**
     * 获取数据*
     */
    private void getData(String tagId) {
        HttpManager.getDataPagingInfo(N_cartaskListActivity.this, HttpManager.getN_CARTASK(tagId, 1, 5), new HttpRequestHandler<Results>() {
                    @Override
                    public void onSuccess(Results results) {
                        Log.i(TAG, "data=" + results);
                    }

                    @Override
                    public void onSuccess(Results results, int totalPages, int currentPage) {
                        ArrayList<N_CARTASK> item = JsonUtils.parsingN_CARTASK(N_cartaskListActivity.this, results.getResultlist());
                        refresh_layout.setRefreshing(false);
                        refresh_layout.setLoading(false);
                        if (item == null || item.isEmpty()) {
                            nodatalayout.setVisibility(View.VISIBLE);
                        } else {
                            carnoTextView.setText(item.get(0).getCARNO());
                            idcardnumTextView.setText(item.get(0).getIDCARDNUM());
                            for (int i = 0; i < item.size(); i++) {
                                items.add(item.get(i));
                            }
                            addData(items);
                        }


                    }

                    @Override
                    public void onFailure(String error) {
                        refresh_layout.setRefreshing(false);
                        nodatalayout.setVisibility(View.VISIBLE);
                    }
                }

        );

    }

    @Override
    public void onLoad() {


    }

    @Override
    public void onRefresh() {
        page = 1;
        getData(tagId);
    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<N_CARTASK> list) {
        n_cartaskListAdapter = new N_cartaskListAdapter(N_cartaskListActivity.this, R.layout.list_item, list);
        recyclerView.setAdapter(n_cartaskListAdapter);
        n_cartaskListAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(N_cartaskListActivity.this, N_cartaskDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("n_cartask", list.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<N_CARTASK> list) {
        n_cartaskListAdapter.addData(list);
    }
}
