package Reports;

import Bills.Bill;
import Bills.BillsDao;
import login.LoginController;
import org.apache.velocity.tools.generic.MathTool;
import spark.Request;
import spark.Response;
import spark.Route;
import util.DateParser;
import util.Path;
import util.PeriodFinder;
import util.ViewUtil;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;


public class ReportController {

    public static Route investmentReport = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        InvestmentsReport report = generateInvestmentsReport(request);
        model.put("report",report);
        return ViewUtil.render(request,model, Path.Template.INVESTMENT_REPORT);
    };
    public static Route profitAndLossesReport = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        HashMap<String, Object> model = new HashMap<>();
        ProfitAndLossesReport report = generateWinAndLossesReport(request);
        model.put("report", report);
        model.put("math", new MathTool());
        return ViewUtil.render(request, model, Path.Template.PROFITANDLOSSES_REPORT);
    };

    public static Route compareProfitAndLossesReport= (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        HashMap<String, Object> model = new HashMap<>();
        ProfitAndLossesReport report1 = generateWinAndLossesReport(request,"");
        model.put("report1", report1);
        ProfitAndLossesReport report2 = generateWinAndLossesReport(request,"1");
        model.put("report2", report2);
        model.put("math", new MathTool());
        return ViewUtil.render(request, model, Path.Template.COMPARE_PROFITANDLOSSES_REPORT);
    };

    private static ProfitAndLossesReport generateWinAndLossesReport(Request request, String report) throws ParseException {
        List<Bill> billList =new BillsDao(request.session().attribute("currentUser")).getAllBills();
        return request.queryParams("periodStart")== null ?
                new ProfitAndLossesReport(billList,request.session().attribute("currentUser"),
                        new PeriodFinder(billList).findPeriodStart(), new PeriodFinder(billList).findPeriodEnd()) :
                new ProfitAndLossesReport(new BillsDao(request.session().attribute("currentUser")).getAllBills(),request.session().attribute("currentUser"),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart"+report)),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"+report))
                );
    }




    private static ProfitAndLossesReport generateWinAndLossesReport(Request request) throws ParseException {
        List<Bill> billList =new BillsDao(request.session().attribute("currentUser")).getAllBills();
        return request.queryParams("periodStart")== null ?
                new ProfitAndLossesReport(billList,request.session().attribute("currentUser"),
                        new PeriodFinder(billList).findPeriodStart(), new PeriodFinder(billList).findPeriodEnd()) :
                new ProfitAndLossesReport(new BillsDao(request.session().attribute("currentUser")).getAllBills(),request.session().attribute("currentUser"),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"))
                );
    }

    private static InvestmentsReport generateInvestmentsReport(Request request) throws ParseException {
        List<Bill> billList =new BillsDao(request.session().attribute("currentUser")).getAllBills();
        return request.queryParams("periodStart")== null ?
                new InvestmentsReport(billList,request.session().attribute("currentUser"),
                        new PeriodFinder(billList).findPeriodStart(), new PeriodFinder(billList).findPeriodEnd()) :
                new InvestmentsReport(new BillsDao(request.session().attribute("currentUser")).getAllBills(),request.session().attribute("currentUser"),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"))
                        );

    }




}
