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
    public static Route winAndLossesReport = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        HashMap<String, Object> model = new HashMap<>();
        WinAndLossesReport report = generateWinAndLossesReport(request);
        model.put("report", report);
        return ViewUtil.render(request, model, Path.Template.WINANDLOSSES_REPORT);
    };

    private static WinAndLossesReport generateWinAndLossesReport(Request request) throws ParseException {
        List<Bill> billList =new BillsDao(request.session().attribute("currentUser")).getAllBills();
        return request.queryParams("periodStart")== null ?
                new WinAndLossesReport(billList,request.session().attribute("currentUser"),
                        new PeriodFinder(billList).findPeriodStart(), new PeriodFinder(billList).findPeriodEnd()) :
                new WinAndLossesReport(new BillsDao(request.session().attribute("currentUser")).getAllBills(),request.session().attribute("currentUser"),
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

    ;
}
