package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.Web.controllers.LoginController;
import Controller.builders.charts.ProfitAndLossesBarChartBuilder;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;
import Model.Charts.BarChart;
import Model.Reports.ProfitAndLossesReport;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static Controller.Web.controllers.LoginController.ensureUserIsLoggedIn;
import static Controller.Web.webutils.RequestQueryHandler.generateProfitAndLossesReport;

public class CompareProfitAndLossesCommand extends FrontCommand {

    private ProfitAndLossesReport firstProfitAndLossesReport;
    private BarChart firstProfitAndLossesReportChart;
    private ProfitAndLossesReport secondProfitAndLossesReport;
    private BarChart secondProfitAndLossesReportChart;

    @Override
    public String execute() throws ParseException {
        ensureUserIsLoggedIn(request, response);
        generateReports();
        generateBarCharts();
        return ViewUtil.render(request, model(), Path.Template.COMPARE_PROFITANDLOSSES_REPORT);
    }


    private void generateReports() throws ParseException {
        firstProfitAndLossesReport= generateProfitAndLossesReport(request,"");
        secondProfitAndLossesReport= generateProfitAndLossesReport(request,"1");
    }

    private void generateBarCharts() {
        firstProfitAndLossesReportChart= new ProfitAndLossesBarChartBuilder().build(firstProfitAndLossesReport);
        secondProfitAndLossesReportChart= new ProfitAndLossesBarChartBuilder().build(secondProfitAndLossesReport);
    }

    private Map model() {
        HashMap<String, Object> model = new HashMap<>();
        fillModel(model);
        addToolsToModel(model);
        return model;
    }

    private void fillModel(HashMap<String, Object> model) {
        model.put("report1", firstProfitAndLossesReport);
        model.put("barChart1", firstProfitAndLossesReportChart);
        model.put("report2", secondProfitAndLossesReport);
        model.put("barChart2", secondProfitAndLossesReportChart);
    }

    private void addToolsToModel(Map model) {
        model.put("math", new MathTool());
        model.put("number", new NumberTool());
    }


}
