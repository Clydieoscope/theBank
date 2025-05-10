package dao;

import model.Transaction;
import model.TransferRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {

    public List<Transaction> getTransactions(Connection conn, int userID, int accountID, Timestamp start, Timestamp end, double min, double max) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transactions t INNER JOIN Accounts a ON t.accountID = a.accountID WHERE a.customerID = ? AND t.accountID = ? " +
                "AND t.timestamp BETWEEN ? AND ? AND t.amount BETWEEN ? AND ? ORDER BY t.timestamp DESC";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userID);
            pstmt.setInt(2, accountID);
            pstmt.setTimestamp(3, start);
            pstmt.setTimestamp(4, end);
            pstmt.setDouble(5, min);
            pstmt.setDouble(6, max);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getInt("transactionID"),
                        rs.getString("transactionType"),
                        rs.getInt("accountID"),
                        rs.getTimestamp("timestamp"),
                        rs.getDouble("amount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public List<Transaction> getLoanTransactions(Connection conn, int userID, int loanID, Timestamp start, Timestamp end, double min, double max) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transactions t " +
                "INNER JOIN LoanPayments p ON t.transactionID = p.transactionID " +
                "INNER JOIN Loans l ON l.loanID = p.loanID " +
                "WHERE l.customerID = ? AND p.loanID = ? " +
                "AND t.timestamp BETWEEN ? AND ? AND t.amount BETWEEN ? AND ? ORDER BY t.timestamp DESC";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userID);
            pstmt.setInt(2, loanID);
            pstmt.setTimestamp(3, start);
            pstmt.setTimestamp(4, end);
            pstmt.setDouble(5, min);
            pstmt.setDouble(6, max);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Transaction t = new Transaction();
                t.setType(rs.getString("transactionType"));
                t.setTimestamp(rs.getTimestamp("timestamp"));
                t.setAmount(rs.getDouble("amount"));

                transactions.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public Boolean transferFunds(Connection conn, int userID, TransferRequest transferRequest) throws SQLException {
        try {
            conn.setAutoCommit(false); // start transaction

            System.out.println("acc_number: " + transferRequest.getAccount_number());
            System.out.println("receiver: " + transferRequest.getTo());
            int receiver = (transferRequest.getTo() == -1) ? transferRequest.getAccount_number() : transferRequest.getTo();
            int sender = transferRequest.getFrom();
            double amount = transferRequest.getAmount();

            PreparedStatement updateSender = conn.prepareStatement(
                    "UPDATE Accounts SET balance = balance - ? WHERE accountID = ? AND customerID = ?"
            );
            updateSender.setDouble(1, amount);
            updateSender.setInt(2, sender);
            updateSender.setInt(3, userID);

            updateSender.executeUpdate();

            PreparedStatement updateReceiver = conn.prepareStatement(
                    "UPDATE Accounts SET balance = balance + ? WHERE accountID = ?"
            );
            updateReceiver.setDouble(1, amount);
            updateReceiver.setInt(2, receiver);

            updateReceiver.executeUpdate();

            PreparedStatement addSentTransaction = conn.prepareStatement(
                    "INSERT INTO Transactions (transactionType, accountID, timestamp, amount) VALUES ('SENT', ?, CURRENT_TIMESTAMP, ?)"
            );

            addSentTransaction.setInt(1, sender);
            addSentTransaction.setDouble(2, amount);

            addSentTransaction.executeUpdate();

            PreparedStatement addReceivedTransaction = conn.prepareStatement(
                    "INSERT INTO Transactions (transactionType, accountID, timestamp, amount) VALUES ('RECEIVED', ?, CURRENT_TIMESTAMP, ?)"
            );
            addReceivedTransaction.setInt(1, receiver);
            addReceivedTransaction.setDouble(2, amount);

            System.out.println("Add received transaction query: " + addReceivedTransaction);
            addReceivedTransaction.executeUpdate();

            conn.commit(); // commit transaction
        } catch (SQLException e) {
            conn.rollback(); // if something goes wrong, rollback
            e.printStackTrace();
            return false;
        } finally {
            conn.setAutoCommit(true); // always return auto commit to true. 
        }

        return true;
    }

}
