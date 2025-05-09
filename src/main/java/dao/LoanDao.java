package dao;

import model.Loan;
import model.LoanApplication;
import model.LoanPayment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDao {

    public List<Loan> getLoans(Connection conn, int id) {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM Loans WHERE customerID = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                loans.add(new Loan(
                        rs.getInt("loanID"),
                        rs.getString("loanType"),
                        rs.getDouble("loanAmount"),
                        rs.getDouble("balance"),
                        rs.getDouble("interestRate"),
                        rs.getInt("term")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    public Loan getLoan(Connection conn, int loanID) {
        String sql = "SELECT * FROM Loans WHERE loanID = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, loanID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Loan(
                        rs.getInt("loanID"),
                        rs.getString("loanType"),
                        rs.getString("loanStatus"),
                        rs.getDouble("loanAmount"),
                        rs.getDouble("balance"),
                        rs.getTimestamp("timestamp"),
                        rs.getDouble("interestRate"),
                        rs.getInt("term")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean processPayment(Connection conn, int userID, LoanPayment payment) throws SQLException {
        try {
            conn.setAutoCommit(false);

            PreparedStatement updateLoan = conn.prepareStatement(
                    "UPDATE Loans SET balance = balance - ? WHERE LoanID = ? AND CustomerID = ?;"
            );
            updateLoan.setDouble(1, payment.getAmount());
            updateLoan.setInt(2, payment.getLoanID());
            updateLoan.setInt(3, userID);

            updateLoan.executeUpdate();

            PreparedStatement updateAccount = conn.prepareStatement(
                    "UPDATE Accounts SET balance = balance - ? WHERE accountID = ? AND CustomerID = ?;"
            );
            updateAccount.setDouble(1, payment.getAmount());
            updateAccount.setInt(2, payment.getAccountID());
            updateAccount.setInt(3, userID);

            updateAccount.executeUpdate();

            PreparedStatement insertTransaction = conn.prepareStatement(
                "INSERT INTO Transactions (transactionType, accountID, timestamp, amount) VALUES (?, ?, CURRENT_TIMESTAMP, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            insertTransaction.setString(1, "PAYMENT");
            insertTransaction.setInt(2, payment.getAccountID());
            insertTransaction.setDouble(3, payment.getAmount());

            insertTransaction.executeUpdate();

            ResultSet generatedKeys = insertTransaction.getGeneratedKeys();
            int transactionId = -1;
            if (generatedKeys.next()) {
                transactionId = generatedKeys.getInt(1);
            }

            PreparedStatement insertLoanPayment = conn.prepareStatement(
                "INSERT INTO LoanPayments (TransactionID, LoanID) VALUES (?, ?)"
            );
            insertLoanPayment.setInt(1, transactionId);
            insertLoanPayment.setInt(2, payment.getLoanID());

            insertLoanPayment.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
            return false;
        } finally {
            conn.setAutoCommit(true);
        }

        return true;
    }

    public Boolean processApplication(Connection conn, int userID, LoanApplication loanApplication) throws SQLException {
        try {
            PreparedStatement addLoan = conn.prepareStatement(
                    "INSERT INTO Loans (loanStatus, loanAmount, balance, customerID, timestamp, interestRate, loanType, term) VALUES\n" +
                            "('PENDING', ?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?);"
            );
            addLoan.setDouble(1, loanApplication.getAmount());
            addLoan.setDouble(2, loanApplication.getAmount());
            addLoan.setInt(3, userID);
            addLoan.setDouble(4, loanApplication.getInterest());
            addLoan.setString(5, loanApplication.getType().toUpperCase());
            addLoan.setInt(6, loanApplication.getTerm());

            addLoan.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Boolean deleteLoan(Connection conn, int userID, int loanID) {
        try {
            PreparedStatement deleteLoan = conn.prepareStatement(
                    "DELETE FROM Loans WHERE loanID = ? AND customerID= ? AND balance = 0"
            );
            deleteLoan.setInt(1, loanID);
            deleteLoan.setInt(2, userID);

            deleteLoan.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }

}
