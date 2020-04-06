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

import static java.lang.Double.parseDouble;
import static Controller.util.RequestUtil.getSessionCurrentUser;
import static Controller.util.RequestUtil.queryParamIsTrue;

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
        model.put("RFC", getSessionCurrentUser(request));
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

    private static BillTimeline generateBillTimeline(Request request) {
        try{
            List<Bill> billList = new BillsDao(getSessionCurrentUser(request)).getAllBills();
            if (request.queryParams("min") == null && request.queryParams("periodStart") == null) {
                return new BillTimelineBuilder().build(getSessionCurrentUser(request), billList, queryParamIsTrue(request,"ascendent"), true, true, true, true);
            }
            if (request.queryParams("min") != null && !request.queryParams("min").isEmpty()) {
                if (!request.queryParams("periodStart").isEmpty()) {
                    return new BillTimelineBuilder().build(getSessionCurrentUser(request), billList, new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")), new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd")), queryParamIsTrue(request,"ascendent"), parseDouble(request.queryParams("min")), parseDouble(request.queryParams("max")), queryParamIsTrue(request, "incomes"), queryParamIsTrue(request, "expenses"), queryParamIsTrue(request, "investments"), queryParamIsTrue(request, "salaries"));
                } else {
                    return new BillTimelineBuilder().build(getSessionCurrentUser(request), billList, queryParamIsTrue(request,"ascendent"), parseDouble(request.queryParams("min")), parseDouble(request.queryParams("max")), queryParamIsTrue(request, "incomes"), queryParamIsTrue(request, "expenses"), queryParamIsTrue(request, "investments"), queryParamIsTrue(request, "salaries"));
                }
            } else {
                if (request.queryParams("periodStart") != null && !request.queryParams("periodStart").isEmpty()) {
                    return new BillTimelineBuilder().build(getSessionCurrentUser(request), billList, new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")), new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd")), queryParamIsTrue(request,"ascendent"), queryParamIsTrue(request, "incomes"), queryParamIsTrue(request, "expenses"), queryParamIsTrue(request, "investments"), queryParamIsTrue(request, "salaries"));
                } else {
                    return new BillTimelineBuilder().build(getSessionCurrentUser(request), billList, queryParamIsTrue(request,"ascendent"), queryParamIsTrue(request, "incomes"), queryParamIsTrue(request, "expenses"), queryParamIsTrue(request, "investments"), queryParamIsTrue(request, "salaries"));
                }
            }
        } catch (Exception e){
            return null;
        }

    }
}
