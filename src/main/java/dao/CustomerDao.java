package dao;

import model.Customer;
import model.LoginRequest;
import model.infoChangeRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    public List<Customer> getAllCustomers(Connection conn) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customers";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public Customer getCustomer(Connection conn, int userID) {

        try {
            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT * FROM Customers WHERE customerID = ?"
            );
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getInt("customerID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public void addCustomer(Connection conn, Customer customer) {
        String sql = "INSERT INTO Customers (first_name, last_name, phone, address, email) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getPhone());
            pstmt.setString(4, customer.getAddress());
            pstmt.setString(5, customer.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer authenticate(Connection conn, LoginRequest request) {
        String sql = "SELECT * FROM Customers WHERE email = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, request.getEmail());
            pstmt.setString(2, request.getPassword());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getInt("customerID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean changeAddress(Connection conn, int userID, infoChangeRequest info) {
        int rows;

        try {
            PreparedStatement updateAddress = conn.prepareStatement(
                    "UPDATE Customers SET address = ? WHERE customerID = ?"
            );
            updateAddress.setString(1, info.getAddress());
            updateAddress.setInt(2, userID);

            rows = updateAddress.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return rows > 0;
    }

    public Boolean changeContact(Connection conn, int userID, infoChangeRequest info) {
        int rows;

        try {
            PreparedStatement updateContact = conn.prepareStatement(
                    "UPDATE Customers SET email = ?, phone = ? WHERE customerID = ?"
            );
            updateContact.setString(1, info.getEmail());
            updateContact.setString(2, info.getPhone());
            updateContact.setInt(3, userID);

            rows = updateContact.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return rows > 0;
    }
}