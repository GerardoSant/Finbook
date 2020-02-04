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
    }

    public static class Template {
        public static final String INDEX= "velocity/index.vm";
        public static final String LOGIN = "velocity/login.vm";
        public static final String BILLS = "velocity/bills.vm";
        public static final String BILLS_ONE = "velocity/oneBill.vm";
        public static final String NOT_FOUND= "velocity/notFound.vm";
    }
}
