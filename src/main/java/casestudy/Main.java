package casestudy;

import casestudy.service.AdminService;
import casestudy.service.LoginService;
import casestudy.service.MemberService;
import casestudy.service.impl.AdminServiceImpl;
import casestudy.service.impl.LoginServiceImpl;
import casestudy.service.impl.MemberServiceImpl;
import casestudy.view.AdminView;
import casestudy.view.LoginView;
import casestudy.view.MemberView;

public class Main {
    public static void main(String[] args) {
        AdminService adminService= new AdminServiceImpl();
        MemberService memberService = new MemberServiceImpl();

        AdminView adminView = new AdminView(adminService);
        LoginService loginService= new LoginServiceImpl();
        LoginView loginView = new LoginView(loginService);

        MemberView memberView = new MemberView(memberService);


        AppBanking appBanking = new AppBanking(loginView,adminView, memberView);
        appBanking.run();
    }

}
