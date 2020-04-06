package Controller.Web;

import spark.Request;
import spark.Response;

import java.text.ParseException;

public abstract class FrontCommand {
    protected Request request;
    protected Response response;

    public void init(Request request, Response response){
        this.request=request;
        this.response=response;
    }

    abstract public String process() throws Exception;
}
