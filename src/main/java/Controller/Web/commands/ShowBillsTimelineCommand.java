package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Model.Bills.BillTimeline;
import Controller.Web.controllers.LoginController;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;

import java.util.HashMap;
import java.util.Map;

import static Controller.Web.webutils.RequestQueryHandler.generateBillTimeline;
import static Controller.Web.webutils.RequestUtil.*;

public class ShowBillsTimelineCommand extends FrontCommand {

    BillTimeline timeline;

    @Override
    public String execute() {
        LoginController.ensureUserIsLoggedIn(request, response);
        timeline = generateBillTimeline(request);
        saveTimelineToSession();
        return ViewUtil.render(request, model(), Path.Template.BILLS_TIMELINE);
    }

    private void saveTimelineToSession() {
        request.session().attribute("currentBillList", timeline.getBillList());
    }


    private Map model() {
        HashMap<String, Object> model = new HashMap<>();
        fillModel(model);
        addToolsToModel(model);
        return model;
    }

    private void fillModel(HashMap<String, Object> model) {
        reduceTimelineList();
        model.put("timeline", timeline);
        model.put("RFC", getSessionUser(request).getCompanyRFC());
    }

    private void addToolsToModel(HashMap<String, Object> model) {
        model.put("math", new MathTool());
        model.put("dateTool", new DateTool());
        model.put("number", new NumberTool());
    }

    private void reduceTimelineList() {
        try {
            timeline.setBillList(timeline.getBillList().subList(0, 30));
        } catch (Exception e) {
            timeline.setBillList(timeline.getBillList().subList(0, timeline.getBillList().size()));
        }
    }


}
