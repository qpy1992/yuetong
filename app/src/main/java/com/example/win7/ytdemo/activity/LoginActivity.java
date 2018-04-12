package com.example.win7.ytdemo.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.win7.ytdemo.R;
import com.example.win7.ytdemo.YApplication;
import com.example.win7.ytdemo.util.Consts;
import com.example.win7.ytdemo.util.Utils;
import com.example.win7.ytdemo.view.CustomProgress;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class LoginActivity extends BaseActivity {
    EditText et_username,et_password;
    Button btn_login;
    CustomProgress dialog;
    LinearLayout ll_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setViews();
        setListeners();
    }

    protected void setViews(){
        ll_login = (LinearLayout)findViewById(R.id.ll_login);
        et_username = (EditText)findViewById(R.id.et_username);
        et_password = (EditText)findViewById(R.id.et_password);
        btn_login = (Button)findViewById(R.id.btn_login);
        Utils.autoScrollView(ll_login, btn_login);//弹出软键盘时滚动视图
    }

    protected void setListeners(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_username.getText().toString().equals("")||et_password.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"请填写完整用户名和密码",Toast.LENGTH_SHORT).show();
                }else {
                    //登录K3用户
                    new LTask(et_username.getText().toString(), et_password.getText().toString()).execute();
                }
            }
        });
    }


    class LTask extends AsyncTask<Void,String,String> {
        String name;
        String pass;

        LTask(String name,String pass){
            this.name = name;
            this.pass = pass;
        }

        @Override
        protected void onPreExecute() {
            dialog = CustomProgress.show(LoginActivity.this,"登录中...",true,null);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            // 命名空间
            String nameSpace = "http://tempuri.org/";
            // 调用的方法名称
            String methodName = "Login";
            // EndPoint
            String endPoint = Consts.ENDPOINT;
            // SOAP Action
            String soapAction = "http://tempuri.org/Login";

            // 指定WebService的命名空间和调用的方法名
            SoapObject rpc = new SoapObject(nameSpace, methodName);

            // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
            rpc.addProperty("UserName", name);
            rpc.addProperty("PassWord", pass);

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
                if (result.substring(0,1).equals("0")) {
                    YApplication.fname = name;
                    YApplication.fgroup = result.replace("0","");
                    return "0";
                } else if(result.equals("1")){
                    return "1";
                }else if(result.equals("2")){
                    return "2";
                }else if (result.equals("3")){
                    return "3";
                }
            }
            return "4";
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            if(s.equals("0")) {
                //登录环信聊天服务器
                EMClient.getInstance().login(et_username.getText().toString(), "123", new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Log.d("main", "登录聊天服务器成功！");
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.d("main", "登录聊天服务器失败！");
                    }
                });
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                finish();
            }else if(s.equals("1")) {
                Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
            }else if(s.equals("2")) {
                Toast.makeText(LoginActivity.this,"用户名不存在",Toast.LENGTH_SHORT).show();
            }else if(s.equals("3")) {
                Toast.makeText(LoginActivity.this,"当前账号已在线",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(s);
        }
    }
}
