package AS;

import spark.Request;
import spark.Response;
import spark.Route;


public class FrontServlet {
    public static Route doGet = (Request request, Response response) -> {
      FrontCommand command = getCommand(request);
      command.init(request,response);
      try{
          return command.process();
      } catch(Exception e){
          UnknownCommand errorCommand = new UnknownCommand(e);
          errorCommand.init(request,response);
          return errorCommand.process();
      }
    };

    private static FrontCommand getCommand(Request request) throws Exception {
        FrontCommand f = (FrontCommand) getCommandClass(request).newInstance();
        return f;
    }

    private static Class getCommandClass(Request request) {
        Class result;
        final String command = "AS." + request.queryParams("command") + "Command";
        try{
            result = Class.forName(command);
        } catch (ClassNotFoundException e) {
            result = UnknownCommand.class;
        }
        return result;
    }

}
