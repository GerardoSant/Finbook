package index;

import spark.Request;
import spark.Response;
import spark.Route;
import util.Path;
import util.ViewUtil;

import java.util.HashMap;
import java.util.Map;

import static util.RequestUtil.getQueryLocale;
import static util.RequestUtil.getSessionLocale;

public class IndexController{
    public static Route serveIndexPage = (Request request, Response response) ->{
        Map<String, Object> model = new HashMap<>();
        System.out.println((String) request.session().attribute("currentUser"));
        System.out.println(getSessionLocale(request));
        return ViewUtil.render(request, model, Path.Template.INDEX);
    };
}
