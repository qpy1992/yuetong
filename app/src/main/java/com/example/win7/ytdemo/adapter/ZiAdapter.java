package com.example.win7.ytdemo.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.win7.ytdemo.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by WIN7 on 2018/3/23.
 */

public class ZiAdapter extends BaseAdapter{
    Context mContext;
    List<HashMap<String,String>> list;
    private int currentItem = -1; //用于记录点击的 Item 的 position，是控制 item 展开的核心

    public ZiAdapter(Context context,List<HashMap<String,String>> list){
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if(view==null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_zi,null);
            holder = new Holder();
            holder.tv_qi = (TextView)view.
                    findViewById(R.id.tv_qi);
            holder.tv_zhi = (TextView)view.
                    findViewById(R.id.tv_zhi);
            holder.tv_progress = (TextView)view.
                    findViewById(R.id.tv_progress);
            holder.tv_plan = (TextView)view.
                    findViewById(R.id.tv_plan);
            holder.tv_budget = (TextView)view.
                    findViewById(R.id.tv_budget);
            holder.tv_pbudget = (TextView)view.
                    findViewById(R.id.tv_pbudget);
            holder.tv_note = (TextView)view.
                    findViewById(R.id.tv_note);
            holder.tv_shuliang = (TextView)view
                    .findViewById(R.id.tv_shuliang);
            holder.tv_danjia = (TextView)view
                    .findViewById(R.id.tv_danjia);
            holder.tv_hanshui = (TextView)view
                    .findViewById(R.id.tv_hanshui);
            holder.tv_buhan = (TextView)view
                    .findViewById(R.id.tv_buhan);
            holder.tv_fuzhu = (TextView)view
                    .findViewById(R.id.tv_fuzhu);
            holder.tv_fuliang = (TextView)view
                    .findViewById(R.id.tv_fuliang);
            holder.tv_fasong = (TextView)view
                    .findViewById(R.id.tv_fasong);
            holder.tv_huikui = (TextView)view
                    .findViewById(R.id.tv_huikui);
            holder.tv_pingfen = (TextView)view
                    .findViewById(R.id.tv_pingfen);
            holder.ll_show = (LinearLayout) view
                    .findViewById(R.id.ll_show);
            holder.ll_hide = (LinearLayout)view
                    .findViewById(R.id.ll_hide);
            holder.ll_note = (LinearLayout)view.
                    findViewById(R.id.ll_note);
            view.setTag(holder);
        }else{
            holder = (Holder)view.getTag();
        }
        final HashMap<String,String> item = list.get(i);

        holder.ll_show.setTag(i);
        holder.tv_qi.setText(item.get("qi"));
        holder.tv_zhi.setText(item.get("zhi"));
        holder.tv_progress.setText(item.get("progress"));
        holder.tv_plan.setText(item.get("plan"));
        holder.tv_budget.setText(item.get("budget"));
        holder.tv_pbudget.setText(item.get("pbudget"));
        holder.tv_note.setText(item.get("note"));
        holder.tv_shuliang.setText("数量："+item.get("shuliang"));
        holder.tv_danjia.setText("单价："+item.get("danjia"));
        holder.tv_hanshui.setText("金额含税："+item.get("hanshui"));
        holder.tv_buhan.setText(item.get("buhan"));
        holder.tv_fuzhu.setText(item.get("fuzhu"));
        holder.tv_fuliang.setText(item.get("fuliang"));
        holder.tv_fasong.setText(item.get("fasong"));
        holder.tv_huikui.setText(item.get("huikui"));
        holder.tv_pingfen.setText(item.get("pingfen"));

        if (currentItem == i) {
            holder.ll_hide.setVisibility(View.VISIBLE);
        } else {
            holder.ll_hide.setVisibility(View.GONE);
        }

        holder.ll_show.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //用 currentItem 记录点击位置
                int tag = (Integer) view.getTag();
                if (tag == currentItem) { //再次点击
                    currentItem = -1; //给 currentItem 一个无效值
                } else {
                    currentItem = tag;
                }
                //通知adapter数据改变需要重新加载
                notifyDataSetChanged(); //必须有的一步
            }
        });


        holder.ll_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = new TextView(mContext);
                tv.setText(item.get("note"));
                tv.setTextSize(16);
                tv.setPadding(60,20,40,10);
                new AlertDialog.Builder(mContext).setView(tv).show();
            }
        });
        return view;
    }

    class Holder{
        LinearLayout ll_show,ll_hide,ll_note;
        TextView tv_progress,tv_plan,tv_budget,
                tv_pbudget,tv_note,tv_shuliang,tv_danjia,tv_hanshui,
                tv_buhan,tv_fuzhu,tv_fuliang,tv_fasong,
                tv_huikui,tv_pingfen,tv_qi,tv_zhi;
    }
}
