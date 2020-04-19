package Controller.Web.webutils;

import spark.Filter;
import spark.Request;
import spark.Response;

import static Controller.Web.webutils.RequestUtil.getQueryLocale;

public class SparkFilters {

    public static Filter handleLocaleChange = (Request request, Response response) -> {
        if (thereIsQueryLocale(request)) {
            request.session().attribute("locale", getQueryLocale(request));
            response.redirect(request.pathInfo());
        }
    };

    private static boolean thereIsQueryLocale(Request request) {
        return getQueryLocale(request) != null;
    }

}
