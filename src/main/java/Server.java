import Controller.Web.FrontServlet;
import Controller.Web.Shiny.ShinyController;
import Controller.Web.upload.UploadController;
import Controller.Web.login.LoginController;
import Controller.Web.index.IndexController;
import Controller.Web.bills.BillsController;
import Controller.Web.Dashboard.DashboardController;
import Controller.Web.reports.ReportController;

import WebSocket.EchoWebSocket;
import Controller.Web.webutils.SparkFilters;
import Controller.Web.webutils.Path;
import static spark.Spark.*;

import static Controller.Web.webutils.ViewUtil.notFound;

public class Server {
    public static void main(String[] args) {


        //Configure Spark
        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        port(8080);
        webSocket("/echo", EchoWebSocket.class);
        //init();


        // Set up before-filters (called before each get/post)

        before("*", SparkFilters.handleLocaleChange);


        // Set up routes



        get("/", (request, response) -> {
            response.redirect("/uploadBills/");
            return null;
        });
        get(Path.Web.BILLS_UPLOAD, IndexController.serveIndexPage);
        get(Path.Web.LOGIN, LoginController.serveLoginPage);
        get(Path.Web.BILLS, BillsController.fetchAllBills);
        get(Path.Web.ONE_BILL, BillsController.fetchOneBill);
        get(Path.Web.DOWNLOAD_ONE_BILL, BillsController.downloadOneBill);
        get(Path.Web.ONE_BILL_EMAIL, BillsController.oneBillSendEmail);
        get(Path.Web.BILLS_TIMELINE, BillsController.billsTimeline);
        get(Path.Web.REPORT_INVESTMENTS, ReportController.investmentReport);
        get(Path.Web.REPORT_COMPARE_INVESTMENTS, ReportController.compareInvestmentsReport);
        get(Path.Web.REPORT_PROFITANDLOSSES, ReportController.profitAndLossesReport);
        get(Path.Web.REPORT_COMPARE_PROFITANDLOSSES, ReportController.compareProfitAndLossesReport);
        get(Path.Web.REPORT_AMORTIZATION, ReportController.amortizationReport);
        get(Path.Web.SHINY_EXAMPLE, ShinyController.shinyExample);
        get(Path.Web.MAIN_INDEX, IndexController.serveMainIndexPage);
        get(Path.Web.BILLS_INDEX, IndexController.serveBillsIndexPage);
        get(Path.Web.REPORTS_INDEX, IndexController.serveReportsIndexPage);
        get(Path.Web.DASHBOARD, DashboardController.serveDashboard);
        get(Path.Web.SIGN_AWAIT, LoginController.serveSignAwait);


        post(Path.Web.UPLOAD, UploadController.handleUploadPost);
        post(Path.Web.LOGIN, LoginController.handleLoginPost);
        post(Path.Web.LOGOUT, LoginController.handleLogoutPost);



        internalServerError("/throwexception");
        notFound(notFound);

        get("/throwexception", (request, response) -> {
            throw new Exception();
        });

        exception(Exception.class, (e, request, response) -> {
            e.printStackTrace();
            response.status(404);
            response.redirect("/error");
        });

        get("/error", notFound);


        get("/mainPage", FrontServlet.doGet);



    }
}