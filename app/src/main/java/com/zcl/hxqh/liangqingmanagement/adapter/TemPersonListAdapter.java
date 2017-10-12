package com.zcl.hxqh.liangqingmanagement.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.CompoundButton;

import com.zcl.hxqh.liangqingmanagement.R;
import com.zcl.hxqh.liangqingmanagement.bean.TemPERSON;
import com.zcl.hxqh.liangqingmanagement.view.widght.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 人员选择表
 */
public class TemPersonListAdapter extends BaseQuickAdapter<TemPERSON> {
    private static final String TAG = "TemPersonListAdapter";
    private String type;
    public CheckBoxListener mCheckBoxListener;


    public TemPersonListAdapter(Context context, int layoutResId, List data, String type) {
        super(context, layoutResId, data);
        this.type = type;
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 5)
            anim.setStartDelay(index * 150);
    }


    @Override
    protected void convert(BaseViewHolder helper, final TemPERSON item) {
        CardView cardView = helper.getView(R.id.card_container);
        Log.e(TAG, "" + item.getpersonid());
        if (type.equals("TELLER"))//被告知人
        {
            helper.setVisible(R.id.checkBox_id, true);
        }
        helper.setText(R.id.personid_text_id, item.getpersonid());
        helper.setText(R.id.name_text_id, item.getdisplayname());
        helper.setOnCheckedChangeListener(R.id.checkBox_id, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    mCheckBoxListener.getCheckTemPERSON(b,item);
            }
        });
    }

    public interface CheckBoxListener {
        public void getCheckTemPERSON(boolean b,TemPERSON temPERSON);
    }

    public CheckBoxListener getmCheckBoxListener() {
        return mCheckBoxListener;
    }

    public void setmCheckBoxListener(CheckBoxListener mCheckBoxListener) {
        this.mCheckBoxListener = mCheckBoxListener;
    }
}
