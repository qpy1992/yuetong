package com.example.win7.ytdemo.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.win7.ytdemo.YApplication;

/**
 * Created by WIN7 on 2018/3/19.
 */

public class BaseActivity extends AppCompatActivity {
    private YApplication      mApplication;
    private ProgressDialog    mProgressDialog;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YApplication.mBaseActivityList.add(this);
        /**
         *  所有的Activity都依附于一个Application，在Activity中只要通过 getApplication（）方法，就能拿到当前应用中的Application对象
         *
         */
        mApplication = (YApplication) getApplication();
        mApplication.addActivity(this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mSharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
    }

    public void showDialog(String msg) {
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    public void hideDialog() {
        mProgressDialog.hide();
    }
}
