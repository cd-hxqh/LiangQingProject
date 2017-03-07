package com.zcl.hxqh.liangqingmanagement.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.N_LABAPYRULE;
import com.zcl.hxqh.liangqingmanagement.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 用工验收
 */
public class N_labapyruleAdapter extends BaseQuickAdapter<N_LABAPYRULE> {
    public N_labapyruleAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, N_LABAPYRULE item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.sn_text_id, item.getSN());
        helper.setText(R.id.item_text_id, item.getITEM());
        helper.setText(R.id.score_text_id, item.getRULE());
        helper.setText(R.id.rule_text_id, item.getSCORE());
    }


}
