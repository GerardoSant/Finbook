package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.util.BillFilter;
import Controller.util.DateParser;
import Controller.util.RequestUtil;
import Model.Bills.Bill;
import com.google.gson.Gson;
import java.util.List;


public class FilterBillsCommand extends FrontCommand {
    @Override
    public String process() {
        List<Bill> billList = request.session().attribute("billList");
        billList = applyFilters(billList);
        request.session().attribute("currentBillList",billList);
        try {
            return new Gson().toJson(billList.subList(0,30));
        } catch(IndexOutOfBoundsException e){
            return new Gson().toJson(billList.subList(0,billList.size()));
        }
    }

    private List<Bill> applyFilters(List<Bill> billList) {
        if(getParam("receiver")!=null){
            billList=filterByReceiverName(billList);
        } else{
            billList=filterByIssuerName(billList);
        }

        billList=filterByTotal(billList);
        billList= filterByDate(billList);
        billList= filterByPC(billList);
        billList= filterByBillType(billList);
        return billList;
    }

    private List<Bill> filterByIssuerName(List<Bill> billList) {
        return !paramIsEmpty("issuer") ? BillFilter.filterByIssuer(billList, getParam("issuer")) : billList;
    }


    private List<Bill> filterByReceiverName(List<Bill> billList) {
        return !paramIsEmpty("receiver") ? BillFilter.filterByReceiver(billList, getParam("receiver")) : billList;
    }

    private List<Bill> filterByTotal(List<Bill> billList) {
        if(!paramIsEmpty("minTotal")) billList=BillFilter.filterByMinTotal(billList, Integer.parseInt(getParam("minTotal")));
        if(!paramIsEmpty("maxTotal")) billList=BillFilter.filterByMaxTotal(billList, Integer.parseInt(getParam("maxTotal")));
        return billList;
    };

    private List<Bill> filterByDate(List<Bill> billList) {
        System.out.println(getParam("periodStart"));
        System.out.println(getParam("periodEnd"));
        try{
            if(!paramIsEmpty("periodStart")) billList=BillFilter.filterByPeriodStart(billList, new DateParser("yyyy-MM-dd").parseDate(getParam("periodStart")));
            if(!paramIsEmpty("periodEnd")) billList=BillFilter.filterByPeriodEnd(billList, new DateParser("yyyy-MM-dd").parseDate(getParam("periodEnd")));
        } catch(Exception e){}
        return billList;
    }

    private List<Bill> filterByPC(List<Bill> billList){
        return !paramIsEmpty("PC") ? BillFilter.filterByPC(billList, getParam("PC")) : billList;
    }

    private List<Bill> filterByBillType(List<Bill> billList) {
        if (getParam("billType").equals("income")) return BillFilter.filterBySales(billList,"E-5756930");
        if (getParam("billType").equals("egress")) return BillFilter.filterByReturns(billList,"E-5756930");
        if (getParam("billType").equals("payroll")) return BillFilter.filterBySalaries(billList,"E-5756930");
        if (getParam("billType").equals("purchases")) return BillFilter.filterByPurchases(billList,"E-5756930");
        if (getParam("billType").equals("investments")) return BillFilter.filterByInvestments(billList,"E-5756930");
        if (getParam("billType").equals("services")) return BillFilter.filterByExternalServices(billList,"E-5756930");
        return billList;
    }


    private String getParam(String param) {
        return RequestUtil.getParam(request,param);
    }

    private boolean paramIsEmpty(String param) {
        return RequestUtil.paramIsEmpty(request,param);
    }


}
