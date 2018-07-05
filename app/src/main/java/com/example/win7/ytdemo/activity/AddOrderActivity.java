package com.example.win7.ytdemo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.win7.ytdemo.R;
import com.example.win7.ytdemo.adapter.MyPagerAdapter;
import com.example.win7.ytdemo.entity.OrderDataInfo;
import com.example.win7.ytdemo.fragment.AllOrderFragment;
import com.example.win7.ytdemo.fragment.ApplyFragment;
import com.example.win7.ytdemo.util.Consts;
import com.example.win7.ytdemo.util.ToastUtils;
import com.example.win7.ytdemo.view.CustomProgress;
import com.example.win7.ytdemo.view.MyFixedViewpager;

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
 * @创建时间 2018/6/28 15:19
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class AddOrderActivity extends BaseActivity implements View.OnClickListener {
    private ImageView        img_back;
    private TextView         tv_title;
    private TabLayout        mTablayout;//导航标签
    private MyFixedViewpager mView_pager;//自我viewpager可实现禁止滑动
    private String[] mStrings  = {"主表", "子表"};
    private boolean  mEditable = false;
    private CustomProgress dialog;
    private String orderID = "";//订单表id
    private OrderDataInfo orderInfo;//订单表详情封装
    private String        kind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        Intent intent = getIntent();
        kind = intent.getStringExtra("kind");
        if (null == kind) {
            ToastUtils.showToast(AddOrderActivity.this, "error:未传递种类");
            finish();
            return;
        }
        if (kind.equals("check")) {//查看
            mEditable = false;
        }
        if (kind.equals("edit")) {//修改
            mEditable = true;
        }
        if (kind.equals("add")) {//新增
            mEditable = true;
        }
        orderID = intent.getStringExtra("orderID");
        initView();
        initData();
        initListener();
    }

    private void initView() {
        img_back = findViewById(R.id.img_back);
        tv_title = findViewById(R.id.tv_title);
        mTablayout = findViewById(R.id.tablayout);
        mView_pager = findViewById(R.id.view_pager);
    }

    private void initData() {
        String title = getIntent().getStringExtra("title");
        tv_title.setText(title);
        if (kind.equals("check") || kind.equals("edit")) {
            orderInfo = new OrderDataInfo();
            //获取订单表详情
            new ZHUTask(orderID).execute();
        }
        if (kind.equals("add")) {
            initTabFragment();
        }
    }

    private void initListener() {
        img_back.setOnClickListener(this);
    }

    private void initTabFragment() {
        // 创建一个集合,装填Fragment
        ArrayList<Fragment> fragments = new ArrayList<>();
        // 装填
        //汇总界面
        AllOrderFragment allOrderFragment = new AllOrderFragment();
        fragments.add(allOrderFragment);
        //申请界面
        ApplyFragment applyFragment = new ApplyFragment();
        fragments.add(applyFragment);

        // 创建ViewPager适配器
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        myPagerAdapter.setFragments(fragments);
        // 给ViewPager设置适配器
        mView_pager.setAdapter(myPagerAdapter);
        //设置viewpager不可滑动
        mView_pager.setCanScroll(false);
        //tablayout关联tablayout和viewpager实现联动
        mTablayout.setupWithViewPager(mView_pager);
        for (int i = 0; i < mStrings.length; i++) {
            mTablayout.getTabAt(i).setText(mStrings[i]);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                if (mEditable) {
                    new AlertDialog.Builder(AddOrderActivity.this)
                            .setTitle("确认退出？").setNegativeButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).setPositiveButton("取消", null).show();
                } else {
                    finish();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mEditable) {
                new AlertDialog.Builder(AddOrderActivity.this)
                        .setTitle("确认退出？").setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setPositiveButton("取消", null).show();
            } else {
                finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean getEditable() {
        return mEditable;
    }

    public OrderDataInfo getOrderInfo() {
        return orderInfo;
    }

    class ZHUTask extends AsyncTask<Void, String, String> {
        String mOrderID;

        ZHUTask(String orderID) {
            this.mOrderID = orderID;
        }

        @Override
        protected void onPreExecute() {
            dialog = CustomProgress.show(AddOrderActivity.this, "加载中...", true, null);
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
                    String taxRate = recordEle.elementTextTrim("FAmount10");//税率%
                    String unitOther = recordEle.elementTextTrim("FName16");//辅助
                    String unitNum = recordEle.elementTextTrim("FDecimal2");//辅量
                    String ticDataRespon = recordEle.elementTextTrim("FTime3");//发票日-权责制
                    String remarkTicNO = recordEle.elementTextTrim("FText2");//备注-发票号码/税票说明
                    String ticTaxSub = recordEle.elementTextTrim("FName17");//发票税务科目
                    String score = recordEle.elementTextTrim("FName18");//评分
                    String sendMsg = recordEle.elementTextTrim("FText");//发送消息
                    String getMsg = recordEle.elementTextTrim("FText1");//回馈消息

                    orderInfo.setFbillNo(fbillNo);
                    orderInfo.setCurrency(currency);
                    orderInfo.setRate(rate);
                    orderInfo.setOrga(orga);
                    orderInfo.setOweInvTotal(oweInvTotal);
                    orderInfo.setThisOweInv(thisOweInv);
                    orderInfo.setPayDate(payDate);
                    orderInfo.setPayContact(payContact);
                    orderInfo.setPayAmount(payAmount);
                    orderInfo.setPaynoTax(paynoTax);
                    orderInfo.setPaywithTax(paywithTax);
                    orderInfo.setIncomeCont(incomeCont);
                    orderInfo.setIncomeInv(incomeInv);
                    orderInfo.setInvwithTax(invwithTax);
                    orderInfo.setSpayTotal(spayTotal);
                    orderInfo.setInnerIncome(innerIncome);
                    orderInfo.setContent(content);
                    orderInfo.setInnnerCost(innnerCost);
                    orderInfo.setOutCost(outCost);
                    orderInfo.setTotalUnrece(totalUnrece);
                    orderInfo.setThisUnrece(thisUnrece);
                    orderInfo.setShRecceData(shRecceData);
                    orderInfo.setRecIncome(recIncome);
                    orderInfo.setShRecTotal(shRecTotal);
                    orderInfo.setOutTicIncome(outTicIncome);
                    orderInfo.setOutTickTotal(outTickTotal);
                    orderInfo.setOutTickWTax(outTickWTax);
                    List<OrderDataInfo.ListsonBean> listson = new ArrayList<>();
                    OrderDataInfo.ListsonBean bean = new OrderDataInfo.ListsonBean();
                    bean.setContentX(content2);
                    bean.setDate(data);
                    bean.setSinPerson(singPerson);
                    bean.setApplyPartX(applyPart);
                    bean.setResponsPartX(responsPart);
                    bean.setBodyIncomeX(bodyIncome);
                    bean.setBankIncomeX(bankIncome);
                    bean.setPlanBudgetX(planBudget);
                    bean.setBudSubX(budSub);
                    bean.setBudBalanceX(budBalance);
                    bean.setUnitX(unit);
                    bean.setNumberX(number);
                    bean.setUnitPriceX(unitPrice);
                    bean.setMoneyTaxX(moneyTax);
                    bean.setTaxAmountX(taxAmount);
                    bean.setRMBNoTaxX(RMBNoTax);
                    bean.setTaxRateX(taxRate);
                    bean.setUnitOtherX(unitOther);
                    bean.setUnitNumX(unitNum);
                    bean.setRemarkTicNOX(remarkTicNO);
                    bean.setTicTaxSubX(ticTaxSub);
                    listson.add(bean);
                    orderInfo.setListson(listson);
                    orderInfo.setContent2(content2);
                    orderInfo.setRemark(remark);
                    orderInfo.setData(data);
                    orderInfo.setSingPerson(singPerson);
                    orderInfo.setApplyPart(applyPart);
                    orderInfo.setResponsPart(responsPart);
                    orderInfo.setBodyIncome(bodyIncome);
                    orderInfo.setBankIncome(bankIncome);
                    orderInfo.setPlanBudget(planBudget);
                    orderInfo.setBudSub(budSub);
                    orderInfo.setBudBalance(budBalance);
                    orderInfo.setUnit(unit);
                    orderInfo.setNumber(number);
                    orderInfo.setUnitPrice(unitPrice);
                    orderInfo.setMoneyTax(moneyTax);
                    orderInfo.setTaxAmount(taxAmount);
                    orderInfo.setRMBNoTax(RMBNoTax);
                    orderInfo.setTaxRate(taxRate);
                    orderInfo.setUnitOther(unitOther);
                    orderInfo.setUnitNum(unitNum);
                    orderInfo.setTicDataRespon(ticDataRespon);//
                    orderInfo.setRemarkTicNO(remarkTicNO);
                    orderInfo.setTicTaxSub(ticTaxSub);
                    orderInfo.setScore(score);//
                    orderInfo.setSendMsg(sendMsg);//
                    orderInfo.setGetMsg(getMsg);//
                }
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.showToast(AddOrderActivity.this, "查找详情出错");
                finish();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            //初始化导航页
            initTabFragment();
        }
    }
}
