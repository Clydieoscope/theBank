package model;

public class LoanPayment {
    private int loanID;
    private int accountID;
    private double amount;

    public LoanPayment(int loanID, int accountID, double amount){
        this.loanID = loanID;
        this.accountID = accountID;
        this.amount = amount;
    }

    public int getLoanID() {
        return loanID;
    }

    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
