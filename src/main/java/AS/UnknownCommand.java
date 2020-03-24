package AS;

import View.writers.ErrorLogWriter;
import Implementations.FileErrorLogWriter;
import org.eclipse.jetty.http.HttpStatus;
import Controller.util.Path;
import Controller.util.ViewUtil;

import java.util.HashMap;

public class UnknownCommand extends FrontCommand {

    private Exception exception;

    public UnknownCommand() {
        this.exception = new CommandNotFoundException();
    }

    public UnknownCommand(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String process() {
        response.status(HttpStatus.NOT_FOUND_404);
        writeErrorOnLog();
        return ViewUtil.render(request, new HashMap<>(), Path.Template.NOT_FOUND);
    }

    private void writeErrorOnLog() {
        ErrorLogWriter errorWriter = new FileErrorLogWriter("src/main/resources/error/ErrorLog.txt");
        errorWriter.write(exception);
    }
}
