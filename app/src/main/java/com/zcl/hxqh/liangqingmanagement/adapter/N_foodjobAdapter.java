package com.zcl.hxqh.liangqingmanagement.adapter;

import android.animation.Animator;
import android.content.Context;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.N_FOODJOB;
import com.zcl.hxqh.liangqingmanagement.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 出入仓告知记录
 */
public class N_foodjobAdapter extends BaseQuickAdapter<N_FOODJOB> {
    public N_foodjobAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, N_FOODJOB item) {
        helper.setText(R.id.foodjobnum_text_id, item.getFOODJOBNUM());
        helper.setText(R.id.loc_text_id, item.getLOC());
        helper.setText(R.id.reportdate_text_id, item.getREPORTDATE());
        helper.setText(R.id.holder_text_id, item.getHOLDERDISPLAYNAME());
    }


}
