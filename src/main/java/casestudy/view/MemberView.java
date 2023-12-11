package casestudy.view;

import casestudy.model.Member;
import casestudy.service.MemberService;
import casestudy.service.impl.MemberServiceImpl;
import casestudy.utils.InputUtils;

public class MemberView {
    private final MemberService memberService;

    public MemberView(MemberService memberService) {
        memberService = new MemberServiceImpl();
        this.memberService = memberService;
    }

    public void chooseMemberActive(Member member){
        do {
            System.out.println("------------------------------------------------------------------------");
            System.out.printf("%-20s %-30s %20s\n", "|", "1. Kiểm tra số dư tài khoản", "|");
            System.out.printf("%-20s %-30s %20s\n", "|", "2. Nạp tiền", "|");
            System.out.printf("%-20s %-30s %20s\n", "|", "3. Chuyển tiền", "|");
            System.out.printf("%-20s %-30s %20s\n", "|", "4. Rút tiền", "|");
            System.out.printf("%-20s %-30s %20s\n", "|", "5. Đăng ký sổ tiết kiệm", "|");
            System.out.printf("%-20s %-30s %20s\n", "|", "6. Kiểm tra sổ tiết kiệm", "|");
            System.out.printf("%-20s %-30s %20s\n", "|", "7. Kiểm tra lịch sử giao dịch", "|");
            System.out.printf("%-20s %-30s %20s\n", "|", "8. Đăng xuất", "|");
            System.out.println("------------------------------------------------------------------------");

            int choice = InputUtils.getNumberMinMax("Mời nhập: ", 0 , 8);

            switch (choice){
                case 0:
                    System.exit(-1);
                case 1:
                    showBalance(member);
                    break;
                case 2:
                    depositToCard(member);
                    break;
                case 3:
                    transferMoney(member);
                    break;
                case 4:
                    withdrawnFromCard(member);
                    break;
                case 5:
                    registerBankPassBook(member);
                    break;
                case 6:
                    checkListPassBook(member);
                    break;
                case 7:
                    checkHistory(member);
                    break;
                case 8:
                    logOut();
                    break;
            }
        }while (true);
    }

    private void checkHistory(Member member) {
        memberService.showHistoryTransaction(member);
    }

    private void checkListPassBook(Member member) {
        memberService.showListPassBook(member);
    }

    private void logOut() {
        memberService.logOut();
    }

    private void registerBankPassBook(Member member) {
        memberService.choiceLevelPassBook(member);
    }

    private void withdrawnFromCard(Member member) {
        memberService.getMoney(member);
        showBalance(member);
    }

    private void transferMoney(Member member) {
        memberService.transferTo(member);
    }

    private void depositToCard(Member member) {
        memberService.addMoney(member);
        showBalance(member);
    }
    private void showBalance(Member member) {
        memberService.getInforMember(member);
    }
}
