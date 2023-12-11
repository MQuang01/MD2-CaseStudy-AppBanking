package casestudy.utils;

import java.util.regex.Pattern;

public class ValidateUtils {
    public static final String PHONE_NUMBER_REGEX = "^(0(32|33|34|35|36|37|38|39|86|96|97|98|90|91|92|93|" +
            "94|95|81|82|83|84|85|88|89|70|79|77|76|78|56|58|99|59))[0-9]{7}$";
    public static boolean isValidPhoneNumber(String strNumber){
        return Pattern.matches(PHONE_NUMBER_REGEX, strNumber);
    }

    public static void checkStatusReq(String item){
        if(true){
            System.out.println("APPROVE");
        }else {
            System.out.println("PENDING");
        }
    }
}
