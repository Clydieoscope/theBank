package model;

public class Account {
    private int id;
    private int customerID;
    private String type;
    private double balance;
    private double interest;

    public Account() {}

    public Account(int id, String type, double balance) {
        this.id = id;
        this.type = type;
        this.balance = balance;
    }

    public Account(int id, String type, double balance, double interest) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.interest = interest;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
