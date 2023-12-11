package casestudy.service.impl;

import casestudy.model.Member;
import casestudy.service.MemberService;
import casestudy.utils.Config;
import casestudy.utils.FileUtils;
import casestudy.utils.InputUtils;

import java.util.List;

public class MemberServiceImpl extends BaseService implements MemberService {
    @Override
    public void addMoney(Member member) {
        long money = InputUtils.getNumber("Nhập số tiền muốn nạp: ");

        List<Member> members = getAllMember();
        for (Member m : members){
            if(m.getUsername().equals(member.getUsername())){
                m.setBalance(m.getBalance() + money);
                member.setBalance(member.getBalance() + money);
                System.out.println("Nạp tiền thành công");


                FileUtils.writeFile(members, Config.PATH_FILE_MEMBER);
                return;
            }
        }
        System.err.println("Nạp tiền không thành công");
    }
    @Override
    public void getInforMember(Member member) {
        List<Member> members = getAllMember();
        for (Member m : members){
            if(m.getId() == member.getId()){
                System.out.printf("%20s %20s\n", "Họ và Tên", "Số dư tài khoản");
                System.out.printf("%20s %20s\n", member.getFullName(), member.getBalance());
                return;
            }
        }
    }
    @Override
    public void transferTo(Member member) {
        List<Member> members = getAllMember();
        String numberCard = InputUtils.getString("Chuyển đến số tài khoản: ");
        if (numberCard == member.getUsername()){
            System.err.println("Không thể chuyển tiền vào số tài khoản của bạn");
        }

        for (Member m : members){
            if(m.getUsername().equals(numberCard)){
                long money = InputUtils.getNumber("Nhập số tiền cần chuyển: ");

                if(member.getBalance() < money){
                    System.err.println("Tài khoản không đủ");
                    callMemberView(member);
                }else {
                    // trừ tiền tài khoản
                    for (Member m1 : members){
                        if (m1.getUsername().equals(member.getUsername())){
                            m1.setBalance(m1.getBalance() - money);
                        }
                    }
                    member.setBalance(member.getBalance() - money);

                    // nạp tiền tài khoản
                    m.setBalance(m.getBalance() + money);

                    FileUtils.writeFile(members, Config.PATH_FILE_MEMBER);
                    System.out.println("Đã chuyển thành công");
                    return;
                }
            }
        }
        System.err.println("Không tìm thấy số tài khoản người nhận");
    }

    @Override
    public void getMoney(Member member) {
        List<Member> members = getAllMember();
        long money = InputUtils.getNumber("Nhập số tiền cần rút: ");
        for (Member m : members){
            if(m.getBalance() < money){
                System.err.println("Số dư tài khoản không đủ");
                callMemberView(member);
            }else {
                m.setBalance(m.getBalance() - money);
                member.setBalance(member.getBalance() - money);
                System.out.println("Rút tiền thành công");

                FileUtils.writeFile(members, Config.PATH_FILE_MEMBER);
                return;
            }
        }
        System.err.println("Rút tiền không thành công");
    }

    @Override
    public void logOut() {
        System.out.println("Đăng xuất thành công");
        callLogInView();
    }

    @Override
    public void choiceLevelPassBook(Member member) {
        long money = Long.parseLong(InputUtils.getString("Nhập số tiền bạn muốn gửi: "));
        List<Member> members = getAllMember();
        for(Member m : members){
            if (m.getBalance() < money){
                System.err.println("Số dư tài khoản không đủ");
                choiceLevelPassBook(member);
                return;
            }
        }

        System.out.println("------------------------------------------------------------------------");
        System.out.printf("Với %s này.\nBạn sẽ nhận được %s đồng sau 6 tháng.\n", money, Double.parseDouble(String.valueOf(Math.round((money * Math.pow(1 + 0.044 / 1, 1 * 6)) * 100 / 100))));
        System.out.println("------------------------------------------------------------------------");
        System.out.printf("Với %s này.\nBạn sẽ nhận được %s đồng sau 12 tháng.\n", money, Double.parseDouble(String.valueOf(Math.round((money * Math.pow(1 + 0.042 / 1, 1 * 12)) * 100 / 100))));
        System.out.println("------------------------------------------------------------------------");
        System.out.printf("Với %s này.\nBạn sẽ nhận được %s đồng sau 24 tháng.\n", money, Double.parseDouble(String.valueOf(Math.round((money * Math.pow(1 + 0.041 / 1, 1 * 24)) * 100 / 100))));

        System.out.println("------------------------------------------------------------------------");
        System.out.printf("%-20s %-30s %20s\n", "|", "Chọn hạn mức với lãi suất", "|");
        System.out.printf("%-20s %-30s %20s\n", "|", "1. 6 Tháng với 4,40% lãi suất", "|");
        System.out.printf("%-20s %-30s %20s\n", "|", "2. 12 Tháng với 4,20% lãi suất", "|");
        System.out.printf("%-20s %-30s %20s\n", "|", "3. 24 Tháng với 4,10% lãi suất", "|");
        System.out.printf("%-20s %-30s %20s\n", "|", "0. Quay lại", "|");
        System.out.println("------------------------------------------------------------------------");

        int choice = InputUtils.getNumberMinMax("Mời nhập: ", 0, 3);
        switch (choice){
            case 0:
                callMemberView(member);
                break;
            case 1:
            case 2:
            case 3:
                openPassBook(member, money);
                break;
        }
    }

    @Override
    public void openPassBook(Member member, long money) {
        List<Member> members = getAllMember();
        for (Member m : members){
            if (m.getUsername().equals(member.getUsername())){
                m.setBalance(m.getBalance() - money);
                member.setBalance(member.getBalance() - money);
                System.out.println("Đăng ký thành công");

                FileUtils.writeFile(members, Config.PATH_FILE_MEMBER);
                callMemberView(member);
                return;
            }
        }
        System.out.println("Không tìm thấy thông tin khách hàng\nKhông thể đăng ký sổ tiết kiệm");
    }
}
