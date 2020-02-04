package Bills;

import Bills.Location.GeoNamesLocationLoader;
import login.LoginController;
import spark.Request;
import spark.Response;
import spark.Route;
import util.Path;
import util.ViewUtil;

import java.util.HashMap;

import static util.RequestUtil.*;

public class BillsController {
    public static Route fetchAllBills = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        model.put("bills", new BillsDao(request.session().attribute("currentUser")).getAllBills());
        //model.put("bills", new BillsDao("45").getAllBills());
        return ViewUtil.render(request,model, Path.Template.BILLS);
    };
    public static Route fetchOneBill= (Request request, Response response) -> {
        //LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        //Bill bill = new BillsDao("45").getBillByUUID(getParamUUID(request));
        Bill bill = new BillsDao(request.session().attribute("currentUser")).getBillByUUID(getParamUUID(request));
        model.put("bill",bill);
        model.put("redirected", removeSessionAttrLoginRedirect(request));
        model.put("location", new GeoNamesLocationLoader().load(bill.getPC(),"ES"));
        return ViewUtil.render(request, model, Path.Template.BILLS_ONE);
    };
    public static Route downloadOneBill = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request,response);
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
        Bill bill = new BillsDao("45").getBillByUUID(getParamUUID(request));
        model.put("bill",bill);
        response.redirect("/bills/" + getParamUUID(request));
        System.out.println("Mail sent to " +  getParamEmail(request));
        request.session().attribute("redirected", true);
        return null;
    };
}
