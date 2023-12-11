package casestudy.view;

import casestudy.AppBanking;
import casestudy.service.LoginService;
import casestudy.service.impl.LoginServiceImpl;
import casestudy.utils.Config;
import casestudy.utils.FileUtils;
import casestudy.utils.InputUtils;

public class LoginView {
    private final LoginService loginService;

    public LoginView(LoginService loginService) {
        loginService = new LoginServiceImpl();
        this.loginService = loginService;

        if (!FileUtils.checkFileExits(Config.PATH_FILE_INFORMATION)){
            loginService.initInfor();
        }else{
            loginService.setCurrentIdInfor();
        }

        if (!FileUtils.checkFileExits(Config.PATH_FILE_ADMIN)){
            loginService.initAdmin();
        }else{
            loginService.setCurrentIdAdmin();
        }

        if (!FileUtils.checkFileExits(Config.PATH_FILE_MEMBER)){
            loginService.initMember();
        }else{
            loginService.setCurrentIdMember();
        }

        if (!FileUtils.checkFileExits(Config.PATH_FILE_HISTORY)){
            loginService.initHistory();
        }else{
            loginService.setCurrentIdHistory();
        }
    }

    public void showMenuView() {
        do {
            System.out.println("------------------------------------------------------");
            System.out.println("|                                                    |");
            System.out.printf("%-20s %s %21s\n", "|", "App Banking", "|");
            System.out.println("|                                                    |");
            System.out.println("------------------------------------------------------");
            System.out.printf("%-20s %-12s %20s\n", "|", "1. Đăng nhập", "|");
            System.out.printf("%-20s %-12s %20s\n", "|", "2. Đăng ký", "|");
            System.out.printf("%-20s %-12s %20s\n", "|", "0. Thoát", "|");
            System.out.println("------------------------------------------------------");

            int choice = InputUtils.getNumberMinMax("Mời nhập lựa chọn: ",0,2);
            switch (choice){
                case 0:
                    System.exit(-1);
                case 1:
                    logIn();
                    break;
                case 2:
                    signIn();
                    break;
            }
        } while (true);
    }

    public void logIn() {
        loginService.checkLogin();
    }

    private void signIn() {
        loginService.inputInforSignIn();
    }
}
