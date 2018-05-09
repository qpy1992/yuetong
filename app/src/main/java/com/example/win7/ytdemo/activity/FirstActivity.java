package com.example.win7.ytdemo.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.win7.ytdemo.R;

public class FirstActivity extends AppCompatActivity {
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        try {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        startActivity(new Intent(FirstActivity.this,
                                LoginActivity.class));
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
