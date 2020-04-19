package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Model.Bills.BillTimeline;
import Controller.Web.login.LoginController;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;
import java.util.HashMap;

import static Controller.Web.webutils.RequestQueryHandler.generateBillTimeline;
import static Controller.Web.webutils.RequestUtil.*;

public class ShowBillsTimelineCommand extends FrontCommand {
    @Override
    public String process() {
        LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        BillTimeline timeline = generateBillTimeline(request);
        request.session().attribute("currentBillList",timeline.getBillList());
        reduceTimelineList(timeline);
        model.put("timeline", timeline);
        model.put("math", new MathTool());
        model.put("dateTool", new DateTool());
        model.put("RFC", getSessionUser(request).getCompanyRFC());
        model.put("number", new NumberTool());
        return ViewUtil.render(request, model, Path.Template.BILLS_TIMELINE);
    }

    private void reduceTimelineList(BillTimeline timeline) {
        try{
            timeline.setBillList(timeline.getBillList().subList(0,30));
        } catch(Exception e){
            timeline.setBillList(timeline.getBillList().subList(0,timeline.getBillList().size()));
        }
    }


}
