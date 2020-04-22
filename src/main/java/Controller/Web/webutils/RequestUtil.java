package Controller.Web.webutils;

import Model.User.User;
import View.daos.UserDao;
import spark.Request;

public class RequestUtil {
    public static String getQueryUsername(Request request) {
        return request.queryParams("username");
    }

    public static String getQueryPassword(Request request) {
        return request.queryParams("pswd");
    }

    public static User getSessionUser(Request request) {
        return request.session().attribute("user");
    }

    public static void setSessionUser(Request request, User user) {
        request.session().attribute("user", user);
    }

    public static String getQueryLocale(Request request) {
        return request.queryParams("locale");
    }

    public static String getSessionLocale(Request request) {
        return request.session().attribute("locale") == null ? "en" : request.session().attribute("locale");
    }

    public static String getParamUUID(Request request) {
        return request.params("uuid");
    }

    public static String getParamEmail(Request request) {
        return request.params("email");
    }

    public static boolean removeSessionAttrLoggedOut(Request request) {
        Object loggedOut = request.session().attribute("loggedOut");
        request.session().removeAttribute("loggedOut");
        return loggedOut != null;
    }

    public static boolean removeSessionAttrLoginRedirect(Request request) {
        Object loginRedirect = request.session().attribute("redirected");
        request.session().removeAttribute("redirected");
        return loginRedirect != null;
    }

    public static boolean removeSessionAttrEmailSent(Request request) {
        Object emailSent = request.session().attribute("emailSent");
        request.session().removeAttribute("emailSent");
        return emailSent != null;
    }

    public static boolean queryParamIsTrue(Request request, String queryParam) {
        if (request.queryParams(queryParam) != null) return request.queryParams(queryParam).equals("true");
        return false;
    }

    public static boolean paramIsEmpty(Request request, String queryParam) {
        return request.queryParams(queryParam).isEmpty();
    }

    public static String getParam(Request request, String queryParam) {
        return request.queryParams(queryParam);
    }

}
