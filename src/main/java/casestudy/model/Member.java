package casestudy.model;

import casestudy.utils.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Member implements IParser{
    private long id;
    private String username;
    private String password;
    private String fullName;
    private String phoneNum;
    private LocalDate doB;
    private long balance;
    private ERank eRank;
    private List<PassBookBank> passBookBanks;
    public Long passBookBankQuantity;
    public static long currentId;


    public Member() {
        passBookBanks = new ArrayList<>();
    }

    public Member(long id, String fullName, String phoneNum, LocalDate doB, long balance, ERank eRank) {
        this.id = id;
        this.username = String.valueOf(phoneNum);
        this.password = "123123";
        this.fullName = fullName;
        this.phoneNum = phoneNum;
        this.doB = doB;
        this.balance = balance;
        this.eRank = eRank;
        this.passBookBankQuantity = 0L;
    }

    public Member(String fullName, String phoneNum, LocalDate doB, long balance, ERank eRank) {
        this.fullName = fullName;
        this.username = String.valueOf(phoneNum);
        this.password = "123123";
        this.phoneNum = phoneNum;
        this.doB = doB;
        this.balance = balance;
        this.eRank = eRank;
        this.passBookBankQuantity = 0L;
    }

    public Member(String fullName) {
        this.fullName = fullName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public LocalDate getDoB() {
        return doB;
    }

    public void setDoB(LocalDate doB) {
        this.doB = doB;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public ERank geteRank() {
        return eRank;
    }

    public void seteRank(ERank eRank) {
        this.eRank = eRank;
    }

    public List<PassBookBank> getPassBookBanks() {
        return passBookBanks;
    }

    public void setPassBookBanks(List<PassBookBank> passBookBanks) {
        this.passBookBanks = passBookBanks;
    }

    public Long getPassBookBankQuantity() {
        return passBookBankQuantity;
    }

    public void setPassBookBankQuantity(Long passBookBankQuantity) {
        this.passBookBankQuantity = passBookBankQuantity;
    }

    @Override
    public String toString() {
        //1,minhquang,123123,0772717214,Minh Quang,1999-2-12,500000,VIP,3
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", id,username,password,phoneNum,fullName,doB,balance,eRank,passBookBankQuantity);
    }

    @Override
    public void parse(String line) {
        String [] items = line.split(",");

        //id,username,psw,fullName,phoneNum,doB,blance,ranked
        this.id = Long.parseLong(items[0]);
        this.username = items[1];
        this.password = items[2];
        this.phoneNum = items[3];
        this.fullName = items[4];
        this.doB = DateUtils.parse(items[5]);
        this.balance = Long.parseLong(items[6]);
        this.eRank = ERank.valueOf(items[7]);

        if (items[8].equals("null")){
            this.passBookBankQuantity = 0L;
        }else {
            this.passBookBankQuantity = Long.parseLong(items[8]);
        }


    }
}
