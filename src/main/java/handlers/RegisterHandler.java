package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.RegisterRequest;
import result.RegisterResult;
import services.RegisterService;
import utilities.Extensions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * The handler for registering a new user.
 */
public class RegisterHandler implements HttpHandler {
    /**
     * Handles registering a new user.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException if an error occurs while handling the request.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream requestBody = exchange.getRequestBody();
                String requestJson = Extensions.readString(requestBody);

                RegisterRequest request = new Gson().fromJson(requestJson, RegisterRequest.class);
                RegisterResult result = new RegisterService().register(request);

                if (result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                    String resultJson = new Gson().toJson(result);
                    OutputStream responseBody = exchange.getResponseBody();
                    Extensions.writeString(resultJson, responseBody);

                    responseBody.close();
                    success = true;
                }
            }

            if (!success) {

            }
        }
        catch (IOException ex) {

        }
    }
}
