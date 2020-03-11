package Log;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;

public class FileErrorLogWriter implements ErrorLogWriter {
    private String filePath;

    public FileErrorLogWriter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void write(Exception exception) {
        File file = new File(filePath);
        try{
            FileWriter fr =new FileWriter(file,true);
            fr.write(errorText(exception));
            fr.close();
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    private String errorText(Exception exception) {
        String result= new Date().toString() + "\n" + "Error message:\n" + exception.getMessage() + "\n" + "Error Stack Trace:\n"+
                exception.getStackTrace() + "\n" + getStrackTraceText(exception) + "\n" +  "----------------\n";
        return result;
    }

    private String getStrackTraceText(Exception exception) {
        String result="";
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            result += stackTraceElement.toString() + "\n";
        }
        return result;
    }
}
