package handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

/**
 * The handler object for serving web files.
 */
public class FileHandler extends HandlerBase {

    /**
     * Handles serving up the requested file.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response.
     * @throws IOException if an error occurs while handling the request.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                String requestPath = exchange.getRequestURI().toString();
                File requestedFile = getRequestedFileFromPath(requestPath);

                if (requestedFile.exists()) {
                    handleFileFound(exchange, requestedFile);
                    exchange.getResponseBody().close();
                }
                else  {
                    handleFileNotFound(exchange);
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

    private void handleFileFound(HttpExchange exchange, File requestedFile) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream responseBody = exchange.getResponseBody();
        Files.copy(requestedFile.toPath(), responseBody);
    }

    private void handleFileNotFound(HttpExchange exchange) throws IOException {
        File notFound = new File("web/HTML/404.html");
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
        OutputStream responseBody = exchange.getResponseBody();
        Files.copy(notFound.toPath(), responseBody);
    }

    private File getRequestedFileFromPath(String requestPath) {
        if (requestPath.equals("/")) {
            requestPath += "index.html";
        }

        return new File("web" + requestPath);
    }
}
