package casestudy.model;

public enum ERank {
    NORMAL(1,"Normal"), VIP(2,"Vip");
    private long id;
    private String name;

    ERank(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
