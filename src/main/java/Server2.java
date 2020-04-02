import Controller.Web.FrontServlet;
import Controller.Web.upload.UploadController;
import Controller.Web.login.LoginController;
import Controller.Web.index.IndexController;

import WebSocket.EchoWebSocket;
import Controller.util.Filters;
import Controller.util.Path;
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
        
        get("/mainPage",  FrontServlet.doGet);

        //get("*",notFound);

    }

    private static void configureRoutes() {
        configureGetRoutes();
        configurePostRoutes();
        configureAjaxPostRoutes();
    }

    private static void configurePostRoutes() {
        post(Path.Web.UPLOAD, UploadController.handleUploadPost);
        post(Path.Web.LOGIN, LoginController.handleLoginPost);
        post(Path.Web.LOGOUT, LoginController.handleLogoutPost);
    }

    private static void configureAjaxPostRoutes() {
        post("/filterbills", FrontServlet.runCommand("FilterBills"));
        post("/loadbills", FrontServlet.runCommand("LoadBills"));
    }

    private static void configureGetRoutes() {
        get("/", (request, response) -> {
            response.redirect("/index/");
            return null;
        });
        get(Path.Web.INDEX, IndexController.serveIndexPage);
        get(Path.Web.LOGIN, LoginController.serveLoginPage);
        get(Path.Web.BILLS, FrontServlet.runCommand("ShowBills"));
        get(Path.Web.ONE_BILL, FrontServlet.runCommand("ShowBill"));
        get(Path.Web.DOWNLOAD_ONE_BILL, FrontServlet.runCommand("DownloadBill"));
        get(Path.Web.ONE_BILL_EMAIL, FrontServlet.runCommand("SendBillByEmail"));
        get(Path.Web.BILLS_TIMELINE, FrontServlet.runCommand("ShowBillsTimeline"));
        get(Path.Web.REPORT_INVESTMENTS, FrontServlet.runCommand("ShowInvestmentsReport"));
        get(Path.Web.REPORT_COMPARE_INVESTMENTS, FrontServlet.runCommand("CompareInvestmentsReport"));
        get(Path.Web.REPORT_PROFITANDLOSSES, FrontServlet.runCommand("ShowProfitAndLossesReport"));
        get(Path.Web.REPORT_COMPARE_PROFITANDLOSSES, FrontServlet.runCommand("CompareProfitAndLosses"));
        get(Path.Web.REPORT_AMORTIZATION, FrontServlet.runCommand("ShowAmortizationReport"));
        get(Path.Web.DASHBOARD, FrontServlet.runCommand("ShowDashboard"));
        get("/bills2",FrontServlet.runCommand("ShowBills2"));

        get(Path.Web.SIGN_AWAIT, LoginController.serveSignAwait);
    }

    private static void configureBeforeFilters() {
        before("*", Filters.handleLocaleChange);
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