package com.example.win7.ytdemo.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.win7.ytdemo.R;
import com.example.win7.ytdemo.adapter.ZiAdapter;
import com.example.win7.ytdemo.util.Consts;
import com.example.win7.ytdemo.view.CustomProgress;

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

public class ZiFragment extends Fragment {
    Context                       mContext;
    View                          view;
    ListView                      lv_zi;
    TextView                      tv_zi;
    ZiAdapter                     adapter;
    List<HashMap<String, String>> list;
    DecimalFormat df  = new DecimalFormat("#0.00");
    DecimalFormat df1 = new DecimalFormat("#0.0000");
    String taskno, qi, zhi, neirong, jiliang, shuliang, danjia, progress, plan, budget, pbudget, note, hanshui, buhan, fuzhu, fuliang, fasong, huikui, pingfen;
    CustomProgress dialog;
    private List mSumBitmapList = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        view = inflater.inflate(R.layout.fragment_zi, container, false);
        setViews();
        setListeners();
        return view;
    }

    protected void setViews() {
        lv_zi = (ListView) view.findViewById(R.id.lv_zi);
        tv_zi = (TextView) view.findViewById(R.id.tv_zi);
        list = new ArrayList<>();
        taskno = getArguments().getString("taskno");
        new ZITask(taskno).execute();
    }

    protected void setListeners() {

    }

    class ZITask extends AsyncTask<Void, String, String> {
        String Taskno;

        ZITask(String Taskno) {
            this.Taskno = Taskno;
        }

        @Override
        protected void onPreExecute() {
            dialog = CustomProgress.show(mContext, "加载中...", true, null);
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
            String sql = " select b.FTime qi,b.FTime1 zhi,g.FName respon,h.FName progress,h.F_111 plans,i.FName budget,h.f_107 pbudget,b.FNOTE note," +
                    "   j.FName contacts,k.FName neirong,l.FName jiliang,b.FDecimal shuliang,b.FDecimal1 danjia,b.FAmount2 hanshui,b.FAmount3 buhan," +
                    "   b.FText fasong,b.FText1 huikui,o.FName pingfen,p.FName js1,b.FCheckBox1 qr1,q.FName js2,b.FCheckBox2 qr2," +
                    "   m.FName js3,b.FCheckBox3 qr3,n.FName js4,b.FCheckBox4 qr4,r.FName js5,b.FCheckBox5 qr5,s.fname fuzhu,b.fdecimal2 fuliang" +
                    "    from t_BOS200000000 a inner join t_BOS200000000Entry2 b on a.FID=b.FID" +
                    "   left join t_Currency c on c.FCurrencyID=a.FBase3 left join t_Item_3001 d on d.FItemID=a.FBase11" +
                    "   left join t_Department e on e.FItemID=a.FBase11 left join t_Item_3006 f on f.FItemID=a.FBase13" +
                    "   left join t_Emp g on g.FItemID=b.FBase4 left join t_Item_3007 h on h.FItemID=b.FBase left join" +
                    "   t_item i on i.FItemID=h.F_105 left join t_Emp j on j.FItemID=b.FBase10 left join t_ICItem k on k.FItemID=b.FBase1" +
                    "   left join t_MeasureUnit l on l.FMeasureUnitID=b.FBase2 left join t_Item o on o.FItemID=b.FBase14" +
                    "   left join t_Emp p on p.FItemID=b.FBase5 left join t_Emp q on q.FItemID=b.FBase6" +
                    "   left join t_Emp m on m.FItemID=b.FBase7 left join t_Emp n on n.FItemID=b.fbase8" +
                    "   left join t_Emp r on r.FItemID=b.FBase9 left join t_MeasureUnit s on s.FMeasureUnitID=k.FSecUnitID where a.fbillno='" + Taskno + "'";
            rpc.addProperty("FSql", sql);
            rpc.addProperty("FTable", "t_BOS200000000");

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
                    qi = recordEle.elementTextTrim("qi");
                    zhi = recordEle.elementTextTrim("zhi");
                    neirong = recordEle.elementTextTrim("neirong");
                    jiliang = recordEle.elementTextTrim("jiliang");
                    shuliang = recordEle.elementTextTrim("shuliang");
                    danjia = recordEle.elementTextTrim("danjia");
                    progress = recordEle.elementTextTrim("progress");
                    plan = recordEle.elementTextTrim("plans");
                    budget = recordEle.elementTextTrim("budget");
                    pbudget = recordEle.elementTextTrim("pbudget");
                    note = recordEle.elementTextTrim("note");
                    hanshui = recordEle.elementTextTrim("hanshui");
                    buhan = recordEle.elementTextTrim("buhan");
                    fuzhu = recordEle.elementTextTrim("fuzhu");
                    fuliang = recordEle.elementTextTrim("fuliang");
                    fasong = recordEle.elementTextTrim("fasong");
                    huikui = recordEle.elementTextTrim("huikui");
                    pingfen = recordEle.elementTextTrim("pingfen");
                    String a = recordEle.elementTextTrim("js1");
                    String b = recordEle.elementTextTrim("js2");
                    String c = recordEle.elementTextTrim("js3");
                    String d = recordEle.elementTextTrim("js4");
                    String e = recordEle.elementTextTrim("js5");
                    String qr1 = recordEle.elementTextTrim("qr1");
                    String qr2 = recordEle.elementTextTrim("qr2");
                    String qr3 = recordEle.elementTextTrim("qr3");
                    String qr4 = recordEle.elementTextTrim("qr4");
                    String qr5 = recordEle.elementTextTrim("qr5");
                    Log.i("审核标志", qr1 + qr2 + qr3 + qr4 + qr5);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("qi", qi);
                    map.put("zhi", zhi);
                    map.put("neirong", neirong);
                    map.put("jiliang", jiliang);
                    map.put("shuliang", df1.format(Double.parseDouble(shuliang)));
                    map.put("danjia", df.format(Double.parseDouble(danjia)));
                    map.put("progress", progress);
                    map.put("plan", plan);
                    map.put("budget", budget);
                    map.put("pbudget", df.format(Double.parseDouble(pbudget)));
                    map.put("note", note);
                    map.put("hanshui", df.format(Double.parseDouble(hanshui)));
                    map.put("buhan", df.format(Double.parseDouble(buhan)));
                    map.put("fuzhu", fuzhu);
                    map.put("fuliang", df1.format(Double.parseDouble(fuliang)));
                    map.put("fasong", fasong);
                    map.put("huikui", huikui);
                    map.put("pingfen", pingfen);
                    map.put("a", a);
                    map.put("b", b);
                    map.put("c", c);
                    map.put("d", d);
                    map.put("e", e);
                    map.put("qr1", qr1);
                    map.put("qr2", qr2);
                    map.put("qr3", qr3);
                    map.put("qr4", qr4);
                    map.put("qr5", qr5);
                    list.add(map);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (list.size() == 0) {
                return "0";
            } else {
                return "1";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if (s.equals("0")) {
                tv_zi.setVisibility(View.VISIBLE);
            } else {
                adapter = new ZiAdapter(mContext, list, mSumBitmapList);
                lv_zi.setAdapter(adapter);
            }
        }
    }
}
