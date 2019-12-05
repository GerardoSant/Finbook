package util;

import spark.Filter;
import spark.Request;
import spark.Response;

import static util.RequestUtil.getQueryLocale;

public class Filters {

    public static Filter handleLocaleChange = (Request request, Response response) -> {
        if (getQueryLocale(request) != null) {
            request.session().attribute("locale", getQueryLocale(request));
            response.redirect(request.pathInfo());
        }
    };

}
