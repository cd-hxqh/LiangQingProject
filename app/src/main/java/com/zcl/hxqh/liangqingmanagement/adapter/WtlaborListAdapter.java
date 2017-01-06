package com.zcl.hxqh.liangqingmanagement.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.WTLABOR;
import com.zcl.hxqh.liangqingmanagement.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 用工管理
 */
public class WtlaborListAdapter extends BaseQuickAdapter<WTLABOR> {
    public WtlaborListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, WTLABOR item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.start_text_title, "工作地点:");
        helper.setText(R.id.start_text_id, item.getPLACE());
        helper.setText(R.id.end_text_title, "开始时间:");
        helper.setText(R.id.end_text_id, item.getSTARTTIME());
        helper.setText(R.id.status_text_title, "状态:");
        helper.setText(R.id.status_text_id, item.getSTATUS());
    }


}
