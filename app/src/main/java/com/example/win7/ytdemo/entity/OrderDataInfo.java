package com.example.win7.ytdemo.entity;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/7/5 8:35
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class OrderDataInfo {

    /**
     * fbillNo : 单据号
     * currency : 币种
     * rate : 汇率
     * orga : 组织机构
     * oweInvTotal : 累计欠进项发票额
     * thisOweInv : 本单欠进项发票额
     * payDate : 应付账期天数
     * payContact : 付款往来
     * payAmount : 付款量合计
     * paynoTax : 付款成本不含税合计
     * paywithTax : 付款含税合计
     * incomeCont : 进项票往来
     * incomeInv : 进项发票量合计
     * invwithTax : 进项发票含税总额合计
     * spayTotal : 应付款合计
     * innerIncome : 入出库往来
     * content : 内容
     * innnerCost : 入库成本合计
     * outCost : 出库成本合计
     * totalUnrece : 累计：已开票未收款额！！
     * thisUnrece : 本单：已开票未收款额！！
     * shRecceData : 应收帐期天数（销项发日算）！！
     * recIncome : 收款往来
     * shRecTotal : 应收款合计
     * outTicIncome : 销项票往来
     * outTickTotal : 销项发票量合计
     * outTickWTax : 销项开票含税总额合计
     * content2 : 内 容
     * remark : 摘要
     * data : 日期
     * singPerson : 制单人
     * applyPart : 申请部门
     * responsPart : 责任部门/考核
     * bodyIncome : 表体往来
     * bankIncome : 往来-银行及帐号
     * planBudget : 计划预算进度
     * budSub : 预算科目
     * budBalance : 预算余额
     * unit : 计量
     * number : 数量
     * unitPrice : 单价含税
     * moneyTax : 金额含税
     * taxAmount : 税额
     * RMBNoTax : 人民币不含税额
     * taxRate : 税率
     * unitOther : 辅助
     * unitNum : 辅量
     * ticDataRespon : 发票日-权责制
     * remarkTicNO : 备注-发票号码/税票说明
     * ticTaxSub : 发票税务科目
     * score : 评分
     * sendMsg : 发送消息
     * getMsg : 回馈消息
     */

    private String            fbillNo;
    private String            currency;
    private String            rate;
    private String            orga;
    private String            oweInvTotal;
    private String            thisOweInv;
    private String            payDate;
    private String            payContact;
    private String            payAmount;
    private String            paynoTax;
    private String            paywithTax;
    private String            incomeCont;
    private String            incomeInv;
    private String            invwithTax;
    private String            spayTotal;
    private String            innerIncome;
    private String            content;
    private String            innnerCost;
    private String            outCost;
    private String            totalUnrece;
    private String            thisUnrece;
    private String            shRecceData;
    private String            recIncome;
    private String            shRecTotal;
    private String            outTicIncome;
    private String            outTickTotal;
    private String            outTickWTax;
    private String            content2;
    private String            remark;
    private String            data;
    private String            singPerson;
    private String            applyPart;
    private String            responsPart;
    private String            bodyIncome;
    private String            bankIncome;
    private String            planBudget;
    private String            budSub;
    private String            budBalance;
    private String            unit;
    private String            number;
    private String            unitPrice;
    private String            moneyTax;
    private String            taxAmount;
    private String            RMBNoTax;
    private String            taxRate;
    private String            unitOther;
    private String            unitNum;
    private String            ticDataRespon;
    private String            remarkTicNO;
    private String            ticTaxSub;
    private String            score;
    private String            sendMsg;
    private String            getMsg;
    private List<ListsonBean> listson;

    public String getFbillNo() {
        return fbillNo;
    }

    public void setFbillNo(String fbillNo) {
        this.fbillNo = fbillNo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getOrga() {
        return orga;
    }

    public void setOrga(String orga) {
        this.orga = orga;
    }

    public String getOweInvTotal() {
        return oweInvTotal;
    }

    public void setOweInvTotal(String oweInvTotal) {
        this.oweInvTotal = oweInvTotal;
    }

    public String getThisOweInv() {
        return thisOweInv;
    }

    public void setThisOweInv(String thisOweInv) {
        this.thisOweInv = thisOweInv;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayContact() {
        return payContact;
    }

    public void setPayContact(String payContact) {
        this.payContact = payContact;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getPaynoTax() {
        return paynoTax;
    }

    public void setPaynoTax(String paynoTax) {
        this.paynoTax = paynoTax;
    }

    public String getPaywithTax() {
        return paywithTax;
    }

    public void setPaywithTax(String paywithTax) {
        this.paywithTax = paywithTax;
    }

    public String getIncomeCont() {
        return incomeCont;
    }

    public void setIncomeCont(String incomeCont) {
        this.incomeCont = incomeCont;
    }

    public String getIncomeInv() {
        return incomeInv;
    }

    public void setIncomeInv(String incomeInv) {
        this.incomeInv = incomeInv;
    }

    public String getInvwithTax() {
        return invwithTax;
    }

    public void setInvwithTax(String invwithTax) {
        this.invwithTax = invwithTax;
    }

    public String getSpayTotal() {
        return spayTotal;
    }

    public void setSpayTotal(String spayTotal) {
        this.spayTotal = spayTotal;
    }

    public String getInnerIncome() {
        return innerIncome;
    }

    public void setInnerIncome(String innerIncome) {
        this.innerIncome = innerIncome;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getInnnerCost() {
        return innnerCost;
    }

    public void setInnnerCost(String innnerCost) {
        this.innnerCost = innnerCost;
    }

    public String getOutCost() {
        return outCost;
    }

    public void setOutCost(String outCost) {
        this.outCost = outCost;
    }

    public String getTotalUnrece() {
        return totalUnrece;
    }

    public void setTotalUnrece(String totalUnrece) {
        this.totalUnrece = totalUnrece;
    }

    public String getThisUnrece() {
        return thisUnrece;
    }

    public void setThisUnrece(String thisUnrece) {
        this.thisUnrece = thisUnrece;
    }

    public String getShRecceData() {
        return shRecceData;
    }

    public void setShRecceData(String shRecceData) {
        this.shRecceData = shRecceData;
    }

    public String getRecIncome() {
        return recIncome;
    }

    public void setRecIncome(String recIncome) {
        this.recIncome = recIncome;
    }

    public String getShRecTotal() {
        return shRecTotal;
    }

    public void setShRecTotal(String shRecTotal) {
        this.shRecTotal = shRecTotal;
    }

    public String getOutTicIncome() {
        return outTicIncome;
    }

    public void setOutTicIncome(String outTicIncome) {
        this.outTicIncome = outTicIncome;
    }

    public String getOutTickTotal() {
        return outTickTotal;
    }

    public void setOutTickTotal(String outTickTotal) {
        this.outTickTotal = outTickTotal;
    }

    public String getOutTickWTax() {
        return outTickWTax;
    }

    public void setOutTickWTax(String outTickWTax) {
        this.outTickWTax = outTickWTax;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSingPerson() {
        return singPerson;
    }

    public void setSingPerson(String singPerson) {
        this.singPerson = singPerson;
    }

    public String getApplyPart() {
        return applyPart;
    }

    public void setApplyPart(String applyPart) {
        this.applyPart = applyPart;
    }

    public String getResponsPart() {
        return responsPart;
    }

    public void setResponsPart(String responsPart) {
        this.responsPart = responsPart;
    }

    public String getBodyIncome() {
        return bodyIncome;
    }

    public void setBodyIncome(String bodyIncome) {
        this.bodyIncome = bodyIncome;
    }

    public String getBankIncome() {
        return bankIncome;
    }

    public void setBankIncome(String bankIncome) {
        this.bankIncome = bankIncome;
    }

    public String getPlanBudget() {
        return planBudget;
    }

    public void setPlanBudget(String planBudget) {
        this.planBudget = planBudget;
    }

    public String getBudSub() {
        return budSub;
    }

    public void setBudSub(String budSub) {
        this.budSub = budSub;
    }

    public String getBudBalance() {
        return budBalance;
    }

    public void setBudBalance(String budBalance) {
        this.budBalance = budBalance;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getMoneyTax() {
        return moneyTax;
    }

    public void setMoneyTax(String moneyTax) {
        this.moneyTax = moneyTax;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getRMBNoTax() {
        return RMBNoTax;
    }

    public void setRMBNoTax(String RMBNoTax) {
        this.RMBNoTax = RMBNoTax;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getUnitOther() {
        return unitOther;
    }

    public void setUnitOther(String unitOther) {
        this.unitOther = unitOther;
    }

    public String getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(String unitNum) {
        this.unitNum = unitNum;
    }

    public String getTicDataRespon() {
        return ticDataRespon;
    }

    public void setTicDataRespon(String ticDataRespon) {
        this.ticDataRespon = ticDataRespon;
    }

    public String getRemarkTicNO() {
        return remarkTicNO;
    }

    public void setRemarkTicNO(String remarkTicNO) {
        this.remarkTicNO = remarkTicNO;
    }

    public String getTicTaxSub() {
        return ticTaxSub;
    }

    public void setTicTaxSub(String ticTaxSub) {
        this.ticTaxSub = ticTaxSub;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSendMsg() {
        return sendMsg;
    }

    public void setSendMsg(String sendMsg) {
        this.sendMsg = sendMsg;
    }

    public String getGetMsg() {
        return getMsg;
    }

    public void setGetMsg(String getMsg) {
        this.getMsg = getMsg;
    }

    public List<ListsonBean> getListson() {
        return listson;
    }

    public void setListson(List<ListsonBean> listson) {
        this.listson = listson;
    }

    public static class ListsonBean {
        /**
         * content : 内容
         * date : 日期
         * sinPerson : 制单人
         * applyPart : 申请部门
         * responsPart : 责任部门
         * bodyIncome : 表体往来
         * bankIncome : 往来-银行及帐号
         * planBudget : 计划预算进度
         * budSub : 预算科目
         * budBalance : 预算余额
         * unit : 计量
         * number : 数量
         * unitPrice : 单价含税
         * moneyTax : 金额含税
         * taxAmount : 税额
         * RMBNoTax : 人民币不含税额
         * taxRate : 税率
         * unitOther : 辅助
         * unitNum : 辅量
         * remarkTicNO : 备注
         * ticTaxSub : 发票税务科目
         */

        //        @SerializedName("content")
        private String contentX;
        private String date;
        private String sinPerson;
        //        @SerializedName("applyPart")
        private String applyPartX;
        //        @SerializedName("responsPart")
        private String responsPartX;
        //        @SerializedName("bodyIncome")
        private String bodyIncomeX;
        //        @SerializedName("bankIncome")
        private String bankIncomeX;
        //        @SerializedName("planBudget")
        private String planBudgetX;
        //        @SerializedName("budSub")
        private String budSubX;
        //        @SerializedName("budBalance")
        private String budBalanceX;
        //        @SerializedName("unit")
        private String unitX;
        //        @SerializedName("number")
        private String numberX;
        //        @SerializedName("unitPrice")
        private String unitPriceX;
        //        @SerializedName("moneyTax")
        private String moneyTaxX;
        //        @SerializedName("taxAmount")
        private String taxAmountX;
        //        @SerializedName("RMBNoTax")
        private String RMBNoTaxX;
        //        @SerializedName("taxRate")
        private String taxRateX;
        //        @SerializedName("unitOther")
        private String unitOtherX;
        //        @SerializedName("unitNum")
        private String unitNumX;
        //        @SerializedName("remarkTicNO")
        private String remarkTicNOX;
        //        @SerializedName("ticTaxSub")
        private String ticTaxSubX;

        public String getContentX() {
            return contentX;
        }

        public void setContentX(String contentX) {
            this.contentX = contentX;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSinPerson() {
            return sinPerson;
        }

        public void setSinPerson(String sinPerson) {
            this.sinPerson = sinPerson;
        }

        public String getApplyPartX() {
            return applyPartX;
        }

        public void setApplyPartX(String applyPartX) {
            this.applyPartX = applyPartX;
        }

        public String getResponsPartX() {
            return responsPartX;
        }

        public void setResponsPartX(String responsPartX) {
            this.responsPartX = responsPartX;
        }

        public String getBodyIncomeX() {
            return bodyIncomeX;
        }

        public void setBodyIncomeX(String bodyIncomeX) {
            this.bodyIncomeX = bodyIncomeX;
        }

        public String getBankIncomeX() {
            return bankIncomeX;
        }

        public void setBankIncomeX(String bankIncomeX) {
            this.bankIncomeX = bankIncomeX;
        }

        public String getPlanBudgetX() {
            return planBudgetX;
        }

        public void setPlanBudgetX(String planBudgetX) {
            this.planBudgetX = planBudgetX;
        }

        public String getBudSubX() {
            return budSubX;
        }

        public void setBudSubX(String budSubX) {
            this.budSubX = budSubX;
        }

        public String getBudBalanceX() {
            return budBalanceX;
        }

        public void setBudBalanceX(String budBalanceX) {
            this.budBalanceX = budBalanceX;
        }

        public String getUnitX() {
            return unitX;
        }

        public void setUnitX(String unitX) {
            this.unitX = unitX;
        }

        public String getNumberX() {
            return numberX;
        }

        public void setNumberX(String numberX) {
            this.numberX = numberX;
        }

        public String getUnitPriceX() {
            return unitPriceX;
        }

        public void setUnitPriceX(String unitPriceX) {
            this.unitPriceX = unitPriceX;
        }

        public String getMoneyTaxX() {
            return moneyTaxX;
        }

        public void setMoneyTaxX(String moneyTaxX) {
            this.moneyTaxX = moneyTaxX;
        }

        public String getTaxAmountX() {
            return taxAmountX;
        }

        public void setTaxAmountX(String taxAmountX) {
            this.taxAmountX = taxAmountX;
        }

        public String getRMBNoTaxX() {
            return RMBNoTaxX;
        }

        public void setRMBNoTaxX(String RMBNoTaxX) {
            this.RMBNoTaxX = RMBNoTaxX;
        }

        public String getTaxRateX() {
            return taxRateX;
        }

        public void setTaxRateX(String taxRateX) {
            this.taxRateX = taxRateX;
        }

        public String getUnitOtherX() {
            return unitOtherX;
        }

        public void setUnitOtherX(String unitOtherX) {
            this.unitOtherX = unitOtherX;
        }

        public String getUnitNumX() {
            return unitNumX;
        }

        public void setUnitNumX(String unitNumX) {
            this.unitNumX = unitNumX;
        }

        public String getRemarkTicNOX() {
            return remarkTicNOX;
        }

        public void setRemarkTicNOX(String remarkTicNOX) {
            this.remarkTicNOX = remarkTicNOX;
        }

        public String getTicTaxSubX() {
            return ticTaxSubX;
        }

        public void setTicTaxSubX(String ticTaxSubX) {
            this.ticTaxSubX = ticTaxSubX;
        }
    }
}
