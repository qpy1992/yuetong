package com.example.win7.ytdemo.util;

import android.os.AsyncTask;

import com.example.win7.ytdemo.entity.Tasks;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.Request;

/**
 * @创建者 AndyYan
 * @创建时间 2018/7/2 11:11
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class NetworkRequestUtils {
    public static NetworkRequestUtils requestUtils;

    public static NetworkRequestUtils getInstance() {
        if (requestUtils == null) {
            synchronized (NetworkRequestUtils.class) {
                if (requestUtils == null)
                    requestUtils = new NetworkRequestUtils();
            }
        }
        return requestUtils;
    }

    private void requestTaskInfo(String sql,RequestCallBack callBack) {
        new ConditionTask(sql).execute();
    }

    class ConditionTask extends AsyncTask<Void, String, String> {
        String sql;

        public ConditionTask(String sql) {
            this.sql = sql;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//            list.clear();
//            dialog = CustomProgress.show(mContext, "加载中...", true, null);
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
            }

            // 获取返回的数据
            SoapObject object = (SoapObject) envelope.bodyIn;

            // 获取返回的结果
            String result = object.getProperty(0).toString();
            Document doc = null;

            try {
                doc = DocumentHelper.parseText(result); // 将字符串转为XML

                Element rootElt = doc.getRootElement(); // 获取根节点

                Iterator iter = rootElt.elementIterator("Cust"); // 获取根节点下的子节点head

                // 遍历head节点
                while (iter.hasNext()) {
                    Element recordEle = (Element) iter.next();
                    Tasks tasks = new Tasks();
                    String finterid = recordEle.elementTextTrim("fid");
                    String fbillno = recordEle.elementTextTrim("fbillno");
                    tasks.setFinterid(finterid);
                    tasks.setFbillno(fbillno);
//                    list.add(tasks);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            if (list.size() == 0) {
//                return "0";
//            } else {
//                return "1";
//            }
            return "0";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }

    public interface RequestCallBack {

        void onError(Request request, IOException e);

        void onSuccess(int code, String resbody);
    }
}
