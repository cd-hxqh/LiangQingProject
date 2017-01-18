package com.zcl.hxqh.liangqingmanagement.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.view.activity.N_grainjcListActivity;


/**
 * 粮情管理的fragment
 */
public class CclqjcdFragment extends BaseFragment {
    private static final String TAG = "CclqjcdFragment";
    private TextView lqjcText;//粮情检查
    private TextView sbssaqwsjc_text;//设备设施安全卫生检查
    private TextView zyxcjcText;//作业现场检查

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lqjcd, container,
                false);

        findByIdView(view);
        initView();
        return view;
    }


    /**
     * 初始化界面组件*
     */
    private void findByIdView(View view) {
        lqjcText = (TextView) view.findViewById(R.id.lqjc_id);
        sbssaqwsjc_text = (TextView) view.findViewById(R.id.sbwsjc_id);
        zyxcjcText = (TextView) view.findViewById(R.id.zyxcjc_id);
    }


    /**
     * 设置事件监听*
     */
    private void initView() {
        lqjcText.setOnClickListener(OnClickListener);
        sbssaqwsjc_text.setOnClickListener(OnClickListener);
        zyxcjcText.setOnClickListener(OnClickListener);

    }

    private View.OnClickListener OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), N_grainjcListActivity.class);
            switch (v.getId()) {
                case R.id.lqjc_id:
                    intent.putExtra("worktype","粮情检查");
                    startActivityForResult(intent, 0);
                    break;
                case R.id.sbwsjc_id:
                    intent.putExtra("worktype","设备设施安全卫生检查");
                    startActivityForResult(intent, 0);
                    break;
                case R.id.zyxcjc_id:
                    intent.putExtra("worktype","作业现场检查");
                    startActivityForResult(intent, 0);
                    break;
            }

        }
    };


}
