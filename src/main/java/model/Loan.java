package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Loan {

    int id;
    String type;
    String status;
    Timestamp timestamp;
    double amount;
    double balance;
    double rate;
    int term;

    public Loan(){}

    public Loan(int id, String type, double amount, double balance, double rate, int term) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.rate = rate;
        this.term = term;
    }

    public Loan(int id, String type, String status, double amount, double balance, Timestamp timestamp, double rate, int term) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.balance = balance;
        this.timestamp = timestamp;
        this.rate = rate;
        this.term = term;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }
}
