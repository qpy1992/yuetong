package com.example.win7.ytdemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.win7.ytdemo.R;
import com.example.win7.ytdemo.YApplication;
import com.example.win7.ytdemo.activity.BasicDataActivity;
import com.example.win7.ytdemo.activity.DakaActivity;
import com.example.win7.ytdemo.activity.DetailActivity;
import com.example.win7.ytdemo.activity.JiankongActivity;
import com.example.win7.ytdemo.activity.NeedCheckActivity;
import com.example.win7.ytdemo.activity.SettingsActivity;
import com.example.win7.ytdemo.activity.TongjiActivity;
import com.example.win7.ytdemo.adapter.GridViewAdapter;
import com.example.win7.ytdemo.entity.MainMenuEntity;
import com.example.win7.ytdemo.util.Consts;
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

public class MeFragment extends Fragment {
    Context mContext;
    View view;
    Toolbar toolbar;
    TextView tv_user,tv_depart,tv_company,tv_detail,tv_lately,tv_zhidu;
    LinearLayout ll_user,ll_depart,ll_company,ll_detail,ll_lately,ll_zhidu;
    Intent intent;
    String username,depart,company,detail,lately,zhidu="";
    GridView gv_me;
    GridViewAdapter adapter;
    private int[] resArr = new int[]{R.drawable.daka, R.drawable.jiankong,R.drawable.tongji,R.drawable.ic_action_tick,R.drawable.ic_action_barcode_2};
    private String[] textArr = new String[]{"打卡", "实时监控","统计表","待审核","收款码"};
    private List<MainMenuEntity> list = new ArrayList<MainMenuEntity>();
    MainMenuEntity data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        view = inflater.inflate(R.layout.fragment_me, container, false);
        for (int i = 0; i < resArr.length; i++) {
            data = new MainMenuEntity();
            data.setResId(resArr[i]);
            data.setText(textArr[i]);
            list.add(data);
        }
        setTool();
        setViews();
        setListeners();
        return view;
    }

    protected void setTool(){
        toolbar = (Toolbar) view.findViewById(R.id.id_toolbar);
        toolbar.setTitle(getResources().getString(R.string.me));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_settings:
                        startActivity(new Intent(mContext, SettingsActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu,menu);
    }

    protected void setViews() {
        gv_me = (GridView)view.findViewById(R.id.gv_me);
        tv_user = (TextView) view.findViewById(R.id.tv_user);
        tv_depart = (TextView) view.findViewById(R.id.tv_depart);
        tv_company = (TextView) view.findViewById(R.id.tv_company);
        tv_lately = (TextView) view.findViewById(R.id.tv_lately);
        tv_detail = (TextView) view.findViewById(R.id.tv_detail);
        tv_zhidu = (TextView) view.findViewById(R.id.tv_zhidu);
        ll_user = (LinearLayout)view.findViewById(R.id.ll_user);
        ll_depart = (LinearLayout)view.findViewById(R.id.ll_depart);
        ll_company = (LinearLayout)view.findViewById(R.id.ll_company);
        ll_lately = (LinearLayout)view.findViewById(R.id.ll_lately);
        ll_detail = (LinearLayout)view.findViewById(R.id.ll_companydetail);
        ll_zhidu = (LinearLayout)view.findViewById(R.id.ll_zhidu);
        adapter = new GridViewAdapter(mContext,list);
        gv_me.setAdapter(adapter);
        intent = new Intent(mContext, DetailActivity.class);
        new MTask().execute();
    }

    protected void setListeners(){
        ll_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                intent.putExtra("title","用户");
//                intent.putExtra("content",username);
//                startActivity(intent);
                TextView tv = new TextView(mContext);
                tv.setText(username);
                tv.setTextSize(16);
                tv.setPadding(60,20,40,10);
                new AlertDialog.Builder(mContext).setTitle("用户").setView(tv).show();
            }
        });
        ll_depart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                intent.putExtra("title","部门");
//                intent.putExtra("content",depart);
//                startActivity(intent);
                TextView tv = new TextView(mContext);
                tv.setText(depart);
                tv.setTextSize(16);
                tv.setPadding(60,20,40,10);
                new AlertDialog.Builder(mContext).setTitle("部门").setView(tv).show();
            }
        });
        ll_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                intent.putExtra("title","公司");
//                intent.putExtra("content",company);
//                startActivity(intent);
                TextView tv = new TextView(mContext);
                tv.setText(company);
                tv.setTextSize(16);
                tv.setPadding(60,20,40,10);
                new AlertDialog.Builder(mContext).setTitle("公司").setView(tv).show();
            }
        });
        ll_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                intent.putExtra("title","公司简介");
//                intent.putExtra("content",detail);
//                startActivity(intent);
                TextView tv = new TextView(mContext);
                tv.setText(detail);
                tv.setTextSize(16);
                tv.setPadding(60,20,40,10);
                new AlertDialog.Builder(mContext).setTitle("公司简介").setView(tv).show();
            }
        });
        ll_lately.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                intent.putExtra("title","最近发布");
//                intent.putExtra("content",lately);
//                startActivity(intent);
                TextView tv = new TextView(mContext);
                tv.setText(lately);
                tv.setTextSize(16);
                tv.setPadding(60,20,40,10);
                new AlertDialog.Builder(mContext).setTitle("最近发布").setView(tv).show();
            }
        });
        ll_zhidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                intent.putExtra("title","制度");
//                intent.putExtra("content",zhidu);
//                startActivity(intent);
                TextView tv = new TextView(mContext);
                tv.setText(zhidu);
                tv.setTextSize(16);
                tv.setPadding(60,20,40,10);
                new AlertDialog.Builder(mContext).setTitle("制度").setView(tv).show();
            }
        });
        gv_me.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        startActivity(new Intent(mContext, DakaActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(mContext, JiankongActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(mContext, TongjiActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(mContext, NeedCheckActivity.class));
                        break;
                    case 4:
                        ImageView iv = new ImageView(mContext);
                        iv.setImageResource(R.drawable.receive_barcode);
                        new AlertDialog.Builder(mContext).setView(iv).show();
                        break;
                }
            }
        });
    }

    class MTask extends AsyncTask<Void,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
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
            rpc.addProperty("FSql", "select a.fname username,b.fname depart,c.FName company,c.F_101 detail,c.f_102 lately,e.f_102 zhidu from t_User d inner join  t_Emp a on d.FEmpID=a.fitemid left join t_Department b on a.FDepartmentID=b.FItemID left join t_Item_3001 c on c.FItemID=b.f_102 left join t_Item_3006 e on e.F_101=b.FItemID where d.FName='"+YApplication.fname+"'");
            rpc.addProperty("FTable", "t_user");

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
                Log.i("MeFragment", e.toString() + "==================================");
            }

            // 获取返回的数据
            SoapObject object = (SoapObject) envelope.bodyIn;

            // 获取返回的结果
            if (null != object) {
                Log.i("返回结果", object.getProperty(0).toString()+"=========================");
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
                        username = recordEle.elementTextTrim("username"); // 拿到head节点下的子节点title值
                        depart = recordEle.elementTextTrim("depart");
                        company = recordEle.elementTextTrim("company");
                        detail = recordEle.elementTextTrim("detail");
                        lately = recordEle.elementTextTrim("lately");
                        zhidu = recordEle.elementTextTrim("zhidu");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return username+";"+depart+";"+company+";"+detail+";"+lately+";"+zhidu+";QAQ";
            }else {
                return "";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!s.equals("")){
                String[] str = s.split(";");
                tv_user.setText(str[0]);
                tv_depart.setText(str[1]);
                tv_company.setText(str[2]);
                tv_detail.setText(str[3]);
                tv_lately.setText(str[4]);
                tv_zhidu.setText(str[5]);
            }
        }
    }
}
