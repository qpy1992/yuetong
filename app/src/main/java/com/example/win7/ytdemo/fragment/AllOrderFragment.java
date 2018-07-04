package com.example.win7.ytdemo.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.win7.ytdemo.R;
import com.example.win7.ytdemo.activity.AddOrderActivity;
import com.example.win7.ytdemo.adapter.LvShowMoreAdapter;
import com.example.win7.ytdemo.util.Consts;
import com.example.win7.ytdemo.util.ToastUtils;
import com.example.win7.ytdemo.view.CustomProgress;

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

/**
 * @创建者 AndyYan
 * @创建时间 2018/6/26 9:02
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class AllOrderFragment extends Fragment implements View.OnClickListener {
    private View   mRootView;
    private Button bt_submit;
    private boolean mEditable = false;//是否可编辑
    private TextView tv_hsje, tv_fkwl;
    private String orderID = "";//订单表id
    private CustomProgress dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_all_order, null);
        AddOrderActivity activity = (AddOrderActivity) getActivity();
        mEditable = activity.getEditable();
        orderID = activity.getIntent().getStringExtra("orderID");
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        tv_hsje = mRootView.findViewById(R.id.tv_hsje);
        tv_fkwl = mRootView.findViewById(R.id.tv_fkwl);//付款往来
        bt_submit = mRootView.findViewById(R.id.bt_submit);
    }

    private void initData() {
        //先行判断，进入条件
        Intent intent = getActivity().getIntent();
        String kind = intent.getStringExtra("kind");
        if (null == kind) {
            ToastUtils.showToast(getContext(), "error:未传递种类");
            getActivity().finish();
            return;
        }
        if (kind.equals("check")) {//查看
            bt_submit.setVisibility(View.GONE);
        }
        new ZHUTask(orderID).execute();
        tv_hsje.setOnClickListener(this);
        tv_fkwl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!mEditable) {
            ToastUtils.showToast(getContext(), "目前是查询页面，不可修改");
            return;
        }
        switch (view.getId()) {
            case R.id.tv_hsje:
                changeViewContent(tv_hsje, "含税金额合计:");
                break;
            case R.id.tv_fkwl:
                changeViewContent(tv_fkwl, "付款往来");
                break;
            default:
                break;
        }
    }

    private void changeViewContent(final TextView tvcontent, final String title) {
        //弹出dailog展示修改内容
        final EditText et = new EditText(getContext());
        //写入数据
        String oldContent = String.valueOf(tv_hsje.getText());
        et.setText(oldContent);
        new AlertDialog.Builder(getContext()).setView(et).setTitle(title)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //修改改变内容的textview
                        String content = String.valueOf(et.getText()).trim();
                        if (content.equals("")) {
                            //为空，弹出选择菜单列表
                            showMoreWriteInfo(tvcontent, title);
                        } else {
                            tvcontent.setText(content);
                        }
                    }
                }).setNegativeButton("取消", null).show();
    }

    private void showMoreWriteInfo(final TextView tvcontent, String title) {
        View view = View.inflate(getContext(), R.layout.view_only_list, null);
        ListView lv_showmore = view.findViewById(R.id.lv_showmore);
        final List<String> mShowData = new ArrayList();
        mShowData.add("1052");
        mShowData.add("9527");
        mShowData.add("89757");
        final LvShowMoreAdapter showMoreAdapter = new LvShowMoreAdapter(getContext(), mShowData);
        lv_showmore.setAdapter(showMoreAdapter);
        final AlertDialog dialog = new AlertDialog.Builder(getContext()).setView(view).setTitle(title).show();
        lv_showmore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvcontent.setText(mShowData.get(i));
                dialog.dismiss();
            }
        });
    }

    class ZHUTask extends AsyncTask<Void, String, String> {
        String mOrderID;

        ZHUTask(String orderID) {
            this.mOrderID = orderID;
        }

        @Override
        protected void onPreExecute() {
            dialog = CustomProgress.show(getContext(), "加载中...", true, null);
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
            String sql = "select a.FBillNo,c.FName,a.FAmount4,d.FName,FAmount36,FAmount29," +
                    " a.FInteger,e.fname,a.FDecimal8,a.FAmount9,FAmount17,f.FName,FDecimal12,FAmount16,FAmount27,g.FName,h.FName," +
                    " FAmount12,FAmount13,FAmount37,FAmount30, FInteger1,i.FName,FAmount28,j.FName,FDecimal13,FAmount19," +
                    " k.FName,b.FNOTE2,FTime2,l.FName,m.FName,n.FName,o.FName,o.FBankAccount,p.FName,q.FName,p.F_109," +
                    " r.FName,b.FDecimal,b.FDecimal1,b.FAmount2,FAmount, b.FAmount3,b.FAmount10,s.FName,b.FDecimal," +
                    " b.FTime3,b.FText2, t.FName,u.FName,b.FText,b.FText1 " +
                    " from t_BOS200000011 a inner join t_BOS200000011Entry2 b on a.FID=b.FID" +
                    " left join t_Currency c on c.FCurrencyID=a.FBase3 left join t_Item_3001 d on d.FItemID=a.FBase11 left join t_Emp e on e.FItemID=a.FBase24 left join" +
                    " t_Emp f on f.FItemID=a.FBase13 left join t_Emp g on g.FItemID=a.FBase25 left join t_ICItem h on h.FItemID=a.FBase26 left join" +
                    " t_Emp i on i.FItemID=a.FBase27 left join t_Emp j on f.FItemID=a.FBase16 left join t_ICItem k on k.FItemID=b.FBase1 left join" +
                    " t_Emp l on l.FItemID=b.FBase15 left join t_Department m on m.FItemID=b.FBase18 left join t_Department n on n.FItemID=b.FBase14 left join" +
                    " t_Emp o on o.FItemID=b.FBase10 left join t_Item_3007 p on p.FItemID=b.FBase left join t_Item q on q.FItemID=b.FBase21 left join" +
                    " t_MeasureUnit r on r.FItemID=b.FBase2 left join t_MeasureUnit s on s.FItemID=k.FSecUnitID left join t_Item t on t.FItemID=b.FBase17" +
                    " left join t_Item u on u.FItemID=b.FBase14 where a.FBillNo ='" + mOrderID + "'";
            //            String sql = "select a.FBillNo 单据号,c.FName 币别,a.FAmount4 汇率,d.FName 组织机构,FAmount36 [累计：欠进项发票额！！！],FAmount29 [本单：欠进项发票额！],\n" +
            //                    " a.FInteger [应付帐期天数（进项发票日算）！],e.fname 付款往来,a.FDecimal8 付款量合计,a.FAmount9 付款成本不含税合计,FAmount17 付款含税合计,\n" +
            //                    " f.FName 进项票往来,FDecimal12 进项发票量合计,FAmount16 进项发票含税总额合计,FAmount27 应付款合计,g.FName 入出库往来,h.FName 内容,\n" +
            //                    " FAmount12 入库成本合计,FAmount13 出库成本合计,FAmount37 [累计：已开票未收款额！！],FAmount30 [本单：已开票未收款额！！],\n" +
            //                    " FInteger1 [应收帐期天数（销项发票日算）！！],i.FName 收款往来,FAmount28 应收款合计,j.FName 销项票往来,FDecimal13 销项发票量合计,FAmount19 销项开票含税总额合计,\n" +
            //                    " k.FName [内 容],b.FNOTE2 摘要,FTime2 日期,l.FName 制单人,m.FName 申请部门,n.FName [责任部门/考核],o.FName 表体往来,o.FBankAccount [往来-银行及帐号],\n" +
            //                    " p.FName 计划预算进度,q.FName 预算科目,p.F_109 预算余额,r.FName 计量,b.FDecimal 数量,b.FDecimal1 单价含税,b.FAmount2 金额含税,FAmount 税额,\n" +
            //                    " b.FAmount3 人民币不含税额,b.FAmount10 [税率%],s.FName 辅助,b.FDecimal 辅量,b.FTime3 [发票日-权责制],b.FText2 [备注-发票号码/税票说明],\n" +
            //                    " t.FName 发票税务科目,u.FName 评分,b.FText 发送消息,b.FText1 回馈消息 from t_BOS200000011 a inner join t_BOS200000011Entry2 b on a.FID=b.FID\n" +
            //                    " left join t_Currency c on c.FCurrencyID=a.FBase3 left join t_Item_3001 d on d.FItemID=a.FBase11 left join t_Emp e on e.FItemID=a.FBase24 left join\n" +
            //                    " t_Emp f on f.FItemID=a.FBase13 left join t_Emp g on g.FItemID=a.FBase25 left join t_ICItem h on h.FItemID=a.FBase26 left join \n" +
            //                    " t_Emp i on i.FItemID=a.FBase27 left join t_Emp j on f.FItemID=a.FBase16 left join t_ICItem k on k.FItemID=b.FBase1 left join \n" +
            //                    " t_Emp l on l.FItemID=b.FBase15 left join t_Department m on m.FItemID=b.FBase18 left join t_Department n on n.FItemID=b.FBase14 left join \n" +
            //                    " t_Emp o on o.FItemID=b.FBase10 left join t_Item_3007 p on p.FItemID=b.FBase left join t_Item q on q.FItemID=b.FBase21 left join \n" +
            //                    " t_MeasureUnit r on r.FItemID=b.FBase2 left join t_MeasureUnit s on s.FItemID=k.FSecUnitID left join t_Item t on t.FItemID=b.FBase17\n" +
            //                    " left join t_Item u on u.FItemID=b.FBase14 where a.FBillNo ='" + mOrderID + "'";
            Log.i("主表查询语句", sql);
            rpc.addProperty("FSql", sql);
            rpc.addProperty("FTable", "t_Currency");

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
            Log.i("返回结果", object.getProperty(0).toString() + "=========================");
            String result = object.getProperty(0).toString();
            Document doc = null;

            try {
                doc = DocumentHelper.parseText(result); // 将字符串转为XML

                Element rootElt = doc.getRootElement(); // 获取根节点

                Iterator iter = rootElt.elementIterator("Cust"); // 获取根节点下的子节点head

                // 遍历head节点
                while (iter.hasNext()) {
                    Element recordEle = (Element) iter.next();
                    String fbillNo = recordEle.elementTextTrim("FBillNo");//单据号
                    String currency = recordEle.elementTextTrim("FName");//币种
                    String rate = recordEle.elementTextTrim("FAmount4");//汇率
                    String orga = recordEle.elementTextTrim("FName1");//组织机构
                    String oweInvTotal = recordEle.elementTextTrim("FAmount36");//累计欠进项发票额
                    String thisOweInv = recordEle.elementTextTrim("FAmount29");//本单欠进项发票额
                    String payDate = recordEle.elementTextTrim("FInteger");//应付账期天数(进项发票日算)
                    String payContact = recordEle.elementTextTrim("fname2");//付款往来
                    String payAmount = recordEle.elementTextTrim("FDecimal8");//付款量合计
                    String paynoTax = recordEle.elementTextTrim("FAmount9");//付款成本不含税合计
                    String paywithTax = recordEle.elementTextTrim("FAmount17");//付款含税合计
                    String incomeCont = recordEle.elementTextTrim("FName3");//进项票往来
                    String incomeInv = recordEle.elementTextTrim("FDecimal12");//进项发票量合计
                    String invwithTax = recordEle.elementTextTrim("FAmount16");//进项发票含税总额合计
                    String spayTotal = recordEle.elementTextTrim("FAmount27");//应付款合计
                    String innerIncome = recordEle.elementTextTrim("FName4");//入出库往来
                    String content = recordEle.elementTextTrim("FName5");//内容
                    String innnerCost = recordEle.elementTextTrim("FAmount12");//入库成本合计
                    String outCost = recordEle.elementTextTrim("FAmount13");//出库成本合计
                    String totalUnrece = recordEle.elementTextTrim("FAmount37");//累计：已开票未收款额！！
                    String thisUnrece = recordEle.elementTextTrim("FAmount30");//本单：已开票未收款额！！
                    String shRecceData = recordEle.elementTextTrim("FInteger1");//应收帐期天数（销项发票日算）！！
                    String recIncome = recordEle.elementTextTrim("FName6");//收款往来
                    String shRecTotal = recordEle.elementTextTrim("FAmount28");//应收款合计
                    String outTicIncome = recordEle.elementTextTrim("FName7");//销项票往来
                    String outTickTotal = recordEle.elementTextTrim("FDecimal13");//销项发票量合计
                    String outTickWTax = recordEle.elementTextTrim("FAmount19");//销项开票含税总额合计
                    String content2 = recordEle.elementTextTrim("FName8");//内 容
                    String remark = recordEle.elementTextTrim("FNOTE2");//摘要
                    String data = recordEle.elementTextTrim("FTime2");//日期
                    String singPerson = recordEle.elementTextTrim("FName9");//制单人
                    String applyPart = recordEle.elementTextTrim("FName10");//申请部门
                    String responsPart = recordEle.elementTextTrim("FName11");//责任部门/考核
                    String bodyIncome = recordEle.elementTextTrim("FName12");//表体往来
                    String bankIncome = recordEle.elementTextTrim("FBankAccount");//往来-银行及帐号
                    String planBudget = recordEle.elementTextTrim("FName13");//计划预算进度
                    String budSub = recordEle.elementTextTrim("FName14");//预算科目
                    String budBalance = recordEle.elementTextTrim("F_109");//预算余额
                    String unit = recordEle.elementTextTrim("FName15");//计量
                    String number = recordEle.elementTextTrim("FDecimal");//数量
                    String unitPrice = recordEle.elementTextTrim("FDecimal1");//单价含税
                    String moneyTax = recordEle.elementTextTrim("FAmount2");//金额含税
                    String taxAmount = recordEle.elementTextTrim("FAmount");//税额
                    String RMBNoTax = recordEle.elementTextTrim("FAmount3");//人民币不含税额
                    String TaxRate = recordEle.elementTextTrim("FAmount10");//税率%
                    String unitOther = recordEle.elementTextTrim("FName16");//辅助
                    String unitNum = recordEle.elementTextTrim("FDecimal2");//辅量
                    String ticDataRespon = recordEle.elementTextTrim("FTime3");//发票日-权责制
                    String remarkTicNO = recordEle.elementTextTrim("FText2");//备注-发票号码/税票说明
                    String ticTaxSub = recordEle.elementTextTrim("FName17");//发票税务科目
                    String score = recordEle.elementTextTrim("FName18");//评分
                    String sendMsg = recordEle.elementTextTrim("FText");//发送消息
                    String getMsg = recordEle.elementTextTrim("FText1");//回馈消息
                }
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.showToast(getContext(), "查找出错");
                getActivity().finish();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
        }
    }
}
