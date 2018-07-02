package com.example.win7.ytdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.win7.ytdemo.R;
import com.example.win7.ytdemo.activity.AddOrderActivity;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/7/2 13:34
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class OrderAdapter extends BaseAdapter {
    private Context mContext;
    private List    mList;

    public OrderAdapter(Context context, List list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHolder viewHolder;
        if (null == view) {
            viewHolder = new MyViewHolder();
            view = View.inflate(mContext, R.layout.item_task, null);
            viewHolder.tv_taskno = view.findViewById(R.id.tv_taskno);
            viewHolder.iv_edit = view.findViewById(R.id.iv_edit);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) view.getTag();
        }
        viewHolder.tv_taskno.setText("测试");
        viewHolder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddOrderActivity.class);
                intent.putExtra("kind", "edit");
                intent.putExtra("title","编辑订单表");
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    private class MyViewHolder {
        TextView  tv_taskno;
        ImageView iv_edit;
    }
}
