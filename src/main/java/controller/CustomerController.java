package controller;

import com.google.gson.Gson;
import model.Customer;
import service.CustomerService;
import java.sql.Connection;

import static spark.Spark.*;

public class CustomerController {
    public static void setupRoutes(Connection conn) {
        CustomerService customerService = new CustomerService(conn);
        Gson gson = new Gson();

        get("/customer", (req, res) -> {
            res.type("application/json");
            return gson.toJson(customerService.getCustomers());
        });

        post("/customer", (req, res) -> {
            res.type("application/json");
            Customer customer = gson.fromJson(req.body(), Customer.class);
            customerService.createCustomer(customer);
            res.status(201);
            return gson.toJson(customer);
        });
    }
}