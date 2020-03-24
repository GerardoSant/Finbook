package AS;

import Model.Bills.Bill;
import View.daos.BillsDao;
import Model.Charts.BarChart;
import Controller.builders.reports.InvestmentReportBuilder;
import Controller.builders.charts.InvestmentsBarChartBuilder;
import Model.Reports.InvestmentsReport;
import Controller.Web.login.LoginController;
import org.apache.velocity.tools.generic.MathTool;
import spark.Request;
import Controller.util.DateParser;
import Controller.util.Path;
import Controller.util.ViewUtil;

import java.util.HashMap;
import java.util.List;

public class CompareInvestmentsReportCommand extends FrontCommand {

    @Override
    public String process() {
        LoginController.ensureUserIsLoggedIn(request, response);
        HashMap<String, Object> model = new HashMap<>();
        InvestmentsReport report1 = generateInvestmentsReport(request,"");
        BarChart barChart = new InvestmentsBarChartBuilder().build(report1);
        model.put("report1", report1);
        model.put("barChart1", barChart);
        InvestmentsReport report2 = generateInvestmentsReport(request,"1");
        BarChart barChart2 = new InvestmentsBarChartBuilder().build(report2);
        model.put("report2", report2);
        model.put("barChart2", barChart2);
        model.put("math", new MathTool());
        return ViewUtil.render(request, model, Path.Template.COMPARE_INVESTMENTS_REPORT);
    }

    private static InvestmentsReport generateInvestmentsReport(Request request, String report) {
        try{
            List<Bill> billList =new BillsDao(request.session().attribute("currentUser")).getAllBills();
            return request.queryParams("periodStart")== null ?
                    new InvestmentReportBuilder(billList,request.session().attribute("currentUser")).buildReport():
                    new InvestmentReportBuilder(new BillsDao(request.session().attribute("currentUser")).getAllBills(),request.session().attribute("currentUser"),
                            new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart"+report)),
                            new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"+report))
                    ).buildReport();
        } catch(Exception e){
            return null;
        }

    }


}
