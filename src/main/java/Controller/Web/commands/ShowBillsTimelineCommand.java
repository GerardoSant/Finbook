package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Model.Bills.Bill;
import Model.Bills.BillTimeline;
import Controller.builders.other.BillTimelineBuilder;
import View.daos.BillsDao;
import Controller.Web.login.LoginController;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import spark.Request;
import Controller.util.DateParser;
import Controller.util.Path;
import Controller.util.ViewUtil;
import java.util.HashMap;
import java.util.List;

import static Controller.util.RequestQueryHandler.generateBillTimeline;
import static Controller.util.RequestUtil.*;
import static java.lang.Double.parseDouble;

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
