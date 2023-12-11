package casestudy.service;

import casestudy.model.Member;

public interface MemberService {
    void addMoney(Member member);

    void getInforMember(Member member);

    void transferTo(Member member);

    void getMoney(Member member);
    void logOut();
    void choiceLevelPassBook(Member member);

    void openPassBook(Member member, long money);
}
