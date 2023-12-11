package casestudy.service;

public interface LoginService {

     void initInfor();
     void setCurrentIdInfor();


     void initAdmin();
     void setCurrentIdAdmin();


     void initMember();
     void setCurrentIdMember();



     void checkLogin(String username, String psw);

    void inputInforSignIn();
}
