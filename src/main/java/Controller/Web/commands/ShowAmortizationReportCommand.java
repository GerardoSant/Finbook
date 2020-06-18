package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.Web.controllers.LoginController;
import View.builders.charts.AmortizationBarChartBuilder;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;
import Model.Charts.BarChart;
import Model.Reports.AmortizationReport;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static Controller.Web.webutils.RequestQueryHandler.generateAmortizationReport;

public class ShowAmortizationReportCommand extends FrontCommand {

    AmortizationReport amortizationReport;
    BarChart amortizationReportBarChart;

    @Override
    public String execute() throws ParseException {
        LoginController.ensureUserIsLoggedIn(request,response);
        amortizationReport= generateAmortizationReport(request);
        amortizationReportBarChart= new AmortizationBarChartBuilder().build(amortizationReport);
        return ViewUtil.render(request,model(), Path.Template.AMORTIZATION_REPORT);
    }

    private Map model() {
        HashMap<String, Object> model=new HashMap<>();
        fillModel(model);
        addToolsToModel(model);
        return model;
    }

    private void fillModel(HashMap<String, Object> model) {
        model.put("report",amortizationReport);
        model.put("barChart",amortizationReportBarChart);
    }

    private void addToolsToModel(HashMap<String, Object> model) {
        model.put("date", new DateTool());
        model.put("math", new MathTool());
        model.put("number", new NumberTool());
    }


}
