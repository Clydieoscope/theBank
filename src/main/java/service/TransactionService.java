package service;

import dao.TransactionDao;
import model.Transaction;
import model.TransferRequest;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class TransactionService {
    private final TransactionDao transactionDao;
    private final Connection conn;

    public TransactionService(Connection conn) {
        this.conn = conn;
        this.transactionDao = new TransactionDao();
    }

    public List<Transaction> getTransactions(int userID, int accountID, Timestamp start, Timestamp end, double min, double max) {
        return transactionDao.getTransactions(conn, userID, accountID, start, end, min, max);
    }

    public List<Transaction> getLoanTransactions(int userID, int loanID, Timestamp start, Timestamp end, double min, double max) {
        return transactionDao.getLoanTransactions(conn, userID, loanID, start, end, min, max);
    }

    public Boolean transferFunds(int userID, TransferRequest transferRequest) throws SQLException {
        return transactionDao.transferFunds(conn, userID, transferRequest);
    }
}
