package Bills;

import Bills.Location.GeoNamesLocationLoader;
import Bills.MailSender.SMTPMailSender;
import login.LoginController;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import spark.Request;
import spark.Response;
import spark.Route;
import util.DateParser;
import util.Path;
import util.RequestUtil;
import util.ViewUtil;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import static java.lang.Double.parseDouble;
import static util.RequestUtil.*;

public class BillsController {
    public static Route fetchAllBills = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        HashMap<String, Object> model = new HashMap<>();
        model.put("math", new MathTool());
        model.put("bills", new BillsDao(request.session().attribute("currentUser")).getAllBills());
        return ViewUtil.render(request, model, Path.Template.BILLS);
    };
    public static Route fetchOneBill = (Request request, Response response) -> {
        //LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        //Bill bill = new BillsDao("45").getBillByUUID(getParamUUID(request));
        Bill bill = new BillsDao(request.session().attribute("currentUser")).getBillByUUID(getParamUUID(request));
        System.out.println(bill);
        model.put("bill", bill);
        model.put("redirected", removeSessionAttrLoginRedirect(request));
        model.put("emailSent", removeSessionAttrEmailSent(request));
        model.put("location", new GeoNamesLocationLoader().load(bill.getPC(), "ES"));
        return ViewUtil.render(request, model, Path.Template.BILLS_ONE);
    };
    public static Route downloadOneBill = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        response.type("text/xml");
        StringBuilder sb = new StringBuilder();
        //Bill bill = new BillsDao("45").getBillByUUID(getParamUUID(request));
        Bill bill = new BillsDao(request.session().attribute("currentUser")).getBillByUUID(getParamUUID(request));
        sb.append(bill.getXmlFile());
        return sb.toString();
    };


    public static Route oneBillSendEmail = (Request request, Response response) -> {
        //LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        Bill bill = new BillsDao(getSessionCurrentUser(request)).getBillByUUID(getParamUUID(request));
        model.put("bill", bill);
        request.session().attribute("redirected", true);
        try {
            new SMTPMailSender().send(bill, getParamEmail(request), getSessionCurrentUser(request));
            request.session().attribute("emailSent", true);
        } catch (Exception e) {
        }
        response.redirect("/bills/" + getParamUUID(request));
        return null;
    };

    public static Route billsTimeline = (Request request, Response response) -> {
        //LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        //List<Bill> billList = new BillsDao(getSessionCurrentUser(request)).getAllBills();
        List<Bill> billList = new BillsDao("E-5756930").getAllBills();
        //BillTimeline timeline = new BillTimeline(billList, true);
        BillTimeline timeline = generateBillTimeline(request);
        model.put("timeline", timeline);
        model.put("math", new MathTool());
        model.put("date", new DateTool());
        //model.put("RFC", getSessionCurrentUser(request));
        model.put("RFC", "E-5756930");
        return ViewUtil.render(request, model, Path.Template.BILLS_TIMELINE);
    };

    private static BillTimeline generateBillTimeline(Request request) throws ParseException {
        List<Bill> billList = new BillsDao("E-5756930").getAllBills();
        if (request.queryParams("min") == null && request.queryParams("periodStart") == null) {
            return new BillTimelineBuilder().build("E-5756930", billList, queryParamIsTrue(request,"ascendent"), true, true, true, true);
        }
        if (request.queryParams("min") != null && !request.queryParams("min").isEmpty()) {
            if (!request.queryParams("periodStart").isEmpty()) {
                return new BillTimelineBuilder().build("E-5756930", billList, new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")), new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd")), queryParamIsTrue(request,"ascendent"), parseDouble(request.queryParams("min")), parseDouble(request.queryParams("max")), queryParamIsTrue(request, "incomes"), queryParamIsTrue(request, "expenses"), queryParamIsTrue(request, "investments"), queryParamIsTrue(request, "salaries"));
            } else {
                return new BillTimelineBuilder().build("E-5756930", billList, queryParamIsTrue(request,"ascendent"), parseDouble(request.queryParams("min")), parseDouble(request.queryParams("max")), queryParamIsTrue(request, "incomes"), queryParamIsTrue(request, "expenses"), queryParamIsTrue(request, "investments"), queryParamIsTrue(request, "salaries"));
            }
        } else {
            if (request.queryParams("periodStart") != null && !request.queryParams("periodStart").isEmpty()) {
                return new BillTimelineBuilder().build("E-5756930", billList, new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodStart")), new DateParser("yyyy-MM-dd").parseDate(request.queryParams("periodEnd")), queryParamIsTrue(request,"ascendent"), queryParamIsTrue(request, "incomes"), queryParamIsTrue(request, "expenses"), queryParamIsTrue(request, "investments"), queryParamIsTrue(request, "salaries"));
            } else {
                return new BillTimelineBuilder().build("E-5756930", billList, queryParamIsTrue(request,"ascendent"), queryParamIsTrue(request, "incomes"), queryParamIsTrue(request, "expenses"), queryParamIsTrue(request, "investments"), queryParamIsTrue(request, "salaries"));
            }
        }
    }


}
