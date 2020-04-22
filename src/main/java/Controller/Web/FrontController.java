package Controller.Web;

import Controller.Web.commands.ErrorCommand;
import Controller.Web.commands.UnknownCommand;
import spark.Request;
import spark.Response;
import spark.Route;


public class FrontController {
    public static final String COMMANDS_PATH = "Controller.Web.commands.";

    public static Route runCommand(String commandName) {
        return (request, response) -> {
            FrontCommand command = getCommand(commandName);
            command.init(request,response);
            return processCommand(request, response, command);
        };
    }

    private static FrontCommand getCommand(String commandName) throws Exception {
        FrontCommand command = (FrontCommand) getCommandClass(commandName).getDeclaredConstructor().newInstance();
        return command;
    }

    private static Class getCommandClass(String commandName) {
        Class commandClass;
        final String command = COMMANDS_PATH + commandName + "Command";
        try{
            commandClass = Class.forName(command);
        } catch (ClassNotFoundException e) {
            commandClass = UnknownCommand.class;
        }
        return commandClass;
    }

    private static Object processCommand(Request request, Response response, FrontCommand command) {
        try{
            return command.execute();
        } catch(Exception e){
            e.printStackTrace();
            return runErrorCommand(request, response, e);
        }
    }

    private static Object runErrorCommand(Request request, Response response, Exception e) {
        ErrorCommand errorCommand = new ErrorCommand(e);
        errorCommand.init(request, response);
        return errorCommand.execute();
    }









    /* AS */

    public static Route doGet = (Request request, Response response) -> {
      FrontCommand command = getCommand(request);
      command.init(request,response);
      try{
          return command.execute();
      } catch(Exception e){
          ErrorCommand errorCommand = new ErrorCommand(e);
          errorCommand.init(request,response);
          return errorCommand.execute();
      }
    };

    private static FrontCommand getCommand(Request request) throws Exception {
        FrontCommand f = (FrontCommand) getCommandClass(request).newInstance();
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


}
