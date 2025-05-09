package service;

import dao.LoanDao;
import model.Loan;
import model.LoanApplication;
import model.LoanPayment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LoanService {
    private final LoanDao loanDao;
    private final Connection conn;

    public LoanService(Connection conn) {
        this.conn = conn;
        this.loanDao = new LoanDao();
    }

    public List<Loan> getLoans(int id) {
        return loanDao.getLoans(conn, id);
    }

    public Loan getLoan(int id) {
        return loanDao.getLoan(conn, id);
    }

    public Boolean processPayment(int userID, LoanPayment payment) throws SQLException {
        return loanDao.processPayment(conn, userID, payment);
    }

    public Boolean processApplication(int userID, LoanApplication application) throws SQLException {
        return loanDao.processApplication(conn, userID, application);
    }

    public Boolean deleteLoan(int userID, int loanID) {
        return loanDao.deleteLoan(conn, userID, loanID);
    }
}
