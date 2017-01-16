package com.zcl.hxqh.liangqingmanagement.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.Asset;
import com.zcl.hxqh.liangqingmanagement.model.N_STOREINFO;
import com.zcl.hxqh.liangqingmanagement.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 设备表
 */
public class AssetListAdapter extends BaseQuickAdapter<Asset> {
    public AssetListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, Asset item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.item_num_title, "设备");
        helper.setText(R.id.item_desc_title, "设备位置");
        helper.setText(R.id.item_num_text, item.getN_MODELNUM());
        helper.setText(R.id.item_desc_text, item.getN_LOCANAME());
    }


}
