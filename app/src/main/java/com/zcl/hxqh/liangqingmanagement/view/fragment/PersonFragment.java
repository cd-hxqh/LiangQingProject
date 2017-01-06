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
 * 人员查询
 */
public class PersonFragment extends BaseFragment {


    private static final String TAG = "PersonFragment";

    private static final int PERSION_MARK=1001;

    private Button personBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container,
                false);

        findByIdView(view);
        initView();
        return view;
    }


    /**
     * 初始化界面组件*
     */
    private void findByIdView(View view) {
        personBtn = (Button) view.findViewById(R.id.person_btn_id);

    }


    /**
     * 设置事件监听*
     */
    private void initView() {

        personBtn.setOnClickListener(personBtnOnClickListener);


    }

    private View.OnClickListener personBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Nfc_Activity.class);
            intent.putExtra("mark",PERSION_MARK);
            startActivityForResult(intent, 0);
        }
    };


}
