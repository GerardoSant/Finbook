package AS;

import Model.Bills.Bill;
import View.daos.BillsDao;
import Implementations.GeoNamesLocationLoader;
import Controller.util.Path;
import Controller.util.ViewUtil;

import java.util.HashMap;

import static Controller.util.RequestUtil.*;

public class ShowBillCommand extends FrontCommand {
    @Override
    public String process() {
        HashMap<String, Object> model = new HashMap<>();
        Bill bill = new BillsDao(request.session().attribute("currentUser")).getBillByUUID(request.queryParams("billID"));
        model.put("bill", bill);
        model.put("redirected", removeSessionAttrLoginRedirect(request));
        model.put("emailSent", removeSessionAttrEmailSent(request));
        model.put("location", new GeoNamesLocationLoader().load(bill.getPC(), "ES"));
        return ViewUtil.render(request, model, Path.Template.BILLS_ONE);
    }
}
