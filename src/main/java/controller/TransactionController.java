package controller;

import com.google.gson.Gson;
import model.Customer;
import model.Transaction;
import service.TransactionService;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static spark.Spark.*;

public class TransactionController {
    public static void setupRoutes(Connection conn) {
        TransactionService transactionService = new TransactionService(conn);

        path("/transaction", () -> {
            get("/:id", (request, response) -> {
                response.type("application/json");

                String param_min = request.queryParams("min");
                String param_max = request.queryParams("max");
                String param_start = request.queryParams("start");
                String param_end = request.queryParams("end");

                int accountID = Integer.parseInt(request.params(":id"));
                Customer user = request.session().attribute("user");

                System.out.println(param_min + " " + param_min + " " + param_start + " " + param_end);

                Date start = param_start != null ? Date.valueOf(param_start) : Date.valueOf(LocalDate.of(2000, 1, 1));
                Date end = param_end != null ? Date.valueOf(param_end) : Date.valueOf(LocalDate.now());

                System.out.println(start + " " + end);

                double min = param_min != null ? Double.parseDouble(param_min) : 0;
                double max = param_max != null ? Double.parseDouble(param_max) : 99999999;

                List<Transaction> transactions = transactionService.getTransactions(user.getId(), accountID, start, end, min, max);

                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(transactions)));
            });

            get("/loan/:id", (request, response) -> {
                response.type("application/json");

                String param_min = request.queryParams("min");
                String param_max = request.queryParams("max");
                String param_start = request.queryParams("start");
                String param_end = request.queryParams("end");

                int loanID = Integer.parseInt(request.params(":id"));
                Customer user = request.session().attribute("user");

                Date start = param_start != null ? Date.valueOf(param_start) : Date.valueOf(LocalDate.of(2000, 1, 1));
                Date end = param_end != null ? Date.valueOf(param_end) : Date.valueOf(LocalDate.now());

                double min = param_min != null ? Double.parseDouble(param_min) : 0;
                double max = param_max != null ? Double.parseDouble(param_max) : 99999999;

                List<Transaction> transactions = transactionService.getLoanTransactions(user.getId(), loanID, start, end, min, max);

                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(transactions)));
            });
        });
    }
}