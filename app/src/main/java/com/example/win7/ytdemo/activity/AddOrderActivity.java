package com.example.win7.ytdemo.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.win7.ytdemo.R;
import com.example.win7.ytdemo.adapter.MyPagerAdapter;
import com.example.win7.ytdemo.fragment.AllOrderFragment;
import com.example.win7.ytdemo.fragment.ApplyFragment;
import com.example.win7.ytdemo.view.MyFixedViewpager;

import java.util.ArrayList;

/**
 * @创建者 AndyYan
 * @创建时间 2018/6/28 15:19
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class AddOrderActivity extends BaseActivity implements View.OnClickListener {
    private ImageView        img_back;
    private TabLayout        mTablayout;//导航标签
    private MyFixedViewpager mView_pager;//自我viewpager可实现禁止滑动
    private String[] mStrings = {"汇总", "申请"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        mTablayout = findViewById(R.id.tablayout);
        mView_pager = findViewById(R.id.view_pager);
    }

    private void initData() {
        //初始化导航页
        initTabFragment();
    }

    private void initListener() {
        img_back.setOnClickListener(this);
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
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                new AlertDialog.Builder(AddOrderActivity.this)
                        .setTitle("确认退出？").setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(AddOrderActivity.this)
                    .setTitle("确认退出？").setNegativeButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
