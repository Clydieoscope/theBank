package model;

public class LoanPayment {
    String loanID;
    int accountID;
    double amount;

    public LoanPayment(String loanID, int accountID, double amount){
        this.loanID = loanID;
        this.accountID = accountID;
        this.amount = amount;
    }

}
