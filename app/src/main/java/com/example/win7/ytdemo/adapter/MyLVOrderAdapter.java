package com.example.win7.ytdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.win7.ytdemo.R;
import com.example.win7.ytdemo.entity.OrderDataInfo;

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
    private Context                         mContext;
    private List<OrderDataInfo.ListsonBean> mList;
    private int                             mOpenItem;

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
            viewHolder.view_line = view.findViewById(R.id.view_line);
            viewHolder.lin_open = view.findViewById(R.id.lin_open);
            viewHolder.lin_sorh = view.findViewById(R.id.lin_sorh);
            //需填充数据的view
            viewHolder.tv_con = view.findViewById(R.id.tv_con);
            viewHolder.tv_date = view.findViewById(R.id.tv_date);
            viewHolder.tv_zdr = view.findViewById(R.id.tv_zdr);
            viewHolder.tv_sqbm = view.findViewById(R.id.tv_sqbm);
            viewHolder.tv_zrbm = view.findViewById(R.id.tv_zrbm);
            viewHolder.tv_wl = view.findViewById(R.id.tv_wl);
            viewHolder.tv_wlyhzh = view.findViewById(R.id.tv_wlyhzh);
            viewHolder.tv_jhys = view.findViewById(R.id.tv_jhys);
            viewHolder.tv_yskm = view.findViewById(R.id.tv_yskm);
            viewHolder.tv_ysye = view.findViewById(R.id.tv_ysye);
            viewHolder.tv_jl = view.findViewById(R.id.tv_jl);
            viewHolder.tv_sl = view.findViewById(R.id.tv_sl);
            viewHolder.tv_djhs = view.findViewById(R.id.tv_djhs);
            viewHolder.tv_jehs = view.findViewById(R.id.tv_jehs);
            viewHolder.tv_se = view.findViewById(R.id.tv_se);
            viewHolder.tv_rmbbhse = view.findViewById(R.id.tv_rmbbhse);
            viewHolder.tv_slpercent = view.findViewById(R.id.tv_slpercent);
            viewHolder.tv_fz = view.findViewById(R.id.tv_fz);
            viewHolder.tv_fl = view.findViewById(R.id.tv_fl);
            viewHolder.tv_bz = view.findViewById(R.id.tv_bz);
            viewHolder.tv_fpswkm = view.findViewById(R.id.tv_fpswkm);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) view.getTag();
        }
        //填充数据
        OrderDataInfo.ListsonBean bean = mList.get(i);
        viewHolder.tv_con.setText(bean.getContentX());
        viewHolder.tv_date.setText(bean.getDate());
        viewHolder.tv_zdr.setText(bean.getSinPerson());
        viewHolder.tv_sqbm.setText(bean.getApplyPartX());
        viewHolder.tv_zrbm.setText(bean.getResponsPartX());
        viewHolder.tv_wl.setText(bean.getBodyIncomeX());
        viewHolder.tv_wlyhzh.setText(bean.getBankIncomeX());
        viewHolder.tv_jhys.setText(bean.getPlanBudgetX());
        viewHolder.tv_yskm.setText(bean.getBudSubX());
        viewHolder.tv_ysye.setText(bean.getBudBalanceX());
        viewHolder.tv_jl.setText(bean.getUnitX());
        viewHolder.tv_sl.setText(bean.getNumberX());
        viewHolder.tv_djhs.setText(bean.getUnitPriceX());
        viewHolder.tv_jehs.setText(bean.getMoneyTaxX());
        viewHolder.tv_se.setText(bean.getTaxAmountX());
        viewHolder.tv_rmbbhse.setText(bean.getRMBNoTaxX());
        viewHolder.tv_slpercent.setText(bean.getTaxRateX());
        viewHolder.tv_fz.setText(bean.getUnitOtherX());
        viewHolder.tv_fl.setText(bean.getUnitNumX());
        viewHolder.tv_bz.setText(bean.getRemarkTicNOX());
        viewHolder.tv_fpswkm.setText(bean.getTicTaxSubX());
        //隐藏展示
        viewHolder.lin_open.setTag(i);
        if (i == mOpenItem) {
            viewHolder.view_line.setBackgroundColor(mContext.getResources().getColor(R.color.black));
            viewHolder.lin_sorh.setVisibility(View.VISIBLE);
        } else {
            viewHolder.view_line.setBackgroundColor(mContext.getResources().getColor(R.color.vm_blue_38));
            viewHolder.lin_sorh.setVisibility(View.GONE);
        }
        viewHolder.lin_open.setOnClickListener(new View.OnClickListener() {
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
        private View view_line;
        TextView tv_con, tv_date, tv_zdr, tv_sqbm, tv_zrbm, tv_wl, tv_wlyhzh, tv_jhys, tv_yskm, tv_ysye, tv_jl, tv_sl, tv_djhs, tv_jehs, tv_se, tv_rmbbhse, tv_slpercent, tv_fz, tv_fl, tv_bz, tv_fpswkm;
        LinearLayout lin_sorh, lin_open;
    }
}
