package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import request.LoadRequest;
import result.LoadResult;
import services.LoadService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * The handler object for loading data directly into the database.
 */
public class LoadHandler extends HandlerBase {
    /**
     * Handles loading data directly into the database.
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

                LoadRequest request = new Gson().fromJson(requestJson, LoadRequest.class);
                LoadResult result = new LoadService().load(request);

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
