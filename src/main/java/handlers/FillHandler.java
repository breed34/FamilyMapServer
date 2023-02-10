package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.FillRequest;
import result.FillResult;
import services.FillService;
import utilities.Extensions;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * The handler object for filling the database with data
 * for a given user.
 */
public class FillHandler implements HttpHandler {
    /**
     * Handles filling the database with data
     * for a given user.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException if an error occurs while handling the request.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                String requestPath = exchange.getRequestURI().toString();
                FillRequest request = getRequestFromPath(requestPath);
                FillResult result = new FillService().fill(request);

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

    private FillRequest getRequestFromPath(String path) {
        String[] pathParameters = path.split("/");
        switch (pathParameters.length) {
            case 3:
                return new FillRequest(pathParameters[2]);
            case 4:
                if (pathParameters[3].matches("[0-9]+")) {
                    return new FillRequest(pathParameters[2],
                            Integer.parseInt(pathParameters[3]));
                }
                break;
            default:
                break;
        }

        return null;
    }
}
