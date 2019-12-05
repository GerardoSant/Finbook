package util;

import org.apache.velocity.app.VelocityEngine;
import spark.ModelAndView;
import spark.Request;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.Map;

import static util.RequestUtil.getSessionCurrentUser;
import static util.RequestUtil.getSessionLocale;

public class ViewUtil {
    public static String render(Request request, Map model, String templatePath){
        model.put("lan", getSessionLocale(request));
        model.put("msg", new MessageBundle(getSessionLocale(request)));
        model.put("currentUser", getSessionCurrentUser(request));
        return strictVelocityEngine().render(new ModelAndView(model, templatePath));
    }


    private static VelocityTemplateEngine strictVelocityEngine() {
        VelocityEngine configuredEngine = new VelocityEngine();
        configuredEngine.setProperty("runtime.references.strict", true);
        configuredEngine.setProperty("resource.loader", "class");
        configuredEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return new VelocityTemplateEngine(configuredEngine);
    }
}
