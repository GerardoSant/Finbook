package Controller.Web;

import Controller.Web.commands.ErrorCommand;
import Controller.Web.commands.UnknownCommand;
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
          ErrorCommand errorCommand = new ErrorCommand(e);
          errorCommand.init(request,response);
          return errorCommand.process();
      }
    };

    private static FrontCommand getCommand(Request request) throws Exception {
        FrontCommand f = (FrontCommand) getCommandClass(request).newInstance();
        return f;
    }

    private static FrontCommand getCommand(String commandName) throws Exception {
        FrontCommand f = (FrontCommand) getCommandClass(commandName).newInstance();
        return f;
    }

    private static Class getCommandClass(Request request) {
        Class result;
        final String command = "Controller.Web.commands." + request.queryParams("command") + "Command";
        try{
            result = Class.forName(command);
        } catch (ClassNotFoundException e) {
            result = UnknownCommand.class;
        }
        return result;
    }

    private static Class getCommandClass(String commandName) {
        Class result;
        final String command = "Controller.Web.commands." + commandName + "Command";
        try{
            result = Class.forName(command);
        } catch (ClassNotFoundException e) {
            result = UnknownCommand.class;
        }
        return result;
    }

    public static Route runCommand(String commandName) {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                FrontCommand command = getCommand(commandName);
                command.init(request,response);
                try{
                    return command.process();
                } catch(Exception e){
                    e.printStackTrace();
                    ErrorCommand errorCommand = new ErrorCommand(e);
                    errorCommand.init(request,response);
                    return errorCommand.process();
                }
            }
        };
    }


}
