package util;

import spark.Request;

public class RequestUtil {
    public static String getQueryUsername(Request request){
        return request.queryParams("username");
    }
    public static String getQueryPassword(Request request){
        return request.queryParams("pswd");
    }

    public static String getSessionCurrentUser(Request request){
        return request.session().attribute("currentUser");
    }

    public static String getQueryLocale(Request request) {
        return request.queryParams("locale");
    }

    public static String getSessionLocale(Request request) {
        return request.session().attribute("locale")==null ? "en" : request.session().attribute("locale");
    }
}
