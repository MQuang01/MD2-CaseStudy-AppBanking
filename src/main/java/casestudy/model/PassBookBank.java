package casestudy.model;

import java.time.LocalDate;

public class PassBookBank{
    private long id;
    private ETypePassBook typePassBook;
    private long valueOfBook;
    private LocalDate dateRegistration;
    private LocalDate dateMaturity;
    private boolean status;
    public static long currentId;
    public PassBookBank() {
    }

    public PassBookBank(long id, ETypePassBook typePassBook,long valueOfBook, LocalDate dateRegistration, LocalDate dateMaturity) {
        this.id = id;
        this.typePassBook = typePassBook;
        this.valueOfBook = valueOfBook;
        this.dateRegistration = dateRegistration;
        this.dateMaturity = dateMaturity;
        this.status = false;
    }

    public PassBookBank(ETypePassBook typePassBook,long valueOfBook, LocalDate dateRegistration, LocalDate dateMaturity) {
        this.typePassBook = typePassBook;
        this.valueOfBook = valueOfBook;
        this.dateRegistration = dateRegistration;
        this.dateMaturity = dateMaturity;
        this.status = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ETypePassBook getTypePassBook() {
        return typePassBook;
    }

    public void setTypePassBook(ETypePassBook typePassBook) {
        this.typePassBook = typePassBook;
    }

    public LocalDate getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(LocalDate dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public LocalDate getDateMaturity() {
        return dateMaturity;
    }

    public void setDateMaturity(LocalDate dateMaturity) {
        this.dateMaturity = dateMaturity;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getValueOfBook() {
        return valueOfBook;
    }

    public void setValueOfBook(long valueOfBook) {
        this.valueOfBook = valueOfBook;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s", typePassBook, valueOfBook, dateRegistration, dateMaturity, status);
    }

}
