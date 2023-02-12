package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import requests.RegisterRequest;
import results.RegisterResult;
import services.RegisterService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * The handler object for registering a new user.
 */
public class RegisterHandler extends HandlerBase {
    /**
     * Handles registering a new user.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException if an error occurs while handling the request.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream requestBody = exchange.getRequestBody();
                String requestJson = readString(requestBody);

                RegisterRequest request = new Gson().fromJson(requestJson, RegisterRequest.class);
                RegisterResult result = new RegisterService().register(request);

                handleResult(exchange, result);
                exchange.getResponseBody().close();
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            ex.printStackTrace();
        }
    }
}
