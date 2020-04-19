package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.Web.controllers.LoginController;
import Controller.builders.charts.AmortizationBarChartBuilder;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;
import Model.Charts.BarChart;
import Model.Reports.AmortizationReport;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;

import java.text.ParseException;
import java.util.HashMap;

import static Controller.Web.webutils.RequestQueryHandler.generateAmortizationReport;

public class ShowAmortizationReportCommand extends FrontCommand {

    @Override
    public String process() throws ParseException {
        LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        AmortizationReport report = generateAmortizationReport(request);
        BarChart barChart = new AmortizationBarChartBuilder().build(report);
        model.put("date", new DateTool());
        model.put("math", new MathTool());
        model.put("report",report);
        model.put("barChart",barChart);
        model.put("number", new NumberTool());
        return ViewUtil.render(request,model, Path.Template.AMORTIZATION_REPORT);
    }


}
