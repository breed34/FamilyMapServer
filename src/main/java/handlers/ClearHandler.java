package handlers;

import com.sun.net.httpserver.HttpExchange;
import results.ClearResult;
import services.ClearService;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * The handler object for clearing the database.
 */
public class ClearHandler extends HandlerBase {
    /**
     * Handles clearing the database.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException if an error occurs while handling the request.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                ClearResult result = new ClearService().clear();

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
