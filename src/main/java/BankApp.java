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
        CustomerService customerService = new CustomerService(Database.getConnection());
        Gson gson = new Gson();
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
            LoginRequest login = gson.fromJson(request.body(), LoginRequest.class);
            Customer customer = customerService.authenticate(login);
            if (customer != null) {
                Session session = request.session(true);
                session.attribute("user", customer);
                response.status(200);
                response.redirect("/app/dashboard");
            } else {
                response.status(401);
            }

            return null;
        });

        get("/user", (request, response) -> {
            response.type("application/json");
            Customer user = request.session().attribute("user");
            return gson.toJson(user);
        });

        // General paths

        path("/app", () -> {

            get("/dashboard", (request, response) -> {
                response.redirect("/dashboard.html");
                return null;
            });

            get("/account/:id", (request, response) -> {
                response.redirect("/account.html?id=" + request.queryParams("id"));
                return null;
            });

            get("/loans", (request, response) -> {
                response.redirect("/loans.html");
                return null;
            });

            get("/banking", (request, response) -> {
                response.redirect("/banking.html");
                return null;
            });
        });
    }
}
