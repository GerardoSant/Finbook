import Controller.Web.FrontServlet;
import Controller.Web.upload.UploadController;
import Controller.Web.login.LoginController;
import Controller.Web.index.IndexController;

import WebSocket.EchoWebSocket;
import Controller.util.Filters;
import Controller.util.Path;
import static spark.Spark.*;

import static Controller.util.ViewUtil.notFound;

public class Server2 {
    public static void main(String[] args) {


        //Configure Spark
        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        port(8080);
        webSocket("/echo", EchoWebSocket.class);
        //init();


        // Set up before-filters (called before each get/post)

        before("*", Filters.handleLocaleChange);


        // Set up routes

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

        //AJAX
        post("/filterbills", FrontServlet.runCommand("FilterBills"));
        post("/loadbills", FrontServlet.runCommand("LoadBills"));

        post(Path.Web.UPLOAD, UploadController.handleUploadPost);
        post(Path.Web.LOGIN, LoginController.handleLoginPost);
        post(Path.Web.LOGOUT, LoginController.handleLogoutPost);

        get("/mainPage",  FrontServlet.doGet);

        //get("*",notFound);





    }
}