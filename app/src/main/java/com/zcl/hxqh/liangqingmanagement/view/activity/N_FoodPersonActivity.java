package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.zcl.hxqh.liangqingmanagement.adapter.BaseQuickAdapter;
import com.zcl.hxqh.liangqingmanagement.adapter.TemPersonListAdapter;
import com.zcl.hxqh.liangqingmanagement.api.JsonUtils;
import com.zcl.hxqh.liangqingmanagement.bean.TemPERSON;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.view.widght.SwipeRefreshLayout;
import com.zcl.hxqh.liangqingmanagement.webserviceclient.AndroidClientService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 出入仓记录人员选项值
 **/

public class N_FoodPersonActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {

    private static final String TAG = "N_FoodPersonActivity";

    @Bind(R.id.title_name)
    TextView titleTextView;//标题
    @Bind(R.id.title_back_id)
    ImageView backImageView;//返回按钮
    @Bind(R.id.search_edit)
    EditText search; //编辑框
    @Bind(R.id.sbmittext_id)
    ImageButton submitBtn; //提交
    LinearLayoutManager layoutManager;


    @Bind(R.id.recyclerView_id)
    RecyclerView recyclerView;//RecyclerView
    @Bind(R.id.have_not_data_id)
    LinearLayout nodatalayout;//暂无数据

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout refresh_layout = null;//界面刷新

    private TemPersonListAdapter temPersonListAdapter;
    private String type;

    protected FlippingLoadingDialog mLoadingDialog;

    private String chooseDisplayname="";//多选字符串名称

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
    }


    @Override
    protected void findViewById() {


    }

    //返回事件
    @OnClick(R.id.title_back_id)
    void setBackImageView() {
        finish();
    }

    @Override
    protected void initView() {
        titleTextView.setText(R.string.xxz_text);
        search.setVisibility(View.GONE);
        if (type.equals("TELLER")) {
            submitBtn.setVisibility(View.VISIBLE);
        }

        layoutManager = new LinearLayoutManager(N_FoodPersonActivity.this);
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

        initAdapter(new ArrayList<TemPERSON>());
        getLoadingDialog(getResources().getString(R.string.seaching_text)).show();
        getPersonData();
    }

    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }


    @Override
    public void onLoad() {
        refresh_layout.setLoading(false);

    }

    @Override
    public void onRefresh() {
        refresh_layout.setRefreshing(false);

    }


    //确认事件
    @OnClick(R.id.sbmittext_id)
    void setSubmitBtn() {
        chooseDisplayname = chooseDisplayname.substring(0, chooseDisplayname.length() - 1);
        Intent intent = getIntent();
        intent.putExtra("displayname", chooseDisplayname);
        setResult(1000, intent);
        finish();
    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<TemPERSON> list) {
        temPersonListAdapter = new TemPersonListAdapter(N_FoodPersonActivity.this, R.layout.list_temperson_item, list, type);
        recyclerView.setAdapter(temPersonListAdapter);
        temPersonListAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!type.equals("TELLER")) {
                    Intent intent = getIntent();
                    TemPERSON person = (TemPERSON) temPersonListAdapter.getData().get(position);
                    intent.putExtra("displayname", person.getdisplayname());
                    intent.putExtra("personid", person.getpersonid());
                    setResult(1000, intent);
                    finish();
                }

            }
        });
        temPersonListAdapter.setmCheckBoxListener(new TemPersonListAdapter.CheckBoxListener() {
            @Override
            public void getCheckTemPERSON(boolean b,TemPERSON temPERSON) {
                Log.e(TAG,"b="+b+",chooseDisplayname="+chooseDisplayname);
                if(b){
                    chooseDisplayname = chooseDisplayname+temPERSON.displayname + "、";
                }else{
                    chooseDisplayname=chooseDisplayname.replace(temPERSON.displayname + "、","");
                }


            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<TemPERSON> list) {
        temPersonListAdapter.addData(list);
    }


    //获取人员数据
    private void getPersonData() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String reviseresult = AndroidClientService.getPersionData(N_FoodPersonActivity.this, type);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLoadingDialog.dismiss();
                getTemPersion(s);
            }
        }.execute();

    }


    /**
     * 解析数据
     **/
    private void getTemPersion(String s) {
        ArrayList<TemPERSON> item = JsonUtils.parsingTemPERSON(s);
        refresh_layout.setRefreshing(false);
        refresh_layout.setLoading(false);
        if (item == null || item.isEmpty()) {
            nodatalayout.setVisibility(View.VISIBLE);
        } else {
            addData(item);
            nodatalayout.setVisibility(View.GONE);


        }
    }
}