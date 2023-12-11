package casestudy.service;

import casestudy.model.Admin;
import casestudy.model.Information;
import casestudy.model.Member;

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

    void findBy(Admin admin);
}
