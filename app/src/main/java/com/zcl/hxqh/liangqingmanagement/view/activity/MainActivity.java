package com.zcl.hxqh.liangqingmanagement.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zcl.hxqh.liangqingmanagement.AppManager;
import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.adapter.BaseQuickAdapter;
import com.zcl.hxqh.liangqingmanagement.adapter.MyGridViewAdpter;
import com.zcl.hxqh.liangqingmanagement.adapter.MyViewPagerAdapter;
import com.zcl.hxqh.liangqingmanagement.adapter.WfassignmentListAdapter;
import com.zcl.hxqh.liangqingmanagement.api.HttpManager;
import com.zcl.hxqh.liangqingmanagement.api.HttpRequestHandler;
import com.zcl.hxqh.liangqingmanagement.api.JsonUtils;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.constants.Constants;
import com.zcl.hxqh.liangqingmanagement.model.ProdctBean;
import com.zcl.hxqh.liangqingmanagement.model.WFASSIGNMENT;
import com.zcl.hxqh.liangqingmanagement.model.WORKORDER;
import com.zcl.hxqh.liangqingmanagement.until.AccountUtils;
import com.zcl.hxqh.liangqingmanagement.view.widght.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity
 */
public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {

    public static final int ROTASSETNUM_CODE = 10001;  //移动设备查询

    public static final int ITEMNUM_CODE = 10002;  //工具查询查询

    private ImageView memberImg;
    private TextView memberText;
    private ViewPager viewPager;
    private LinearLayout group;//圆点指示器
    private ImageView[] ivPoints;//小圆点图片的集合
    private int totalPage; //总的页数
    private int mPageSize = 8; //每页显示的最大的数量
    private List<ProdctBean> listDatas;//总的数据源
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中
    private int currentPage;//当前页
    private ArrayList<String> list = new ArrayList<>();//appid集合
    private String[] proName = {"仓储基础工作", "扦样单", "货运预报", "工单管理", "工位管理", "人员查询", "车辆查询",
            "用工验收", "移动设备借用", "移动设备归还", "移动设备查询", "工具借用", "工具归还", "工具查询", "调车作业指令单", "其他"};

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
    private WfassignmentListAdapter workorderListAdapter;
    private int page = 1;


    ArrayList<WFASSIGNMENT> items = new ArrayList<WFASSIGNMENT>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getIntentData();
        findViewById();
        initView();
    }

    private void getIntentData() {
        list = getIntent().getStringArrayListExtra("appidArray");
    }

    @Override
    protected void findViewById() {
        memberImg = (ImageView) findViewById(R.id.img_member);
        memberText = (TextView) findViewById(R.id.txt_member);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        group = (LinearLayout) findViewById(R.id.points);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_id);
        refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        nodatalayout = (LinearLayout) findViewById(R.id.have_not_data_id);
    }

    @Override
    protected void initView() {
        layoutManager = new LinearLayoutManager(MainActivity.this);
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

        refresh_layout.setRefreshing(true);
        initAdapter(new ArrayList<WFASSIGNMENT>());
        getData();
        memberText.setText(AccountUtils.getdisplayName(MainActivity.this));
        listDatas = new ArrayList<ProdctBean>();
        if (list != null && list.size() != 0) {
            if (list.contains(Constants.N_GRAINJC_APPID)) {//粮情标准单
                listDatas.add(new ProdctBean(proName[0], Constants.N_GRAINJC_APPID, R.drawable.item_lqbz));
            }
            if (list.contains(Constants.N_SAMPLE_APPID)) {//扦样单
                listDatas.add(new ProdctBean(proName[1], Constants.N_SAMPLE_APPID, R.drawable.item_qyd));
            }
            if (list.contains(Constants.N_CAR_APPID)) {//货运预报
                listDatas.add(new ProdctBean(proName[2], Constants.N_CAR_APPID, R.drawable.item_hyyb));
            }
            if (list.contains(Constants.WORKORDER_APPID)) {//工单管理
                listDatas.add(new ProdctBean(proName[3], Constants.WORKORDER_APPID, R.drawable.item_gdgl));
            }
            if (list.contains(Constants.N_WTLINE_APPID)) {//工位管理
                listDatas.add(new ProdctBean(proName[4], Constants.N_WTLINE_APPID, R.drawable.item_gwgl));
            }
            if (list.contains(Constants.PERSON_APPID)) {//人员查询
                listDatas.add(new ProdctBean(proName[5], Constants.PERSON_APPID, R.drawable.item_rycx));
            }
            if (list.contains(Constants.N_CARTASK_NAME)) {//车辆查询
                listDatas.add(new ProdctBean(proName[6], Constants.N_CARTASK_NAME, R.drawable.item_car));
            }
            if (list.contains(Constants.N_CARTASK_NAME)) {//用工验收
                listDatas.add(new ProdctBean(proName[7], Constants.N_LABAPYS_APPID, R.drawable.item_labapys));
            }
            if (list.contains(Constants.N_MOVEASSE_APPID)) {//移动设备或工具
                listDatas.add(new ProdctBean(proName[8], Constants.N_MOVEASSE_APPID, R.drawable.item_ydsbjy));
                listDatas.add(new ProdctBean(proName[9], Constants.N_MOVEASSE1_APPID, R.drawable.ic_invuseline));
                listDatas.add(new ProdctBean(proName[10], Constants.N_MOVEASSE2_APPID, R.drawable.item_ydsbcx));
                listDatas.add(new ProdctBean(proName[11], Constants.N_MOVEASSE3_APPID, R.drawable.item_gjjy));
                listDatas.add(new ProdctBean(proName[12], Constants.N_MOVEASSE4_APPID, R.drawable.item_gjgh));
                listDatas.add(new ProdctBean(proName[13], Constants.N_MOVEASSE5_APPID, R.drawable.item_gjcx));
            }

            if (list.contains(Constants.N_INSTRUCS_APPID)) {//调车作业指令单
                listDatas.add(new ProdctBean(proName[14], Constants.N_INSTRUCS_APPID, R.drawable.ic_n_instrucs));
            }
            listDatas.add(new ProdctBean(proName[15], "其他", R.drawable.item_qt));

        }
        //总的页数向上取整
        totalPage = (int) Math.ceil(listDatas.size() * 1.0 / mPageSize);
        viewPagerList = new ArrayList<View>();
        for (int i = 0; i < totalPage; i++) {
            //每个页面都是inflate出一个新实例
            final GridView gridView = (GridView) View.inflate(this, R.layout.item_gridview, null);
            gridView.setAdapter(new MyGridViewAdpter(this, listDatas, i, mPageSize));
            //添加item点击监听
            gridView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {
                    // TODO Auto-generated method stub
                    Object obj = gridView.getItemAtPosition(position);
                    if (obj != null && obj instanceof ProdctBean) {
//                        System.out.println(obj);
//                        Toast.makeText(MainActivity.this, ((ProdctBean) obj).getName(), Toast.LENGTH_SHORT).show();
                        ProdctBean prodctBean = (ProdctBean) obj;
                        Intent intent;
                        switch (prodctBean.getAppid()) {
                            case Constants.N_GRAINJC_APPID:
                                intent = new Intent(MainActivity.this, CclqjcdActivity.class);
                                startActivity(intent);
                                break;
                            case Constants.N_SAMPLE_APPID:
                                intent = new Intent(MainActivity.this, QydActivity.class);
                                startActivity(intent);
                                break;
                            case Constants.N_CAR_APPID:
                                intent = new Intent(MainActivity.this, HyybActivity.class);
                                startActivity(intent);
                                break;
                            case Constants.N_WTLINE_APPID:
                                intent = new Intent(MainActivity.this, WtlineActivity.class);
                                startActivity(intent);
                                break;
                            case Constants.PERSON_APPID:
                                intent = new Intent(MainActivity.this, PersonActivity.class);
                                startActivity(intent);
                                break;
                            case Constants.N_CARTASK_NAME:
                                intent = new Intent(MainActivity.this, N_cartaskActivity.class);
                                startActivity(intent);
                                break;
                            case Constants.WORKORDER_APPID:
                                intent = new Intent(MainActivity.this, WorkOrderActivity.class);
                                startActivity(intent);
                                break;
                            case Constants.N_LABAPYS_APPID:
                                intent = new Intent(MainActivity.this, N_labapysActivity.class);
                                startActivity(intent);
                                break;
                            case Constants.N_MOVEASSE_APPID:
                                intent = new Intent(MainActivity.this, InvuseActivity.class);
                                intent.putExtra("mark", ROTASSETNUM_CODE);
                                startActivityForResult(intent, 0);
                                break;
                            case Constants.N_MOVEASSE1_APPID:
                                intent = new Intent(MainActivity.this, GhAddActivity.class);
                                intent.putExtra("mark", ROTASSETNUM_CODE);
                                startActivityForResult(intent, 0);
                                break;
                            case Constants.N_MOVEASSE2_APPID:
                                intent = new Intent(MainActivity.this, MipcaActivityCapture.class);
                                intent.putExtra("mark", ROTASSETNUM_CODE);
                                startActivityForResult(intent, 0);
                                break;
                            case Constants.N_MOVEASSE3_APPID:
                                intent = new Intent(MainActivity.this, InvuseActivity.class);
                                intent.putExtra("mark", ITEMNUM_CODE);
                                startActivityForResult(intent, 0);
                                break;
                            case Constants.N_MOVEASSE4_APPID:
                                intent = new Intent(MainActivity.this, GhAddActivity.class);
                                intent.putExtra("mark", ITEMNUM_CODE);
                                startActivityForResult(intent, 0);
                                break;
                            case Constants.N_MOVEASSE5_APPID:
                                intent = new Intent(MainActivity.this, MipcaActivityCapture.class);
                                intent.putExtra("mark", ITEMNUM_CODE);
                                startActivityForResult(intent, 0);
                                break;
                            case Constants.N_INSTRUCS_APPID: //调度作业指令单
                                intent = new Intent(MainActivity.this, N_instrucstaskActivity.class);
                                startActivityForResult(intent, 0);
                                break;
                            case "其他":
                                intent = new Intent(MainActivity.this, SettingActivity.class);
                                startActivity(intent);
                                break;
                        }
                    }
                }
            });
            //每一个GridView作为一个View对象添加到ViewPager集合中
            viewPagerList.add(gridView);
        }
        //设置ViewPager适配器
        viewPager.setAdapter(new MyViewPagerAdapter(viewPagerList));

        if (totalPage == 1) {
            group.setVisibility(View.GONE);
        }
        //添加小圆点
        ivPoints = new ImageView[totalPage];
        for (int i = 0; i < totalPage; i++) {
            //循坏加入点点图片组
            ivPoints[i] = new ImageView(this);
            if (i == 0) {
                ivPoints[i].setImageResource(R.drawable.page_focuese);
            } else {
                ivPoints[i].setImageResource(R.drawable.page_unfocused);
            }
            ivPoints[i].setPadding(8, 8, 8, 8);
            group.addView(ivPoints[i]);
        }
        //设置ViewPager的滑动监听，主要是设置点点的背景颜色的改变
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                //currentPage = position;
                for (int i = 0; i < totalPage; i++) {
                    if (i == position) {
                        ivPoints[i].setImageResource(R.drawable.page_focuese);
                    } else {
                        ivPoints[i].setImageResource(R.drawable.page_unfocused);
                    }
                }
            }
        });
    }

    /**
     * 获取数据*
     */
    private void getData() {
        HttpManager.getDataPagingInfo(MainActivity.this, HttpManager.getWFASSIGNMENT(AccountUtils.getloginUserName(MainActivity.this), page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
//                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<WFASSIGNMENT> item = JsonUtils.parsingWFASSIGNMENT(MainActivity.this, results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<WFASSIGNMENT>();
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
    private void initAdapter(final List<WFASSIGNMENT> list) {
        nodatalayout.setVisibility(View.GONE);
        workorderListAdapter = new WfassignmentListAdapter(MainActivity.this, R.layout.list_item_wfassignment, list);
        recyclerView.setAdapter(workorderListAdapter);
        workorderListAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(MainActivity.this, WorkorderDetailsActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("workorder", items.get(position));
//                bundle.putInt("position", position);
//                intent.putExtras(bundle);
//                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<WFASSIGNMENT> list) {
        workorderListAdapter.addData(list);
    }

    private View.OnClickListener addOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            WORKORDER n_carline = new WORKORDER();
            Intent intent = new Intent(MainActivity.this, WorkorderAddNewActivity.class);
            startActivityForResult(intent, 1);
        }
    };

    @Override
    public void onLoad() {
        page++;
        getData();
    }

    @Override
    public void onRefresh() {
        page = 1;
        getData();
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {


        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.AppExit(MainActivity.this);
        }
    }

}
