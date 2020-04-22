package Implementations;

import View.writers.ErrorLogWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class TxtFileErrorLogWriter implements ErrorLogWriter {
    private String filePath;

    public TxtFileErrorLogWriter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void write(Exception exception) {
        try{
            writeExceptionOnFile(exception, new File(filePath));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void writeExceptionOnFile(Exception exception, File file) throws IOException {
        FileWriter fr =new FileWriter(file,true);
        fr.write(errorLog(exception));
        fr.close();
    }

    private String errorLog(Exception exception) {
        String errorText= new Date().toString() + "\n" + "Error message:\n" + exception.getMessage() + "\n" + "Error Stack Trace:\n"+
                exception.getStackTrace() + "\n" + getStrackTraceText(exception) + "\n" +  "----------------\n";
        return errorText;
    }

    private String getStrackTraceText(Exception exception) {
        String stackTraceText="";
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            stackTraceText += stackTraceElement.toString() + "\n";
        }
        return stackTraceText;
    }
}
