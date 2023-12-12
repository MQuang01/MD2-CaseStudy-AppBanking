package casestudy.utils;

import casestudy.model.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class ValidateUtils {
    public static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";
    public static final String NAME_REGEX = "^[a-zA-Z\\s]+$";
    public static final String PHONE_NUMBER_REGEX = "^(0(32|33|34|35|36|37|38|39|86|96|97|98|90|91|92|93|" +
            "94|95|81|82|83|84|85|88|89|70|79|77|76|78|56|58|99|59))[0-9]{7,8}$";
    public static boolean isValidPhoneNumber(String strNumber){
        return Pattern.matches(PHONE_NUMBER_REGEX, strNumber);
    }

    public static boolean isValidName(String name){
        return Pattern.matches(NAME_REGEX, name);
    }

    public static boolean isValidDate(String date){
        return Pattern.matches(DATE_REGEX, date);
    }
}
