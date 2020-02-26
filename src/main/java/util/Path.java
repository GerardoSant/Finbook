package util;

import lombok.*;

public class Path {

    public static class Web{
        @Getter public static final String INDEX = "/index/";
        @Getter public static final String UPLOAD = "/upload";
        @Getter public static final String LOGIN = "/login";
        @Getter public static final String LOGOUT = "/logout";
        @Getter public static final String BILLS = "/bills";
        @Getter public static final String ONE_BILL = "/bills/:uuid";
        @Getter public static final String DOWNLOAD_ONE_BILL= "/bills/:uuid/bill.xml";
        @Getter public static final String ONE_BILL_EMAIL= "/bills/:uuid/:email/";
        @Getter public static final String BILLS_TIMELINE = "/timeline";
        @Getter public static final String SHINY_EXAMPLE= "/shiny";
        @Getter public static final String MAIN_INDEX = "/main";
        @Getter public static final String BILLS_INDEX = "/main/bills";
        @Getter public static final String REPORT_INVESTMENTS = "/reports/investments";
        @Getter public static final String REPORT_PROFITANDLOSSES = "/reports/profitandlosses";



    }

    public static class Template {
        public static final String INDEX= "velocity/index.vm";
        public static final String MAININDEX= "velocity/mainIndex.vm";
        public static final String LOGIN = "velocity/login.vm";
        public static final String BILLS = "velocity/bills.vm";
        public static final String BILLS_ONE = "velocity/oneBill.vm";
        public static final String BILLS_TIMELINE = "velocity/billsTimeline.vm";
        public static final String NOT_FOUND= "velocity/notFound.vm";
        public static final String EXAMPLE_SHINY= "velocity/exampleShiny.vm";
        public static final String INVESTMENT_REPORT= "velocity/investmentReport.vm";
        public static final String PROFITANDLOSSES_REPORT = "velocity/profitAndLossesReport.vm";
        public static final String BILLSINDEX = "velocity/billsIndex.vm";
    }
}
