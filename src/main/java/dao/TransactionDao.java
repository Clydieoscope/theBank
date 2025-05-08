package dao;

import model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class TransactionDao {

    public List<Transaction> getTransactions(Connection conn, int userID, int accountID, Date start, Date end, double min, double max) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transactions t INNER JOIN Accounts a ON t.accountID = a.accountID WHERE a.customerID = ? AND t.accountID = ? " +
                "AND t.date BETWEEN ? AND ? AND t.amount BETWEEN ? AND ? ORDER BY t.date DESC";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userID);
            pstmt.setInt(2, accountID);
            pstmt.setDate(3, start);
            pstmt.setDate(4, end);
            pstmt.setDouble(5, min);
            pstmt.setDouble(6, max);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getInt("transactionID"),
                        rs.getString("transactionType"),
                        rs.getInt("accountID"),
                        rs.getDate("date"),
                        rs.getDouble("amount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public List<Transaction> getLoanTransactions(Connection conn, int userID, int loanID, Date start, Date end, double min, double max) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transactions t " +
                "INNER JOIN LoanPayments p ON t.transactionID = p.transactionID " +
                "INNER JOIN Loans l ON l.loanID = p.loanID " +
                "WHERE l.customerID = ? AND p.loanID = ? " +
                "AND t.date BETWEEN ? AND ? AND t.amount BETWEEN ? AND ? ORDER BY t.date DESC";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userID);
            pstmt.setInt(2, loanID);
            pstmt.setDate(3, start);
            pstmt.setDate(4, end);
            pstmt.setDouble(5, min);
            pstmt.setDouble(6, max);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Transaction t = new Transaction();
                t.setType(rs.getString("transactionType"));
                t.setDate(rs.getDate("date"));
                t.setAmount(rs.getDouble("amount"));

                transactions.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

//    public Transaction getTransaction(Connection conn, int id) {
//        String sql = "SELECT * FROM Transactions WHERE transactionID = ?";
//
//        try {
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setInt(1, id);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                return new Transaction(
//                        rs.getInt("transactionID"),
//                        rs.getString("transactionType"),
//                        rs.getInt("accountID"),
//                        rs.getDate("datetime"),
//                        rs.getDouble("amount")
//                );
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

}
