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
import com.zcl.hxqh.liangqingmanagement.view.activity.WorkorderListActivity;
import com.zcl.hxqh.liangqingmanagement.view.widght.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 消缺工单的fragment
 */
public class WorkOrderFragment extends BaseFragment{
    private static final String TAG = "WorkOrderFragment";
    private TextView qxWorkorder;//缺陷工单
    private TextView jjWorkorder;//紧急工单

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workorder, container,
                false);

        findByIdView(view);
        initView();
        return view;
    }


    /**
     * 初始化界面组件*
     */
    private void findByIdView(View view) {
        qxWorkorder = (TextView) view.findViewById(R.id.workorder_qx_id);
        jjWorkorder = (TextView) view.findViewById(R.id.workorder_jj_id);
    }


    /**
     * 设置事件监听*
     */
    private void initView() {
        qxWorkorder.setOnClickListener(qxWorkorderOnClickListener);
    }

    private View.OnClickListener qxWorkorderOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), WorkorderListActivity.class);
            startActivity(intent);
        }
    };

}
