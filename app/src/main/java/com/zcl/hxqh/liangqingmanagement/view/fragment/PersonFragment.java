package com.zcl.hxqh.liangqingmanagement.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.adapter.BaseQuickAdapter;
import com.zcl.hxqh.liangqingmanagement.adapter.WtlaborListAdapter;
import com.zcl.hxqh.liangqingmanagement.adapter.WtlineListAdapter;
import com.zcl.hxqh.liangqingmanagement.api.HttpManager;
import com.zcl.hxqh.liangqingmanagement.api.HttpRequestHandler;
import com.zcl.hxqh.liangqingmanagement.api.JsonUtils;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.liangqingmanagement.model.N_WTLINE;
import com.zcl.hxqh.liangqingmanagement.model.PERSON;
import com.zcl.hxqh.liangqingmanagement.model.WTLABOR;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.view.activity.Nfc_Activity;
import com.zcl.hxqh.liangqingmanagement.view.activity.WtlaborDetailsActivity;
import com.zcl.hxqh.liangqingmanagement.view.widght.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 车辆查询的fragment
 */
public class PersonFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {


    private static final String TAG = "PersonFragment";

    /**
     * 一卡通卡号
     **/
    private TextView tagidTextView;
    /**
     * TagID扫描按钮
     **/
    private ImageView tagIdImageView;

    /**
     * 姓名
     **/
    private TextView displaynameTextView;
    /**
     * 身份证
     **/
    private TextView n_idnumTextView;
    /**
     * 工种
     **/
    private TextView n_worktypeTextView;
    /**
     * 电话
     **/
    private TextView n_phoneTextView;

    /**
     * ViewPager*
     */
    private ViewPager viewPager;

    /**
     * 用工信息,考勤记录*
     */
    private TextView workText, attenceText;


    private List<View> views;// Tab页面列表
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    private View view1, view2;//各个页卡


    //用工记录开始

    LinearLayoutManager layoutManager1;
    /**
     * RecyclerView*
     */
    public RecyclerView recyclerView1;
    /**
     * 暂无数据*
     */
    private LinearLayout nodatalayout1;
    /**
     * 界面刷新*
     */
    private SwipeRefreshLayout refresh_layout1 = null;

    /**
     * 适配器*
     */
    private WtlaborListAdapter wtlaborListAdapter;

    ArrayList<WTLABOR> wtitems = new ArrayList<WTLABOR>();


    //用工记录结束

    //考勤记录开始

    LinearLayoutManager layoutManager2;
    /**
     * RecyclerView*
     */
    public RecyclerView recyclerView2;
    /**
     * 暂无数据*
     */
    private LinearLayout nodatalayout2;
    /**
     * 界面刷新*
     */
    private SwipeRefreshLayout refresh_layout2 = null;

    /**
     * 适配器*
     */
    private WtlineListAdapter wtlineListAdapter;

    ArrayList<N_WTLINE> items = new ArrayList<N_WTLINE>();


    //考勤记录结束


    protected FlippingLoadingDialog mLoadingDialog;


    /**
     * 一卡通ID
     **/
    private String tagId = "";

    public int current = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_jbxx_details, container,
                false);

        findByIdView(view);
        initView();
        InitViewPager();
        return view;
    }


    /**
     * 初始化界面组件*
     */
    private void findByIdView(View view) {
        tagidTextView = (TextView) view.findViewById(R.id.cardid_text_id);
        tagIdImageView = (ImageView) view.findViewById(R.id.nfc_image_id);

        displaynameTextView = (TextView) view.findViewById(R.id.person_name_id);
        n_idnumTextView = (TextView) view.findViewById(R.id.person_idnum_id);
        n_worktypeTextView = (TextView) view.findViewById(R.id.person_worktype_id);
        n_phoneTextView = (TextView) view.findViewById(R.id.person_phone_id);

        workText = (TextView) view.findViewById(R.id.work_history);
        attenceText = (TextView) view.findViewById(R.id.attence_history);
        viewPager = (ViewPager) view.findViewById(R.id.pager);


    }


    /**
     * 设置事件监听*
     */
    private void initView() {
        tagIdImageView.setOnClickListener(tagIdImageViewOnClickListener);
        InitTextView();

    }


    private void InitViewPager() {
        views = new ArrayList<View>();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view1 = inflater.inflate(R.layout.fragment_list2, null);
        view2 = inflater.inflate(R.layout.fragment_list2, null);
        views.add(view1);
        views.add(view2);
        viewPager.setAdapter(new MyViewPagerAdapter(views));
        viewPager.setCurrentItem(0);
        initwtlabor(view1);
        initwtline(view2);
        setBackground(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 初始化头标
     */

    private void InitTextView() {
        workText.setOnClickListener(new MyOnClickListener(0));
        attenceText.setOnClickListener(new MyOnClickListener(1));
    }

    private class MyOnClickListener implements View.OnClickListener {

        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {
            current = index;
            setBackground(index);
            viewPager.setCurrentItem(index);
            if (index == 0) {
                if (tagId.equals("")) {
                    MessageUtils.showMiddleToast(getActivity(), "请扫描一卡通卡号");
                } else {
                    getLoadingDialog("正在获取数据").show();
                    getWTLABORData(tagId);
                }


            } else if (index == 1) {

                if (tagId.equals("")) {
                    MessageUtils.showMiddleToast(getActivity(), "请扫描一卡通卡号");
                } else {
                    getLoadingDialog("正在获取数据").show();
                    getN_WTLINEData(tagId);
                }
            }
        }

    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量

        public void onPageScrollStateChanged(int arg0) {


        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {


        }

        public void onPageSelected(int arg0) {
            setBackground(arg0);

        }

    }


    /**
     * 设置背景颜色*
     */
    private void setBackground(int index) {
        if (index == 0) {
            workText.setBackgroundResource(R.color.blue_1);
            attenceText.setBackgroundResource(R.color.light_gray);
        } else if (index == 1) {
            workText.setBackgroundResource(R.color.light_gray);
            attenceText.setBackgroundResource(R.color.blue_1);
        }
    }


    private View.OnClickListener tagIdImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //跳转至NFC扫描界面
            Intent intent = new Intent(getActivity(), Nfc_Activity.class);
            startActivityForResult(intent, 0);

        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {

            case 1002:
                tagId = data.getExtras().getString("tagId");
                tagidTextView.setText(tagId);
                getPersonData(tagId);
                setBackground(0);
                getLoadingDialog("正在获取数据").show();
                getWTLABORData(tagId);
                break;

        }
    }


    @Override
    public void onLoad() {

    }

    @Override
    public void onRefresh() {
        if (tagId.equals("")) {
            MessageUtils.showErrorMessage(getActivity(), "请先扫描卡号");
        } else {
            if (current == 0) {
                getWTLABORData(tagId);
            } else if (current == 1) {
                getN_WTLINEData(tagId);
            }

        }
    }


    /**
     * 获取人员基本信息数据*
     */
    private void getPersonData(String n_cardnum) {
        HttpManager.getDataPagingInfo(getActivity(), HttpManager.getPERSON(n_cardnum, 1, 5), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                refresh_layout2.setRefreshing(false);
                ArrayList<PERSON> item = JsonUtils.parsingPERSON(getActivity(), results.getResultlist());
                if (item != null && item.size() == 1) {
                    PERSON person = item.get(0);
                    displaynameTextView.setText(person.getDISPLAYNAME());
                    n_idnumTextView.setText(person.getN_IDNUM());
                    n_worktypeTextView.setText(person.getN_WORKTYPE());
                    n_phoneTextView.setText(person.getN_PHONE());
                } else {
                    MessageUtils.showErrorMessage(getActivity(), "此卡无相关记录");
                }
            }

            @Override
            public void onFailure(String error) {
                refresh_layout2.setRefreshing(false);
                MessageUtils.showErrorMessage(getActivity(), "此卡无相关记录");
            }
        });
    }


    /**
     * 获取用工记录
     **/
    private void initwtlabor(View view) {
        recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerView_id);
        refresh_layout1 = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        nodatalayout1 = (LinearLayout) view.findViewById(R.id.have_not_data_id);

        layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager1.scrollToPosition(0);
        recyclerView1.setLayoutManager(layoutManager1);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        refresh_layout1.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        refresh_layout1.setOnRefreshListener(this);

        wtitems = new ArrayList<WTLABOR>();
        initWtlaborListAdapter(wtitems);
    }


    /**
     * 获取考勤记录
     **/
    private void initwtline(View view) {
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView_id);
        refresh_layout2 = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        nodatalayout2 = (LinearLayout) view.findViewById(R.id.have_not_data_id);

        layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager2.scrollToPosition(0);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        refresh_layout2.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        refresh_layout2.setOnRefreshListener(this);

        items = new ArrayList<N_WTLINE>();
        initwtlineAdapter(items);
    }


    /**
     * 获取用工数据*
     */
    private void initWtlaborListAdapter(final List<WTLABOR> list) {
        wtlaborListAdapter = new WtlaborListAdapter(getActivity(), R.layout.list_item, list);
        recyclerView1.setAdapter(wtlaborListAdapter);
        wtlaborListAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), WtlaborDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("wtlabor", list.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 获取考勤数据*
     */
    private void initwtlineAdapter(final List<N_WTLINE> list) {
        wtlineListAdapter = new WtlineListAdapter(getActivity(), R.layout.wtline_list_item, list);
        recyclerView2.setAdapter(wtlineListAdapter);
    }


    /**
     * 添加用工数据*
     */
    private void addWtlaborData(final List<WTLABOR> list) {
        wtlaborListAdapter.addData(list);
    }

    /**
     * 添加考勤数据*
     */
    private void addwtlineData(final List<N_WTLINE> list) {
        wtlineListAdapter.addData(list);
    }


    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(getActivity(), msg);
        return mLoadingDialog;
    }


    /**
     * 获取用工记录数据*
     */
    private void getWTLABORData(String cardnum) {
        HttpManager.getDataPagingInfo(getActivity(), HttpManager.getWTLABOR(cardnum, 1, 5), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                mLoadingDialog.dismiss();
                ArrayList<WTLABOR> item = JsonUtils.parsingWTLABOR(getActivity(), results.getResultlist());
                refresh_layout1.setRefreshing(false);
                refresh_layout1.setLoading(false);
                Log.i(TAG, "size=" + item.size());
                if (item == null || item.isEmpty()) {
                    nodatalayout1.setVisibility(View.VISIBLE);
                } else {
                    nodatalayout1.setVisibility(View.GONE);
                    initWtlaborListAdapter(item);
                }
            }

            @Override
            public void onFailure(String error) {
                nodatalayout1.setVisibility(View.VISIBLE);
            }
        });

    }

    /**
     * 获取考勤记录数据*
     */
    private void getN_WTLINEData(String n_cardnum) {
        HttpManager.getDataPagingInfo(getActivity(), HttpManager.getN_WTLINE2(n_cardnum, 1, 5), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                mLoadingDialog.dismiss();
                ArrayList<N_WTLINE> item = JsonUtils.parsingN_WTLINE(getActivity(), results.getResultlist());
                refresh_layout2.setRefreshing(false);
                refresh_layout2.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout2.setVisibility(View.VISIBLE);
                } else {
                    nodatalayout2.setVisibility(View.GONE);
                    initwtlineAdapter(item);
//                    addwtlineData(item);
                }
            }

            @Override
            public void onFailure(String error) {
                nodatalayout2.setVisibility(View.VISIBLE);
            }
        });

    }


}
