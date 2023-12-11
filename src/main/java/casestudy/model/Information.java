package casestudy.model;

import casestudy.service.LoginService;
import casestudy.service.impl.BaseService;
import casestudy.utils.DateUtils;
import casestudy.utils.ValidateUtils;

import java.time.LocalDate;
import java.util.List;

public class Information implements IParser{
    private long id;
    private String fullName;
    private String phoneNum;
    private LocalDate doB;
    private boolean status;
    private Admin approved_by;

    public static long currentId;

    public Information() {
    }

    public Information(long id, String fullName, String phoneNUm, LocalDate doB) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNum = phoneNUm;
        this.doB = doB;
        this.status = false;
        this.approved_by = null;
    }

    public Information(String fullName, String phoneNUm, LocalDate doB) {
        this.fullName = fullName;
        this.phoneNum = phoneNUm;
        this.doB = doB;
        this.status = false;
        this.approved_by = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Admin getApproved_by() {
        return approved_by;
    }

    public void setApproved_by(Admin approved_by) {
        this.approved_by = approved_by;
    }



    //id,fullName,phone,doB,null,null
    @Override
    public String toString() {
        String inforApproved = approved_by != null ? approved_by.getFullName() : "null";
        return String.format("%s,%s,%s,%s,%s,%s", id, fullName, phoneNum, doB, status, inforApproved);
    }

    public void parse(String line) {
        String [] items = line.split(",");

        //1,Minh Quang 1,0934960651,2000-11-21,false,approve_by,cancel_by
        this.id = Long.parseLong(items[0]);
        this.fullName = items[1];
        this.phoneNum = items[2];
        this.doB = DateUtils.parse(items[3]);
        this.status = Boolean.parseBoolean(items[4]);

        if (!items[5].equals("null")){
            this.approved_by = new Admin(items[5]);
        }else {
            this.approved_by = null;
        }

    }
}
