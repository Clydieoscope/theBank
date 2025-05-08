package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Transaction {
    private int id;
    private String type;
    private Integer AccountId;
    private double amount;
    private Timestamp timestamp;
    private Date date;

    public Transaction() {}

    public Transaction(int id, String type, Integer AccountId, Date date, double amount) {
        this.id = id;
        this.type = type;
        this.AccountId = AccountId;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Integer getAccountId() {
        return AccountId;
    }

    public double getAmount() {
        return amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAccountId(Integer AccountId) {
        this.AccountId = AccountId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
