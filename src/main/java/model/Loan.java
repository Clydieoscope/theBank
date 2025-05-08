package model;

import java.sql.Date;

public class Loan {

    int id;
    String type;
    String status;
    Date date;
    double amount;
    double rate;

    public Loan(){}

    public Loan(int id, String type, double amount, double rate) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.rate = rate;
    }

    public Loan(int id, String type, String status, double amount, Date date, double rate) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.date = date;
        this.rate = rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
