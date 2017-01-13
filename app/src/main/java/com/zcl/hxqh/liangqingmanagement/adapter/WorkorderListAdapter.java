package com.zcl.hxqh.liangqingmanagement.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.N_CARLINE;
import com.zcl.hxqh.liangqingmanagement.model.WORKORDER;
import com.zcl.hxqh.liangqingmanagement.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 缺陷工单
 */
public class WorkorderListAdapter extends BaseQuickAdapter<WORKORDER> {
    public WorkorderListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }



    @Override
    protected void convert(BaseViewHolder helper, WORKORDER item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.workorder_sb_text, item.getSB());
        helper.setText(R.id.item_desc_text, item.getDESCRIPTION());
        helper.setText(R.id.workorder_reportdate, item.getREPORTDATE());
        helper.setText(R.id.workorder_fxr, item.getFXR());
    }


}
