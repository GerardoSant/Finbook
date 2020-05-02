package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.util.bill.BillFilter;
import Controller.util.bill.BillOrdenator;
import Controller.util.date.DateParser;
import Controller.Web.webutils.RequestUtil;
import Model.Bills.Bill;
import com.google.gson.Gson;
import java.util.List;

import static Controller.Web.webutils.RequestUtil.getSessionUser;
import static java.lang.Boolean.parseBoolean;


public class FilterBillsCommand extends FrontCommand {
    @Override
    public String execute() {
        List<Bill> billList = applyFilters(getSessionBillList());
        saveBillListInSession(billList);
        return billListAsJson(billList);
    }

    private String billListAsJson(List<Bill> billList) {
        try {
            return new Gson().toJson(billList.subList(0,30));
        } catch(IndexOutOfBoundsException e){
            return new Gson().toJson(billList.subList(0,billList.size()));
        }
    }

    private void saveBillListInSession(List<Bill> billList) {
        request.session().attribute("currentBillList",billList);
    }

    private List<Bill> getSessionBillList() {
        return request.session().attribute("billList");
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
        billList = applyOrder(billList);
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
        if (getParam("billType").equals("income")) return BillFilter.filterBySales(billList,getSessionUser(request).getCompanyRFC());
        if (getParam("billType").equals("egress")) return BillFilter.filterByReturns(billList,getSessionUser(request).getCompanyRFC());
        if (getParam("billType").equals("payroll")) return BillFilter.filterBySalaries(billList,getSessionUser(request).getCompanyRFC());
        if (getParam("billType").equals("purchases")) return BillFilter.filterByPurchases(billList,getSessionUser(request).getCompanyRFC());
        if (getParam("billType").equals("investments")) return BillFilter.filterByInvestments(billList,getSessionUser(request).getCompanyRFC());
        if (getParam("billType").equals("services")) return BillFilter.filterByExternalServices(billList,getSessionUser(request).getCompanyRFC());
        return billList;
    }

    private List<Bill> applyOrder(List<Bill> billList) {
        if (getParam("orderBy").equals("date")) return BillOrdenator.orderByDate(billList, parseBoolean(getParam("orderDescending")));
        if (getParam("orderBy").equals("pc")) return BillOrdenator.orderByPC(billList, parseBoolean(getParam("orderDescending")));
        if (getParam("orderBy").equals("billType")) return BillOrdenator.orderByType(billList, parseBoolean(getParam("orderDescending")));
        if (getParam("orderBy").equals("receiver")) return BillOrdenator.orderByReceiver(billList, parseBoolean(getParam("orderDescending")));
        if (getParam("orderBy").equals("issuer")) return BillOrdenator.orderByIssuer(billList, parseBoolean(getParam("orderDescending")));
        if (getParam("orderBy").equals("total")) return BillOrdenator.orderByTotal(billList, parseBoolean(getParam("orderDescending")));
        if (getParam("orderBy").equals("currency")) return BillOrdenator.orderByCurrency(billList, parseBoolean(getParam("orderDescending")));
        return billList;
    }


    private String getParam(String param) {
        return RequestUtil.getParam(request,param);
    }

    private boolean paramIsEmpty(String param) {
        return RequestUtil.paramIsEmpty(request,param);
    }


}
