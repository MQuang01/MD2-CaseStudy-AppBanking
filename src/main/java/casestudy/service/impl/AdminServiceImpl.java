package casestudy.service.impl;

import casestudy.model.*;
import casestudy.service.AdminService;
import casestudy.utils.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class AdminServiceImpl extends BaseService implements AdminService {

    @Override
    public void getRequest() {
        List<Information> inforList = getAllInfor();

        //1,Minh Quang 1,0934960651,2000-11-21,false,null,null
        System.out.printf("%5s | %15s | %15s | %15s | %10s | %10s |\n",
                "ID", "Name", "Phone Number", "DoB", "Status", "Approve by");
        for (Information infor : inforList) {
            String strStatus = infor.isStatus() ? "APPROVED" : "PENDING";
            String inforApproved = infor.getApproved_by() != null ? infor.getApproved_by().getFullName() : "null";
            System.out.printf("%5s | %15s | %15s | %15s | %10s | %10s |\n",
                    infor.getId(), infor.getFullName(), infor.getPhoneNum(), infor.getDoB(), strStatus, inforApproved);
        }
    }

    @Override
    public void hanldeRequest(Admin admin) {
        List<Information> inforList = getInforList();
        long id = InputUtils.getNumber("Nhập id bạn muốn xử lý (nhập 0 để quay lại): ");
        if (id == 0) {
            callAdminView(admin);
            return;
        }

        for (Information infor : inforList) {
            if (infor.getId() == id) {
                if (infor.getApproved_by() != null) {
                    System.err.println("Thông tin này đã được xử lý");
                    callAdminView(admin);
                }
            }
            if (infor.getId() == id) {
                System.out.println("0. Thoát");
                System.out.println("1. Tạo tài khoản");
                System.out.println("2. Hủy yêu cầu");
                System.out.println("3. Quay lại");

                int choice = InputUtils.getNumberMinMax("Mời nhập: ", 0, 3);
                switch (choice) {
                    case 0:
                        System.exit(-1);
                        break;
                    case 1:
                        createNewMember(admin, infor);
                        showAllMember();
                        callAdminView(admin);
                        break;
                    case 2:
                        deleteRequest(admin, infor);
                        break;
                    case 3:
                        hanldeRequest(admin);
                        break;
                }
            }
        }
        System.out.println("Không tìm thấy id");
    }

    @Override
    public void showAllMember() {
        List<Member> members = getAllMember();

        //1,0772717214,123123,0772717214,Quoc Anh,2000-05-21,1000000,NORMAL
        System.out.printf("%5s | %15s | %15s | %15s | %15s | %15s | %10s | %10s | %10s |\n",
                "ID", "Username", "Password", "Phone Number", "Name member", "DoB", "Balance", "Ranked", "PassBook");
        for (Member m : members) {
            System.out.printf("%5s | %15s | %15s | %15s | %15s | %15s | %10s | %10s | %10s |\n",
                    m.getId(), m.getUsername(), m.getPassword(), m.getPhoneNum(),
                    m.getFullName(), m.getDoB(), m.getBalance(), m.geteRank(), m.getPassBookBankQuantity());
        }
    }

    public List<Information> getInforList() {
        return getAllInfor();
    }

    @Override
    public void createNewMember(Admin admin, Information information) {
        List<Member> members = getAllMember();
        for (Member member : members){
            if(member.getPhoneNum().equals(information.getPhoneNum())){
                System.err.println("Số điện thoại này đã được đăng ký");
                System.err.println("Yêu cầu admin hủy yêu cầu");
                System.out.printf("%5s | %15s | %15s | %15s\n",
                        information.getId(), information.getFullName(), information.getPhoneNum(), information.getDoB());
                hanldeRequest(admin);
                return;
            }
        }

        Member newMember = new Member(++Member.currentId, information.getFullName(), information.getPhoneNum(), information.getDoB(), 100000, ERank.NORMAL);

        members.add(newMember);

        FileUtils.writeFile(members, Config.PATH_FILE_MEMBER);
        System.out.println("Đã tạo tài khoản thành công");

        List<Information> inforList = getAllInfor();
        for (Information infor : inforList) {
            if (infor.getPhoneNum().equals(information.getPhoneNum())) {
                infor.setStatus(true);
                infor.setApproved_by(admin);

                FileUtils.writeFile(inforList, Config.PATH_FILE_INFORMATION);
                return;
            }
        }
    }

    @Override
    public void logOut() {
        System.out.println("Đăng xuất thành công");
        callLogInView();
    }

    @Override
    public void deleteRequest(Admin admin, Information information) {
        List<Information> inforList = getAllInfor();
        for (Information infor : inforList) {
            if (infor.getPhoneNum().equals(information.getPhoneNum())) {
                inforList.remove(infor);

                FileUtils.writeFile(inforList, Config.PATH_FILE_INFORMATION);
                System.out.println("Đã xóa thành công");
                return;
            }
        }
    }

    @Override
    public void createNewMemberBy(Admin admin) {
        List<Member> memberList = getAllMember();
        String fullName = InputUtils.getString("Nhập họ và tên: ");
        String phoneNum = InputUtils.getPhoneNumber("Mời nhập số điện thoại: ");
        for (Member member : memberList) {
            if (member.getUsername().equals(phoneNum)) {
                System.err.println("Số điện thoại này đã được đăng ký tài khoản");
                createNewMemberBy(admin);
                return;
            }
        }

        LocalDate DoB = DateUtils.parse(InputUtils.getString("Nhập ngày sinh (yyyy-MM-dd): "));
        long money = InputUtils.getNumber("Nhập số tiền gửi vào tài khoản: ");
        ERank eRank = ERank.valueOf(InputUtils.getString("Nhập xếp hạng của tài khoản (NORMAL or VIP): ").toUpperCase());

        Member newMember = new Member(++Member.currentId, fullName, phoneNum, DoB, money, eRank);

        memberList.add(newMember);

        FileUtils.writeFile(memberList, Config.PATH_FILE_MEMBER);
        System.out.println("Khởi tạo tài khoản thành công!");
    }

    @Override
    public void deleteMemberBy(Admin admin) {
        List<Member> members = getAllMember();

        System.out.println("0. Quay lại");
        System.out.println("1. Xóa theo id");
        System.out.println("2. Xóa theo số tài khoản");

        int choice = InputUtils.getNumberMinMax("Mời nhập: ", 0, 2);
        switch (choice) {
            case 0:
                callAdminView(admin);
                break;
            case 1:
                showAllMember();
                long id = InputUtils.getNumber("Nhập id muốn xóa: ");
                for (Member m : members) {
                    if (m.getId() == id) {
                        members.remove(m);

                        FileUtils.writeFile(members, Config.PATH_FILE_MEMBER);
                        System.out.println("Đã xóa thành công");
                        showAllMember();
                        return;
                    }
                }
                System.err.println("Xóa thất bại hoặc không tìm thấy id");
                break;
            case 2:
                showAllMember();
                String numCardBank = InputUtils.getString("Nhập số tài khoản muốn xóa: ");
                for (Member m : members) {
                    if (m.getUsername().equals(numCardBank)) {
                        members.remove(m);

                        FileUtils.writeFile(members, Config.PATH_FILE_MEMBER);
                        System.out.println("Đã xóa thành công");
                        showAllMember();
                        return;
                    }
                }
                System.err.println("Xóa thất bại hoặc không tìm thấy số tài khoản");
                break;
        }
    }

    @Override
    public void editInforMember(Admin admin) {
        showAllMember();
        long id = InputUtils.getNumber("Nhập id muốn chỉnh sửa (nhập 0 để quay lại): ");
        if (id == 0) {
            callAdminView(admin);
            return;
        }
        updateInfor(admin, id);
    }

    @Override
    public void findBy(Admin admin) {
        List<Member> members = getAllMember();
        System.out.println("0. Quay lại");
        System.out.println("1. Tìm kiếm theo id");
        System.out.println("2. Tìm kiếm theo số tài khoản");

        int choice = InputUtils.getNumberMinMax("Mời nhập: ", 0, 2);

        switch (choice) {
            case 0:
                callAdminView(admin);
                break;
            case 1:
                long id = InputUtils.getNumber("Nhập id muốn tìm: ");
                for (Member m : members) {
                    if (m.getId() == id) {
                        System.out.printf("%5s | %15s | %15s | %15s | %15s | %15s | %10s | %10s | %10s |\n",
                                "ID", "Username", "Password", "Phone Number", "Name member", "DoB", "Balance", "Ranked", "PassBook");
                        System.out.printf("%5s | %15s | %15s | %15s | %15s | %15s | %10s | %10s |\n",
                                m.getId(), m.getUsername(), m.getPassword(), m.getPhoneNum(),
                                m.getFullName(), m.getDoB(), m.getBalance(), m.geteRank(), m.getPassBookBankQuantity());
                        return;
                    }
                }
                System.err.println("Không tìm thấy id");
                break;
            case 2:
                String numberCardBank = InputUtils.getString("Nhập số tài khoản cần tìm: ");
                for (Member m : members) {
                    if (m.getUsername().equals(numberCardBank)) {
                        System.out.printf("%5s | %15s | %15s | %15s | %15s | %15s | %10s | %10s | %10s |\n",
                                "ID", "Username", "Password", "Phone Number", "Name member", "DoB", "Balance", "Ranked", "PassBook");
                        System.out.printf("%5s | %15s | %15s | %15s | %15s | %15s | %10s | %10s |\n",
                                m.getId(), m.getUsername(), m.getPassword(), m.getPhoneNum(),
                                m.getFullName(), m.getDoB(), m.getBalance(), m.geteRank(), m.getPassBookBankQuantity());
                        return;
                    }
                }
                System.err.println("Không tìm thấy số tài khoản");
                break;
        }
    }

    @Override
    public void getHistoryList(Admin admin) {
        System.out.println("1. Tìm kiếm theo tên");
        System.out.println("2. Tìm kiếm theo hình thức giao dịch");
        System.out.println("3. Tìm kiếm theo ngày");
        System.out.println("4. Tìm kiếm theo tháng");
        System.out.println("5. Tìm kiếm theo năm");
        System.out.println("6. Hiển thị tất cả");
        System.out.println("0. Quay lại");

        int choice = InputUtils.getNumberMinMax("Mời nhập", 0, 6);
        switch (choice){
            case 0:
                callAdminView(admin);
                break;
            case 1:
                String fullName = InputUtils.getString("Nhập tên tìm kiếm: ");
                showHistoryByName(fullName);
                break;
            case 2:
                String payments = InputUtils.getString("Nhập hình thức thanh toán: ");
                showHistoryByPayments(payments);
                break;
            case 3:
                int daySearch = InputUtils.getNumberMinMax("Nhập ngày cần tìm: ", 1, 31);
                showHistoryByDay(daySearch);
                break;
            case 4:
                Month monthSearch = Month.of(InputUtils.getNumberMinMax("Nhập tháng cần tìm: ", 1, 12));
                showHistoryByMonth(monthSearch);
                break;
            case 5:
                int yearSearch = InputUtils.getNumberMinMax("Nhập năm cần tìm: ", 1990, 2023);
                showHistoryByYear(yearSearch);
                break;
            case 6:
                showAllTransaction();
                break;

        }
    }

    public void showAllTransaction(){
        List<History> histories = getAllHistory();
        if (histories.isEmpty()){
            System.out.println("No found transaction");
            return;
        }

        System.out.printf("%5s | %15s | %15s | %15s | %20s |\n", "ID", "Full Name", "Money", "Action", "Create At");
        for (History history : histories){
            System.out.printf("%5s | %15s | %15s | %15s | %20s |\n",
                    history.getId(), history.getMember().getFullName(), history.getMoney(), history.getAction(), history.getCreateAt());
        }

    }
    public void showHistoryByYear(int yearSearch) {
        List<History> histories = getAllHistory();
        if (histories.isEmpty()){
            System.out.println("No found transaction");
            return;
        }

        List<History> listResult = new ArrayList<>();
        for (History history : histories){
            if(history.getCreateAt().getYear() == yearSearch){
                listResult.add(history);
            }
        }

        if (listResult.isEmpty()){
            System.out.println("No found transaction");
        }else {
            System.out.printf("%5s | %15s | %15s | %15s | %20s |\n", "ID", "Full Name", "Money", "Action", "Create At");
            for (History histori1 : listResult){
                System.out.printf("%5s | %15s | %15s | %15s | %20s |\n",
                        histori1.getId(), histori1.getMember().getFullName(), histori1.getMoney(), histori1.getAction(), histori1.getCreateAt());
            }
        }
    }

    public void showHistoryByMonth(Month monthSearch) {
        List<History> histories = getAllHistory();
        if (histories.isEmpty()){
            System.out.println("No found transaction");
            return;
        }

        List<History> listResult = new ArrayList<>();
        for (History history : histories){
            if(history.getCreateAt().getMonth() == monthSearch){
                listResult.add(history);
            }
        }

        if (listResult.isEmpty()){
            System.out.println("No found transaction");
        }else {
            System.out.printf("%5s | %15s | %15s | %15s | %20s |\n", "ID", "Full Name", "Money", "Action", "Create At");
            for (History histori1 : listResult){
                System.out.printf("%5s | %15s | %15s | %15s | %20s |\n",
                        histori1.getId(), histori1.getMember().getFullName(), histori1.getMoney(), histori1.getAction(), histori1.getCreateAt());
            }
        }
    }

    public void showHistoryByDay(int daySearch) {
        List<History> histories = getAllHistory();
        if (histories.isEmpty()){
            System.out.println("No found transaction");
            return;
        }

        List<History> listResult = new ArrayList<>();
        for (History history : histories){
            if(history.getCreateAt().getDayOfMonth() == daySearch){
                listResult.add(history);
            }
        }

        if (listResult.isEmpty()){
            System.out.println("No found transaction");
        }else {
            System.out.printf("%5s | %15s | %15s | %15s | %20s |\n", "ID", "Full Name", "Money", "Action", "Create At");
            for (History histori1 : listResult){
                System.out.printf("%5s | %15s | %15s | %15s | %20s |\n",
                        histori1.getId(), histori1.getMember().getFullName(), histori1.getMoney(), histori1.getAction(), histori1.getCreateAt());
            }
        }
    }

    public void showHistoryByName(String fullName) {
        List<History> histories = getAllHistory();
        if (histories.isEmpty()){
            System.out.println("No found transaction");
            return;
        }

        List<History> listResult = new ArrayList<>();

        for (History history : histories){
            if(history.getMember().getFullName().equalsIgnoreCase(fullName)){
                listResult.add(history);
            }
        }

        if (listResult.isEmpty()){
            System.out.println("No found transaction");
        }else {
            System.out.printf("%5s | %15s | %15s | %15s | %20s |\n", "ID", "Full Name", "Money", "Action", "Create At");
            for (History histori1 : listResult){
                System.out.printf("%5s | %15s | %15s | %15s | %20s |\n",
                        histori1.getId(), histori1.getMember().getFullName(), histori1.getMoney(), histori1.getAction(), histori1.getCreateAt());
            }
        }
    }

    public void showHistoryByPayments(String payments) {
        List<History> histories = getAllHistory();
        if (histories.isEmpty()){
            System.out.println("No found transaction");
            return;
        }

        List<History> listResult = new ArrayList<>();

        System.out.printf("%5s | %15s | %15s | %15s | %20s |\n", "ID", "Full Name", "Money", "Action", "Create At");
        for (History history : histories){
            if(history.getAction().equalsIgnoreCase(payments)){
                listResult.add(history);
            }
        }

        if (listResult.isEmpty()){
            System.out.println("No found transaction");
        }else {
            System.out.printf("%5s | %15s | %15s | %15s | %20s |\n", "ID", "Full Name", "Money", "Action", "Create At");
            for (History histori1 : listResult){
                System.out.printf("%5s | %15s | %15s | %15s | %20s |\n",
                        histori1.getId(), histori1.getMember().getFullName(), histori1.getMoney(), histori1.getAction(), histori1.getCreateAt());
            }
        }
    }

    public void updateInfor(Admin admin, Long id) {
        List<Member> members = getAllMember();

        System.out.printf("%5s | %15s | %15s | %15s | %15s | %15s | %10s | %10s | %10s |\n",
                "ID", "Username", "Password", "Phone Number", "Name member", "DoB", "Balance", "Ranked", "PassBook");
        for (Member m : members) {
            if (m.getId() == id) {
                System.out.printf("%5s | %15s | %15s | %15s | %15s | %15s | %10s | %10s |\n",
                        m.getId(), m.getUsername(), m.getPassword(), m.getPhoneNum(),
                        m.getFullName(), m.getDoB(), m.getBalance(), m.geteRank(), m.getPassBookBankQuantity());
                do {
                    System.out.println("0. Quay lại");
                    System.out.println("1. Chỉnh sửa số tài khoản");
                    System.out.println("2. Chỉnh sửa mật khẩu");
                    System.out.println("3. Chỉnh sửa số điện thoại");
                    System.out.println("4. Chỉnh sửa tên người dùng");
                    System.out.println("5. Chỉnh sửa ngày sinh");
                    System.out.println("6. Chỉnh sửa số dư");
                    System.out.println("7. Chỉnh sửa thứ hạng");

                    int choice = InputUtils.getNumberMinMax("Mời nhập trường muốn chỉnh sửa: ", 0, 7);
                    switch (choice) {
                        case 0:
                            editInforMember(admin);
                            break;
                        case 1:
                            String numCardBankUpd = InputUtils.getString("Nhập số tài khoản: ");
                            List<Member> memberList = getAllMember();
                            for (Member member : memberList) {
                                if (member.getUsername().equals(numCardBankUpd) || !ValidateUtils.isValidPhoneNumber(numCardBankUpd)) {
                                    System.out.println("Số tài khoản đã tồn tại\nHoặc không đúng định dạng");
                                    return;
                                }
                            }
                            m.setUsername(numCardBankUpd);

                            break;
                        case 2:
                            String pswUpd = InputUtils.getString("Nhập mật khẩu: ");
                            m.setPassword(pswUpd);

                            break;
                        case 3:
                            String phoneNumUpd = InputUtils.getPhoneNumber("Nhập số điện thoại: ");
                            if (!ValidateUtils.isValidPhoneNumber(phoneNumUpd)) {
                                System.out.println("Hoặc không đúng định dạng");
                                return;
                            }

                            m.setPhoneNum(phoneNumUpd);

                            break;
                        case 4:
                            String fullNameUpd = InputUtils.getString("Nhập họ và tên: ");
                            if(!ValidateUtils.isValidName(fullNameUpd)){
                                System.out.println("Hoặc không đúng định dạng");
                                return;
                            }

                            m.setFullName(fullNameUpd);

                            break;
                        case 5:
                            LocalDate dobUpd = DateUtils.parse(InputUtils.getString("Nhập ngày sinh (yyyy-MM-dd): "));
                            m.setDoB(dobUpd);
                            break;
                        case 6:
                            long balanceUpd = InputUtils.getNumber("Nhập số dư thay đổi: ");
                            m.setBalance(balanceUpd);

                            break;
                        case 7:
                            ERank eRankUpd = ERank.valueOf(InputUtils.getString("Nhập thứ hạng (NORMAL or VIP): "));
                            m.seteRank(eRankUpd);

                            break;
                    }

                    System.out.printf("%5s | %15s | %15s | %15s | %15s | %15s | %10s | %10s | %10s |\n",
                            "ID", "Username", "Password", "Phone Number", "Name member", "DoB", "Balance", "Ranked", "PassBook");
                    System.out.printf("%5s | %15s | %15s | %15s | %15s | %15s | %10s | %10s |\n",
                            m.getId(), m.getUsername(), m.getPassword(), m.getPhoneNum(),
                            m.getFullName(), m.getDoB(), m.getBalance(), m.geteRank(), m.getPassBookBankQuantity());

                    System.out.println("1. Tiếp tục chỉnh sửa");
                    System.out.println("2. Lưu chỉnh sửa");
                    System.out.println("3. Hủy bỏ chỉnh sửa");

                    int choice1 = InputUtils.getNumberMinMax("Mời nhập: ", 1, 3);
                    switch (choice1) {
                        case 1:
                            break;
                        case 2:
                            FileUtils.writeFile(members, Config.PATH_FILE_MEMBER);
                            System.out.println("Chỉnh sửa thành công");
                            callAdminView(admin);
                            break;
                        case 3:
                            editInforMember(admin);
                            break;
                    }
                } while (true);
            }
        }
        System.out.println("Không tìm thấy id");
    }
}

