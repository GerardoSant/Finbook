package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Model.Bills.Bill;
import View.daos.BillsDao;
import Model.Charts.BarChart;
import Controller.builders.reports.InvestmentReportBuilder;
import Controller.builders.charts.InvestmentsBarChartBuilder;
import Model.Reports.InvestmentsReport;
import Controller.Web.login.LoginController;
import org.apache.velocity.tools.generic.NumberTool;
import spark.Request;
import Controller.util.DateParser;
import Controller.util.Path;
import Controller.util.ViewUtil;

import java.util.HashMap;
import java.util.List;

import static Controller.util.RequestQueryHandler.generateInvestmentsReport;
import static Controller.util.RequestUtil.getSessionUser;

public class ShowInvestmentsReportCommand extends FrontCommand {
    @Override
    public String process() {
        LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        InvestmentsReport report = generateInvestmentsReport(request);
        BarChart barChart = new InvestmentsBarChartBuilder().build(report);
        model.put("report",report);
        model.put("barChart", barChart);
        model.put("number", new NumberTool());
        return ViewUtil.render(request,model, Path.Template.INVESTMENT_REPORT);
    }


}
