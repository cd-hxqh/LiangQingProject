package com.zcl.hxqh.liangqingmanagement.view.activity;

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
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.adapter.BaseQuickAdapter;
import com.zcl.hxqh.liangqingmanagement.adapter.InvuseListAdapter;
import com.zcl.hxqh.liangqingmanagement.api.HttpManager;
import com.zcl.hxqh.liangqingmanagement.api.HttpRequestHandler;
import com.zcl.hxqh.liangqingmanagement.api.JsonUtils;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.model.INVUSE;
import com.zcl.hxqh.liangqingmanagement.view.widght.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 * 工具借用
 */

public class GjjyActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final String TAG = "GjjyActivity";
    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;
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
    private InvuseListAdapter invuseListAdapter;
    /**
     * 编辑框*
     */
    private EditText search;
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;


    ArrayList<INVUSE> items = new ArrayList<INVUSE>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_id);
        refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        nodatalayout = (LinearLayout) findViewById(R.id.have_not_data_id);
        search = (EditText) findViewById(R.id.search_edit);
    }

    @Override
    protected void initView() {
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleTextView.setText(R.string.gjjy_text);
        setSearchEdit();

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

        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setOnLoadListener(this);
    }

    private View.OnClickListener addImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent1 = new Intent(GjjyActivity.this, N_carAddActivity.class);
            startActivityForResult(intent1, 0);
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        refresh_layout.setRefreshing(true);
        initAdapter(new ArrayList<INVUSE>());
        items = new ArrayList<>();
        getData(searchText);
    }

    @Override
    public void onLoad() {
        page++;
        getData(searchText);
    }

    @Override
    public void onRefresh() {
        page = 1;
        getData(searchText);
    }


    private void setSearchEdit() {
        SpannableString msp = new SpannableString("XX搜索");
        Drawable drawable = getResources().getDrawable(R.drawable.ic_search);
        msp.setSpan(new ImageSpan(drawable), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        search.setHint(msp);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    GjjyActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    invuseListAdapter.removeAll(items);
                    items = new ArrayList<INVUSE>();
                    nodatalayout.setVisibility(View.GONE);
                    refresh_layout.setRefreshing(true);
                    page = 1;
                    getData(searchText);
                    return true;
                }
                return false;
            }
        });
    }


    /**
     * 获取数据*
     */
    private void getData(String search) {
        HttpManager.getDataPagingInfo(GjjyActivity.this, HttpManager.getINVUSE(search, page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<INVUSE> item = JsonUtils.parsingINVUSE(GjjyActivity.this, results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<INVUSE>();
                            initAdapter(items);
                        }
                        for (int i = 0; i < item.size(); i++) {
                            items.add(item.get(i));
                        }
                        addData(item);
                    }
                    nodatalayout.setVisibility(View.GONE);

                    initAdapter(items);
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
    private void initAdapter(final List<INVUSE> list) {
        invuseListAdapter = new InvuseListAdapter(GjjyActivity.this, R.layout.list_item_hyyb, list);
        recyclerView.setAdapter(invuseListAdapter);
        invuseListAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(GjjyActivity.this, InvuseDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("invuse", items.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<INVUSE> list) {
        invuseListAdapter.addData(list);
    }

}
