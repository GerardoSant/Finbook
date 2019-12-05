package util;

import lombok.*;

public class Path {

    public static class Web{
        @Getter public static final String INDEX = "/index/";
        @Getter public static final String UPLOAD = "/upload";
        @Getter public static final String LOGIN = "/login";
        @Getter public static final String LOGOUT = "/logout";
    }

    public static class Template {
        public static final String INDEX= "velocity/index.vm";
        public static final String LOGIN = "velocity/login.vm";
    }
}
