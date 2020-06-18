package Controller.Web.webutils;

import View.builders.other.BillTimelineBuilder;
import View.builders.reports.AmortizationReportBuilder;
import View.builders.reports.InvestmentReportBuilder;
import View.builders.reports.ProfitAndLossesReportBuilder;
import Controller.util.bill.BillFilter;
import Controller.util.date.DateParser;
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

    public static final String DATE_INTL_STD_FORMAT = "yyyy-MM-dd";
    public static final String PERIOD_START_QUERY_PARAM = "periodStart";
    public static final String PERIOD_END_QUERY_PARAM = "periodEnd";

    public static ProfitAndLossesReport generateProfitAndLossesReport(Request request) throws ParseException {
        User user = getSessionUser(request);
        List<Bill> billList =new BillsDao(user.getCompanyRFC()).getAllBills();
        return !thereIsPeriodQuery(request) ?
                new ProfitAndLossesReportBuilder(billList,user.getCompanyRFC()).buildReport() :
                new ProfitAndLossesReportBuilder(new BillsDao(user.getCompanyRFC()).getAllBills(),user.getCompanyRFC(),
                        new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams(PERIOD_START_QUERY_PARAM)),
                        new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams(PERIOD_END_QUERY_PARAM))).buildReport();
    }
    public static ProfitAndLossesReport generateReducedProfitAndLossesReport(Request request) throws ParseException {
        User user = getSessionUser(request);
        List<Bill> billList =new BillsDao(user.getCompanyRFC()).getAllBills();
        return !thereIsPeriodQuery(request) ?
                new ProfitAndLossesReportBuilder(billList,user.getCompanyRFC()).buildReducedReport() :
                new ProfitAndLossesReportBuilder(new BillsDao(user.getCompanyRFC()).getAllBills(),user.getCompanyRFC(),
                        new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams(PERIOD_START_QUERY_PARAM)),
                        new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams(PERIOD_END_QUERY_PARAM))).buildReducedReport();
    }

    public static AmortizationReport generateAmortizationReport(Request request) throws ParseException {
        User user = getSessionUser(request);
        List<Bill> billList =new BillsDao(user.getCompanyRFC()).getAllBills();
        return !thereIsPeriodQuery(request) ?
                new AmortizationReportBuilder(billList,user.getCompanyRFC()).buildReport() :
                new AmortizationReportBuilder(billList, user.getCompanyRFC(),
                        new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams(PERIOD_START_QUERY_PARAM)),
                        new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams(PERIOD_END_QUERY_PARAM))).buildReport();
    }

    public static BillsDistribution generateBillsDistribution(Request request) throws ParseException {
        User user = getSessionUser(request);
        List<Bill> billList =new BillsDao(user.getCompanyRFC()).getAllBills();
        return !thereIsPeriodQuery(request) ?
                new BillsDistribution(billList,user.getCompanyRFC()) :
                new BillsDistribution(BillFilter.filterByPeriod(billList, new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams(PERIOD_START_QUERY_PARAM)), new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams(PERIOD_END_QUERY_PARAM))),user.getCompanyRFC());
    }

    public static Top5Sales generateTop5Sales(Request request) throws ParseException {
        User user = getSessionUser(request);
        List<Bill> billList =new BillsDao(user.getCompanyRFC()).getAllBills();
        return !thereIsPeriodQuery(request) ?
                new Top5Sales(new BillsDao(user.getCompanyRFC()).getAllBills(), user.getCompanyRFC()) :
                new Top5Sales(BillFilter.filterByPeriod(billList, new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams(PERIOD_START_QUERY_PARAM)), new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams(PERIOD_END_QUERY_PARAM))),user.getCompanyRFC());
    }

    public static InvestmentsReport generateInvestmentsReport(Request request, String report) {
        try{
            User user = getSessionUser(request);
            List<Bill> billList =new BillsDao(user.getCompanyRFC()).getAllBills();
            return request.queryParams(PERIOD_START_QUERY_PARAM)== null ?
                    new InvestmentReportBuilder(billList,user.getCompanyRFC()).buildReport():
                    new InvestmentReportBuilder(new BillsDao(user.getCompanyRFC()).getAllBills(),user.getCompanyRFC(),
                            new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams(PERIOD_START_QUERY_PARAM+report)),
                            new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams(PERIOD_END_QUERY_PARAM+report))
                    ).buildReport();
        } catch(Exception e){
            return null;
        }

    }

    public static ProfitAndLossesReport generateProfitAndLossesReport(Request request, String report) throws ParseException {
        User user = getSessionUser(request);
        List<Bill> billList =new BillsDao(user.getCompanyRFC()).getAllBills();
        return request.queryParams(PERIOD_START_QUERY_PARAM)== null ?
                new ProfitAndLossesReportBuilder(billList,user.getCompanyRFC()).buildReport() :
                new ProfitAndLossesReportBuilder(new BillsDao(user.getCompanyRFC()).getAllBills(),user.getCompanyRFC(),
                        new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams(PERIOD_START_QUERY_PARAM+report)),
                        new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams(PERIOD_END_QUERY_PARAM+report))).buildReport();
    }

    private static boolean thereIsPeriodQuery(Request request) {
        return request.queryParams(PERIOD_START_QUERY_PARAM) != null;
    }

    public static BillTimeline generateBillTimeline(Request request) {
        try{
            List<Bill> billList = new BillsDao(getSessionUser(request).getCompanyRFC()).getAllBills();
            if (request.queryParams("min") == null && request.queryParams(PERIOD_START_QUERY_PARAM) == null) {
                return new BillTimelineBuilder().build(getSessionUser(request).getCompanyRFC(), billList, queryParamIsTrue(request,"ascendent"), true, true, true, true);
            }
            if (request.queryParams("min") != null && !request.queryParams("min").isEmpty()) {
                if (!request.queryParams(PERIOD_START_QUERY_PARAM).isEmpty()) {
                    return new BillTimelineBuilder().build(getSessionUser(request).getCompanyRFC(), billList, new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams(PERIOD_START_QUERY_PARAM)),
                            new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams(PERIOD_END_QUERY_PARAM)), queryParamIsTrue(request,"ascendent"),
                            parseDouble(request.queryParams("min")), parseDouble(request.queryParams("max")), queryParamIsTrue(request, "incomes"),
                            queryParamIsTrue(request, "expenses"), queryParamIsTrue(request, "investments"), queryParamIsTrue(request, "salaries"));
                } else {
                    return new BillTimelineBuilder().build(getSessionUser(request).getCompanyRFC(), billList, queryParamIsTrue(request,"ascendent"), parseDouble(request.queryParams("min")),
                            parseDouble(request.queryParams("max")), queryParamIsTrue(request, "incomes"),
                            queryParamIsTrue(request, "expenses"),
                            queryParamIsTrue(request, "investments"), queryParamIsTrue(request, "salaries"));
                }
            } else {
                if (request.queryParams(PERIOD_START_QUERY_PARAM) != null && !request.queryParams(PERIOD_START_QUERY_PARAM).isEmpty()) {
                    return new BillTimelineBuilder().build(getSessionUser(request).getCompanyRFC(), billList, new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams(PERIOD_START_QUERY_PARAM)), new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams("periodEnd")), queryParamIsTrue(request,"ascendent"), queryParamIsTrue(request, "incomes"), queryParamIsTrue(request, "expenses"), queryParamIsTrue(request, "investments"), queryParamIsTrue(request, "salaries"));
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
            return request.queryParams(PERIOD_START_QUERY_PARAM)== null ?
                    new InvestmentReportBuilder(billList,getSessionUser(request).getCompanyRFC()).buildReport():
                    new InvestmentReportBuilder(new BillsDao(getSessionUser(request).getCompanyRFC()).getAllBills(),getSessionUser(request).getCompanyRFC(),
                            new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams(PERIOD_START_QUERY_PARAM)),
                            new DateParser(DATE_INTL_STD_FORMAT).parseDate(request.queryParams(PERIOD_END_QUERY_PARAM))
                    ).buildReport();
        } catch(Exception e){
            return null;
        }
    }
}
