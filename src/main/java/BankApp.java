import static spark.Spark.*;

import com.google.gson.Gson;
import controller.AccountController;
import controller.CustomerController;
import controller.LoanController;
import controller.TransactionController;
import db.Database;
import model.Customer;
import model.LoginRequest;
import service.CustomerService;
import spark.Session;

public class BankApp {
    public static void main(String[] args) {
        port(4567);
        staticFiles.location("/public");
        Database.connect();
        CustomerController.setupRoutes(Database.getConnection());
        AccountController.setupRoutes(Database.getConnection());
        LoanController.setupRoutes(Database.getConnection());
        TransactionController.setupRoutes(Database.getConnection());

        get("/logout", (request, response) -> {
            request.session().invalidate();
            response.redirect("/");

            return null;
        });

        post("/login", (request, response) -> {
            LoginRequest login = new Gson().fromJson(request.body(), LoginRequest.class);
            CustomerService customerService = new CustomerService(Database.getConnection());
            Customer customer = customerService.authenticate(login);
            
            if (customer != null) {
                Session session = request.session(true);
                session.attribute("user", customer);
                response.status(200);
                response.redirect("dashboard.html");
            } else {
                response.status(401);
            }

            return response;
        });

        get("/user", (request, response) -> {
            response.type("application/json");
            Customer user = request.session().attribute("user");
            return new Gson().toJson(user);
        });

    }
}
