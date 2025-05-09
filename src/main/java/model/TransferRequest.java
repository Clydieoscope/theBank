package model;

public class TransferRequest {
    private int from;
    private int to;
    private int account_number;
    private double amount;

    public TransferRequest(int from, int to, int account_number, double amount) {
        this.from = from;
        this.to = to;
        this.account_number = account_number;
        this.amount = amount;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getAccount_number() {
        return account_number;
    }

    public void setAccount_number(int account_number) {
        this.account_number = account_number;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
