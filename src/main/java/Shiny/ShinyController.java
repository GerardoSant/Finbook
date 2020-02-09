package Shiny;

import Bills.BillsDao;
import login.LoginController;
import spark.Request;
import spark.Response;
import spark.Route;
import util.Path;
import util.ViewUtil;

import java.util.HashMap;

import static util.RequestUtil.getSessionCurrentUser;

public class ShinyController {
    public static Route shinyExample = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        model.put("currentUser", getSessionCurrentUser(request));
        return ViewUtil.render(request, model, Path.Template.EXAMPLE_SHINY);
    };
}
