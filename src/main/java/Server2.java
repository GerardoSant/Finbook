import Controller.Web.FrontController;
import Controller.Web.controllers.LoginController;

import WebSocket.EchoWebSocket;
import Controller.Web.webutils.SparkFilters;
import Controller.Web.webutils.Path;
import static spark.Spark.*;

public class Server2 {
    public static void main(String[] args) {
        //Configure Spark
        configureSpark();
        configureWebSocket();

        // Set up before-filters (called before each get/post)

        configureBeforeFilters();

        // Set up routes

        configureRoutes();

        //AS
        
        get("/mainPage",  FrontController.doGet);

        //get("*",notFound);

    }

    private static void configureRoutes() {
        configureGetRoutes();
        configurePostRoutes();
        configureAjaxPostRoutes();
    }

    private static void configurePostRoutes() {
        post(Path.Web.LOGIN, LoginController.handleLoginPost);
        post(Path.Web.LOGOUT, LoginController.handleLogoutPost);
    }

    private static void configureAjaxPostRoutes() {
        post(Path.Web.UPLOAD, FrontController.runCommand("UploadBills"));
        post(Path.Web.FILTER_BILLS, FrontController.runCommand("FilterBills"));
        post(Path.Web.LOAD_BILLS, FrontController.runCommand("LoadBills"));
    }

    private static void configureGetRoutes() {
        get("/", (request, response) -> {
            response.redirect("/login");
            return null;
        });

        get(Path.Web.LOGIN, LoginController.serveLoginPage);
        get(Path.Web.BILLS_UPLOAD, FrontController.runCommand("ShowUploadBills"));
        get(Path.Web.BILLS, FrontController.runCommand("ShowBills"));
        get(Path.Web.ONE_BILL, FrontController.runCommand("ShowBill"));
        get(Path.Web.DOWNLOAD_ONE_BILL, FrontController.runCommand("DownloadBill"));
        get(Path.Web.ONE_BILL_EMAIL, FrontController.runCommand("SendBillByEmail"));
        get(Path.Web.BILLS_TIMELINE, FrontController.runCommand("ShowBillsTimeline"));
        get(Path.Web.REPORT_INVESTMENTS, FrontController.runCommand("ShowInvestmentsReport"));
        get(Path.Web.REPORT_COMPARE_INVESTMENTS, FrontController.runCommand("CompareInvestmentsReport"));
        get(Path.Web.REPORT_PROFITANDLOSSES, FrontController.runCommand("ShowProfitAndLossesReport"));
        get(Path.Web.REPORT_COMPARE_PROFITANDLOSSES, FrontController.runCommand("CompareProfitAndLosses"));
        get(Path.Web.REPORT_AMORTIZATION, FrontController.runCommand("ShowAmortizationReport"));
        get(Path.Web.DASHBOARD, FrontController.runCommand("ShowDashboard"));

        get(Path.Web.SIGN_AWAIT, LoginController.serveSignAwait);
    }

    private static void configureBeforeFilters() {
        before("*", SparkFilters.handleLocaleChange);
    }

    private static void configureWebSocket() {
        webSocket("/echo", EchoWebSocket.class);
    }

    private static void configureSpark() {
        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        port(8080);
    }
}