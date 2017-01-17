package com.zcl.hxqh.liangqingmanagement.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.WORKORDER;
import com.zcl.hxqh.liangqingmanagement.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 紧急工单
 */
public class WorkorderJinJiListAdapter extends BaseQuickAdapter<WORKORDER> {
    public WorkorderJinJiListAdapter(Context context, int layoutResId, List data) {
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
        helper.setText(R.id.wonum_text_id, item.getWONUM());
        helper.setText(R.id.n_region_text_id, item.getN_REGION());
        helper.setText(R.id.gzms_text_id, item.getDESCRIPTION());
        helper.setText(R.id.status_text_id, item.getSTATUS());
        helper.setText(R.id.workorder_reportdate, item.getREPORTDATE());
        helper.setText(R.id.workorder_fxr, item.getFXR());
    }


}
