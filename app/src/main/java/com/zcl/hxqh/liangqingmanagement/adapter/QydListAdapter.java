package com.zcl.hxqh.liangqingmanagement.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.N_SAMPLE;
import com.zcl.hxqh.liangqingmanagement.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 扦样单
 */
public class QydListAdapter extends BaseQuickAdapter<N_SAMPLE> {
    public QydListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, N_SAMPLE item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.samplenum_text_id, item.getSAMPLENUM());
        helper.setText(R.id.n_region_text_id, item.getN_QCTASKLINENUM());
        helper.setText(R.id.workorder_reportdate, item.getENTERDATE());
        helper.setText(R.id.workorder_fxr, item.getENTERBY());
    }


}
