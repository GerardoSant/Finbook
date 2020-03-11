package AS;

import Bills.Bill;
import Bills.BillsDao;
import Reports.BarChart;
import Reports.InvestmentReportBuilder;
import Reports.InvestmentsBarChartBuilder;
import Reports.InvestmentsReport;
import login.LoginController;
import spark.Request;
import util.DateParser;
import util.Path;
import util.ViewUtil;

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
