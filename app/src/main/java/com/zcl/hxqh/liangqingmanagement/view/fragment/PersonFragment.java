package com.zcl.hxqh.liangqingmanagement.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.adapter.BaseQuickAdapter;
import com.zcl.hxqh.liangqingmanagement.adapter.HyybListAdapter;
import com.zcl.hxqh.liangqingmanagement.adapter.PersonAdapter;
import com.zcl.hxqh.liangqingmanagement.adapter.WtlineListAdapter;
import com.zcl.hxqh.liangqingmanagement.api.HttpManager;
import com.zcl.hxqh.liangqingmanagement.api.HttpRequestHandler;
import com.zcl.hxqh.liangqingmanagement.api.JsonUtils;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.model.N_CAR;
import com.zcl.hxqh.liangqingmanagement.model.N_WTLINE;
import com.zcl.hxqh.liangqingmanagement.model.PERSON;
import com.zcl.hxqh.liangqingmanagement.until.AccountUtils;
import com.zcl.hxqh.liangqingmanagement.until.MessageUtils;
import com.zcl.hxqh.liangqingmanagement.view.activity.N_carDetailsActivity;
import com.zcl.hxqh.liangqingmanagement.view.activity.N_grainjcAddActivity;
import com.zcl.hxqh.liangqingmanagement.view.activity.Nfc_Activity;
import com.zcl.hxqh.liangqingmanagement.view.widght.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 人员查询的fragment
 */
public class PersonFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "PersonFragment";

    /**
     * ViewPager*
     */
    private ViewPager viewPager;

    /**
     * TextView*
     */
    private TextView jbxxText, workText, attenceText;

    private List<View> views;// Tab页面列表
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    private View view1, view2, view3;//各个页卡

    private ImageView imageView;

    public TextView cardnum;//一卡通编号
    private TextView name;//姓名
    private TextView idnum;//身份证
    private TextView worktype;//工种
    private TextView phone;//电话

    LinearLayoutManager layoutManager2;


//    /**
//     * RecyclerView*
//     */
//    public RecyclerView recyclerView2;
//    /**
//     * 暂无数据*
//     */
//    private LinearLayout nodatalayout2;
//    /**
//     * 界面刷新*
//     */
//    private SwipeRefreshLayout refresh_layout2 = null;
//    /**
//     * 适配器*
//     */
//    private WulineListAdapter wulineListAdapter;
//
//    LinearLayoutManager layoutManager;

    LinearLayoutManager layoutManager3;
    /**
     * RecyclerView*
     */
    public RecyclerView recyclerView3;
    /**
     * 暂无数据*
     */
    private LinearLayout nodatalayout3;
    /**
     * 界面刷新*
     */
    private SwipeRefreshLayout refresh_layout3 = null;
    /**
     * 适配器*
     */
    private WtlineListAdapter wtlineListAdapter;
    ArrayList<N_WTLINE> items = new ArrayList<N_WTLINE>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_details, container,
                false);

        findByIdView(view);
        initView();
        InitViewPager();
        InitTextView();
        InitImageView(view);
        return view;
    }


    /**
     * 初始化界面组件*
     */
    private void findByIdView(View view) {
        jbxxText = (TextView) view.findViewById(R.id.person_details);
        workText = (TextView) view.findViewById(R.id.work_history);
        attenceText = (TextView) view.findViewById(R.id.attence_history);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
    }


    /**
     * 设置事件监听*
     */
    private void initView() {


    }

    private void InitViewPager() {
        views = new ArrayList<View>();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view1 = inflater.inflate(R.layout.fragment_person_jbxx_details, null);
        view2 = inflater.inflate(R.layout.fragment_list2, null);
        view3 = inflater.inflate(R.layout.fragment_list2, null);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        viewPager.setAdapter(new MyViewPagerAdapter(views));
        viewPager.setCurrentItem(0);
        initjbxx(view1);
//        initlqjc(view2);
        initwtline(view3);
        setBackground(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 初始化头标
     */

    private void InitTextView() {


        jbxxText.setOnClickListener(new MyOnClickListener(0));
        workText.setOnClickListener(new MyOnClickListener(1));
        attenceText.setOnClickListener(new MyOnClickListener(2));
    }

    /**
     * 2      * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
     * 3
     */

    private void InitImageView(View view) {
        imageView = (ImageView) view.findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.line).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);// 设置动画初始位置

    }

    private class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {
            setBackground(index);
            viewPager.setCurrentItem(index);
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
            jbxxText.setBackgroundResource(R.color.blue_1);
            workText.setBackgroundResource(R.color.light_gray);
            attenceText.setBackgroundResource(R.color.light_gray);
        } else if (index == 1) {
            jbxxText.setBackgroundResource(R.color.light_gray);
            workText.setBackgroundResource(R.color.blue_1);
            attenceText.setBackgroundResource(R.color.light_gray);
        } else if (index == 2) {
            jbxxText.setBackgroundResource(R.color.light_gray);
            workText.setBackgroundResource(R.color.light_gray);
            attenceText.setBackgroundResource(R.color.blue_1);
        }
    }

    private void initjbxx(View view){
        cardnum = (TextView) view.findViewById(R.id.person_cardnum);
        name = (TextView) view.findViewById(R.id.person_name);
        idnum = (TextView) view.findViewById(R.id.person_idnum);
        worktype = (TextView) view.findViewById(R.id.person_worktype);
        phone = (TextView) view.findViewById(R.id.person_phone);

        cardnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), Nfc_Activity.class);
//                intent.putExtra("type", "person");
//                startActivityForResult(intent,0);
                cardnum.setText("1762571946");
                getPersonData("1762571946");
                getN_WTLINEData("1762571946");
            }
        });
    }

    private void initwtline(View view){
        recyclerView3 = (RecyclerView) view.findViewById(R.id.recyclerView_id);
        refresh_layout3 = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        nodatalayout3 = (LinearLayout) view.findViewById(R.id.have_not_data_id);

        layoutManager3 = new LinearLayoutManager(getActivity());
        layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager3.scrollToPosition(0);
        recyclerView3.setLayoutManager(layoutManager3);
        recyclerView3.setItemAnimator(new DefaultItemAnimator());
        refresh_layout3.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
//        refresh_layout3.setRefreshing(true);

        refresh_layout3.setOnRefreshListener(this);

        initwtlineAdapter(new ArrayList<N_WTLINE>());
        items = new ArrayList<>();
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
                ArrayList<PERSON> item = JsonUtils.parsingPERSON(getActivity(), results.getResultlist());
                if (item!=null&&item.size()==1){
                    PERSON person = item.get(0);
                    name.setText(person.getDISPLAYNAME());
                    idnum.setText(person.getN_IDNUM());
                    worktype.setText(person.getN_WORKTYPE());
                    phone.setText(person.getN_PHONE());
                }else {
                    MessageUtils.showErrorMessage(getActivity(),"人员查询失败，请重新扫描");
                }
            }

            @Override
            public void onFailure(String error) {
                MessageUtils.showErrorMessage(getActivity(),"人员查询失败，请重新扫描");
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
                ArrayList<N_WTLINE> item = JsonUtils.parsingN_WTLINE(getActivity(), results.getResultlist());
                refresh_layout3.setRefreshing(false);
                refresh_layout3.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout3.setVisibility(View.VISIBLE);
                } else {
                    nodatalayout3.setVisibility(View.GONE);
                    initwtlineAdapter(item);
//                    addwtlineData(items);
                }
            }

            @Override
            public void onFailure(String error) {
                nodatalayout3.setVisibility(View.VISIBLE);
            }
        });

    }

    /**
     * 获取数据*
     */
    private void initwtlineAdapter(final List<N_WTLINE> list) {
        wtlineListAdapter = new WtlineListAdapter(getActivity(), R.layout.wtline_list_item, list);
        recyclerView3.setAdapter(wtlineListAdapter);
    }

    /**
     * 添加数据*
     */
    private void addwtlineData(final List<N_WTLINE> list) {
        wtlineListAdapter.addData(list);
    }

    @Override
    public void onRefresh() {
        if (cardnum.getText().toString().equals("")){
            MessageUtils.showErrorMessage(getActivity(),"请先扫描卡号");
        }else {
            getN_WTLINEData(cardnum.getText().toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1003:
                String tagId = data.getExtras().getString("tagId");
                cardnum.setText("8058933");
                getPersonData("8058933");
                getN_WTLINEData("8058933");
                break;

        }
    }

}
