package casestudy.service.impl;

import casestudy.model.ETypePassBook;
import casestudy.model.History;
import casestudy.model.Member;
import casestudy.model.PassBookBank;
import casestudy.service.MemberService;
import casestudy.utils.Config;

import casestudy.utils.FileUtils;
import casestudy.utils.InputUtils;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class MemberServiceImpl extends BaseService implements MemberService {
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
    public void addMoney(Member member) {
        long money = InputUtils.getNumber("Nhập số tiền muốn nạp: ");
        if (money < 10000 || money > 2000000000){
            System.err.println("Nạp tối thiểu 10 nghìn đồng và tối đa 2 tỷ");
            addMoney(member);
            return;
        }

        List<Member> members = getAllMember();
        for (Member m : members){
            if(m.getUsername().equals(member.getUsername())){
                m.setBalance(m.getBalance() + money);
                member.setBalance(member.getBalance() + money);
                System.out.println("Nạp tiền thành công");

                History history = new History(++History.currentId, m, "+" + money, "Nạp tiền", LocalDate.now());
                List<History> histories = getAllHistory();
                histories.add(history);

                FileUtils.writeFile(histories, Config.PATH_FILE_HISTORY);
                FileUtils.writeFile(members, Config.PATH_FILE_MEMBER);
                return;
            }
        }
        System.err.println("Nạp tiền không thành công");
    }
    @Override
    public void transferTo(Member member) {
        getInforMember(member);
        String numberCard = InputUtils.getString("Chuyển đến số tài khoản: ");
        if (numberCard.equals(member.getUsername())){
            System.err.println("Không thể chuyển tiền vào số tài khoản của bạn");
            transferTo(member);
            return;
        }

        inputMoneyForTransfer(numberCard, member);
//        List<Member> members = getAllMember();
//        for (Member m : members){
//            if(m.getUsername().equals(numberCard)){
//                long money = InputUtils.getNumber("Nhập số tiền cần chuyển: ");
//
//                if(m.getBalance() < money){
//                    System.err.println("Tài khoản không đủ");
//                    callMemberView(member);
//                    return;
//                }else if (money < 10000 || money > 100000000){
//                    System.err.println("Rút tối thiểu 10 nghìn đồng và tối đa 100 triệu");
//                    transferTo(member);
//                    return;
//                } else {
//                    // trừ tiền tài khoản
//                    for (Member m1 : members){
//                        if (m1.getUsername().equals(member.getUsername())){
//                            m1.setBalance(m1.getBalance() - money);
//                        }
//                    }
//                    member.setBalance(member.getBalance() - money);
//
//                    // nạp tiền tài khoản
//                    m.setBalance(m.getBalance() + money);
//
//                    History history1 = new History(++History.currentId, member, "-" + money, "Chuyển tiền", LocalDate.now());
//                    History history2 = new History(++History.currentId, m, "+" + money, "Nhận tiền", LocalDate.now());
//                    List<History> histories = getAllHistory();
//                    histories.add(history1);
//                    histories.add(history2);
//
//                    FileUtils.writeFile(histories, Config.PATH_FILE_HISTORY);
//                    FileUtils.writeFile(members, Config.PATH_FILE_MEMBER);
//                    System.out.println("Đã chuyển thành công");
//                    return;
//                }
//            }
//        }
//        System.err.println("Không tìm thấy số tài khoản người nhận");
    }
    private void inputMoneyForTransfer(String numberCard, Member member){
        List<Member> members = getAllMember();
        for (Member m : members){
            if(m.getUsername().equals(numberCard)){
                long money = InputUtils.getNumber("Nhập số tiền cần chuyển: ");

                if(m.getBalance() < money){
                    System.err.println("Tài khoản không đủ");
                    callMemberView(member);
                    return;
                }else if (money < 10000 || money > 100000000){
                    System.err.println("Chuyển tối thiểu 10 nghìn đồng và tối đa 100 triệu");
                    inputMoneyForTransfer(numberCard, member);
                    return;
                } else {
                    // người chuyển trừ tiền
                    for (Member m1 : members){
                        if (m1.getUsername().equals(member.getUsername())){
                            m1.setBalance(m1.getBalance() - money);
                        }
                    }
                    member.setBalance(member.getBalance() - money);

                    // người được chuyển nhận tiền
                    m.setBalance(m.getBalance() + money);

                    History history1 = new History(++History.currentId, member, "-" + money, "Chuyển tiền", LocalDate.now());
                    History history2 = new History(++History.currentId, m, "+" + money, "Nhận tiền", LocalDate.now());
                    List<History> histories = getAllHistory();
                    histories.add(history1);
                    histories.add(history2);

                    FileUtils.writeFile(histories, Config.PATH_FILE_HISTORY);
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
        long money = InputUtils.getNumber("Nhập số tiền cần rút (Nhập 0 để quay lại): ");
        if (money == 0){
            callMemberView(member);
            return;
        }

        if (money < 10000 || money > 100000000){
            System.err.println("Rút tối thiểu 10 nghìn đồng và tối đa 100 triệu");
            getMoney(member);
            return;
        }

        List<Member> members = getAllMember();
        for (Member m : members){
            if(m.getBalance() < money){
                System.err.println("Số dư tài khoản không đủ");
                getInforMember(member);
                getMoney(member);
                return;
            }else {
                m.setBalance(m.getBalance() - money);
                member.setBalance(member.getBalance() - money);
                System.out.println("Rút tiền thành công");

                History history = new History(++History.currentId, m, "-" + money, "Rút tiền", LocalDate.now());
                List<History> histories = getAllHistory();
                histories.add(history);

                FileUtils.writeFile(histories, Config.PATH_FILE_HISTORY);
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
    public void showListPassBook(Member member) {
        if (!member.getPassBookBanks().isEmpty()){
            System.out.printf("%5s | %20s | %15s | %20s | %20s | %10s |\n",
                    "ID", "Type", "Value", "Registration_At", "Maturity_At", "Status");
            for(PassBookBank psBook : member.getPassBookBanks()){

                LocalDate maturDate = psBook.getDateMaturity();
                if (LocalDate.now().isBefore(maturDate)){
                    psBook.setStatus(false);
                }else {
                    psBook.setStatus(true);
                }

                String checkStatus = psBook.isStatus() ? "Đã rút" : "Chưa rút";
                System.out.printf("%5s | %20s | %15s | %20s | %20s | %10s |\n",
                        psBook.getId(), psBook.getTypePassBook(), psBook.getValueOfBook(), psBook.getDateRegistration(), psBook.getDateMaturity(), checkStatus);

                if(checkStatus.equals("Đã rút")){
                    psBook.setValueOfBook(0);
                    member.setBalance(member.getBalance() + Long.parseLong(String.valueOf(Math.round((psBook.getValueOfBook() * Math.pow(1 + 0.041, 24)) * 100 / 100))));
                }
            }
        }else {
            System.out.println("No found !");
        }
    }

    @Override
    public void showHistoryTransaction(Member member) {
        List<History> histories = getAllHistory();
        if (histories.isEmpty()){
            System.out.println("No found transaction");
            return;
        }

        List<History> listResult = new ArrayList<>();

        for (History history : histories){
            if(history.getMember().getFullName().equalsIgnoreCase(member.getFullName())){
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


    @Override
    public void choiceLevelPassBook(Member member) {
        String checked = InputUtils.getString("Bạn muốn đăng ký tất cả số dư? (Y/N or 'q' để thoát): ");
        if (checked.equalsIgnoreCase("q")){
            callMemberView(member);
            return;
        }
        long money = 0;

        if(!checked.equalsIgnoreCase("Y") && !checked.equalsIgnoreCase("N")){
            System.err.println("Nhập Y hoặc N");
            choiceLevelPassBook(member);
            return;
        }else if(checked.equalsIgnoreCase("Y")){
            money = member.getBalance();
        }else {
            money = Long.parseLong(InputUtils.getString("Nhập số tiền bạn muốn gửi: "));

            if (member.getBalance() < money){
                System.err.println("Số dư tài khoản không đủ");
                choiceLevelPassBook(member);
                return;
            }
        }

        if (money < 200000 || money > 1000000000){
            System.err.println("Đăng ký tối thiểu 200 nghìn đồng và tối đa 1 tỷ");
            choiceLevelPassBook(member);
            return;
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
                openPassBook6(member, money);
                break;
            case 2:
                openPassBook12(member, money);
                break;
            case 3:
                openPassBook24(member, money);
                break;
        }
    }

    public void openPassBook24(Member member, long money) {
        PassBookBank passBookBank = new PassBookBank(++PassBookBank.currentId,ETypePassBook.TWENTYFOUR_MONTH, money, LocalDate.now(), LocalDate.now().plusMonths(24));

        hanldePassBook(member, passBookBank, money);
        System.out.println("Đăng ký thành công");
    }

    public void openPassBook12(Member member, long money) {
        PassBookBank passBookBank = new PassBookBank(++PassBookBank.currentId,ETypePassBook.TWENTEEN_MONTH, money, LocalDate.now(), LocalDate.now().plusMonths(12));

        hanldePassBook(member, passBookBank, money);
        System.out.println("Đăng ký thành công");

    }

    public void openPassBook6(Member member, long money) {
        PassBookBank passBookBank = new PassBookBank(++PassBookBank.currentId, ETypePassBook.SIX_MONTH, money, LocalDate.now(), LocalDate.now().plusMonths(6));

        hanldePassBook(member, passBookBank, money);

        System.out.println("Đăng ký thành công");

    }

    public void hanldePassBook(Member member, PassBookBank passBookBank, long money){
        member.getPassBookBanks().add(passBookBank);
        member.setPassBookBankQuantity(member.getPassBookBankQuantity() + 1);
        member.setBalance(member.getBalance() - money);


        List<Member> members = getAllMember();
        for (Member m : members){
            if(m.getUsername().equals(member.getUsername())){
                m.getPassBookBanks().add(passBookBank);
                m.setPassBookBankQuantity(m.getPassBookBankQuantity() + 1);
                m.setBalance(m.getBalance() - money);
            }
        }
        FileUtils.writeFile(members, Config.PATH_FILE_MEMBER);
    }
}
