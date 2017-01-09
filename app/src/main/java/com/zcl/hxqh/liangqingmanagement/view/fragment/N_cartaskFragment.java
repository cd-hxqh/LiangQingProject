package com.zcl.hxqh.liangqingmanagement.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.view.activity.Nfc_Activity;


/**
 * 车辆查询
 */
public class N_cartaskFragment extends BaseFragment {


    private static final String TAG = "N_cartaskFragment";

    private static final int N_CARTASK_MARK=1002;

    private Button n_cartaskBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_n_cartask, container,
                false);

        findByIdView(view);
        initView();
        return view;
    }


    /**
     * 初始化界面组件*
     */
    private void findByIdView(View view) {
        n_cartaskBtn = (Button) view.findViewById(R.id.cartask_btn_id);

    }


    /**
     * 设置事件监听*
     */
    private void initView() {

        n_cartaskBtn.setOnClickListener(n_cartaskBtnOnClickListener);


    }

    private View.OnClickListener n_cartaskBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Nfc_Activity.class);
            intent.putExtra("mark",N_CARTASK_MARK);
            startActivityForResult(intent, 0);
        }
    };


}
