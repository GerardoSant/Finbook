import Bills.BillsController;
import Reports.ReportController;
import Shiny.ShinyController;
import index.IndexController;
import upload.UploadController;
import login.LoginController;
import util.Filters;
import util.Path;
import util.ViewUtil;

import static spark.Spark.*;

public class Server {
    public static void main(String[] args) {


        //Configure Spark
        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        port(8080);
        init();

        // Set up before-filters (called before each get/post)

        before("*", Filters.handleLocaleChange);


        // Set up routes

        get("/", (request, response) -> {
            response.redirect("/index/");
            return null;
        });
        get(Path.Web.INDEX, IndexController.serveIndexPage);
        get(Path.Web.LOGIN, LoginController.serveLoginPage);
        get(Path.Web.BILLS, BillsController.fetchAllBills);
        get(Path.Web.ONE_BILL, BillsController.fetchOneBill);
        get(Path.Web.DOWNLOAD_ONE_BILL, BillsController.downloadOneBill);
        get(Path.Web.ONE_BILL_EMAIL, BillsController.oneBillSendEmail);
        get(Path.Web.BILLS_TIMELINE, BillsController.billsTimeline);
        get(Path.Web.REPORT_INVESTMENTS, ReportController.investmentReport);
        get(Path.Web.REPORT_PROFITANDLOSSES, ReportController.profitAndLossesReport);
        get(Path.Web.REPORT_COMPARE_PROFITANDLOSSES, ReportController.compareProfitAndLossesReport);
        get(Path.Web.SHINY_EXAMPLE, ShinyController.shinyExample);
        get(Path.Web.MAIN_INDEX, IndexController.serveMainIndexPage);
        get(Path.Web.BILLS_INDEX, IndexController.serveBillsIndexPage);
        get(Path.Web.REPORTS_INDEX, IndexController.serveReportsIndexPage);

        post(Path.Web.UPLOAD, UploadController.handleUploadPost);
        post(Path.Web.LOGIN, LoginController.handleLoginPost);
        post(Path.Web.LOGOUT, LoginController.handleLogoutPost);


        get("*", ViewUtil.notFound);


    }
}
