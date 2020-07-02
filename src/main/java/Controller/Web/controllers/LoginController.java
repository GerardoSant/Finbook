package Controller.Web.controllers;

import Controller.util.sign.FinbookSignValidator;
import View.daos.UserDao;
import SparkApp.SignWebSocket;
//import io.finbook.TextGenerator;
import io.finbook.TextGenerator;
import io.finbook.Verifier;
import spark.Request;
import spark.Response;
import spark.Route;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;

import java.util.HashMap;
import java.util.Map;

import static Controller.Web.webutils.RequestUtil.*;

public class LoginController {

    public static Route serveLoginPage = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("redirected", removeSessionAttrLoginRedirect(request));
        model.put("failedLogin", removeSessionAttrLoginFailed(request));
        return ViewUtil.render(request, model, Path.Template.LOGIN);
    };

    public static Route handleLoginPost = (request, response) -> {
        if (userLogInBySign(request)) {
            handleLogInBySign(request, response);
        } else {
            handleStandardLogIn(request, response);
        }
        return null;
    };

    private static void handleLogInBySign(Request request, Response response) {
        byte[] sign = getUserSign(request);
        if (signedTextEqualsToTextSentToSign(request, sign)) {
            System.out.println(new Verifier(sign).validateSign());
            logInUser(request,response, getSignAuthor(sign));
        } else {
            redirectToLogin(response);
        }
    }

    private static byte[] getUserSign(Request request) {
        return SignWebSocket.messages.get(request.queryParams("sign"));
    }

    private static boolean signedTextEqualsToTextSentToSign(Request request, byte[] sign) {
        return true;
        //return new FinbookSignValidator().getDecryptedText(sign).equals(getTextSentToSign(request));
    }

    private static String getSignAuthor(byte[] sign) {
        return new FinbookSignValidator().getSignAuthor(sign);
    }

    private static String getTextSentToSign(Request request) {
        return request.queryParams("sign");
    }

    private static void handleStandardLogIn(Request request, Response response) {
        logInUser(request,response, getQueryUsername(request));
    }

    private static void logInUser(Request request, Response response, String userRFC) {
        setSessionUser(request, new UserDao().getUser(userRFC));
        if(userIsNotLoggedIn(request)) {
            redirectToFailedLogin(request, response);
        } else{
            redirectToDashboard(response);
        }
    }


    private static boolean userLogInBySign(Request request) {
        return request.queryParams("sign") != null;
    }

    public static Route handleLogoutPost = (request, response) -> {
        logOutUser(request, response);
        redirectToLogin(response);
        return null;
    };

    private static void logOutUser(Request request, Response response) {
        request.session().removeAttribute("user");
        request.session().attribute("loggedOut", true);
    }

    public static Route serveSignAwait = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("textToSign", TextGenerator.generateRandomText());
        //model.put("textToSign", "testText");
        //new Verifier().validateSign();

        return ViewUtil.render(request, model, Path.Template.SIGNAWAIT);
    };


    public static void ensureUserIsLoggedIn(Request request, Response response) {
        if (userIsNotLoggedIn(request)) {
            request.session().attribute("redirected", true);
            redirectToLogin(response);
        }
    }

    private static boolean userIsNotLoggedIn(Request request) {
        return getSessionUser(request) == null;
    }

    private static void redirectToFailedLogin(Request request, Response response){
        request.session().attribute("failedLogin", true);
        response.redirect(Path.Web.LOGIN);
    }
    private static void redirectToLogin(Response response) {
        response.redirect(Path.Web.LOGIN);
    }

    private static void redirectToDashboard(Response response) {
        response.redirect(Path.Web.DASHBOARD);
    }


}
