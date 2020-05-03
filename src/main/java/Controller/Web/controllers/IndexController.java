package Controller.Web.controllers;

import spark.Request;
import spark.Response;
import spark.Route;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;

import java.util.HashMap;
import java.util.Map;

public class IndexController{
    

    public static Route serveIndexPage = (Request request, Response response) ->{
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.UPLOAD_BILLS);
    };
    public static Route serveMainIndexPage = (Request request, Response response) ->{
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.MAININDEX);
    };

    public static Route serveBillsIndexPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.BILLSINDEX);
    };
    public static Route serveReportsIndexPage = (Request request, Response response) ->{
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.REPORTSINDEX);
    };
    public static Route serveLandingPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.LANDING_PAGE);
    };
}
