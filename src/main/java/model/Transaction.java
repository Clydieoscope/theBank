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

    public Transaction(int id, String type, Integer AccountId, Timestamp timestamp, double amount) {
        this.id = id;
        this.type = type;
        this.AccountId = AccountId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.date = new Date(timestamp.getTime());
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        this.date = new Date(timestamp.getTime());
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getAccountId() {
        return AccountId;
    }

    public void setAccountId(Integer accountId) {
        AccountId = accountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
