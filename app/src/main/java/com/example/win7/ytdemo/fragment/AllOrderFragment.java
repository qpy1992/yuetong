package com.example.win7.ytdemo.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.win7.ytdemo.R;
import com.example.win7.ytdemo.activity.AddOrderActivity;
import com.example.win7.ytdemo.adapter.LvShowMoreAdapter;
import com.example.win7.ytdemo.entity.OrderDataInfo;
import com.example.win7.ytdemo.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/6/26 9:02
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class AllOrderFragment extends Fragment implements View.OnClickListener {
    private View   mRootView;
    private Button bt_submit;
    private boolean mEditable = false;//是否可编辑
    private TextView tv_hsje, tv_fkwl, tv_orid, tv_RMB, tv_rate, tv_zzjg, tv_nr1, tv_fkze, tv_ljqjx, tv_bdqjx, tv_yfzq, tv_fkl, tv_fkcb, tv_fkhs, tv_jxp, tv_jxfpl, tv_jxfphs, tv_yfk, tv_rckwl, tv_rkcb, tv_ckcb;
    private TextView tv_bdkccb, tv_ljykp, tv_bdykp, tv_yszq, tv_skwl, tv_ysk, tv_xxpwl, tv_xxfpl, tv_xxkphs;
    private String orderID = "";//订单表id

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_all_order, null);
        AddOrderActivity activity = (AddOrderActivity) getActivity();
        mEditable = activity.getEditable();
        orderID = activity.getIntent().getStringExtra("orderID");
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        tv_orid = mRootView.findViewById(R.id.tv_orid);//单据号
        tv_RMB = mRootView.findViewById(R.id.tv_RMB);//币别
        tv_rate = mRootView.findViewById(R.id.tv_rate);//汇率
        tv_zzjg = mRootView.findViewById(R.id.tv_zzjg);//组织机构
        tv_nr1 = mRootView.findViewById(R.id.tv_nr1);//内容
        tv_hsje = mRootView.findViewById(R.id.tv_hsje);//含税金额合计
        tv_fkze = mRootView.findViewById(R.id.tv_fkze);//付款总额合计
        tv_ljqjx = mRootView.findViewById(R.id.tv_ljqjx);//累计：欠进项发票额
        tv_bdqjx = mRootView.findViewById(R.id.tv_bdqjx);//本单：欠进项发票额
        tv_yfzq = mRootView.findViewById(R.id.tv_yfzq);//应付账期天数
        tv_fkwl = mRootView.findViewById(R.id.tv_fkwl);//付款往来
        tv_fkl = mRootView.findViewById(R.id.tv_fkl);//付款量合计
        tv_fkcb = mRootView.findViewById(R.id.tv_fkcb);//付款成本不含税合计
        tv_fkhs = mRootView.findViewById(R.id.tv_fkhs);//付款含税合计
        tv_jxp = mRootView.findViewById(R.id.tv_jxp);//进项票往来
        tv_jxfpl = mRootView.findViewById(R.id.tv_jxfpl);//进项发票量合计
        tv_jxfphs = mRootView.findViewById(R.id.tv_jxfphs);//进项发票含税总额合计
        tv_yfk = mRootView.findViewById(R.id.tv_yfk);//应付款合计
        tv_rckwl = mRootView.findViewById(R.id.tv_rckwl);//入出库往来
        tv_rkcb = mRootView.findViewById(R.id.tv_rkcb);//入库成本合计
        tv_ckcb = mRootView.findViewById(R.id.tv_ckcb);//出库成本合计
        tv_bdkccb = mRootView.findViewById(R.id.tv_bdkccb);//本单库存成本
        tv_ljykp = mRootView.findViewById(R.id.tv_ljykp);//累计：已开票未收款额
        tv_bdykp = mRootView.findViewById(R.id.tv_bdykp);//本单：已开票未收款额
        tv_yszq = mRootView.findViewById(R.id.tv_yszq);//应收账期天数
        tv_skwl = mRootView.findViewById(R.id.tv_skwl);//收款往来
        tv_ysk = mRootView.findViewById(R.id.tv_ysk);//应收款合计
        tv_xxpwl = mRootView.findViewById(R.id.tv_xxpwl);//销项票往来
        tv_xxfpl = mRootView.findViewById(R.id.tv_xxfpl);//销项发票量合计
        tv_xxkphs = mRootView.findViewById(R.id.tv_xxkphs);//销项开票含税总额合计
        bt_submit = mRootView.findViewById(R.id.bt_submit);
    }

    private void initData() {
        //先行判断，进入条件
        Intent intent = getActivity().getIntent();
        String kind = intent.getStringExtra("kind");
        if (null == kind) {
            ToastUtils.showToast(getContext(), "error:未传递种类");
            getActivity().finish();
            return;
        }
        if (kind.equals("check") || kind.equals("edit")) {//查看//编辑
            if (kind.equals("check"))
                bt_submit.setVisibility(View.GONE);
            //填入数据
            writeDataIn();
        }
        //给tv设置点击修改事件
        setListener();
        bt_submit.setOnClickListener(this);
    }

    private void setListener() {
        if (mEditable) {
            setTvChangeListener(tv_orid, "单据号");
            setTvChangeListener(tv_RMB, "币别");
            setTvChangeListener(tv_rate, "汇率");
            setTvChangeListener(tv_zzjg, "组织机构");
            setTvChangeListener(tv_nr1, "内容");
            setTvChangeListener(tv_hsje, "含税金额合计");
            setTvChangeListener(tv_fkze, "付款总额合计");
            setTvChangeListener(tv_ljqjx, "累计：欠进项发票额");
            setTvChangeListener(tv_bdqjx, "本单：欠进项发票额");
            setTvChangeListener(tv_yfzq, "应付账期天数");
            setTvChangeListener(tv_fkwl, "付款往来");
            setTvChangeListener(tv_fkl, "付款量合计");
            setTvChangeListener(tv_fkcb, "付款成本不含税合计");
            setTvChangeListener(tv_fkhs, "付款含税合计");
            setTvChangeListener(tv_jxp, "进项票往来");
            setTvChangeListener(tv_jxfpl, "进项发票量合计");
            setTvChangeListener(tv_jxfphs, "进项发票含税总额合计");
            setTvChangeListener(tv_yfk, "应付款合计");
            setTvChangeListener(tv_rckwl, "入出库往来");
            setTvChangeListener(tv_rkcb, "入库成本合计");
            setTvChangeListener(tv_ckcb, "出库成本合计");
            setTvChangeListener(tv_bdkccb, "本单库存成本");
            setTvChangeListener(tv_ljykp, "累计：已开票未收款额");
            setTvChangeListener(tv_bdykp, "本单：已开票未收款额");
            setTvChangeListener(tv_yszq, "应收账期天数");
            setTvChangeListener(tv_skwl, "收款往来");
            setTvChangeListener(tv_ysk, "应收款合计");
            setTvChangeListener(tv_xxpwl, "销项票往来");
            setTvChangeListener(tv_xxfpl, "销项发票量合计");
            setTvChangeListener(tv_xxkphs, "销项开票含税总额合计");
            setTvChangeListener(tv_fkwl, "付款往来");
        }
    }

    private void setTvChangeListener(final TextView tv, final String title) {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeViewContent(tv, title);
            }
        });
    }

    private void writeDataIn() {
        AddOrderActivity activity = (AddOrderActivity) getActivity();
        OrderDataInfo orderInfo = activity.getOrderInfo();
        tv_orid.setText(orderInfo.getFbillNo());
        tv_zzjg.setText(orderInfo.getOrga());
        tv_nr1.setText(orderInfo.getContent());
        tv_hsje.setText(orderInfo.getMoneyTax());
        //        tv_fkze.setText(orderInfo.get);
        tv_ljqjx.setText(orderInfo.getOweInvTotal());
        tv_bdqjx.setText(orderInfo.getThisOweInv());
        tv_yfzq.setText(orderInfo.getPayDate());
        tv_fkwl.setText(orderInfo.getPayContact());
        tv_fkl.setText(orderInfo.getPayAmount());
        tv_fkcb.setText(orderInfo.getPaynoTax());
        tv_fkhs.setText(orderInfo.getPaywithTax());
        tv_jxp.setText(orderInfo.getIncomeCont());
        tv_jxfpl.setText(orderInfo.getIncomeInv());
        tv_jxfphs.setText(orderInfo.getInvwithTax());
        tv_yfk.setText(orderInfo.getSpayTotal());
        tv_rckwl.setText(orderInfo.getInnerIncome());
        tv_rkcb.setText(orderInfo.getInnnerCost());
        tv_ckcb.setText(orderInfo.getOutCost());
        //        tv_bdkccb.setText(orderInfo.get);
        tv_ljykp.setText(orderInfo.getTotalUnrece());
        tv_bdykp.setText(orderInfo.getThisUnrece());
        tv_yszq.setText(orderInfo.getShRecceData());
        tv_skwl.setText(orderInfo.getRecIncome());
        tv_ysk.setText(orderInfo.getShRecTotal());
        tv_xxpwl.setText(orderInfo.getOutTicIncome());
        tv_xxfpl.setText(orderInfo.getOutTickTotal());
        tv_xxkphs.setText(orderInfo.getOutTickWTax());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_submit:
                //TODO:提交
                break;
            default:
                break;
        }
    }

    private void changeViewContent(final TextView tvcontent, final String title) {
        //弹出dailog展示修改内容
        final EditText et = new EditText(getContext());
        //写入数据
        String oldContent = String.valueOf(tvcontent.getText());
        et.setText(oldContent);
        new AlertDialog.Builder(getContext()).setView(et).setTitle(title)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //修改改变内容的textview
                        String content = String.valueOf(et.getText()).trim();
                        if (content.equals("")) {
                            //为空，弹出选择菜单列表
                            showMoreWriteInfo(tvcontent, title);
                        } else {
                            tvcontent.setText(content);
                        }
                    }
                }).setNegativeButton("取消", null).show();
    }

    private void showMoreWriteInfo(final TextView tvcontent, String title) {
        View view = View.inflate(getContext(), R.layout.view_only_list, null);
        ListView lv_showmore = view.findViewById(R.id.lv_showmore);
        final List<String> mShowData = new ArrayList();
        mShowData.add("1052");
        mShowData.add("9527");
        mShowData.add("89757");
        final LvShowMoreAdapter showMoreAdapter = new LvShowMoreAdapter(getContext(), mShowData);
        lv_showmore.setAdapter(showMoreAdapter);
        final AlertDialog dialog = new AlertDialog.Builder(getContext()).setView(view).setTitle(title).show();
        lv_showmore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvcontent.setText(mShowData.get(i));
                dialog.dismiss();
            }
        });
    }
}
