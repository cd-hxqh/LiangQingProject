package com.zcl.hxqh.liangqingmanagement.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.view.activity.WorkorderJinJiActivity;
import com.zcl.hxqh.liangqingmanagement.view.activity.WorkorderListActivity;


/**
 * 消缺工单的fragment
 */
public class WorkOrderFragment extends BaseFragment {
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
        jjWorkorder.setOnClickListener(jjWorkorderOnClickListener);
    }

    private View.OnClickListener qxWorkorderOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), WorkorderListActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener jjWorkorderOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), WorkorderJinJiActivity.class);
            startActivityForResult(intent, 0);
        }
    };

}
