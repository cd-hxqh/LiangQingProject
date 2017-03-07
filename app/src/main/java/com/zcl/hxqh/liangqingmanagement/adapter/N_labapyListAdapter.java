package com.zcl.hxqh.liangqingmanagement.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.N_LABAPY;
import com.zcl.hxqh.liangqingmanagement.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 用工验收
 */
public class N_labapyListAdapter extends BaseQuickAdapter<N_LABAPY> {
    public N_labapyListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }



    @Override
    protected void convert(BaseViewHolder helper, N_LABAPY item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.workorder_sb_text, item.getN_LABAPYNUM());
        helper.setText(R.id.details_text_id, item.getDETAILS());
        helper.setText(R.id.workorder_reportdate, item.getYSSTART());
        helper.setText(R.id.workorder_fxr, item.getLINKMAN());
    }


}
