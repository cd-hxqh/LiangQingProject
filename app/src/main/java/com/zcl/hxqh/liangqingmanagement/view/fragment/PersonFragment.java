package com.zcl.hxqh.liangqingmanagement.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.adapter.BaseQuickAdapter;
import com.zcl.hxqh.liangqingmanagement.adapter.HyybListAdapter;
import com.zcl.hxqh.liangqingmanagement.api.HttpManager;
import com.zcl.hxqh.liangqingmanagement.api.HttpRequestHandler;
import com.zcl.hxqh.liangqingmanagement.api.JsonUtils;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.model.N_CAR;
import com.zcl.hxqh.liangqingmanagement.until.AccountUtils;
import com.zcl.hxqh.liangqingmanagement.view.activity.N_carDetailsActivity;
import com.zcl.hxqh.liangqingmanagement.view.widght.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 人员查询的fragment
 */
public class PersonFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {


    private static final String TAG = "HyybFragment";


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
    private HyybListAdapter hyybListAdapter;

    ArrayList<N_CAR> items = new ArrayList<N_CAR>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container,
                false);

        findByIdView(view);
        initView();
        return view;
    }


    /**
     * 初始化界面组件*
     */
    private void findByIdView(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_id);
        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        nodatalayout = (LinearLayout) view.findViewById(R.id.have_not_data_id);
    }


    /**
     * 设置事件监听*
     */
    private void initView() {
        layoutManager = new LinearLayoutManager(getActivity());
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


    }

    @Override
    public void onStart() {
        super.onStart();
        refresh_layout.setRefreshing(true);
        initAdapter(new ArrayList<N_CAR>());
        items = new ArrayList<>();
//        getData(searchText);
    }

    @Override
    public void onLoad() {
//        page++;
//        getData(searchText);
    }

    @Override
    public void onRefresh() {
//        page = 1;
//        getData(searchText);
    }

    /**
     * 获取数据*
     */
    private void getData(String search) {

    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<N_CAR> list) {
        hyybListAdapter = new HyybListAdapter(getActivity(), R.layout.list_item, list);
        recyclerView.setAdapter(hyybListAdapter);
        hyybListAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), N_carDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("n_car", items.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<N_CAR> list) {
        hyybListAdapter.addData(list);
    }


}
