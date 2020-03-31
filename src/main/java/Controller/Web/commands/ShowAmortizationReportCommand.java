package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.Web.login.LoginController;
import Controller.builders.charts.AmortizationBarChartBuilder;
import Controller.builders.reports.AmortizationReportBuilder;
import Controller.util.DateParser;
import Controller.util.Path;
import Controller.util.ViewUtil;
import Model.Bills.Bill;
import Model.Charts.BarChart;
import Model.Reports.AmortizationReport;
import View.daos.BillsDao;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import spark.Request;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

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

    private static AmortizationReport generateAmortizationReport(Request request) throws ParseException {
        List<Bill> billList =new BillsDao(request.session().attribute("currentUser")).getAllBills();
        return request.queryParams("periodStart")== null ?
                new AmortizationReportBuilder(billList,request.session().attribute("currentUser")).buildReport() :
                new AmortizationReportBuilder(billList, request.session().attribute("currentUser"), new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"))).buildReport();
    }
}
