package controller;

import com.google.gson.Gson;
import model.Customer;
import model.Loan;
import model.LoanPayment;
import model.LoginRequest;
import service.LoanService;
import spark.Session;

import java.sql.Connection;

import static spark.Spark.*;

public class LoanController {
    public static void setupRoutes(Connection conn) {
        LoanService loanService = new LoanService(conn);

        path("/loan", () -> {
            get("/", (request, response) -> {
                response.type("application/json");
                Customer user = request.session().attribute("user");
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(loanService.getLoans(user.getId()))));
            });

            get("/:id", (request, response) -> {
                response.type("application/json");
                int id = Integer.parseInt(request.params(":id"));
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, new Gson().toJson(loanService.getLoan(id))));
            });

            post("/pay", (request, response) -> {
                LoanPayment payment = new Gson().fromJson(request.body(), LoanPayment.class);
                Boolean success = loanService.processPayment(payment);

                if (success) {
                    response.status(200);
                } else {
                    response.status(401);
                }

                return response;
            });
        });

    }
}