package com.example.win7.ytdemo.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.win7.ytdemo.R;
import com.example.win7.ytdemo.activity.AddOrderActivity;
import com.example.win7.ytdemo.adapter.MyLVOrderAdapter;
import com.example.win7.ytdemo.entity.OrderDataInfo;
import com.example.win7.ytdemo.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/6/26 9:04
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class ApplyFragment extends Fragment implements View.OnClickListener {
    private View     mRootView;
    private TextView tv_add;
    private ListView lv_order;
    private int mOpenItem = -1;
    private MyLVOrderAdapter orderAdapter;
    private AddOrderActivity orderActivity;
    private boolean mEditable = false;//是否可编辑
    private List<OrderDataInfo.ListsonBean> mData;//存放子表详情对象

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_apply_order, null);
        orderActivity = (AddOrderActivity) getActivity();
        mEditable = orderActivity.getEditable();
        initView();
        initData();
        initListener();
        return mRootView;
    }

    private void initView() {
        tv_add = mRootView.findViewById(R.id.tv_add);
        lv_order = mRootView.findViewById(R.id.lv_order);
    }

    private void initData() {
        String kind = orderActivity.getIntent().getStringExtra("kind");
        if (null == kind) {
            ToastUtils.showToast(getContext(), "查询添加失败");
            return;
        }
        mData = new ArrayList();
        //区分进入类别
        if (kind.equals("add")) {

        }
        if (kind.equals("edit") || kind.equals("check")) {//查看和编辑//是有查询数据的,填入当前fragment中的数据集合
            OrderDataInfo orderInfo = orderActivity.getOrderInfo();
            List<OrderDataInfo.ListsonBean> listson = orderInfo.getListson();
            mData.addAll(listson);
        }
        orderAdapter = new MyLVOrderAdapter(getContext(), mData, mOpenItem);
        lv_order.setAdapter(orderAdapter);
    }

    private void initListener() {
        tv_add.setOnClickListener(this);
        if (!mEditable) {//不可编辑时，不显示新增按钮
            tv_add.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                //弹出dailog然用户填写子表
                showAlogToChange();
                break;
        }
    }

    private void showAlogToChange() {
        View view = View.inflate(getContext(), R.layout.lv_item_open_detail, null);
        final TextView tv_con = view.findViewById(R.id.tv_con);//内容
        final TextView tv_date = view.findViewById(R.id.tv_date);//日期
        final TextView tv_zdr = view.findViewById(R.id.tv_zdr);//制单人
        final TextView tv_sqbm = view.findViewById(R.id.tv_sqbm);//申请部门
        final TextView tv_zrbm = view.findViewById(R.id.tv_zrbm);//责任部门
        final TextView tv_wl = view.findViewById(R.id.tv_wl);//往来
        final TextView tv_wlyhzh = view.findViewById(R.id.tv_wlyhzh);//往来银行及账号
        final TextView tv_jhys = view.findViewById(R.id.tv_jhys);//计划预算进度
        final TextView tv_yskm = view.findViewById(R.id.tv_yskm);//预算科目
        final TextView tv_ysye = view.findViewById(R.id.tv_ysye);//预算余额
        final TextView tv_jl = view.findViewById(R.id.tv_jl);//计量
        final TextView tv_sl = view.findViewById(R.id.tv_sl);//数量
        final TextView tv_djhs = view.findViewById(R.id.tv_djhs);//单价含税
        final TextView tv_jehs = view.findViewById(R.id.tv_jehs);//金额含税
        final TextView tv_se = view.findViewById(R.id.tv_se);//税额
        final TextView tv_rmbbhse = view.findViewById(R.id.tv_rmbbhse);//人民币不含税额
        final TextView tv_slpercent = view.findViewById(R.id.tv_slpercent);//税率%
        final TextView tv_fz = view.findViewById(R.id.tv_fz);//辅助
        final TextView tv_fl = view.findViewById(R.id.tv_fl);//辅量
        final TextView tv_bz = view.findViewById(R.id.tv_bz);//备注
        final TextView tv_fpswkm = view.findViewById(R.id.tv_fpswkm);//发票税务科目
        new AlertDialog.Builder(getContext()).setView(view).setTitle("修改")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //确定新增子表
                        OrderDataInfo.ListsonBean listsonBean = new OrderDataInfo.ListsonBean();
                        listsonBean.setContentX(getTvContent(tv_con));
                        listsonBean.setDate(getTvContent(tv_date));
                        listsonBean.setSinPerson(getTvContent(tv_zdr));
                        listsonBean.setApplyPartX(getTvContent(tv_sqbm));
                        listsonBean.setResponsPartX(getTvContent(tv_zrbm));
                        listsonBean.setBodyIncomeX(getTvContent(tv_wl));
                        listsonBean.setBankIncomeX(getTvContent(tv_wlyhzh));
                        listsonBean.setPlanBudgetX(getTvContent(tv_jhys));
                        listsonBean.setBudSubX(getTvContent(tv_yskm));
                        listsonBean.setBudBalanceX(getTvContent(tv_ysye));
                        listsonBean.setUnitX(getTvContent(tv_jl));
                        listsonBean.setNumberX(getTvContent(tv_sl));
                        listsonBean.setUnitPriceX(getTvContent(tv_djhs));
                        listsonBean.setMoneyTaxX(getTvContent(tv_jehs));
                        listsonBean.setTaxAmountX(getTvContent(tv_se));
                        listsonBean.setRMBNoTaxX(getTvContent(tv_rmbbhse));
                        listsonBean.setTaxRateX(getTvContent(tv_slpercent));
                        listsonBean.setUnitOtherX(getTvContent(tv_fz));
                        listsonBean.setUnitNumX(getTvContent(tv_fl));
                        listsonBean.setRemarkTicNOX(getTvContent(tv_bz));
                        listsonBean.setTicTaxSubX(getTvContent(tv_fpswkm));
                        mData.add(listsonBean);
                        orderAdapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("取消", null).show();
        setChangeListener(tv_con, "内容");
        setChangeListener(tv_date, "日期");
        setChangeListener(tv_zdr, "制单人");
        setChangeListener(tv_sqbm, "申请部门");
        setChangeListener(tv_zrbm, "责任部门");
        setChangeListener(tv_wl, "往来");
        setChangeListener(tv_wlyhzh, "往来银行及账号");
        setChangeListener(tv_jhys, "计划预算进度");
        setChangeListener(tv_yskm, "预算科目");
        setChangeListener(tv_ysye, "预算余额");
        setChangeListener(tv_jl, "计量");
        setChangeListener(tv_sl, "数量");
        setChangeListener(tv_djhs, "单价含税");
        setChangeListener(tv_jehs, "金额含税");
        setChangeListener(tv_se, "税额");
        setChangeListener(tv_rmbbhse, "人民币不含税额");
        setChangeListener(tv_slpercent, "税率%");
        setChangeListener(tv_fz, "辅助");
        setChangeListener(tv_fl, "辅量");
        setChangeListener(tv_bz, "备注");
        setChangeListener(tv_fpswkm, "发票税务科目");
    }

    private void setChangeListener(final TextView tv, final String title) {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cont = String.valueOf(tv.getText());
                final EditText et = new EditText(getContext());
                et.setText(cont);
                new AlertDialog.Builder(getContext()).setView(et).setTitle(title)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //修改tv的数据
                                String et_con = String.valueOf(et.getText());
                                tv.setText(et_con);
                            }
                        }).setNegativeButton("取消", null).show();
            }
        });
    }

    private String getTvContent(TextView tv) {
        String text = String.valueOf(tv.getText());
        return text;
    }
}
