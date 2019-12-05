import index.IndexController;
import upload.UploadController;
import login.LoginController;
import util.Filters;
import util.Path;

import static spark.Spark.*;

public class Server {
    public static void main(String[] args) {



        //Configure Spark
        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        port(8080);
        init();

        // Set up before-filters (called before each get/post)

        before("*", Filters.handleLocaleChange);


        // Set up routes

        get("/" , (request, response) -> { response.redirect("/index/"); return null;});
        get(Path.Web.INDEX, IndexController.serveIndexPage);
        get(Path.Web.LOGIN, LoginController.serveLoginPage);
        post(Path.Web.UPLOAD, UploadController.handleUploadPost);
        post(Path.Web.LOGIN, LoginController.handleLoginPost);
        post(Path.Web.LOGOUT, LoginController.handleLogoutPost);


    }
}
