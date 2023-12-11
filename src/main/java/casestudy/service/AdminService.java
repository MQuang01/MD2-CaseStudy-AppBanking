package casestudy.service;

import casestudy.model.Admin;
import casestudy.model.Information;
import casestudy.model.Member;

import java.time.Month;
import java.util.List;

public interface AdminService {
    void getRequest();

    void hanldeRequest(Admin admin);

    void showAllMember();

    List<Information> getInforList();

    void createNewMember(Admin admin, Information information);

    void logOut();

    void deleteRequest(Admin admin, Information infor);

    void createNewMemberBy(Admin admin);

    void deleteMemberBy(Admin admin);

    void editInforMember(Admin admin);
    void updateInfor(Admin admin, Long id);


    void findBy(Admin admin);



    void getHistoryList(Admin admin);
    void showHistoryByPayments(String payments);
    void showHistoryByName(String fullName);
    void showHistoryByDay(int daySearch);
    void showHistoryByMonth(Month monthSearch);
    void showHistoryByYear(int yearSearch);
    void showAllTransaction();
}
