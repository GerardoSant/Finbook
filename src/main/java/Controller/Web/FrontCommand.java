package Controller.Web;

import Controller.Command;
import spark.Request;
import spark.Response;

import java.text.ParseException;

public abstract class FrontCommand implements Command {
    protected Request request;
    protected Response response;

    public void init(Request request, Response response){
        this.request=request;
        this.response=response;
    }

    abstract public String execute() throws Exception;
}
