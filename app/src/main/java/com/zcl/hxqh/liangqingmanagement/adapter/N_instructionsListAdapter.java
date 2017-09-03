package com.zcl.hxqh.liangqingmanagement.adapter;

import android.animation.Animator;
import android.content.Context;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.N_INSTRUCTIONS;
import com.zcl.hxqh.liangqingmanagement.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 调车作业指令单行表
 */
public class N_instructionsListAdapter extends BaseQuickAdapter<N_INSTRUCTIONS> {
    private static String TAG = "N_instructionsListAdapter";

    public N_instructionsListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, N_INSTRUCTIONS item) {

        helper.setText(R.id.ordernum_text_id, item.getORDERNUM());
        helper.setText(R.id.through_text_id, item.getTHROUGH());
        helper.setText(R.id.line_text_id, item.getLINE());
        helper.setText(R.id.wagonsg_text_id, item.getWAGONSG());
        helper.setText(R.id.wagonsz_text_id, item.getWAGONSZ());
        helper.setText(R.id.memo_text_id, item.getMEMO());
    }


}
