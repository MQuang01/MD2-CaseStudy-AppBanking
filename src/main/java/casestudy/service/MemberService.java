package casestudy.service;

import casestudy.model.Member;
import casestudy.model.PassBookBank;

import java.util.List;

public interface MemberService {
    void addMoney(Member member);

    void getInforMember(Member member);

    void transferTo(Member member);

    void getMoney(Member member);
    void logOut();
    void choiceLevelPassBook(Member member);
    void openPassBook6(Member member, long money);
    void openPassBook12(Member member, long money);
    void openPassBook24(Member member, long money);
    void showListPassBook(Member member);
    void showHistoryTransaction(Member member);
}
