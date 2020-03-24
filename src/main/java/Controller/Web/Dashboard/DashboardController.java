package Controller.Web.Dashboard;

import View.daos.BillsDao;
import Controller.builders.charts.AmortizationBarChartBuilder;
import Controller.builders.charts.ProfitAndLossesBarChartBuilder;
import Controller.builders.charts.BillsDistributionBarChartBuilder;
import Controller.builders.charts.Top5SalesBarChartBuilder;
import Controller.builders.reports.AmortizationReportBuilder;
import Controller.builders.reports.ProfitAndLossesReportBuilder;
import Model.Bills.Bill;
import Model.Bills.BillsDistribution;
import Model.Bills.Top5Sales;
import Model.Charts.BarChart;
import Model.Reports.AmortizationReport;
import Model.Reports.ProfitAndLossesReport;
import Controller.Web.login.LoginController;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.NumberTool;
import spark.Request;
import spark.Route;
import Controller.util.DateParser;
import Controller.util.Path;
import Controller.util.ViewUtil;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardController {

    public static Route serveDashboard  = (request, response) -> {
        LoginController.ensureUserIsLoggedIn(request,response);
        Map<String, Object> model = new HashMap<>();
        model.put("number", new NumberTool());
        ProfitAndLossesReport report = generateProfitAndLossesReport(request);
        model.put("profitreport",report);
        BarChart profitAndLossesBarChart = new ProfitAndLossesBarChartBuilder().build(report);
        model.put("profitReportBarChart", profitAndLossesBarChart);
        AmortizationReport amortizationReport = generateAmortizationReport(request);
        BarChart amortizationBarChart= new AmortizationBarChartBuilder().build(amortizationReport);
        model.put("amortizationReportBarChart",amortizationBarChart);
        BillsDistribution billsDistribution = new BillsDistribution(new BillsDao(request.session().attribute("currentUser")).getAllBills(),request.session().attribute("currentUser"));
        model.put("billsDistribution",billsDistribution);
        BarChart billsDistributionBarChart = new BillsDistributionBarChartBuilder().build(billsDistribution);
        model.put("billsDistributionBarChart",billsDistributionBarChart);
        Top5Sales top5sales = new Top5Sales(new BillsDao(request.session().attribute("currentUser")).getAllBills(), request.session().attribute("currentUser"));
        model.put("top5sales",top5sales);
        model.put("date", new DateTool());
        BarChart top5SalesBarChart = new Top5SalesBarChartBuilder().build(top5sales);
        model.put("top5salesBarChart", top5SalesBarChart);
        return ViewUtil.render(request, model, Path.Template.DASHBOARD);
    };

    private static ProfitAndLossesReport generateProfitAndLossesReport(Request request) throws ParseException {
        List<Bill> billList =new BillsDao(request.session().attribute("currentUser")).getAllBills();
        return request.queryParams("periodStart")== null ?
                new ProfitAndLossesReportBuilder(billList,request.session().attribute("currentUser")).buildReport() :
                new ProfitAndLossesReportBuilder(new BillsDao(request.session().attribute("currentUser")).getAllBills(),request.session().attribute("currentUser"),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"))).buildReport();
    }

    private static AmortizationReport generateAmortizationReport(Request request) throws ParseException {
        List<Bill> billList =new BillsDao(request.session().attribute("currentUser")).getAllBills();
        return request.queryParams("periodStart")== null ?
                new AmortizationReportBuilder(billList,request.session().attribute("currentUser")).buildReport() :
                new AmortizationReportBuilder(billList, request.session().attribute("currentUser"), new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"))).buildReport();
    }

}
