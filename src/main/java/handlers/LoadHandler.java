package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.LoadRequest;
import result.ClearResult;
import result.LoadResult;
import services.ClearService;
import services.LoadService;
import utilities.Extensions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * The handler object for loading data directly into the database.
 */
public class LoadHandler implements HttpHandler {
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
                String requestJson = Extensions.readString(requestBody);

                LoadRequest request = new Gson().fromJson(requestJson, LoadRequest.class);
                LoadResult result = new LoadService().load(request);

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
