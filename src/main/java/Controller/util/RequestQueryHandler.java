package Controller.util;

import Controller.builders.reports.AmortizationReportBuilder;
import Controller.builders.reports.ProfitAndLossesReportBuilder;
import Model.Bills.Bill;
import Model.Bills.BillsDistribution;
import Model.Bills.Top5Sales;
import Model.Reports.AmortizationReport;
import Model.Reports.ProfitAndLossesReport;
import View.daos.BillsDao;
import spark.Request;

import java.text.ParseException;
import java.util.List;

public class RequestQueryHandler {

    public static ProfitAndLossesReport generateProfitAndLossesReport(Request request) throws ParseException {
        List<Bill> billList =new BillsDao(request.session().attribute("currentUser")).getAllBills();
        return !thereIsPeriodQuery(request) ?
                new ProfitAndLossesReportBuilder(billList,request.session().attribute("currentUser")).buildReport() :
                new ProfitAndLossesReportBuilder(new BillsDao(request.session().attribute("currentUser")).getAllBills(),request.session().attribute("currentUser"),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"))).buildReport();
    }

    public static AmortizationReport generateAmortizationReport(Request request) throws ParseException {
        List<Bill> billList =new BillsDao(request.session().attribute("currentUser")).getAllBills();
        return !thereIsPeriodQuery(request) ?
                new AmortizationReportBuilder(billList,request.session().attribute("currentUser")).buildReport() :
                new AmortizationReportBuilder(billList, request.session().attribute("currentUser"),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"))).buildReport();
    }

    public static BillsDistribution generateBillsDistribution(Request request) throws ParseException {
        List<Bill> billList =new BillsDao(request.session().attribute("currentUser")).getAllBills();
        return !thereIsPeriodQuery(request) ?
                new BillsDistribution(billList,request.session().attribute("currentUser")) :
                new BillsDistribution(BillFilter.filterByPeriod(billList, new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")), new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"))),request.session().attribute("currentUser"));
    }

    public static Top5Sales generateTop5Sales(Request request) throws ParseException {
        List<Bill> billList =new BillsDao(request.session().attribute("currentUser")).getAllBills();
        return !thereIsPeriodQuery(request) ?
                new Top5Sales(new BillsDao(request.session().attribute("currentUser")).getAllBills(), request.session().attribute("currentUser")) :
                new Top5Sales(BillFilter.filterByPeriod(billList, new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")), new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"))),request.session().attribute("currentUser"));
    }

    private static boolean thereIsPeriodQuery(Request request) {
        return request.queryParams("periodStart") != null;
    }
}
