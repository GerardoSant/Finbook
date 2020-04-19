package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Model.Charts.BarChart;
import Controller.builders.charts.InvestmentsBarChartBuilder;
import Model.Reports.InvestmentsReport;
import Controller.Web.login.LoginController;
import org.apache.velocity.tools.generic.NumberTool;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;

import java.util.HashMap;

import static Controller.Web.webutils.RequestQueryHandler.generateInvestmentsReport;

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
