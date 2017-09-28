package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.adapter.BaseQuickAdapter;
import com.zcl.hxqh.liangqingmanagement.adapter.N_taskassetListAdapter;
import com.zcl.hxqh.liangqingmanagement.api.HttpManager;
import com.zcl.hxqh.liangqingmanagement.api.HttpRequestHandler;
import com.zcl.hxqh.liangqingmanagement.api.JsonUtils;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.model.INVUSE;
import com.zcl.hxqh.liangqingmanagement.model.N_PRODUCTIONPLANS;
import com.zcl.hxqh.liangqingmanagement.model.N_TASKASSET;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.view.widght.SwipeRefreshLayout;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 移动设备借用详情
 */
public class InvuseDetailsActivity extends BaseActivity {
    private static String TAG = "InvuseDetailsActivity";

    @Bind(R.id.title_back_id)
    ImageView backImageView;//返回按钮
    @Bind(R.id.title_name)
    TextView titleTextView;//标题

    @Bind(R.id.title_add)
    ImageView menuImageView;//菜单


    /**
     * 界面信息
     **/
    @Bind(R.id.invusenum_text_id)
    TextView invusenumText; //编号
    @Bind(R.id.item_desc_text_id)
    TextView descriptionText; //描述
    @Bind(R.id.productionplansnum_text_id)
    TextView productionplansnumText; //计划编号
    @Bind(R.id.plandesc_text_id)
    TextView plandescText; //计划描述
    @Bind(R.id.enterby_text_id)
    TextView enterbyText; //录入人
    @Bind(R.id.enterdate_text_id)
    TextView enterdateText; //录入时间


    private INVUSE invuse;

    private PopupWindow popupWindow;

    private int mark;

    protected FlippingLoadingDialog mLoadingDialog;


    LinearLayoutManager layoutManager;


    @Bind(R.id.recyclerView_id)
    RecyclerView recyclerView;//RecyclerView
    @Bind(R.id.have_not_data_id)
    LinearLayout nodatalayout;//暂无数据
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout refresh_layout = null; //界面刷新
    /**
     * 适配器*
     */
    private N_taskassetListAdapter n_taskassetlistadapter;

    ArrayList<N_TASKASSET> items = new ArrayList<N_TASKASSET>();

    private int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invuse_details);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();

    }

    private void geiIntentData() {
        mark = getIntent().getExtras().getInt("mark");
        invuse = (INVUSE) getIntent().getSerializableExtra("invuse");
    }

    @Override
    protected void findViewById() {
    }


    @Override
    protected void initView() {
        if (mark == MainActivity.ROTASSETNUM_CODE) {

            titleTextView.setText(R.string.ydsbjy_text);
        } else if (mark == MainActivity.ITEMNUM_CODE) {
            titleTextView.setText(R.string.gjjy_text);
        }
        menuImageView.setVisibility(View.VISIBLE);
        menuImageView.setImageResource(R.drawable.ic_more);

        if (invuse != null) {
            invusenumText.setText(invuse.getINVUSENUM());
            descriptionText.setText(invuse.getDESCRIPTION());
            productionplansnumText.setText(invuse.getPRODUCTIONPLANSNUM());
            plandescText.setText(invuse.getPLANDESC());
            enterbyText.setText(invuse.getENTERBY());
            enterdateText.setText(invuse.getENTERDATE());
        }

        layoutManager = new LinearLayoutManager(InvuseDetailsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        refresh_layout.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refresh_layout.setRefreshing(true);

        refresh_layout.setOnRefreshListener(refreshOnRefreshListener);
        refresh_layout.setOnLoadListener(refreshOnLoadListener);

        initAdapter(new ArrayList<N_TASKASSET>());
        getData();
    }


    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }


    @OnClick(R.id.status_btn_id)
    void setUpdateStatusOnClickListener() {
        getLoadingDialog("正在修改").show();
        startAsyncTask();
    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageViewOnClickListener() {
        finish();
    }

    //菜单事件
    @OnClick(R.id.title_add)
    void setMenuImageViewOnClickListener() {
        showPopupWindow(menuImageView);
    }


    private void showPopupWindow(View view) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(InvuseDetailsActivity.this).inflate(
                R.layout.invuse_powindow, null);


        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.popup_background_mtrl_mult));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

        TextView actualTextView = (TextView) contentView.findViewById(R.id.invuseline_text_id);
        actualTextView.setOnClickListener(actualTextViewOnClickListener);

    }


    //实际领用
    private View.OnClickListener actualTextViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            intent.setClass(InvuseDetailsActivity.this, InvuseLineActivity.class);
            intent.putExtra("invusenum", invuse.getINVUSENUM());
            startActivityForResult(intent, 0);
            popupWindow.dismiss();
        }
    };


    /**
     * 修改状态*
     */
    private void startAsyncTask() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String reviseresult = null;
                reviseresult = AndroidClientService.MobileBorrowChangeStatus(InvuseDetailsActivity.this, invuse.getINVUSENUM());
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                if (s.equals("")) {
                    MessageUtils.showMiddleToast(InvuseDetailsActivity.this, "修改失败");
                } else {
                    MessageUtils.showMiddleToast(InvuseDetailsActivity.this, s);
                }


            }
        }.execute();


    }


    private SwipeRefreshLayout.OnRefreshListener refreshOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            page = 1;
            getData();
        }
    };

    private SwipeRefreshLayout.OnLoadListener refreshOnLoadListener = new SwipeRefreshLayout.OnLoadListener() {
        @Override
        public void onLoad() {
            page++;
            getData();
        }
    };

    //获取数据
    private void getData() {
        HttpManager.getDataPagingInfo(InvuseDetailsActivity.this, HttpManager.getN_PRODUCTIONPLANS(invuse.getPRODUCTIONPLANSNUM(), page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<N_PRODUCTIONPLANS> item = JsonUtils.parsingN_PRODUCTIONPLANS(results.getResultlist());
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {
                    String tasknum = item.get(0).getTASKNUM();
                    getN_TASKASSETDATA(tasknum);
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
    private void getN_TASKASSETDATA(String tasknum) {
        HttpManager.getDataPagingInfo(InvuseDetailsActivity.this, HttpManager.getN_TASKASSET("", tasknum, page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<N_TASKASSET> item = JsonUtils.parsingN_TASKASSET(results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<N_TASKASSET>();
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
    private void initAdapter(final List<N_TASKASSET> list) {
        n_taskassetlistadapter = new N_taskassetListAdapter(InvuseDetailsActivity.this, R.layout.list_item_jh, list);
        recyclerView.setAdapter(n_taskassetlistadapter);
        n_taskassetlistadapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<N_TASKASSET> list) {
        n_taskassetlistadapter.addData(list);
    }

}
