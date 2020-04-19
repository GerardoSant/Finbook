package Controller.Web.controllers;

import View.daos.UserDao;
import SparkApp.SignWebSocket;
import io.finbook.TextGenerator;
import spark.Request;
import spark.Response;
import spark.Route;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;

import java.util.HashMap;
import java.util.Map;

import static Controller.Web.webutils.RequestUtil.*;

public class LoginController {
    public static Route serveLoginPage = (request, response ) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("redirected", removeSessionAttrLoginRedirect(request));
        return ViewUtil.render(request, model, Path.Template.LOGIN);
    };

    public static Route handleLoginPost = (request, response) -> {
        if (request.queryParams("id")!=null) {
            byte[] sign = SignWebSocket.messages.get(request.queryParams("id"));
            for (byte b : sign) {
                System.out.print(b+",");
            }
            SignWebSocket.messages.remove(request.queryParams("id"));
            if (/*new Verifier(sign).validateSign()*/true) {
                request.session().attribute("currentUser", "E-5756930");
                request.session().attribute("user", new UserDao().getUser("E-5756930"));
                response.redirect(Path.Web.DASHBOARD);
            } else{
                response.redirect(Path.Web.LOGIN);
            }
        } else{
            request.session().attribute("user", new UserDao().getUser(getQueryUsername(request)));
            response.redirect(Path.Web.DASHBOARD);
        }
        return null;
    };

    public static Route handleLogoutPost = (request, response ) -> {
        Map<String, Object> model = new HashMap<>();
        request.session().removeAttribute("currentUser");
        request.session().removeAttribute("user");
        request.session().attribute("loggedOut", true);
        response.redirect(Path.Web.LOGIN);
        return null;
    };
    public static Route serveSignAwait = (request, response ) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("textToSign", TextGenerator.generateRandomText());
        return ViewUtil.render(request,model,Path.Template.SIGNAWAIT);
    };


    public static void ensureUserIsLoggedIn(Request request, Response response) {
        if (request.session().attribute("user") == null){
            request.session().attribute("redirected", true);
            response.redirect(Path.Web.LOGIN);
        }
    }
}
