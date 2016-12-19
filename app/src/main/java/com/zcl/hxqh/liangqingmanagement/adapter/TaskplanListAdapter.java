package com.zcl.hxqh.liangqingmanagement.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.N_TASKPLAN;
import com.zcl.hxqh.liangqingmanagement.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 保管员货位对照表
 */
public class TaskplanListAdapter extends BaseQuickAdapter<N_TASKPLAN> {
    public TaskplanListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, N_TASKPLAN item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.plannum_text_id, item.getPLANNUM());
        helper.setText(R.id.description_text_id, item.getDESCRIPTION());
    }


}
