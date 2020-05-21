package Controller.Web.webutils;


public class Path {

    public static class Web{
        public static final String BILLS_UPLOAD = "/uploadbills/";
        public static final String UPLOAD = "/upload";
        public static final String LOGIN = "/login";
        public static final String LOGOUT = "/logout";
        public static final String BILLS = "/bills";
        public static final String FILTER_BILLS="/filterbills";
        public static final String LOAD_BILLS="/loadbills";
        public static final String ONE_BILL = "/bills/:uuid";
        public static final String DOWNLOAD_ONE_BILL= "/bills/:uuid/bill.xml";
        public static final String ONE_BILL_EMAIL= "/bills/:uuid/:email/";
        public static final String BILLS_TIMELINE = "/timeline";
        public static final String SHINY_EXAMPLE= "/shiny";
        public static final String MAIN_INDEX = "/main";
        public static final String BILLS_INDEX = "/main/bills";
        public static final String REPORTS_INDEX = "/main/reports";
        public static final String REPORT_INVESTMENTS = "/reports/investments";
        public static final String REPORT_PROFITANDLOSSES = "/reports/profitandlosses";
        public static final String REPORT_COMPARE_PROFITANDLOSSES = "/reports/compare/profitandlosses";
        public static final String REPORT_COMPARE_INVESTMENTS = "/reports/compare/investments";
        public static final String REPORT_AMORTIZATION = "/reports/amortization";
        public static final String DASHBOARD = "/dashboard";
        public static final String SIGN_AWAIT = "/login/signawait";
        public static final String LANDING_PAGE = "/landingPage";
    }

    public static class Template {
        public static final String UPLOAD_BILLS= "velocity/views/upload/uploadBills.vm";
        public static final String MAININDEX= "velocity/views/index/mainIndex.vm";
        public static final String LOGIN = "velocity/views/login/login.vm";
        public static final String BILLS = "velocity/views/bills/bills.vm";
        public static final String BILLS2 = "velocity/views/bills/bills2.vm";
        public static final String BILLS_ONE = "velocity/views/bills/oneBill.vm";
        public static final String BILLS_TIMELINE = "velocity/views/bills/billsTimeline.vm";
        public static final String NOT_FOUND= "velocity/views/error/notFound.vm";
        public static final String INTERNAL_ERROR= "velocity/views/error/internalError.vm";
        public static final String EXAMPLE_SHINY= "velocity/views/others/exampleShiny.vm";
        public static final String INVESTMENT_REPORT= "velocity/views/reports/investmentReport.vm";
        public static final String PROFITANDLOSSES_REPORT = "velocity/views/reports/profitAndLossesReport.vm";
        public static final String AMORTIZATION_REPORT = "velocity/views/reports/amortizationReport.vm";
        public static final String BILLSINDEX = "velocity/views/index/billsIndex.vm";
        public static final String REPORTSINDEX = "velocity/views/index/reportsIndex.vm";
        public static final String COMPARE_PROFITANDLOSSES_REPORT = "velocity/views/reports/compareProfitAndLossesReport.vm";
        public static final String COMPARE_INVESTMENTS_REPORT = "velocity/views/reports/compareInvestmentsReport.vm";
        public static final String DASHBOARD = "velocity/views/dashboard/dashboard.vm";
        public static final String SIGNAWAIT = "velocity/views/login/signAwait.vm";
        public static final String LANDING_PAGE = "velocity/views/others/landingPage.vm";
    }
}
