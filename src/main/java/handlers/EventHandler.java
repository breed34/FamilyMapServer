package handlers;

import com.sun.net.httpserver.HttpExchange;
import request.AuthenticationRequest;
import request.EventRequest;
import result.AuthenticationResult;
import result.EventResult;
import services.AuthenticationService;
import services.EventService;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * The handler object for getting an event in the database
 * by its eventID.
 */
public class EventHandler extends HandlerBase {
    /**
     * Handles getting an event in the database
     * by its eventID.
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
                    EventRequest request = getRequestFromPath(requestPath, authResult.getUsername());
                    EventResult result = new EventService().getEvent(request);

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

    private EventRequest getRequestFromPath(String path, String activeUserName) {
        String[] pathParameters = path.split("/");
        if (pathParameters.length == 3) {
            return new EventRequest(pathParameters[2], activeUserName);
        }

        return null;
    }
}
