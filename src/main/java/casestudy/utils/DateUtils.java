package casestudy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class DateUtils {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static LocalDate parse(String strDate) {
        try{
            LocalDate date = simpleDateFormat.parse(strDate).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            return date;
        }catch (ParseException parseException){
            parseException.printStackTrace();
        }
        return null;

    }
}