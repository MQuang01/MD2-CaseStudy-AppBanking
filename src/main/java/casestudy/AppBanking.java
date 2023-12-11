package casestudy;

import casestudy.view.AdminView;
import casestudy.view.LoginView;
import casestudy.view.MemberView;

public class AppBanking {
    private LoginView loginView;
    private AdminView adminView;
    private MemberView memberView;


    public AppBanking(LoginView loginView, AdminView adminView, MemberView memberView) {
        this.loginView = loginView;
        this.adminView = adminView;
        this.memberView = memberView;
    }

    public void run() {
        loginView.MainView(this);
    }



    public LoginView getLoginView() {
        return loginView;
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public AdminView getAdminView() {
        return adminView;
    }

    public void setAdminView(AdminView adminView) {
        this.adminView = adminView;
    }

    public MemberView getUserView() {
        return memberView;
    }

    public void setUserView(MemberView memberView) {
        this.memberView = memberView;
    }
}
