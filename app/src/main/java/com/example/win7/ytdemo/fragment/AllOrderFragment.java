package com.example.win7.ytdemo.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.win7.ytdemo.R;
import com.example.win7.ytdemo.activity.AddOrderActivity;
import com.example.win7.ytdemo.util.ToastUtils;

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
    private TextView tv_hsje;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_all_order, null);
        initView();
        initData();
        AddOrderActivity activity = (AddOrderActivity) getActivity();
        mEditable = activity.getEditable();
        return mRootView;
    }

    private void initView() {
        tv_hsje = mRootView.findViewById(R.id.tv_hsje);
        bt_submit = mRootView.findViewById(R.id.bt_submit);
    }

    private void initData() {

        Intent intent = getActivity().getIntent();
        String kind = intent.getStringExtra("kind");
        if (null == kind) {
            ToastUtils.showToast(getContext(), "error:未传递种类");
            getActivity().finish();
            return;
        }
        if (kind.equals("check")) {//查看
            bt_submit.setVisibility(View.GONE);
        }
        tv_hsje.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!mEditable) {
            ToastUtils.showToast(getContext(), "目前是查询页面，不可修改");
            return;
        }
        switch (view.getId()) {
            case R.id.tv_hsje:
                changeViewContent(tv_hsje, "含税金额合计:");
                break;
            default:
                break;
        }
    }

    private void changeViewContent(final TextView tvcontent, String title) {
        //弹出dailog展示修改内容
        final EditText et = new EditText(getContext());
        //写入数据
        String oldContent = String.valueOf(tv_hsje.getText());
        et.setText(oldContent);
        new AlertDialog.Builder(getContext()).setView(et).setTitle(title)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //修改改变内容的textview
                        String content = String.valueOf(et.getText()).trim();
//                        if (content.equals("")) {
//                            ToastUtils.showToast(getContext(), "修改不能为空");
//                            return;
//                        }
                        tvcontent.setText(content);
                    }
                }).setNegativeButton("取消", null).show();
    }
}
