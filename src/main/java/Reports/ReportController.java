package Reports;

import Bills.BillsDao;
import login.LoginController;
import org.apache.velocity.tools.generic.MathTool;
import spark.Request;
import spark.Response;
import spark.Route;
import util.Path;
import util.ViewUtil;

import java.util.HashMap;

import static util.RequestUtil.getSessionCurrentUser;

public class ReportController {

    public static Route investmentReport = (Request request, Response response) -> {
        //LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        //InvestmentsReport report = new InvestmentsReport(new BillsDao(request.session().attribute("currentUser")).getAllBills(), getSessionCurrentUser(request));
        InvestmentsReport report = new InvestmentsReport(new BillsDao("E-5756930").getAllBills(),"E-5756930");
        System.out.println(report.getBase());
        model.put("report",report);

        return ViewUtil.render(request,model, Path.Template.INVESTMENT_REPORT);
    };;
}
