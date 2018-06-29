package com.example.win7.ytdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.win7.ytdemo.R;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/6/26 13:05
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class MyLVOrderAdapter extends BaseAdapter {
    private Context mContext;
    private List    mList;
    private int     mOpenItem;

    public MyLVOrderAdapter(Context context, List list, int openItem) {
        this.mContext = context;
        this.mList = list;
        this.mOpenItem = openItem;
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
            view = View.inflate(mContext, R.layout.lv_item_open_detail, null);
            viewHolder.lin_show = view.findViewById(R.id.lin_show);
            viewHolder.lin_sorh = view.findViewById(R.id.lin_sorh);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) view.getTag();
        }
        viewHolder.lin_show.setTag(i);
        if (i == mOpenItem) {
            viewHolder.lin_sorh.setVisibility(View.VISIBLE);
        } else {
            viewHolder.lin_sorh.setVisibility(View.GONE);
        }
        viewHolder.lin_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //用 currentItem 记录点击位置s
                int tag = (Integer) view.getTag();
                if (tag == mOpenItem) { //再次点击
                    mOpenItem = -1; //给 currentItem 一个无效值
                } else {
                    mOpenItem = tag;
                }
                //通知adapter数据改变需要重新加载
                notifyDataSetChanged();
            }
        });
        return view;
    }

    private class MyViewHolder {
        TextView     tv_add;
        LinearLayout lin_show, lin_sorh;
    }
}
