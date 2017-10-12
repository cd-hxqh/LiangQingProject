package com.zcl.hxqh.liangqingmanagement.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.CompoundButton;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.model.ALNDOMAIN;
import com.zcl.hxqh.liangqingmanagement.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 安全告知项目
 */
public class AlndomainListAdapter extends BaseQuickAdapter<ALNDOMAIN> {
    private static final String TAG = "AlndomainListAdapter";
    public CheckBoxListener mCheckBoxListener;


    public AlndomainListAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, final ALNDOMAIN item) {
        CardView cardView = helper.getView(R.id.card_container);
        helper.setText(R.id.num_text_id, item.getDESCRIPTION());
        helper.setOnCheckedChangeListener(R.id.checkBox_id, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    mCheckBoxListener.getCheckTemPERSON(b,item);

            }
        });
    }

    public interface CheckBoxListener {
        public void getCheckTemPERSON(boolean b,ALNDOMAIN item);
    }

    public CheckBoxListener getmCheckBoxListener() {
        return mCheckBoxListener;
    }

    public void setmCheckBoxListener(CheckBoxListener mCheckBoxListener) {
        this.mCheckBoxListener = mCheckBoxListener;
    }
}
