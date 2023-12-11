package casestudy.utils;

import java.util.Scanner;

public class InputUtils {
    public static Scanner sc = new Scanner(System.in);
    public static int getNumberMinMax(String title, int min, int max) throws IndexOutOfBoundsException {
        System.out.println(title);
        int num;
        try {
            num = Integer.parseInt(sc.nextLine());
            if (num < min || num > max) {
                System.err.println("Chọn từ khoảng " + min + " và " + max);
                return getNumberMinMax(title, min, max);
            }
            return num;
        } catch (Exception e) {
            System.err.println("Không đúng định dạng");
            return getNumberMinMax(title, min, max);
        }
    }

    public static String getString(String title) {
        try {
            System.out.println(title);
            String data = sc.nextLine();
            if (data.equals("")) {
                throw new Exception();
            }
            return data;
        } catch (Exception e) {
            System.err.println("Dữ liệu không đúng kiểu \nVui lòng nhập lại: ");
            return getString(title);
        }
    }

    public static int getNumber(String title) {
        try {
            return Integer.parseInt(getString(title));
        } catch (Exception e) {
            System.err.println("Dữ liệu không đúng kiểu \nVui lòng nhập lại:");
            return getNumber(title);
        }
    }

    public static String getPhoneNumber(String title){
        try {
            String phoneNumber = getString(title);
            if (ValidateUtils.isValidPhoneNumber(phoneNumber)){
                return phoneNumber;
            }else {
                throw new Exception();
            }
        }catch (Exception e) {
            System.err.println("Dữ liệu không đúng kiểu \nVui lòng nhập lại:");
            return getPhoneNumber(title);
        }
    }



}
