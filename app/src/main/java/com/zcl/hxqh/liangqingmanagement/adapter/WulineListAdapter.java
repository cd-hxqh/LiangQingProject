package com.zcl.hxqh.liangqingmanagement.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.N_WTLINE;
import com.zcl.hxqh.liangqingmanagement.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 考勤管理
 */
public class WulineListAdapter extends BaseQuickAdapter<N_WTLINE> {
    public WulineListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, N_WTLINE item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.item_num_title, "姓名/车号:");
        helper.setText(R.id.kq_name_text_id, item.getNAME());
        helper.setText(R.id.start_text_title, "开始时间:");
        helper.setText(R.id.start_text_id, item.getSTART());
        helper.setText(R.id.end_text_title, "结束时间:");
        helper.setText(R.id.end_text_id, item.getEND());
        helper.setText(R.id.status_text_title, "状态:");
        helper.setText(R.id.status_text_id, item.getSTATUS());
        helper.setText(R.id.cardid_text_title, "一卡通编号:");
        helper.setText(R.id.cardid_text_id, item.getCARDID());
    }


}
