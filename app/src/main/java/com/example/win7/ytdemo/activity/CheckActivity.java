package com.example.win7.ytdemo.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import com.example.win7.ytdemo.R;
import com.example.win7.ytdemo.util.Consts;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CheckActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tv_item,tv_num,tv_pri,tv_taxpri,tv_start,tv_end,tv_pro,tv_plans,tv_budget,tv_pbudget,tv_notes,tv_buhan,tv_sup,tv_supl,tv_send,tv_pf,tv_submit;
    EditText et_get;
    List<HashMap<String,String>> list1;
    HashMap<String,String> map;
    String pfid;
    DecimalFormat df  = new DecimalFormat("#0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        setTool();
        setViews();
        setListeners();
    }

    protected void setTool() {
        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        toolbar.setTitle(R.string.check);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

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

    protected void setViews(){
        tv_item = (TextView)findViewById(R.id.tv_item);
        tv_num = (TextView)findViewById(R.id.tv_num);
        tv_pri = (TextView)findViewById(R.id.tv_pri);
        tv_taxpri = (TextView)findViewById(R.id.tv_taxpri);
        tv_start = (TextView)findViewById(R.id.tv_start);
        tv_end = (TextView)findViewById(R.id.tv_end);
        tv_pro = (TextView)findViewById(R.id.tv_pro);
        tv_plans = (TextView)findViewById(R.id.tv_plans);
        tv_budget = (TextView)findViewById(R.id.tv_budget_check);
        tv_pbudget = (TextView)findViewById(R.id.tv_pbudget_check);
        tv_notes = (TextView)findViewById(R.id.tv_notes);
        tv_buhan = (TextView)findViewById(R.id.tv_buhan_check);
        tv_sup = (TextView)findViewById(R.id.tv_sup);
        tv_supl = (TextView)findViewById(R.id.tv_supl);
        tv_send = (TextView)findViewById(R.id.tv_send);
        tv_pf = (TextView)findViewById(R.id.tv_pf);
        tv_submit = (TextView)findViewById(R.id.tv_submit_check);
        et_get = (EditText)findViewById(R.id.et_get);
        map = new HashMap<>();
        list1 = new ArrayList<>();
        Intent intent = getIntent();
        String goodsid = intent.getStringExtra("goodsId");
        new DetailTask(goodsid).execute();
    }

    protected void setListeners(){
        tv_pf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PFTask(tv_pf).execute();
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    class CheckTask extends AsyncTask<Void,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    class DetailTask extends AsyncTask<Void,String,String>{
        String id;

        public DetailTask(String id){
            this.id = id;
        }

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
            String sql = "select b.fname item,a.fdecimal shuliang,a.fdecimal1 danjia,a.famount2 hanshui,a.ftime qi,a.ftime1 zhi,c.fname progress," +
                    "c.f_111 plans,d.fname budget,c.f_107 pbudget,a.fnote,a.famount3 buhan,e.fname fuzhu,a.fdecimal2 fuliang,a.ftext fasong,a.ftext1 huikui,f.fname pf" +
                    " from t_BOS200000000Entry2 a left join t_icitem b on b.fitemid=a.fbase1 " +
                    "left join t_item_3007 c on c.fitemid=a.fbase left join t_item d on d.fitemid=c.f_105 " +
                    "left join t_measureunit e on e.fmeasureunitid=b.fitemid left join t_item f on f.fitemid=a.fbase14 where a.id='"+id+"'";
            rpc.addProperty("FSql", sql);
            rpc.addProperty("FTable", "t_BOS200000000Entry2");

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
                Log.i("CheckActivity", e.toString() + "==================================");
            }

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
                    map.put("item", recordEle.elementTextTrim("item"));
                    map.put("shuliang", recordEle.elementTextTrim("shuliang"));
                    map.put("danjia",recordEle.elementTextTrim("danjia"));
                    map.put("hanshui",recordEle.elementTextTrim("hanshui"));
                    map.put("qi",recordEle.elementTextTrim("qi"));
                    map.put("zhi",recordEle.elementTextTrim("zhi"));
                    map.put("progress",recordEle.elementTextTrim("progress"));
                    map.put("plan",recordEle.elementTextTrim("plans"));
                    map.put("budget",recordEle.elementTextTrim("budget"));
                    map.put("pbudget",recordEle.elementTextTrim("pbudget"));
                    map.put("note",recordEle.elementTextTrim("fnote"));
                    map.put("buhan",recordEle.elementTextTrim("buhan"));
                    map.put("fuzhu",recordEle.elementTextTrim("fuzhu"));
                    map.put("fuliang",recordEle.elementTextTrim("fuliang"));
                    map.put("fasong",recordEle.elementTextTrim("fasong"));
                    map.put("huikui",recordEle.elementTextTrim("huikui"));
                    map.put("pf",recordEle.elementTextTrim("pf"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "0";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv_item.setText(map.get("item"));
            tv_num.setText(df.format(Double.parseDouble(map.get("shuliang"))));
            tv_pri.setText(df.format(Double.parseDouble(map.get("danjia"))));
            tv_taxpri.setText(df.format(Double.parseDouble(map.get("hanshui"))));
            tv_start.setText(map.get("qi"));
            tv_end.setText(map.get("zhi"));
            tv_pro.setText(map.get("progress"));
            tv_plans.setText(map.get("plan"));
            tv_budget.setText(map.get("budget"));
            tv_pbudget.setText(map.get("pbudget"));
            tv_notes.setText(map.get("note"));
            tv_buhan.setText(df.format(Double.parseDouble(map.get("buhan"))));
            tv_sup.setText(map.get("fuzhu"));
            tv_supl.setText(df.format(Double.parseDouble(map.get("fuliang"))));
            tv_send.setText(map.get("fasong"));
            et_get.setText(map.get("huikui"));
            tv_pf.setText(map.get("pf"));
        }
    }

    class PFTask extends AsyncTask<Void, String, String> {
        TextView tv;

        public PFTask(TextView tv) {
            this.tv = tv;
        }

        @Override
        protected void onPreExecute() {
            list1.clear();
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
            String sql = "select fitemid,fname from t_Item where FItemClassID=3010";
            rpc.addProperty("FSql", sql);
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
                Log.i("AddTaskActivity", e.toString() + "==================================");
            }

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
                    HashMap<String, String> map = new HashMap<>();
                    map.put("itemid", recordEle.elementTextTrim("fitemid"));
                    map.put("fname", recordEle.elementTextTrim("fname"));
                    list1.add(map);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "0";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            List<String> strList = new ArrayList<>();
            for (HashMap<String, String> map : list1) {
                String name = map.get("fname");
                strList.add(name);
            }
            final GridView gv = new GridView(CheckActivity.this);
            gv.setNumColumns(3);
            final String[] fen = new String[strList.size()];
            for (int i = 0; i < strList.size(); i++) {
                fen[i] = strList.get(i);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(CheckActivity.this, android.R.layout.simple_list_item_1,
                    fen);
            gv.setAdapter(adapter);
            final AlertDialog dialog = new AlertDialog.Builder(CheckActivity.this).setView(gv).show();
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    pfid = list1.get(i).get("itemid");
                    tv.setText(fen[i]);
                    dialog.dismiss();
                }
            });
        }
    }
}
