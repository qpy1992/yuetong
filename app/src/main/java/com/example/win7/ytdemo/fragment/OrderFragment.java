package com.example.win7.ytdemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.win7.ytdemo.R;
import com.example.win7.ytdemo.YApplication;
import com.example.win7.ytdemo.activity.AddOrderActivity;
import com.example.win7.ytdemo.entity.Tool;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment implements View.OnClickListener {
    private Context  mContext;
    private View     view;
    private TextView tv_add;
    private String group = YApplication.fgroup;
    private Spinner              sp_search;
    private List<String>         strList;//部门类别
    private ArrayAdapter<String> arr_adapter;//订单列表数据
    private TextView             tv_notask, tv_search;
    private ImageView iv_search;
    private ListView  lv_task;//订单列表
    private Tool      tool;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getContext();
        view = inflater.inflate(R.layout.fragment_order, container, false);
        setTool();
        setViews();
        setData();
        setListeners();
        return view;
    }

    protected void setTool() {
        tv_add = view.findViewById(R.id.tv_add);//新增
        tv_add.setOnClickListener(this);
    }

    protected void setViews() {
        sp_search = (Spinner) view.findViewById(R.id.sp_task_search);
        strList = new ArrayList<>();
        strList.add("请选择");
        strList.add("日期");
        strList.add("发票日期");
        strList.add("内容");
        strList.add("往来");
        strList.add("制单人");
        strList.add("申请部门");
        strList.add("责任部门");
        strList.add("计划预算进度");
        arr_adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, strList);
        sp_search.setAdapter(arr_adapter);
        tv_search = (TextView) view.findViewById(R.id.tv_search);
        iv_search = (ImageView) view.findViewById(R.id.iv_search);
        lv_task = (ListView) view.findViewById(R.id.lv_task);
        tv_notask = (TextView) view.findViewById(R.id.tv_notask);
        tv_notask.setVisibility(View.GONE);
        //        list = new ArrayList<>();
        //        list1 = new ArrayList<>();
        tool = new Tool();
    }

    private void setData() {

    }

    protected void setListeners() {
        sp_search.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tv_search.setText(R.string.select);
                tool = new Tool();
                //                index = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                if (group.equals("")) {
                    Toast.makeText(mContext, "您不在任何用户组内，请先申请权限！", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(mContext, AddOrderActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
}
