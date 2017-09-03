package com.zcl.hxqh.liangqingmanagement.adapter;

import android.animation.Animator;
import android.content.Context;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.N_INSTRUCSTASK;
import com.zcl.hxqh.liangqingmanagement.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 调车作业指令单
 */
public class N_instrucstaskListAdapter extends BaseQuickAdapter<N_INSTRUCSTASK> {
    public N_instrucstaskListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, N_INSTRUCSTASK item) {
        helper.setText(R.id.workorder_sb_text, item.getINSTRUCNUM());
        helper.setText(R.id.item_desc_title, "状态:");
        helper.setText(R.id.item_desc_text, item.getSTATUS());
        helper.setText(R.id.workorder_reportdate, item.getENTRYDATE());
        helper.setText(R.id.workorder_fxr, item.getENTRYBY());
    }


}
