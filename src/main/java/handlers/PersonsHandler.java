package handlers;

import com.sun.net.httpserver.HttpExchange;
import requests.AuthenticationRequest;
import requests.PersonsRequest;
import results.AuthenticationResult;
import results.PersonsResult;
import services.AuthenticationService;
import services.PersonsService;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * The handler object for getting all persons associated with
 * the active user from the database.
 */
public class PersonsHandler extends HandlerBase {
    /**
     * Handles getting all persons associated with
     * the active user from the database.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException if an error occurs while handling the request.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                // Authenticate the user
                String authtoken = getAuthtokenFromHeader(exchange);
                AuthenticationRequest authRequest = new AuthenticationRequest(authtoken);
                AuthenticationResult authResult = new AuthenticationService().authenticate(authRequest);

                if (authResult.isSuccess()) {
                    PersonsRequest request = new PersonsRequest(authResult.getUsername());
                    PersonsResult result = new PersonsService().getAllPersons(request);

                    handleResult(exchange, result);
                    exchange.getResponseBody().close();
                }
                else {
                    // Handle failed user authentication
                    handleResult(exchange, authResult);
                    exchange.getResponseBody().close();
                }
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                exchange.getResponseBody().close();
            }
        } catch (IOException ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            ex.printStackTrace();
        }
    }
}
