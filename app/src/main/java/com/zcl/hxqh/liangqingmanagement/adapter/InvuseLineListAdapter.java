package com.zcl.hxqh.liangqingmanagement.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.INVUSELINE;
import com.zcl.hxqh.liangqingmanagement.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 实际领用
 */
public class InvuseLineListAdapter extends BaseQuickAdapter<INVUSELINE> {
    public InvuseLineListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }



    @Override
    protected void convert(BaseViewHolder helper, INVUSELINE item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.workorder_sb_text, item.getITEMNUM());
        helper.setText(R.id.item_desc_text, item.getROTASSETNUMDESC());
        helper.setText(R.id.workorder_reportdate, item.getISSUETONAME());
        helper.setText(R.id.workorder_fxr, item.getACTUALDATE());
    }


}
