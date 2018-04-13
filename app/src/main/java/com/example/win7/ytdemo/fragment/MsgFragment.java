package com.example.win7.ytdemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.win7.ytdemo.R;
import com.example.win7.ytdemo.activity.ChatActivity;
import com.example.win7.ytdemo.adapter.MsgAdapter;
import com.example.win7.ytdemo.entity.Msg;
import com.example.win7.ytdemo.util.Consts;
import com.example.win7.ytdemo.view.CustomProgress;
import com.hyphenate.chat.EMClient;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MsgFragment extends Fragment {
    Context mContext;
    View view;
    ListView lv_msg;
    SwipeRefreshLayout srl_msg;
    MsgAdapter adapter;
    List<Msg> list;
    CustomProgress dialog;
    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        view = inflater.inflate(R.layout.fragment_msg, container, false);
        setTool();
        setViews();
        setListeners();
        return view;
    }

    protected void setTool(){
        toolbar = (Toolbar) view.findViewById(R.id.id_toolbar);
        toolbar.setTitle(R.string.msg);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    protected void setViews(){
        lv_msg = (ListView)view.findViewById(R.id.lv_msg);
        srl_msg = (SwipeRefreshLayout)view.findViewById(R.id.srl_msg);
        list = new ArrayList<>();
        new NTask().execute();
    }

    protected void setListeners(){
        lv_msg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("nickname",list.get(i).getNickname());
                intent.putExtra("name",list.get(i).getName());
                startActivity(intent);
            }
        });

        srl_msg.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorPrimary});
        srl_msg.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            new NTask().execute();
            srl_msg.setRefreshing(false);
            }
        });
    }

    class NTask extends AsyncTask<Void,String,String>{
        @Override
        protected void onPreExecute() {
            list.clear();
            dialog = CustomProgress.show(mContext,"加载中...",true,null);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                String s = "(";
                for(String username:usernames){
                    s = s+"'"+username+"',";
                }
                s = s.substring(0,s.length() - 1);
                s = s+")";
                Log.i("拼接的数据集",s+"=================================");

                // 命名空间
                String nameSpace = "http://tempuri.org/";
                // 调用的方法名称
                String methodName = "JA_select";
                // EndPoint
                String endPoint = Consts.ENDPOINT;
                // SOAP Action
                String soapAction = "http://tempuri.org/JA_select";

                // 指定WebService的命名空间和调用的方法名
                SoapObject rpc = new SoapObject(nameSpace, methodName);

                // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
                Log.i("昵称查询语句","select a.fname from t_emp a inner join t_user d on a.fitemid=b.fempid where d.fname in"+s+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                rpc.addProperty("FSql", "select a.fname,b.fname name from t_emp a inner join t_user b on a.fitemid=b.fempid where b.fname in"+s);
                rpc.addProperty("FTable", "t_user");

                // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

                envelope.bodyOut = rpc;
                // 设置是否调用的是dotNet开发的WebService
                envelope.dotNet = true;
                // 等价于envelope.bodyOut = rpc;
                envelope.setOutputSoapObject(rpc);

                HttpTransportSE transport = new HttpTransportSE(endPoint);
                // 调用WebService
                transport.call(soapAction, envelope);
                // 获取返回的数据
                SoapObject object = (SoapObject) envelope.bodyIn;

                // 获取返回的结果
                Log.i("返回结果", object.getProperty(0).toString() + "=========================");
                String result = object.getProperty(0).toString();
                Document doc = null;

                try {
                    doc = DocumentHelper.parseText(result); // 将字符串转为XML

                    Element rootElt = doc.getRootElement(); // 获取根节点

                    System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称


                    Iterator iter = rootElt.elementIterator("Cust"); // 获取根节点下的子节点head

                    // 遍历head节点
                    while (iter.hasNext()) {
                        Element recordEle = (Element) iter.next();
                        Msg msg = new Msg();
                        msg.setNickname(recordEle.elementTextTrim("fname"));
                        msg.setName(recordEle.elementTextTrim("name"));
                        list.add(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            adapter = new MsgAdapter(mContext,list);
            lv_msg.setAdapter(adapter);
            super.onPostExecute(s);
        }
    }
}
