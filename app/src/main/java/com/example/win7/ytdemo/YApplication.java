package com.example.win7.ytdemo;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import com.example.win7.ytdemo.util.Consts;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by WIN7 on 2018/3/15.
 */

public class YApplication extends Application {
    public static ArrayList<Activity> listActivity = new ArrayList<Activity>();
    public static String fname = "";
    public static String fgroup = "";

    @Override
    public void onCreate() {
        super.onCreate();
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //初始化
        EMClient.getInstance().init(getApplicationContext(), options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    public static void exit(){
        try {
            for (Activity activity : listActivity) {
                EMClient.getInstance().logout(true);
                activity.finish();
                Log.i("退出", activity.toString() + " finish了");
            }
            new ETask().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    static class ETask extends AsyncTask<Void,String,String>{

        @Override
        protected String doInBackground(Void... voids) {
            // 命名空间
            String nameSpace = "http://tempuri.org/";
            // 调用的方法名称
            String methodName = "Z_Exit";
            // EndPoint
            String endPoint = Consts.ENDPOINT;
            // SOAP Action
            String soapAction = "http://tempuri.org/Z_Exit";

            // 指定WebService的命名空间和调用的方法名
            SoapObject rpc = new SoapObject(nameSpace, methodName);

            // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
            rpc.addProperty("UserName", fname);

            // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

            envelope.bodyOut = rpc;
            // 设置是否调用的是dotNet开发的WebService
            envelope.dotNet = true;
            // 等价于envelope.bodyOut = rpc;
            envelope.setOutputSoapObject(rpc);

            HttpTransportSE transport = new HttpTransportSE(endPoint);
            try {
                // 调用WebService
                transport.call(soapAction, envelope);
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("LoginActivity", e.toString() + "==================================");
            }

            // 获取返回的数据
            SoapObject object = (SoapObject) envelope.bodyIn;

            // 获取返回的结果
            if (null != object) {
                Log.i("返回结果", object.getProperty(0).toString()+"=========================");
                String result = object.getProperty(0).toString();
                if (result.equals("成功")) {
                    return "1";
                } else {
                    return "2";
                }
            }
            return "3";
        }
    }
}
