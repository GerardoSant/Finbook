package login;

import spark.Route;
import util.Path;
import util.ViewUtil;
import util.RequestUtil;

import java.util.HashMap;
import java.util.Map;

import static util.RequestUtil.getQueryPassword;
import static util.RequestUtil.getQueryUsername;

public class LoginController {
    public static Route serveLoginPage = (request, response ) -> {
        System.out.println((String) request.session().attribute("currentUser"));
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.LOGIN);
    };

    public static Route handleLoginPost = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        request.session().attribute("currentUser", getQueryUsername(request));
        response.redirect(Path.Web.INDEX);
        return null;
    };

    public static Route handleLogoutPost = (request, response ) -> {
        Map<String, Object> model = new HashMap<>();
        request.session().removeAttribute("currentUser");
        response.redirect(Path.Web.LOGIN);
        return null;
    };
}
