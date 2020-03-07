package Reports;

import Bills.Bill;
import Bills.BillsDao;
import login.LoginController;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import spark.Request;
import spark.Response;
import spark.Route;
import util.DateParser;
import util.Path;
import util.ViewUtil;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;


public class ReportController {

    public static Route investmentReport = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        InvestmentsReport report = generateInvestmentsReport(request);
        BarChart barChart = new InvestmentsBarChartBuilder().build(report);
        model.put("report",report);
        model.put("barChart", barChart);
        return ViewUtil.render(request,model, Path.Template.INVESTMENT_REPORT);
    };
    public static Route profitAndLossesReport = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        HashMap<String, Object> model = new HashMap<>();
        ProfitAndLossesReport report = generateProfitAndLossesReport(request);
        model.put("report", report);
        model.put("math", new MathTool());
        return ViewUtil.render(request, model, Path.Template.PROFITANDLOSSES_REPORT);
    };

    public static Route amortizationReport = (Request request, Response response) ->{
        LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        AmortizationReport report = generateAmortizationReport(request);
        BarChart barChart = new AmortizationBarChartBuilder().build(report);
        model.put("date", new DateTool());
        model.put("math", new MathTool());
        model.put("report",report);
        model.put("barChart",barChart);
        return ViewUtil.render(request,model,Path.Template.AMORTIZATION_REPORT);
    };



    public static Route compareProfitAndLossesReport= (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        HashMap<String, Object> model = new HashMap<>();
        ProfitAndLossesReport report1 = generateProfitAndLossesReport(request,"");
        model.put("report1", report1);
        ProfitAndLossesReport report2 = generateProfitAndLossesReport(request,"1");
        model.put("report2", report2);
        model.put("math", new MathTool());
        return ViewUtil.render(request, model, Path.Template.COMPARE_PROFITANDLOSSES_REPORT);
    };

    public static Route compareInvestmentsReport = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        HashMap<String, Object> model = new HashMap<>();
        InvestmentsReport report1 = generateInvestmentsReport(request,"");
        model.put("report1", report1);
        InvestmentsReport report2 = generateInvestmentsReport(request,"1");
        model.put("report2", report2);
        model.put("math", new MathTool());
        return ViewUtil.render(request, model, Path.Template.COMPARE_INVESTMENTS_REPORT);
    };



    private static ProfitAndLossesReport generateProfitAndLossesReport(Request request, String report) throws ParseException {
        List<Bill> billList =new BillsDao(request.session().attribute("currentUser")).getAllBills();
        return request.queryParams("periodStart")== null ?
                new ProfitAndLossesReportBuilder(billList,request.session().attribute("currentUser")).buildReport() :
                new ProfitAndLossesReportBuilder(new BillsDao(request.session().attribute("currentUser")).getAllBills(),request.session().attribute("currentUser"),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart"+report)),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"+report))).buildReport();
    }




    private static ProfitAndLossesReport generateProfitAndLossesReport(Request request) throws ParseException {
        List<Bill> billList =new BillsDao(request.session().attribute("currentUser")).getAllBills();
        return request.queryParams("periodStart")== null ?
                new ProfitAndLossesReportBuilder(billList,request.session().attribute("currentUser")).buildReport() :
                new ProfitAndLossesReportBuilder(new BillsDao(request.session().attribute("currentUser")).getAllBills(),request.session().attribute("currentUser"),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"))).buildReport();
    }

    private static InvestmentsReport generateInvestmentsReport(Request request) throws ParseException {
        List<Bill> billList =new BillsDao(request.session().attribute("currentUser")).getAllBills();
        return request.queryParams("periodStart")== null ?
                new InvestmentReportBuilder(billList,request.session().attribute("currentUser")).buildReport():
                new InvestmentReportBuilder(new BillsDao(request.session().attribute("currentUser")).getAllBills(),request.session().attribute("currentUser"),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"))
                        ).buildReport();
    }

    private static InvestmentsReport generateInvestmentsReport(Request request, String report) throws ParseException {
        List<Bill> billList =new BillsDao(request.session().attribute("currentUser")).getAllBills();
        return request.queryParams("periodStart")== null ?
                new InvestmentReportBuilder(billList,request.session().attribute("currentUser")).buildReport():
                new InvestmentReportBuilder(new BillsDao(request.session().attribute("currentUser")).getAllBills(),request.session().attribute("currentUser"),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart"+report)),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"+report))
                ).buildReport();
    }

    private static AmortizationReport generateAmortizationReport(Request request) throws ParseException {
        List<Bill> billList =new BillsDao(request.session().attribute("currentUser")).getAllBills();
        return request.queryParams("periodStart")== null ?
                new AmortizationReportBuilder(billList,request.session().attribute("currentUser")).buildReport() :
                new AmortizationReportBuilder(billList, request.session().attribute("currentUser"), new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")),
                        new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd"))).buildReport();
    }




}
