package Controller.util.date;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateCalculator {

    public int daysBetween(Date firstDate, Date secondDate){
        long diff = firstDate.getTime() - secondDate.getTime();
        return (int) Math.abs(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    }

    public int dayOfYear(Date date){
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getDayOfYear();
    }
}
