package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.Web.controllers.LoginController;
import Controller.builders.charts.AmortizationBarChartBuilder;
import Controller.builders.charts.BillsDistributionBarChartBuilder;
import Controller.builders.charts.ProfitAndLossesBarChartBuilder;
import Controller.builders.charts.Top5SalesBarChartBuilder;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;
import Model.Bills.BillsDistribution;
import Model.Bills.Top5Sales;
import Model.Charts.BarChart;
import Model.Reports.AmortizationReport;
import Model.Reports.ProfitAndLossesReport;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.NumberTool;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static Controller.Web.webutils.RequestQueryHandler.*;
import static Controller.Web.webutils.RequestQueryHandler.generateTop5Sales;

public class ShowDashboardCommand extends FrontCommand {
    @Override
    public String execute() throws ParseException {
        LoginController.ensureUserIsLoggedIn(request,response);
        Map<String, Object> model = new HashMap<>();
        model.put("number", new NumberTool());
        ProfitAndLossesReport report = generateProfitAndLossesReport(request);
        model.put("profitAndLossesReport",report);
        BarChart profitAndLossesBarChart = new ProfitAndLossesBarChartBuilder().build(report);
        model.put("profitReportBarChart", profitAndLossesBarChart);
        AmortizationReport amortizationReport = generateAmortizationReport(request);
        BarChart amortizationBarChart= new AmortizationBarChartBuilder().build(amortizationReport);
        model.put("amortizationReportBarChart",amortizationBarChart);
        BillsDistribution billsDistribution = generateBillsDistribution(request);
        model.put("billsDistribution",billsDistribution);
        BarChart billsDistributionBarChart = new BillsDistributionBarChartBuilder().build(billsDistribution);
        model.put("billsDistributionBarChart",billsDistributionBarChart);
        Top5Sales top5sales = generateTop5Sales(request);
        model.put("top5sales",top5sales);
        model.put("date", new DateTool());
        BarChart top5SalesBarChart = new Top5SalesBarChartBuilder().build(top5sales);
        model.put("top5salesBarChart", top5SalesBarChart);
        return ViewUtil.render(request, model, Path.Template.DASHBOARD);
    }
}
