import spark.Route;
import util.Path;
import util.ViewUtil;

import java.util.HashMap;
import java.util.Map;

public class DashboardController {

    public static Route serveDashboard  = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.DASHBOARD);
    };
}
