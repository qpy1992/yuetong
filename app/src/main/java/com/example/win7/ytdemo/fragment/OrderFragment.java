package com.example.win7.ytdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.win7.ytdemo.R;
import com.example.win7.ytdemo.adapter.MyPagerAdapter;
import com.example.win7.ytdemo.view.MyFixedViewpager;

import java.util.ArrayList;

public class OrderFragment extends Fragment {
    private Context          mContext;
    private View             view;
    private Toolbar          toolbar;
    private TabLayout        mTablayout;//导航标签
    private MyFixedViewpager mView_pager;//自我viewpager可实现禁止滑动
    private String[] mStrings = {"汇总", "申请"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        view = inflater.inflate(R.layout.fragment_order, container, false);
        setTool();
        setViews();
        setData();
        setListeners();
        return view;
    }

    protected void setTool() {
        toolbar = (Toolbar) view.findViewById(R.id.id_toolbar);
        toolbar.setTitle(R.string.order);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_add:

                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu2, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    protected void setViews() {
        mTablayout = view.findViewById(R.id.tablayout);
        mView_pager = view.findViewById(R.id.view_pager);
    }

    private void setData() {
        //初始化导航页
        initTabFragment();
    }

    protected void setListeners() {

    }

    private void initTabFragment() {
        // 创建一个集合,装填Fragment
        ArrayList<Fragment> fragments = new ArrayList<>();
        // 装填
        //汇总界面
        AllOrderFragment allOrderFragment = new AllOrderFragment();
        fragments.add(allOrderFragment);
        //申请界面
        ApplyFragment applyFragment = new ApplyFragment();
        fragments.add(applyFragment);

        // 创建ViewPager适配器
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getFragmentManager());
        myPagerAdapter.setFragments(fragments);
        // 给ViewPager设置适配器
        mView_pager.setAdapter(myPagerAdapter);
        //设置viewpager不可滑动
        mView_pager.setCanScroll(false);
        //tablayout关联tablayout和viewpager实现联动
        mTablayout.setupWithViewPager(mView_pager);
        for (int i = 0; i < mStrings.length; i++) {
            mTablayout.getTabAt(i).setText(mStrings[i]);
        }
    }
}
