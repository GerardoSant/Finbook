package Controller.Web.controllers;

import Controller.Web.controllers.LoginController;
import spark.Request;
import spark.Response;
import spark.Route;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;

import java.util.HashMap;

import static Controller.Web.webutils.RequestUtil.getSessionUser;


public class ShinyController {
    public static Route shinyExample = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        model.put("currentUser", getSessionUser(request).getCompanyRFC());
        return ViewUtil.render(request, model, Path.Template.EXAMPLE_SHINY);
    };
}
