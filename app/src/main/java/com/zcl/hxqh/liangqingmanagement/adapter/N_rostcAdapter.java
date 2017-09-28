package com.zcl.hxqh.liangqingmanagement.adapter;

import android.animation.Animator;
import android.content.Context;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.N_ROSTC;
import com.zcl.hxqh.liangqingmanagement.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 风雨雪三查
 */
public class N_rostcAdapter extends BaseQuickAdapter<N_ROSTC> {
    public N_rostcAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, N_ROSTC item) {
        helper.setText(R.id.item_num_id, item.getROSTCNUM());
        helper.setText(R.id.loc_text_id, item.getLOC());
        helper.setText(R.id.examiner_text_id, item.getEXAMINERNAME());
        helper.setText(R.id.beforedate_text_id, item.getBEFOREDATE());
        helper.setText(R.id.latedate_text_id, item.getLATEDATE());
    }


}
