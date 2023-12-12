package casestudy.model;

public enum ETypePassBook {
    SIX_MONTH(1, "6 month"), TWENTEEN_MONTH(2, "12 month"), TWENTYFOUR_MONTH(3, "24 month");
    private long id;
    private String name;

    ETypePassBook(long id, String name) {
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
