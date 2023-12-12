package casestudy.service.impl;

import casestudy.model.*;
import casestudy.service.LoginService;
import casestudy.utils.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoginServiceImpl extends BaseService implements LoginService {

    @Override
    public void initInfor() {
        List<Information> inforList = new ArrayList<>();

        inforList.add(new Information(++Information.currentId, "Minh Quang 1", "0934960651", DateUtils.parse("2000-11-21")));
        inforList.add(new Information(++Information.currentId, "Minh Quang 2", "0771231232", DateUtils.parse("1898-05-11")));

        FileUtils.writeFile(inforList, Config.PATH_FILE_INFORMATION);
    }
    public void setCurrentIdInfor() {
        List<Information> inforList = getAllInfor();

        if(inforList.isEmpty()){
            Information.currentId = 1;
        }else {
            inforList.sort((o1, o2) -> Long.compare(o1.getId(), o2.getId()));
            Information.currentId = inforList.get(inforList.size() - 1).getId();
        }
    }
    public void initAdmin() {
        List<Admin> adminList = new ArrayList<>();

        adminList.add(new Admin(++Admin.currentId, "admin", "Minh Quang"));
        adminList.add(new Admin(++Admin.currentId, "admin1", "Quang Minh"));

        FileUtils.writeFile(adminList, Config.PATH_FILE_ADMIN);
    }
    public void setCurrentIdAdmin() {
        List<Admin> adminList = getAllAdmin();

        if (adminList.isEmpty()) {
            Admin.currentId = 1;
        }else {
            adminList.sort((o1, o2) -> Long.compare(o1.getId(), o2.getId()));
            Admin.currentId = adminList.get(adminList.size() - 1).getId();
        }
    }
    public void initMember() {
        List<Member> members = new ArrayList<>();


        members.add(new Member(++Member.currentId, "Quoc Anh", "0772717214", DateUtils.parse("2000-05-21"),500000, ERank.NORMAL));
        members.add(new Member(++Member.currentId, "Quoc Phu", "0795648524", DateUtils.parse("1995-06-14"),300000, ERank.NORMAL));

        FileUtils.writeFile(members, Config.PATH_FILE_MEMBER);
    }
    public void setCurrentIdMember() {
        List<Member> members = getAllMember();

        if (members.isEmpty()){
            Member.currentId = 1;
        }else {
            members.sort((o1, o2) -> Long.compare(o1.getId(), o2.getId()));
            Member.currentId = members.get(members.size() - 1).getId();
        }
    }
    @Override
    public void initHistory() {

        List<History> histories = new ArrayList<>();
        Member member = new Member(++Member.currentId, "Quangpro", "123123", DateUtils.parse("2001-2-1"), 300000, ERank.NORMAL);
        histories.add(new History(++History.currentId, member, "+300000", "Nạp tiền", DateUtils.parse("2023-11-12")));

        FileUtils.writeFile(histories, Config.PATH_FILE_HISTORY);
    }
    @Override
    public void setCurrentIdHistory() {
        List<History> histories = getAllHistory();

        if (histories.isEmpty()){
            Member.currentId = 1;
        }else {
            histories.sort((o1, o2) -> Long.compare(o1.getId(), o2.getId()));
            History.currentId = histories.get(histories.size() - 1).getId();
        }
    }


    public void checkLogin() {
        System.out.println("Nhập 0 để quay lại");
        String username = InputUtils.getString("Nhập tài khoản: ");
        if (username.equals("0")){
            callLogInView();
            return;
        }

        String psw = InputUtils.getString("Nhập mật khẩu: ");

        if (username.contains("admin")) {
            List<Admin> adminList = getAllAdmin();
            for (Admin ad : adminList) {
                if (ad.getUsername().equals(username) && ad.getPassword().equals(psw)) {
                    System.out.println("Đăng nhập thành công");
                    System.out.println("Xin chào " + ad.getFullName());
                    callAdminView(ad);
                }
            }
            System.err.println("Sai thông tin đăng nhập");
            callLogInView();
        } else {
            List<Member> memberList = getAllMember();
            for (Member member : memberList) {
                if (member.getUsername().equals(username) && member.getPassword().equals(psw)) {
                    System.out.println("Đăng nhập thành công");
                    System.out.println("Xin chào " + member.getFullName());
                    callMemberView(member);
                }
            }
            System.out.println("Sai thông tin đăng nhập");
            callLogInView();
        }
    }

    @Override
    public void inputRegisterInfor() {
        System.out.println("Nhập 'q' để thoát");
        String fullName = InputUtils.getString("Nhập họ và tên: ");
        if (fullName.equals("q")){
            callLogInView();
            return;
        }

        if(!ValidateUtils.isValidName(fullName)){
            System.err.println("Tên không được phép có ký tự (VD: Nguyen Van A)");
            inputRegisterInfor();
            return;
        }

        List<Information> information = getAllInfor();
        List<Member> members = getAllMember();

        String phoneNum = InputUtils.getPhoneNumber("Mời nhập số điện thoại: ");
        for (Member member : members){
            if(member.getUsername().equals(phoneNum)){
                System.err.println("Số điện thoại đã được đăng ký tài khoản");
                inputRegisterInfor();
                return;
            }
        }

        String dobStr = InputUtils.getString("Nhập ngày sinh nhật: ");
        if(!ValidateUtils.isValidDate(dobStr)){
            System.err.println("Nhập theo định dạng yyyy-MM-dd");
            inputRegisterInfor();
            return;
        }
        LocalDate doB = DateUtils.parse(dobStr);


        Information inforUser = new Information(++Information.currentId, fullName, phoneNum, doB);
        information.add(inforUser);

        FileUtils.writeFile(information, Config.PATH_FILE_INFORMATION);
        System.out.println("Gửi thông tin thành công\nKiểm tra điện thoại để nhận tài khoản và mật khẩu");
    }


}
