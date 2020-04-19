package Controller.Web.webutils;

import Controller.builders.other.BillTimelineBuilder;
import Controller.builders.reports.AmortizationReportBuilder;
import Controller.builders.reports.InvestmentReportBuilder;
import Controller.builders.reports.ProfitAndLossesReportBuilder;
import Controller.util.BillFilter;
import Controller.util.DateParser;
import Model.Bills.Bill;
import Model.Bills.BillTimeline;
import Model.Bills.BillsDistribution;
import Model.Bills.Top5Sales;
import Model.Reports.AmortizationReport;
import Model.Reports.InvestmentsReport;
import Model.Reports.ProfitAndLossesReport;
import Model.User.User;
import View.daos.BillsDao;
import spark.Request;

import java.text.ParseException;
import java.util.List;

import static Controller.Web.webutils.RequestUtil.*;
import static Controller.Web.webutils.RequestUtil.queryParamIsTrue;
import static java.lang.Double.parseDouble;

public class RequestQueryHandler {

    public static ProfitAndLossesReport generateProfitAndLossesReport(Request request) throws ParseException {
        User user = getSessionUser(request);
        List<Bill> billList =new BillsDao(user.getCompanyRFC()).getAllBills();
        return !thereIsPeriodQuery(request) ?
                new ProfitAndLossesReportBuilder(billList,user.getCompanyRFC()).buildReport() :
                new ProfitAndLossesReportBuilder(new BillsDao(user.getCompanyRFC()).getAllBills(),user.getCompanyRFC(),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"))).buildReport();
    }
    public static ProfitAndLossesReport generateReducedProfitAndLossesReport(Request request) throws ParseException {
        User user = getSessionUser(request);
        List<Bill> billList =new BillsDao(user.getCompanyRFC()).getAllBills();
        return !thereIsPeriodQuery(request) ?
                new ProfitAndLossesReportBuilder(billList,user.getCompanyRFC()).buildReducedReport() :
                new ProfitAndLossesReportBuilder(new BillsDao(user.getCompanyRFC()).getAllBills(),user.getCompanyRFC(),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"))).buildReducedReport();
    }

    public static AmortizationReport generateAmortizationReport(Request request) throws ParseException {
        User user = getSessionUser(request);
        List<Bill> billList =new BillsDao(user.getCompanyRFC()).getAllBills();
        return !thereIsPeriodQuery(request) ?
                new AmortizationReportBuilder(billList,user.getCompanyRFC()).buildReport() :
                new AmortizationReportBuilder(billList, user.getCompanyRFC(),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"))).buildReport();
    }

    public static BillsDistribution generateBillsDistribution(Request request) throws ParseException {
        User user = getSessionUser(request);
        List<Bill> billList =new BillsDao(user.getCompanyRFC()).getAllBills();
        return !thereIsPeriodQuery(request) ?
                new BillsDistribution(billList,user.getCompanyRFC()) :
                new BillsDistribution(BillFilter.filterByPeriod(billList, new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")), new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"))),user.getCompanyRFC());
    }

    public static Top5Sales generateTop5Sales(Request request) throws ParseException {
        User user = getSessionUser(request);
        List<Bill> billList =new BillsDao(user.getCompanyRFC()).getAllBills();
        return !thereIsPeriodQuery(request) ?
                new Top5Sales(new BillsDao(user.getCompanyRFC()).getAllBills(), user.getCompanyRFC()) :
                new Top5Sales(BillFilter.filterByPeriod(billList, new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")), new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"))),user.getCompanyRFC());
    }

    public static InvestmentsReport generateInvestmentsReport(Request request, String report) {
        try{
            User user = getSessionUser(request);
            List<Bill> billList =new BillsDao(user.getCompanyRFC()).getAllBills();
            return request.queryParams("periodStart")== null ?
                    new InvestmentReportBuilder(billList,user.getCompanyRFC()).buildReport():
                    new InvestmentReportBuilder(new BillsDao(user.getCompanyRFC()).getAllBills(),user.getCompanyRFC(),
                            new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart"+report)),
                            new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"+report))
                    ).buildReport();
        } catch(Exception e){
            return null;
        }

    }

    public static ProfitAndLossesReport generateProfitAndLossesReport(Request request, String report) throws ParseException {
        User user = getSessionUser(request);
        List<Bill> billList =new BillsDao(user.getCompanyRFC()).getAllBills();
        return request.queryParams("periodStart")== null ?
                new ProfitAndLossesReportBuilder(billList,user.getCompanyRFC()).buildReport() :
                new ProfitAndLossesReportBuilder(new BillsDao(user.getCompanyRFC()).getAllBills(),user.getCompanyRFC(),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart"+report)),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"+report))).buildReport();
    }

    private static boolean thereIsPeriodQuery(Request request) {
        return request.queryParams("periodStart") != null;
    }

    public static BillTimeline generateBillTimeline(Request request) {
        try{
            List<Bill> billList = new BillsDao(getSessionUser(request).getCompanyRFC()).getAllBills();
            if (request.queryParams("min") == null && request.queryParams("periodStart") == null) {
                return new BillTimelineBuilder().build(getSessionUser(request).getCompanyRFC(), billList, queryParamIsTrue(request,"ascendent"), true, true, true, true);
            }
            if (request.queryParams("min") != null && !request.queryParams("min").isEmpty()) {
                if (!request.queryParams("periodStart").isEmpty()) {
                    return new BillTimelineBuilder().build(getSessionUser(request).getCompanyRFC(), billList, new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")), new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd")), queryParamIsTrue(request,"ascendent"), parseDouble(request.queryParams("min")), parseDouble(request.queryParams("max")), queryParamIsTrue(request, "incomes"), queryParamIsTrue(request, "expenses"), queryParamIsTrue(request, "investments"), queryParamIsTrue(request, "salaries"));
                } else {
                    return new BillTimelineBuilder().build(getSessionUser(request).getCompanyRFC(), billList, queryParamIsTrue(request,"ascendent"), parseDouble(request.queryParams("min")), parseDouble(request.queryParams("max")), queryParamIsTrue(request, "incomes"), queryParamIsTrue(request, "expenses"), queryParamIsTrue(request, "investments"), queryParamIsTrue(request, "salaries"));
                }
            } else {
                if (request.queryParams("periodStart") != null && !request.queryParams("periodStart").isEmpty()) {
                    return new BillTimelineBuilder().build(getSessionUser(request).getCompanyRFC(), billList, new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")), new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd")), queryParamIsTrue(request,"ascendent"), queryParamIsTrue(request, "incomes"), queryParamIsTrue(request, "expenses"), queryParamIsTrue(request, "investments"), queryParamIsTrue(request, "salaries"));
                } else {
                    return new BillTimelineBuilder().build(getSessionUser(request).getCompanyRFC(), billList, queryParamIsTrue(request,"ascendent"), queryParamIsTrue(request, "incomes"), queryParamIsTrue(request, "expenses"), queryParamIsTrue(request, "investments"), queryParamIsTrue(request, "salaries"));
                }
            }
        } catch (Exception e){
            return null;
        }
    }

    public static InvestmentsReport generateInvestmentsReport(Request request) {
        try{
            List<Bill> billList =new BillsDao(getSessionUser(request).getCompanyRFC()).getAllBills();
            return request.queryParams("periodStart")== null ?
                    new InvestmentReportBuilder(billList,getSessionUser(request).getCompanyRFC()).buildReport():
                    new InvestmentReportBuilder(new BillsDao(getSessionUser(request).getCompanyRFC()).getAllBills(),getSessionUser(request).getCompanyRFC(),
                            new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")),
                            new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"))
                    ).buildReport();
        } catch(Exception e){
            return null;
        }
    }
}
