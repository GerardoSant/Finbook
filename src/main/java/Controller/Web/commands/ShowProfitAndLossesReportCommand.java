package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.Web.login.LoginController;
import Controller.builders.charts.ProfitAndLossesBarChartBuilder;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;
import Model.Charts.BarChart;
import Model.Reports.ProfitAndLossesReport;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import java.text.ParseException;
import java.util.HashMap;
import static Controller.Web.webutils.RequestQueryHandler.generateReducedProfitAndLossesReport;

public class ShowProfitAndLossesReportCommand extends FrontCommand {

    @Override
    public String process() throws ParseException {
        LoginController.ensureUserIsLoggedIn(request, response);
        HashMap<String, Object> model = new HashMap<>();
        ProfitAndLossesReport report = generateReducedProfitAndLossesReport(request);
        BarChart barChart = new ProfitAndLossesBarChartBuilder().build(report);
        model.put("report", report);
        model.put("barChart", barChart);
        model.put("math", new MathTool());
        model.put("number", new NumberTool());
        return ViewUtil.render(request, model, Path.Template.PROFITANDLOSSES_REPORT);
    }
}
