package casestudy.view;

import casestudy.AppBanking;
import casestudy.model.Information;
import casestudy.service.LoginService;
import casestudy.service.impl.LoginServiceImpl;
import casestudy.utils.Config;
import casestudy.utils.DateUtils;
import casestudy.utils.FileUtils;
import casestudy.utils.InputUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    }

    public void MainView(AppBanking appBanking) {
        do {
            System.out.println("-----------------------------------------------------");
            System.out.println("|                                                   |");
            System.out.printf("%-20s %s %20s\n", "|", "App Banking", "|");
            System.out.println("|                                                   |");
            System.out.println("-----------------------------------------------------");
            System.out.printf("%-20s %-11s %20s\n", "|", "1. Login", "|");
            System.out.printf("%-20s %-11s %20s\n", "|", "2. Sign in", "|");
            System.out.println("-----------------------------------------------------");

            int choice = InputUtils.getNumber("Mời nhập lựa chọn: ");
            switch (choice){
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
        String username = InputUtils.getString("Nhập tài khoản: ");
        String password = InputUtils.getString("Nhập mật khẩu: ");

        loginService.checkLogin(username, password);

    }

    private void signIn() {
        loginService.inputInforSignIn();
    }
}
