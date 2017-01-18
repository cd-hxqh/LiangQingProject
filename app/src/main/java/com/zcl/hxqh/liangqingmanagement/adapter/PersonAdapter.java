package com.zcl.hxqh.liangqingmanagement.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.N_CAR;
import com.zcl.hxqh.liangqingmanagement.model.PERSON;
import com.zcl.hxqh.liangqingmanagement.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 货运预报
 */
public class PersonAdapter extends BaseQuickAdapter<PERSON> {
    public PersonAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }



    @Override
    protected void convert(BaseViewHolder helper, PERSON item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.new_loc_title, "编号:");
        helper.setText(R.id.name_title_id, "名称:");
        helper.setText(R.id.oldloc_title_id, "头衔:");
        helper.setText(R.id.loc_title_id, "部门:");
        helper.setText(R.id.loc_text_id, item.getPERSONID());
        helper.setText(R.id.name_text_id, item.getDISPLAYNAME());
        helper.setText(R.id.oldloc_text_id, item.getTITLE());
        helper.setText(R.id.sloc_text_id, item.getDEPARTMENT());
    }


}
