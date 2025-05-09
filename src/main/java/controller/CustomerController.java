package controller;

import com.google.gson.Gson;
import model.Customer;
import model.infoChangeRequest;
import service.CustomerService;
import java.sql.Connection;

import static spark.Spark.*;

public class CustomerController {
    public static void setupRoutes(Connection conn) {
        CustomerService customerService = new CustomerService(conn);

        path("/customer", () -> {
            get("/", (req, res) -> {
                res.type("application/json");
                return new Gson().toJson(customerService.getCustomers());
            });

            get("/:id", (req, res) -> {
                res.type("application/json");
                return new Gson().toJson(customerService.getCustomer(Integer.parseInt(req.params("id"))));
            });

            post("/address", (request, response) -> {
                response.type("application/json");
                Customer user = request.session().attribute("user");
                infoChangeRequest info = new Gson().fromJson(request.body(), infoChangeRequest.class);

                if (customerService.changeAddress(user.getId(), info)) {
                    response.status(202);
                    response.body("Information updated");
                } else {
                    response.status(400);
                    response.body("Update not successful");
                }

                return response;
            });

            post("/contact", (request, response) -> {
                response.type("application/json");
                Customer user = request.session().attribute("user");
                infoChangeRequest info = new Gson().fromJson(request.body(), infoChangeRequest.class);

                if (customerService.changeContact(user.getId(), info)) {
                    response.status(202);
                    response.body("Information updated");
                } else {
                    response.status(400);
                    response.body("Update not successful");
                }

                return response;
            });
        });
    }
}