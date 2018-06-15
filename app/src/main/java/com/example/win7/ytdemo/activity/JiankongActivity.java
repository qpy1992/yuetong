package com.example.win7.ytdemo.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.win7.ytdemo.R;
import com.ezvizuikit.open.EZUIError;
import com.ezvizuikit.open.EZUIKit;
import com.ezvizuikit.open.EZUIPlayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class JiankongActivity extends BaseActivity {
    private Toolbar    toolbar;
    private EZUIPlayer mPlayer;
    private Spinner    mSp_camid;
    private String cameraID = "107910291";
    private List<String> mIdData;//存放摄像头ID

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiankong);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setTool();
        setViews();
        setData();
        setListeners();
    }

    protected void setTool() {
        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        toolbar.setTitle(getResources().getString(R.string.jiankong));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setViews() {
//        mSp_camid = (Spinner) findViewById(R.id.sp_camid);
        //获取EZUIPlayer实例
        mPlayer = (EZUIPlayer) findViewById(R.id.player_ui);
    }

    private void setData() {
        mIdData = new ArrayList();
        mIdData.add("107910291");
        mIdData.add("107910292");

//        CameraIdSpAdapter spAdapter = new CameraIdSpAdapter(JiankongActivity.this, mIdData);
//        mSp_camid.setAdapter(spAdapter);
        //初始化EZUIKit
        EZUIKit.initWithAppKey(getApplication(), "d55fc8eefc784b9db9acbacd1bab29a2");
        //设置授权token
        EZUIKit.setAccessToken("at.7ognuaahb0m0em9d855r905k3owsz4gh-32fephnbo7-0f3wlxx-syjzg8eio");
        //设置播放回调callback
        mPlayer.setCallBack(new EZUIPlayer.EZUIPlayerCallBack() {
            @Override
            public void onPlaySuccess() {

            }

            @Override
            public void onPlayFail(EZUIError ezuiError) {
                String errorString = ezuiError.getErrorString();
                Toast.makeText(JiankongActivity.this, errorString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVideoSizeChange(int i, int i1) {

            }

            @Override
            public void onPrepared() {
                //回放文件列表
                //                List<EZRecordFile> playList = mPlayer.getPlayList();
                mPlayer.startPlay();
            }

            @Override
            public void onPlayTime(Calendar calendar) {

            }

            @Override
            public void onPlayFinish() {
                Toast.makeText(JiankongActivity.this, "播放结束", Toast.LENGTH_SHORT).show();
            }
        });
        //设置播放参数
        mPlayer.setUrl("ezopen://open.ys7.com/" + cameraID + "/1.hd.live");

        //加载中显示的UI
        //创建loadingview
        ProgressBar mLoadView = new ProgressBar(JiankongActivity.this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        mLoadView.setLayoutParams(lp);
        //设置loadingview
        mPlayer.setLoadingView(mLoadView);
    }

    protected void setListeners() {
//        mSp_camid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String camID = mIdData.get(i);
//                //设置播放参数
//                mPlayer.setUrl("ezopen://open.ys7.com/" + camID + "/1.hd.live");
//                mPlayer.startPlay();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mPlayer) {
            mPlayer.startPlay();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止播放
        mPlayer.stopPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        mPlayer.releasePlayer();
    }
}
