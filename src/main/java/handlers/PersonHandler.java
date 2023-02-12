package handlers;

import com.sun.net.httpserver.HttpExchange;
import requests.AuthenticationRequest;
import requests.PersonRequest;
import results.AuthenticationResult;
import results.PersonResult;
import services.AuthenticationService;
import services.PersonService;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * The handler object for getting a person in the database
 * by their personID.
 */
public class PersonHandler extends HandlerBase {
    /**
     * Handles getting a person in the database
     * by their personID.
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
                    String requestPath = exchange.getRequestURI().toString();
                    PersonRequest request = getRequestFromPath(requestPath, authResult.getUsername());
                    PersonResult result = new PersonService().getPerson(request);

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
        }
        catch (IOException ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            ex.printStackTrace();
        }
    }

    private PersonRequest getRequestFromPath(String path, String activeUserName) {
        String[] pathParameters = path.split("/");
        if (pathParameters.length == 3) {
            return new PersonRequest(pathParameters[2], activeUserName);
        }

        return null;
    }
}
