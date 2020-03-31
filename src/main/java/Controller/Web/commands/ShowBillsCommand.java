package Controller.Web.commands;

import Controller.Web.FrontCommand;
import View.daos.BillsDao;
import Controller.Web.login.LoginController;
import org.apache.velocity.tools.generic.MathTool;
import Controller.util.Path;
import Controller.util.ViewUtil;

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
