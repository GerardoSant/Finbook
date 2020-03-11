package AS;

import Bills.BillsDao;
import login.LoginController;
import org.apache.velocity.tools.generic.MathTool;
import util.Path;
import util.ViewUtil;

import java.util.HashMap;

public class ShowBillsCommand extends FrontCommand {
    @Override
    public String process() {
        LoginController.ensureUserIsLoggedIn(request, response);
        HashMap<String, Object> model = new HashMap<>();
        model.put("math", new MathTool());
        model.put("bills", new BillsDao(request.session().attribute("currentUser")).getAllBills());
        return ViewUtil.render(request, model, Path.Template.BILLS);
    }
}
