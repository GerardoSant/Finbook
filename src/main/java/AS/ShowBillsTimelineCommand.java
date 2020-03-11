package AS;

import Bills.Bill;
import Bills.BillTimeline;
import Bills.BillTimelineBuilder;
import Bills.BillsDao;
import login.LoginController;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import spark.Request;
import util.DateParser;
import util.Path;
import util.ViewUtil;
import java.util.HashMap;
import java.util.List;

import static java.lang.Double.parseDouble;
import static util.RequestUtil.getSessionCurrentUser;
import static util.RequestUtil.queryParamIsTrue;

public class ShowBillsTimelineCommand extends FrontCommand {
    @Override
    public String process() {
        LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        List<Bill> billList = new BillsDao(getSessionCurrentUser(request)).getAllBills();
        BillTimeline timeline = generateBillTimeline(request);
        model.put("timeline", timeline);
        model.put("math", new MathTool());
        model.put("date", new DateTool());
        model.put("RFC", getSessionCurrentUser(request));
        return ViewUtil.render(request, model, Path.Template.BILLS_TIMELINE);
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
