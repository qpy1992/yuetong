package com.example.win7.ytdemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.win7.ytdemo.R;
import com.example.win7.ytdemo.adapter.MyLVOrderAdapter;

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

public class ApplyFragment extends Fragment {
    private View     mRootView;
    private ListView lv_order;
    private int mOpenItem = -1;
    private MyLVOrderAdapter orderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_apply_order, null);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        lv_order = mRootView.findViewById(R.id.lv_order);
    }

    private void initData() {
        List mData = new ArrayList();
        mData.add("哈哈");
        mData.add("哈哈");
        mData.add("哈哈");
        orderAdapter = new MyLVOrderAdapter(getContext(), mData, mOpenItem);
        lv_order.setAdapter(orderAdapter);
    }
}
