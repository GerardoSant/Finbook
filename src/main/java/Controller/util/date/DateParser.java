package Controller.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {

    private String format;

    public DateParser(String format) {
        this.format = format;
    }

    public Date parseDate(String date) throws ParseException {
        DateFormat df = new SimpleDateFormat(format);
        return df.parse(date);
    }
    
    
}
