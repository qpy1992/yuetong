package com.example.win7.ytdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.win7.ytdemo.YApplication;

/**
 * Created by WIN7 on 2018/3/19.
 */

public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            YApplication.listActivity.add(this);
    }
}
