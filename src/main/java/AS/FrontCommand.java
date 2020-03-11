package AS;

import spark.Request;
import spark.Response;

public abstract class FrontCommand {
    protected Request request;
    protected Response response;

    public void init(Request request, Response response){
        this.request=request;
        this.response=response;
    }

    abstract public String process();
}
