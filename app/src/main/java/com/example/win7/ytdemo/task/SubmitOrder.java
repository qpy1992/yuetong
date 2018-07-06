package com.example.win7.ytdemo.task;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.win7.ytdemo.entity.OrderDataInfo;
import com.example.win7.ytdemo.util.Consts;
import com.example.win7.ytdemo.view.CustomProgress;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.StringWriter;

/**
 * @创建者 AndyYan
 * @创建时间 2018/7/6 9:10
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class SubmitOrder extends AsyncTask<Void, String, String> {
    private OrderDataInfo mOrderDataInfo;
    private Context       mContext;

    public SubmitOrder(OrderDataInfo orderDataInfo, Context context) {
        this.mOrderDataInfo = orderDataInfo;
        this.mContext = context;
    }

    CustomProgress dialog;

    @Override
    protected void onPreExecute() {
        dialog = CustomProgress.show(mContext, "提交中...", true, null);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            // 命名空间
            String nameSpace = "http://tempuri.org/";
            // 调用的方法名称
            String methodName = "t_BOS200000011";
            // EndPoint
            String endPoint = Consts.ENDPOINT;
            // SOAP Action
            String soapAction = "http://tempuri.org/t_BOS200000011";

            // 指定WebService的命名空间和调用的方法名
            SoapObject rpc = new SoapObject(nameSpace, methodName);
            // 设置需调用WebService接口需要传入的参数
            rpc.addProperty("InterID", mOrderDataInfo.getInnerid());//mr.0
            rpc.addProperty("BillNO", mOrderDataInfo.getFbillNo());//mr.a

            //表头
            Document document = DocumentHelper.createDocument();
            Element rootElement = document.addElement("NewDataSet");
            Element cust = rootElement.addElement("Cust");
            //币别
            cust.addElement("FBase3").setText(mOrderDataInfo.getCurrency());//....
            //汇率
            cust.addElement("FAmount4").setText(mOrderDataInfo.getRate());
            //组织机构
            cust.addElement("FBase11").setText(mOrderDataInfo.getOrga());//...
            //累计：欠进项发票额！！
            cust.addElement("FAmount36").setText(mOrderDataInfo.getOweInvTotal());
            //本单：欠进项发票额！
            cust.addElement("FAmount29").setText(mOrderDataInfo.getThisOweInv());
            //应付帐期天数（进项发票日算）！
            cust.addElement("FInteger").setText(mOrderDataInfo.getPayDate());
            //付款往来
            cust.addElement("FBase24").setText(mOrderDataInfo.getPayContact());//...
            //付款量合计
            cust.addElement("FDecimal8").setText(mOrderDataInfo.getPayAmount());
            //付款成本不含税合计
            cust.addElement("FAmount9").setText(mOrderDataInfo.getPaynoTax());
            //付款含税合计
            cust.addElement("FAmount17").setText(mOrderDataInfo.getPaywithTax());
            //进项票往来
            cust.addElement("FBase13").setText(mOrderDataInfo.getIncomeCont());//..
            //进项发票量合计
            cust.addElement("FDecimal12").setText(mOrderDataInfo.getIncomeInv());
            //进项发票含税总额合计
            cust.addElement("FAmount16").setText(mOrderDataInfo.getInvwithTax());
            //应付款合计
            cust.addElement("FAmount27").setText(mOrderDataInfo.getSpayTotal());
            //入出库往来
            cust.addElement("FBase25").setText(mOrderDataInfo.getInnerIncome());//...
            //内容
            cust.addElement("FBase26").setText(mOrderDataInfo.getContent());//...
            //入库成本合计
            cust.addElement("FAmount12").setText(mOrderDataInfo.getInnnerCost());
            //出库成本合计
            cust.addElement("FAmount13").setText(mOrderDataInfo.getOutCost());
            //累计：已开票未收款额！！
            cust.addElement("FAmount37").setText(mOrderDataInfo.getTotalUnrece());
            //本单：已开票未收款额！！
            cust.addElement("FAmount30").setText(mOrderDataInfo.getThisUnrece());
            //应收帐期天数（销项发票日算）！！
            cust.addElement("FInteger1").setText(mOrderDataInfo.getShRecceData());
            //收款往来
            cust.addElement("FBase27").setText(mOrderDataInfo.getRecIncome());//...
            //应收款合计
            cust.addElement("FAmount28").setText(mOrderDataInfo.getShRecTotal());
            //销项票往来
            cust.addElement("FBase16").setText(mOrderDataInfo.getOutTicIncome());//...
            //销项发票量合计
            cust.addElement("FDecimal13").setText(mOrderDataInfo.getOutTickTotal());
            //销项开票含税总额合计
            cust.addElement("FAmount19").setText(mOrderDataInfo.getOutTickWTax());
            //表体
            Document document2 = DocumentHelper.createDocument();
            Element rootElement2 = document2.addElement("NewDataSet");
            for (OrderDataInfo.ListsonBean listsonBean : mOrderDataInfo.getListson()) {
                Element cust2 = rootElement2.addElement("Cust");
                //内容
                cust2.addElement("FBase1").setText(listsonBean.getContentX());//...
                //摘要
                cust2.addElement("FNOTE2").setText(listsonBean.getRemark());
                //日期
                cust2.addElement("FTime2").setText(listsonBean.getDate());
                //制单人
                cust2.addElement("FBase15").setText(listsonBean.getSinPerson());//...
                //申请部门
                cust2.addElement("FBase18").setText(listsonBean.getApplyPartX());//...
                //评分
                cust2.addElement("FBase14").setText("10");
                //表体往来
                cust2.addElement("FBase10").setText(listsonBean.getBodyIncomeX());//...
                //往来-银行及帐号
                cust2.addElement("FBase").setText(listsonBean.getBankIncomeX());//...
                //
                cust2.addElement("FBase21").setText("0");
                //计划预算进度
                cust2.addElement("FName13").setText(listsonBean.getPlanBudgetX());
                //预算科目
                cust2.addElement("FName14").setText(listsonBean.getBudSubX());
                //预算余额
                cust2.addElement("F_109").setText(listsonBean.getBudBalanceX());
                //计量
                cust2.addElement("FBase2").setText(listsonBean.getUnitX());//...
                //数量
                cust2.addElement("FDecimal").setText(listsonBean.getNumberX());
                //单价
                cust2.addElement("FDecimal1").setText(listsonBean.getUnitPriceX());
                //金额含税
                cust2.addElement("FAmount2").setText(listsonBean.getMoneyTaxX());
                //税额
                cust2.addElement("FAmount").setText(listsonBean.getTaxAmountX());
                //人民币不含税额
                cust2.addElement("FAmount3").setText(listsonBean.getRMBNoTaxX());
                //税率%
                cust2.addElement("FAmount10").setText(listsonBean.getTaxRateX());
                //辅助
                cust2.addElement("FName16").setText(listsonBean.getUnitOtherX());
                //辅量
                cust2.addElement("FDecimal2").setText(listsonBean.getUnitNumX());
                //发票日-权责制
                cust2.addElement("FTime3").setText(listsonBean.getTicDataRespon());
                //备注-发票号码/税票说明
                cust2.addElement("FText2").setText(listsonBean.getRemarkTicNOX());
                //发票税务科目
                cust2.addElement("FBase17").setText(listsonBean.getTicTaxSubX());//...
                //发送消息
                cust2.addElement("FText").setText("");
                //回馈消息
                cust2.addElement("FText1").setText("");
            }

            OutputFormat outputFormat = OutputFormat.createPrettyPrint();
            outputFormat.setSuppressDeclaration(false);
            outputFormat.setNewlines(false);
            StringWriter stringWriter = new StringWriter();
            StringWriter stringWriter2 = new StringWriter();
            // xmlWriter是用来把XML文档写入字符串的(工具)
            XMLWriter xmlWriter = new XMLWriter(stringWriter, outputFormat);
            XMLWriter xmlWriter2 = new XMLWriter(stringWriter2, outputFormat);
            // 把创建好的XML文档写入字符串
            xmlWriter.write(document);
            xmlWriter2.write(document2);
            String fbtouxml = stringWriter.toString().substring(38);
            String fbtixml = stringWriter2.toString().substring(38);
            rpc.addProperty("FBtouXMl", fbtouxml);
            rpc.addProperty("FBtiXML", fbtixml);

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
            String result = "";
            if (envelope.bodyIn instanceof SoapFault) {
                Log.i("服务器返回", (SoapObject) envelope.getResponse() + "");
            } else {
                SoapObject object = (SoapObject) envelope.bodyIn;
                // 获取返回的结果
                Log.i("返回结果", object.getProperty(0).toString() + "=========================");
                result = object.getProperty(0).toString();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.dismiss();
        if (s.equals("成功")) {
            Toast.makeText(mContext, "提交成功", Toast.LENGTH_SHORT).show();
            ((AppCompatActivity) mContext).finish();
        } else {
            Toast.makeText(mContext, "提交失败", Toast.LENGTH_SHORT).show();
        }
    }
}
