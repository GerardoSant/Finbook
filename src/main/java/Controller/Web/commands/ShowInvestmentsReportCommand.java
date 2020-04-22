package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Model.Charts.BarChart;
import Controller.builders.charts.InvestmentsBarChartBuilder;
import Model.Reports.InvestmentsReport;
import Controller.Web.controllers.LoginController;
import org.apache.velocity.tools.generic.NumberTool;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;

import java.util.HashMap;
import java.util.Map;

import static Controller.Web.webutils.RequestQueryHandler.generateInvestmentsReport;

public class ShowInvestmentsReportCommand extends FrontCommand {

    private InvestmentsReport investmentsReport;
    private BarChart investmentsReportBarChart;

    @Override
    public String execute() {
        LoginController.ensureUserIsLoggedIn(request,response);
        investmentsReport = generateInvestmentsReport(request);
        investmentsReportBarChart = new InvestmentsBarChartBuilder().build(investmentsReport);
        return ViewUtil.render(request,model(), Path.Template.INVESTMENT_REPORT);
    }

    private Map model() {
        HashMap<String,Object> model = new HashMap<>();
        fillModel(model);
        addToolsToModel(model);
        return model;
    }

    private void fillModel(HashMap<String, Object> model) {
        model.put("report",investmentsReport);
        model.put("barChart", investmentsReportBarChart);
    }

    private void addToolsToModel(HashMap<String, Object> model) {
        model.put("number", new NumberTool());
    }



}
