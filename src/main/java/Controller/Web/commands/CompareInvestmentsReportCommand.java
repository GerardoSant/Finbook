package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Model.Charts.BarChart;
import Controller.builders.charts.InvestmentsBarChartBuilder;
import Model.Reports.InvestmentsReport;
import Controller.Web.controllers.LoginController;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;

import java.util.HashMap;

import static Controller.Web.webutils.RequestQueryHandler.generateInvestmentsReport;

public class CompareInvestmentsReportCommand extends FrontCommand {

    @Override
    public String process() {
        LoginController.ensureUserIsLoggedIn(request, response);
        HashMap<String, Object> model = new HashMap<>();
        InvestmentsReport report1 = generateInvestmentsReport(request,"");
        BarChart barChart = new InvestmentsBarChartBuilder().build(report1);
        model.put("report1", report1);
        model.put("barChart1", barChart);
        InvestmentsReport report2 = generateInvestmentsReport(request,"1");
        BarChart barChart2 = new InvestmentsBarChartBuilder().build(report2);
        model.put("report2", report2);
        model.put("barChart2", barChart2);
        model.put("math", new MathTool());
        model.put("number", new NumberTool());
        return ViewUtil.render(request, model, Path.Template.COMPARE_INVESTMENTS_REPORT);
    }




}
