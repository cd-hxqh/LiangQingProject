package com.zcl.hxqh.liangqingmanagement.until;

import java.util.ArrayList;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;

import com.zcl.hxqh.liangqingmanagement.R;

/**
 * Created by Administrator on 2017/1/16.
 */

public class CustomerSpinner extends Spinner implements OnItemClickListener {
    private ArrayList<String> list;
    public static String text;

    public CustomerSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //如果视图定义了OnClickListener监听器，调用此方法来执行
    @Override
    public boolean performClick() {
        Context context = getContext();
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        LayoutParams params = new LayoutParams(650, LayoutParams.FILL_PARENT);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> view, View itemView, int position,
                            long id) {
        setSelection(position);
        setText(list.get(position));
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
