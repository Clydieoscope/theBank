package controller;

import com.google.gson.Gson;
import model.Customer;
import model.Transaction;
import model.TransferRequest;
import service.TransactionService;
import java.sql.Connection;
import java.sql.Timestamp;
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

                int accountID = Integer.parseInt(request.params(":id"));
                Customer user = request.session().attribute("user");

                // format parameters for compatibility
                Timestamp start = formatDate(request.queryParams("start"), "00:00:00.0", LocalDateTime.of(2000, 1, 1, 0, 0));
                Timestamp end = formatDate(request.queryParams("end"), "23:59:00.0", LocalDateTime.now());
                double min = formatDouble(request.queryParams("min"), 0);
                double max = formatDouble(request.queryParams("max"), 99999999);

                List<Transaction> transactions = transactionService.getTransactions(user.getId(), accountID, start, end, min, max);

                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(transactions)));
            });

            get("/loan/:id", (request, response) -> {
                response.type("application/json");

                int loanID = Integer.parseInt(request.params(":id"));
                Customer user = request.session().attribute("user");

                // format parameters for compatibility
                Timestamp start = formatDate(request.queryParams("start"), "00:00:00.0", LocalDateTime.of(2000, 1, 1, 0, 0));
                Timestamp end = formatDate(request.queryParams("end"), "23:59:00.0", LocalDateTime.now());
                double min = formatDouble(request.queryParams("min"), 0);
                double max = formatDouble(request.queryParams("max"), 99999999);

                List<Transaction> transactions = transactionService.getLoanTransactions(user.getId(), loanID, start, end, min, max);

                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(transactions)));
            });

            post("/transfer", (request, response) -> {
                TransferRequest transferRequest = new Gson().fromJson(request.body(), TransferRequest.class);
                Customer user = request.session().attribute("user");

                System.out.println("Transfer Request:" +
                        "\n- from:" + transferRequest.getFrom() +
                        "\n- to:" + transferRequest.getTo() +
                        "\n- account_number:" + transferRequest.getAccount_number() +
                        "\n- amount: " + transferRequest.getAmount());

                Boolean success = transactionService.transferFunds(user.getId(), transferRequest);

                if (success) {
                    response.status(200);
                } else {
                    response.status(401);
                }

                return response;
            });
        });
    }

    private static Timestamp formatDate(String date, String time, LocalDateTime defaultValue) {
        if (date == null) return Timestamp.valueOf(defaultValue);

        return Timestamp.valueOf(date + " " + time);
    }

    private static double formatDouble(String value, double defaultValue) {
        if (value == null) return defaultValue;

        return Double.parseDouble(value);
    }
}