package service;

import dao.LoanDao;
import model.Loan;
import model.LoanPayment;

import java.sql.Connection;
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

    public Boolean processPayment(LoanPayment payment) {
        return loanDao.processPayment(conn, payment);
    }
}
