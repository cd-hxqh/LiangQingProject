package com.zcl.hxqh.liangqingmanagement.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.N_TASKASSET;
import com.zcl.hxqh.liangqingmanagement.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 计划领用
 */
public class N_taskassetListAdapter extends BaseQuickAdapter<N_TASKASSET> {
    public N_taskassetListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }



    @Override
    protected void convert(BaseViewHolder helper, N_TASKASSET item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.itemnum_text_id, item.getITEMNUM());
        helper.setText(R.id.sum_text_id, item.getSUM());
    }


}
