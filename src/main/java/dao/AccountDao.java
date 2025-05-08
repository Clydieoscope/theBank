package dao;

import model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {

    public List<Account> getAccounts(Connection conn, int id) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM Accounts WHERE customerID = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                accounts.add(new Account(
                        rs.getInt("accountID"),
                        rs.getString("accountType"),
                        rs.getDouble("balance"),
                        rs.getDouble("interest")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public Account getAccount(Connection conn, int id) {
        String sql = "SELECT * FROM Accounts WHERE accountID = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Account(
                    rs.getInt("accountID"),
                    rs.getString("accountType"),
                    rs.getDouble("balance"),
                    rs.getDouble("interest")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
