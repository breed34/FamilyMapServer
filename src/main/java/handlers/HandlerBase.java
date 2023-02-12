package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import result.ResultBase;

import java.io.*;
import java.net.HttpURLConnection;

public abstract class HandlerBase implements HttpHandler {
    /**
     * Updates the response body of the current HTTP exchange depending on the success state
     * of the given result object.
     *
     * @param exchange the current HTTP exchange object.
     * @param result the given result object.
     * @throws IOException if an error occurs while updating the response body of the exchange.
     */
    protected void handleResult(HttpExchange exchange, ResultBase result) throws IOException {
        if (result.isSuccess()) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        }
        else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        }

        String resultJson = new Gson().toJson(result);
        OutputStream responseBody = exchange.getResponseBody();
        writeString(resultJson, responseBody);
    }

    /**
     * Reads an input stream and returns a string representation of the stream.
     *
     * @param is the input stream.
     * @return a string representation of the input stream.
     * @throws IOException if the input stream can not be read.
     */
    protected String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /**
     * Writes a given string to a given output stream.
     *
     * @param str the given string.
     * @param os the given output stream.
     * @throws IOException if the string can not be written to the output stream.
     */
    protected void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    /**
     * Gets the authtoken from the request headers of the current HTTP exchange.
     *
     * @param exchange the current HTTP exchange object.
     * @return the authtoken from the request headers or null if none is found.
     */
    protected String getAuthtokenFromHeader(HttpExchange exchange) {
        Headers headers = exchange.getRequestHeaders();
        if (headers.containsKey("Authorization")) {
            return headers.getFirst("Authorization");
        }

        return null;
    }
}