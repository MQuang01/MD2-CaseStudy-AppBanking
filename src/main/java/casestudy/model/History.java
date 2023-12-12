package casestudy.model;

import casestudy.utils.DateUtils;

import java.time.LocalDate;
import java.util.Date;

public class History implements IParser{
    private long id;
    private Member member;
    private String money;
    private String action;
    private LocalDate createAt;
    public static long currentId;

    public History() {
    }

    public History(long id, Member member, String money, String action, LocalDate createAt) {
        this.id = id;
        this.member = member;
        this.money = money;
        this.action = action;
        this.createAt = createAt;
    }

    public History(Member member, String money, String action, LocalDate createAt) {
        this.member = member;
        this.money = money;
        this.action = action;
        this.createAt = createAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }


    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s",id,member.getFullName(),money,action,createAt);
    }

    @Override
    public void parse(String line) {
        String [] items = line.split(",");

        this.id = Long.parseLong(items[0]);
        this.member = new Member(items[1]);
        this.money = items[2];
        this.action = items[3];
        this.createAt = DateUtils.parse(items[4]);
    }
}
