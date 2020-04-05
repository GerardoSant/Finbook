package Controller.util;

import View.daos.UserDao;
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

import static Controller.util.RequestUtil.getSessionCurrentUser;
import static Controller.util.RequestUtil.getSessionLocale;

public class ViewUtil {
    public static String render(Request request, Map model, String templatePath){
        model.put("msg", new MessageBundle(getSessionLocale(request)));
        model.put("locale", new Locale(getSessionLocale(request)));
        model.put("currentUser", getSessionCurrentUser(request));
        if(getSessionCurrentUser(request)!=null) model.put("businessName", new UserDao().getUserName(getSessionCurrentUser(request)));
        return strictVelocityEngine().render(new ModelAndView(model, templatePath));
    }


    private static VelocityTemplateEngine strictVelocityEngine() {
        VelocityEngine configuredEngine = new VelocityEngine();
        configuredEngine.setProperty("runtime.references.strict", true);
        configuredEngine.setProperty("resource.loader", "class");
        configuredEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return new VelocityTemplateEngine(configuredEngine);
    }

    public static Route notFound = (Request request, Response response) -> {
        response.status(HttpStatus.NOT_FOUND_404);
        return render(request, new HashMap<>(), Path.Template.NOT_FOUND);
    };

    public static Route internalError = (Request request, Response response)-> {
        response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        return render(request, new HashMap<>(), Path.Template.INTERNAL_ERROR);
    };


}
