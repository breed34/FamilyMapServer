package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.AuthenticationRequest;
import request.PersonRequest;
import result.AuthenticationResult;
import result.PersonResult;
import services.AuthenticationService;
import services.PersonService;
import utilities.Extensions;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * The handler object for getting a person in the database
 * by their personId.
 */
public class PersonHandler implements HttpHandler {
    /**
     * Handles getting a person in the database
     * by their personId.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException if an error occurs while handling the request.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                Headers headers = exchange.getRequestHeaders();
                if (headers.containsKey("Authorization")) {
                    String authtoken = headers.getFirst("Authorization");
                    AuthenticationRequest authRequest = new AuthenticationRequest(authtoken);
                    AuthenticationResult authResult = new AuthenticationService().authenticate(authRequest);

                    if (authResult.isSuccess()) {
                        String requestPath = exchange.getRequestURI().toString();
                        PersonRequest request = getRequestFromPath(requestPath);
                        if (request != null) {
                            request.setActiveUserName(authResult.getUsername());
                        }

                        PersonResult result = new PersonService().getPerson(request);

                        if (result.isSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }

                        String resultJson = new Gson().toJson(result);
                        OutputStream responseBody = exchange.getResponseBody();
                        Extensions.writeString(resultJson, responseBody);

                        responseBody.close();
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);

                        String resultJson = new Gson().toJson(authResult);
                        OutputStream responseBody = exchange.getResponseBody();
                        Extensions.writeString(resultJson, responseBody);

                        responseBody.close();
                    }
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

    private PersonRequest getRequestFromPath(String path) {
        String[] pathParameters = path.split("/");
        if (pathParameters.length == 3) {
            return new PersonRequest(pathParameters[2]);
        }

        return null;
    }
}
