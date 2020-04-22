package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;
import Implementations.TxtFileErrorLogWriter;
import View.writers.ErrorLogWriter;
import org.eclipse.jetty.http.HttpStatus;

import java.util.HashMap;

public class ErrorCommand extends FrontCommand {

    public static final String ERROR_TXT_LOG_PATH = "src/main/resources/error/ErrorLog.txt";
    private Exception exception;

    public ErrorCommand(Exception exception) {
        this.exception = exception;
    }


    @Override
    public String execute() {
        response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        writeErrorOnLog();
        return ViewUtil.render(request, new HashMap<>(), Path.Template.INTERNAL_ERROR);
    }

    private void writeErrorOnLog() {
        ErrorLogWriter errorWriter = new TxtFileErrorLogWriter(ERROR_TXT_LOG_PATH);
        errorWriter.write(exception);
    }
}
