package service;

import dao.CustomerDao;
import model.Customer;
import model.LoginRequest;
import model.infoChangeRequest;

import java.sql.Connection;
import java.util.List;

public class CustomerService {
    private final CustomerDao customerDao;
    private final Connection conn;

    public CustomerService(Connection conn) {
        this.conn = conn;
        this.customerDao = new CustomerDao();
    }
    public List<Customer> getCustomers() {
        return customerDao.getAllCustomers(conn);
    }

    public void createCustomer(Customer customer) {
        customerDao.addCustomer(conn, customer);
    }

    public Customer getCustomer(int userID) {
        return customerDao.getCustomer(conn, userID);
    }

    public Customer authenticate(LoginRequest request) {
        return customerDao.authenticate(conn, request);
    }

    public Boolean changeAddress(int userID, infoChangeRequest info) {
        return customerDao.changeAddress(conn, userID, info);
    }

    public Boolean changeContact(int userID, infoChangeRequest info) {
        return customerDao.changeContact(conn, userID, info);
    }
}