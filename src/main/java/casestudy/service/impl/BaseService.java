package casestudy.service.impl;

import casestudy.model.*;
import casestudy.service.AdminService;
import casestudy.service.LoginService;
import casestudy.service.MemberService;
import casestudy.utils.Config;
import casestudy.utils.FileUtils;
import casestudy.view.AdminView;
import casestudy.view.LoginView;
import casestudy.view.MemberView;

import java.util.List;

public class BaseService {
    private AdminView adminView;
    private LoginView loginView;
    private MemberView memberView;
    private AdminService adminService;
    private LoginService loginService;
    private MemberService memberService;
    private PassBookBank passBookBank;
    private History history;


    public BaseService() {
    }

    public BaseService(AdminView adminView, LoginView loginView, MemberView memberView,
                       AdminService adminService, LoginService loginService, MemberService memberService, PassBookBank passBookBank, History history) {
        this.adminView = adminView;
        this.loginView = loginView;
        this.memberView = memberView;
        this.adminService = adminService;
        this.loginService = loginService;
        this.memberService = memberService;
        this.passBookBank = passBookBank;
        this.history = history;
    }

    public void callAdminView(Admin admin){
        adminView = new AdminView(adminService);
        adminView.chooseAdminActive(admin);
    }

    public void callLogInView(){
        loginView = new LoginView(loginService);
        loginView.showMenuView();
    }

    public void callMemberView(Member member){
        memberView = new MemberView(memberService);
        memberView.chooseMemberActive(member);
    }

    public List<Information> getAllInfor() {
        return FileUtils.readFile(Config.PATH_FILE_INFORMATION, Information.class);
    }
    public List<Member> getAllMember() {
        return FileUtils.readFile(Config.PATH_FILE_MEMBER, Member.class);
    }
    public List<Admin> getAllAdmin() {
        return FileUtils.readFile(Config.PATH_FILE_ADMIN, Admin.class);
    }
    public List<History> getAllHistory() {
        return FileUtils.readFile(Config.PATH_FILE_HISTORY, History.class);
    }


    public AdminService getAdminService() {
        return adminService;
    }

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public MemberService getUserService() {
        return memberService;
    }

    public void setUserService(MemberService memberService) {
        this.memberService = memberService;
    }


    public AdminView getAdminView() {
        return adminView;
    }

    public void setAdminView(AdminView adminView) {
        this.adminView = adminView;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public MemberView getMemberView() {
        return memberView;
    }

    public void setMemberView(MemberView memberView) {
        this.memberView = memberView;
    }

    public MemberService getMemberService() {
        return memberService;
    }

    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    public PassBookBank getPassBookBank() {
        return passBookBank;
    }

    public void setPassBookBank(PassBookBank passBookBank) {
        this.passBookBank = passBookBank;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }
}
