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

import static Controller.Web.webutils.RequestQueryHandler.generateReducedProfitAndLossesReport;

public class ShowProfitAndLossesReportCommand extends FrontCommand {

    private ProfitAndLossesReport profitAndLossesReport;
    private BarChart profitAndLossesReportBarChart;

    @Override
    public String execute() throws ParseException {
        LoginController.ensureUserIsLoggedIn(request, response);
        profitAndLossesReport = generateReducedProfitAndLossesReport(request);
        profitAndLossesReportBarChart = new ProfitAndLossesBarChartBuilder().build(profitAndLossesReport);
        return ViewUtil.render(request, model(), Path.Template.PROFITANDLOSSES_REPORT);
    }

    private Map model() {
        HashMap<String, Object> model = new HashMap<>();
        fillModel(model);
        addToolsToModel(model);
        return model;
    }

    private void addToolsToModel(HashMap<String, Object> model) {
        model.put("math", new MathTool());
        model.put("number", new NumberTool());
    }

    private void fillModel(HashMap<String, Object> model) {
        model.put("report", profitAndLossesReport);
        model.put("barChart", profitAndLossesReportBarChart);
    }
}
