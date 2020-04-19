package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;
import Implementations.FileErrorLogWriter;
import View.writers.ErrorLogWriter;
import org.eclipse.jetty.http.HttpStatus;

import java.util.HashMap;

public class ErrorCommand extends FrontCommand {

    private Exception exception;

    public ErrorCommand(Exception exception) {
        this.exception = exception;
    }


    @Override
    public String process() {
        response.status(HttpStatus.NOT_FOUND_404);
        writeErrorOnLog();
        return ViewUtil.render(request, new HashMap<>(), Path.Template.INTERNAL_ERROR);
    }

    private void writeErrorOnLog() {
        ErrorLogWriter errorWriter = new FileErrorLogWriter("src/main/resources/error/ErrorLog.txt");
        errorWriter.write(exception);
    }
}
