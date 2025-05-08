package controller;

import com.google.gson.Gson;
import model.Customer;
import service.AccountService;
import java.sql.Connection;

import static spark.Spark.*;

public class AccountController {
    public static void setupRoutes(Connection conn) {
        AccountService accountService = new AccountService(conn);

        path("/account", () -> {
            get("/", (request, response) -> {
                response.type("application/json");
                Customer user = request.session().attribute("user");
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(accountService.getAccounts(user.getId()))));
            });

            get("/:id", (request, response) -> {
                response.type("application/json");
                int id = Integer.parseInt(request.params(":id"));
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, new Gson().toJson(accountService.getAccount(id))));
            });
        });

//        post("/account", (req, res) -> {
//            res.type("application/json");
//            Account account = gson.fromJson(req.body(), Account.class);
//            accountService.createAccount(account);
//            res.status(201);
//            return gson.toJson(account);
//        });
    }
}