package Controller.Web.webutils;

import Controller.util.MessageBundle;
import org.apache.velocity.app.VelocityEngine;
import org.eclipse.jetty.http.HttpStatus;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static Controller.Web.webutils.RequestUtil.getSessionLocale;
import static Controller.Web.webutils.RequestUtil.getSessionUser;

public class ViewUtil {
    public static String render(Request request, Map model, String templatePath) {
        return strictVelocityEngine().render(new ModelAndView(fillModel(model, request), templatePath));
    }

    private static VelocityTemplateEngine strictVelocityEngine() {
        VelocityEngine velocityEngine = new VelocityEngine();
        configureEngine(velocityEngine);
        return new VelocityTemplateEngine(velocityEngine);
    }

    private static void configureEngine(VelocityEngine configuredEngine) {
        configuredEngine.setProperty("runtime.references.strict", true);
        configuredEngine.setProperty("resource.loader", "class");
        configuredEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    }

    private static Map fillModel(Map model, Request request) {
        model.put("msg", new MessageBundle(getSessionLocale(request)));
        model.put("locale", new Locale(getSessionLocale(request)));
        model.put("user", getSessionUser(request));
        return model;
    }

    public static Route notFound = (Request request, Response response) -> {
        response.status(HttpStatus.NOT_FOUND_404);
        return render(request, new HashMap<>(), Path.Template.NOT_FOUND);
    };

    public static Route internalError = (Request request, Response response) -> {
        response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        return render(request, new HashMap<>(), Path.Template.INTERNAL_ERROR);
    };


}
