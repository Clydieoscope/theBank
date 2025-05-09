package model;

public class LoanApplication {

    private String type;
    private int term;
    private double interest;
    private double amount;

    public LoanApplication(String type, int term, double interest, double amount){
        this.type = type;
        this.term = term;
        this.interest = interest;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

