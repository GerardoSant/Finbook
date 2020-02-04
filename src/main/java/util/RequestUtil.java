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

    public static String getParamUUID(Request request) {
        return request.params("uuid");
    }

    public static String getParamEmail(Request request){ return request.params("email");}

    public static boolean removeSessionAttrLoggedOut(Request request) {
        Object loggedOut = request.session().attribute("loggedOut");
        request.session().removeAttribute("loggedOut");
        return loggedOut != null;
    }

    public static boolean removeSessionAttrLoginRedirect(Request request) {
        Object loginRedirect = request.session().attribute("redirected");
        request.session().removeAttribute("redirected");
        return loginRedirect!=null;
    }


}
