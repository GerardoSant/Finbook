package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.util.Path;
import Controller.util.ViewUtil;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class ShowUploadBillsCommand extends FrontCommand {
    @Override
    public String process() {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.UPLOAD_BILLS);
    }
}
