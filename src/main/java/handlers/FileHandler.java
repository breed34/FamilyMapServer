package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

/**
 * The handler for serving web files.
 */
public class FileHandler implements HttpHandler {

    /**
     * Handles serving up the requested file.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response.
     * @throws IOException if an error occurs while handling the request.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean isGet = false;
        boolean fileFound = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                isGet = true;

                String urlPath = exchange.getRequestURI().toString();
                if (urlPath.equals("/")) {
                    urlPath += "index.html";
                }

                File requestedFile = new File("web" + urlPath);
                if (requestedFile.exists()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream responseBody = exchange.getResponseBody();
                    Files.copy(requestedFile.toPath(), responseBody);
                    responseBody.close();
                    fileFound = true;
                }
            }

            if (!isGet) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                exchange.getResponseBody().close();
            }

            if (!fileFound) {
                File notFound = new File("web/HTML/404.html");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                OutputStream responseBody = exchange.getResponseBody();
                Files.copy(notFound.toPath(), responseBody);
                responseBody.close();
            }
        }
        catch (IOException ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            ex.printStackTrace();
        }
    }
}
