package dao;

import model.Loan;
import model.LoanPayment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDao {

    public List<Loan> getLoans(Connection conn, int id) {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM Loans WHERE customerID = ?";


        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                loans.add(new Loan(
                        rs.getInt("loanID"),
                        rs.getString("loanType"),
                        rs.getDouble("loanAmount"),
                        rs.getDouble("interestRate")
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
                        rs.getDate("applicationDate"),
                        rs.getDouble("interestRate")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean processPayment(Connection conn, LoanPayment payment) {
        String sql = ""+
        "UPDATE Loans SET balance = balance - ? WHERE loanID = ? AND customerID = ?" + 
        "INSERT INTO Transaction" +
        "INSERT INTO LoanPayment"

        return false;
    }

}
