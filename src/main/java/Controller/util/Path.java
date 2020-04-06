package Controller.util;


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
    }

    public static class Template {
        public static final String UPLOAD_BILLS= "velocity/uploadBills.vm";
        public static final String MAININDEX= "velocity/mainIndex.vm";
        public static final String LOGIN = "velocity/login.vm";
        public static final String BILLS = "velocity/bills.vm";
        public static final String BILLS2 = "velocity/bills2.vm";
        public static final String BILLS_ONE = "velocity/oneBill.vm";
        public static final String BILLS_TIMELINE = "velocity/billsTimeline.vm";
        public static final String NOT_FOUND= "velocity/notFound.vm";
        public static final String INTERNAL_ERROR= "velocity/internalError.vm";
        public static final String EXAMPLE_SHINY= "velocity/exampleShiny.vm";
        public static final String INVESTMENT_REPORT= "velocity/investmentReport.vm";
        public static final String PROFITANDLOSSES_REPORT = "velocity/profitAndLossesReport.vm";
        public static final String AMORTIZATION_REPORT = "velocity/amortizationReport.vm";
        public static final String BILLSINDEX = "velocity/billsIndex.vm";
        public static final String REPORTSINDEX = "velocity/reportsIndex.vm";
        public static final String COMPARE_PROFITANDLOSSES_REPORT = "velocity/compareProfitAndLossesReport.vm";
        public static final String COMPARE_INVESTMENTS_REPORT = "velocity/compareInvestmentsReport.vm";
        public static final String DASHBOARD = "velocity/dashboard.vm";
        public static final String SIGNAWAIT = "velocity/signAwait.vm";
    }
}
