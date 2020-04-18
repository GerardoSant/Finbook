package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.Web.login.LoginController;
import Controller.builders.charts.ProfitAndLossesBarChartBuilder;
import Controller.builders.reports.ProfitAndLossesReportBuilder;
import Controller.util.DateParser;
import Controller.util.Path;
import Controller.util.ViewUtil;
import Model.Bills.Bill;
import Model.Charts.BarChart;
import Model.Reports.ProfitAndLossesReport;
import View.daos.BillsDao;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import spark.Request;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import static Controller.util.RequestQueryHandler.generateProfitAndLossesReport;

public class CompareProfitAndLossesCommand extends FrontCommand {
    @Override
    public String process() throws ParseException {
        LoginController.ensureUserIsLoggedIn(request, response);
        HashMap<String, Object> model = new HashMap<>();
        ProfitAndLossesReport report1 = generateProfitAndLossesReport(request,"");
        BarChart barChart = new ProfitAndLossesBarChartBuilder().build(report1);
        model.put("report1", report1);
        model.put("barChart1", barChart);
        ProfitAndLossesReport report2 = generateProfitAndLossesReport(request,"1");
        BarChart barChart2 = new ProfitAndLossesBarChartBuilder().build(report2);
        model.put("report2", report2);
        model.put("barChart2", barChart2);
        model.put("math", new MathTool());
        model.put("number", new NumberTool());
        return ViewUtil.render(request, model, Path.Template.COMPARE_PROFITANDLOSSES_REPORT);
    }


}
