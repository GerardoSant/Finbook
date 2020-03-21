package login;

import spark.Request;
import spark.Response;
import spark.Route;
import util.Path;
import util.ViewUtil;
import util.RequestUtil;

import java.util.HashMap;
import java.util.Map;

import static util.RequestUtil.*;

public class LoginController {
    public static Route serveLoginPage = (request, response ) -> {
        System.out.println((String) request.session().attribute("currentUser"));
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("redirected", removeSessionAttrLoginRedirect(request));
        return ViewUtil.render(request, model, Path.Template.LOGIN);
    };

    public static Route handleLoginPost = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        request.session().attribute("currentUser", getQueryUsername(request));
        response.redirect(Path.Web.DASHBOARD);
        return null;
    };

    public static Route handleLogoutPost = (request, response ) -> {
        Map<String, Object> model = new HashMap<>();
        request.session().removeAttribute("currentUser");
        request.session().attribute("loggedOut", true);
        response.redirect(Path.Web.LOGIN);
        return null;
    };


    public static void ensureUserIsLoggedIn(Request request, Response response) {
        if (request.session().attribute("currentUser") == null){
            request.session().attribute("redirected", true);
            response.redirect(Path.Web.LOGIN);
        }
    }
}
