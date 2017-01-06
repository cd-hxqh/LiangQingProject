package com.zcl.hxqh.liangqingmanagement.view.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.zcl.hxqh.liangqingmanagement.adapter.N_cartaskListAdapter;
import com.zcl.hxqh.liangqingmanagement.api.HttpManager;
import com.zcl.hxqh.liangqingmanagement.api.HttpRequestHandler;
import com.zcl.hxqh.liangqingmanagement.api.JsonUtils;
import com.zcl.hxqh.liangqingmanagement.bean.Results;
import com.zcl.hxqh.liangqingmanagement.model.N_CARTASK;
import com.zcl.hxqh.liangqingmanagement.view.activity.N_cartaskDetailsActivity;
import com.zcl.hxqh.liangqingmanagement.view.activity.Nfc_Activity;
import com.zcl.hxqh.liangqingmanagement.view.widght.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 车辆查询的fragment
 */
public class N_cartaskFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {


    private static final String TAG = "N_cartaskFragment";

    /**
     * 一卡通卡号
     **/
    private TextView tagidTextView;
    /**
     * TagID扫描按钮
     **/
    private ImageView tagIdImageView;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.n_cartask_details, container,
                false);

        findByIdView(view);
        initView();
        return view;
    }


    /**
     * 初始化界面组件*
     */
    private void findByIdView(View view) {
        tagidTextView = (TextView) view.findViewById(R.id.cardid_text_id);
        tagIdImageView = (ImageView) view.findViewById(R.id.nfc_image_id);

        carnoTextView = (TextView) view.findViewById(R.id.carline_carno_id);
        idcardnumTextView = (TextView) view.findViewById(R.id.idcardnum_text_id);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_id);
        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        nodatalayout = (LinearLayout) view.findViewById(R.id.have_not_data_id);

    }


    /**
     * 设置事件监听*
     */
    private void initView() {
        tagIdImageView.setOnClickListener(tagIdImageViewOnClickListener);

        layoutManager = new LinearLayoutManager(getActivity());
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
                refresh_layout.setRefreshing(true);
                getData("1762571946");
//                getData(tagId);

                break;

        }
    }


    /**
     * 获取数据*
     */
    private void getData(String tagId) {
        HttpManager.getDataPagingInfo(getActivity(), HttpManager.getN_CARTASK(tagId, 1, 5), new HttpRequestHandler<Results>() {
                    @Override
                    public void onSuccess(Results results) {
                        Log.i(TAG, "data=" + results);
                    }

                    @Override
                    public void onSuccess(Results results, int totalPages, int currentPage) {
                        ArrayList<N_CARTASK> item = JsonUtils.parsingN_CARTASK(getActivity(), results.getResultlist());
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
        n_cartaskListAdapter = new N_cartaskListAdapter(getActivity(), R.layout.list_item, list);
        recyclerView.setAdapter(n_cartaskListAdapter);
        n_cartaskListAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), N_cartaskDetailsActivity.class);
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
