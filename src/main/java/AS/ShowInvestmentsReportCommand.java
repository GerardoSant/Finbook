package AS;

import Model.Bills.Bill;
import View.daos.BillsDao;
import Model.Charts.BarChart;
import Controller.builders.reports.InvestmentReportBuilder;
import Controller.builders.charts.InvestmentsBarChartBuilder;
import Model.Reports.InvestmentsReport;
import Controller.Web.login.LoginController;
import org.apache.velocity.tools.generic.NumberTool;
import spark.Request;
import Controller.util.DateParser;
import Controller.util.Path;
import Controller.util.ViewUtil;

import java.util.HashMap;
import java.util.List;

public class ShowInvestmentsReportCommand extends FrontCommand {
    @Override
    public String process() {
        LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        InvestmentsReport report = generateInvestmentsReport(request);
        BarChart barChart = new InvestmentsBarChartBuilder().build(report);
        model.put("report",report);
        model.put("barChart", barChart);
        model.put("number", new NumberTool());
        return ViewUtil.render(request,model, Path.Template.INVESTMENT_REPORT);
    }

    private static InvestmentsReport generateInvestmentsReport(Request request) {
        try{
            List<Bill> billList =new BillsDao(request.session().attribute("currentUser")).getAllBills();
            return request.queryParams("periodStart")== null ?
                    new InvestmentReportBuilder(billList,request.session().attribute("currentUser")).buildReport():
                    new InvestmentReportBuilder(new BillsDao(request.session().attribute("currentUser")).getAllBills(),request.session().attribute("currentUser"),
                            new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")),
                            new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"))
                    ).buildReport();
        } catch(Exception e){
            return null;
        }
    }
}
