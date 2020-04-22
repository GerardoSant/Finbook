package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;

import java.util.HashMap;
import java.util.Map;

public class ShowUploadBillsCommand extends FrontCommand {
    @Override
    public String execute() {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.UPLOAD_BILLS);
    }
}
