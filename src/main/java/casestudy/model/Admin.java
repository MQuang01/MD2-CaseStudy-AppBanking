package casestudy.model;

public class Admin implements IParser{
    private long id;
    private String username;
    private String password;
    private String fullName;
    public static long currentId;

    public Admin(){
    }

    public Admin(String fullName){
        this.fullName = fullName;
    }

    public Admin(long id, String username, String fullName){
        this.id = id;
        this.username = username;
        this.password = "123";
        this.fullName = fullName;
    }

    public Admin(String username, String fullName) {
        this.username = username;
        this.password = "123";
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


    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s",id,username,password,fullName);
    }

    @Override
    public void parse(String line) {
        String[] items = line.split(",");

        this.id = Long.parseLong(items[0]);
        this.username = items[1];
        this.password = items[2];
        this.fullName = items[3];
    }
}
