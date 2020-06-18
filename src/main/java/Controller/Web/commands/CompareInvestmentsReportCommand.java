package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Model.Charts.BarChart;
import View.builders.charts.InvestmentsBarChartBuilder;
import Model.Reports.InvestmentsReport;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;

import java.util.HashMap;
import java.util.Map;

import static Controller.Web.controllers.LoginController.ensureUserIsLoggedIn;
import static Controller.Web.webutils.RequestQueryHandler.generateInvestmentsReport;

public class CompareInvestmentsReportCommand extends FrontCommand {

    private InvestmentsReport firstInvestmentsReport;
    private BarChart firstInvestmentsReportChart;
    private InvestmentsReport secondInvestmentsReport;
    private BarChart secondInvestmentsReportChart;

    @Override
    public String execute() {
        ensureUserIsLoggedIn(request, response);
        generateReports();
        generateBarCharts();
        return ViewUtil.render(request, model(), Path.Template.COMPARE_INVESTMENTS_REPORT);
    }

    private void generateReports() {
        firstInvestmentsReport = generateInvestmentsReport(request, "");
        secondInvestmentsReport = generateInvestmentsReport(request, "1");
    }

    private void generateBarCharts() {
        firstInvestmentsReportChart = new InvestmentsBarChartBuilder().build(firstInvestmentsReport);
        secondInvestmentsReportChart = new InvestmentsBarChartBuilder().build(secondInvestmentsReport);
    }


    private Map model() {
        HashMap<String, Object> model = new HashMap<>();
        fillModel(model);
        addToolsToModel(model);
        return model;
    }

    private void fillModel(HashMap<String, Object> model) {
        model.put("report1", firstInvestmentsReport);
        model.put("barChart1", firstInvestmentsReportChart);
        model.put("report2", secondInvestmentsReport);
        model.put("barChart2", secondInvestmentsReportChart);
    }


    private void addToolsToModel(Map model) {
        model.put("math", new MathTool());
        model.put("number", new NumberTool());
    }

}
