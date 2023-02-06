import java.io.*;
import java.net.*;
import java.util.logging.Logger;

import com.sun.net.httpserver.*;
import request.RegisterRequest;
import services.RegisterService;

public class Server {
	private static final int MAX_WAITING_CONNECTIONS = 12;
	private static final Logger logger = Logger.getLogger("Server");
	private HttpServer server;

	private void run(String portNumber) {
		logger.info(String.format("Initializing HTTP Server on port %s", portNumber));
		
		try {
			server = HttpServer.create(
						new InetSocketAddress(Integer.parseInt(portNumber)),
						MAX_WAITING_CONNECTIONS);
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}

		server.setExecutor(null);

		logger.info("Creating contexts");
		// server.createContext("/games/list", new ListGamesHandler());
		// server.createContext("/routes/claim", new ClaimRouteHandler());
		// server.createContext("/", new FileHandler());

		logger.info("Starting server");
		server.start();
		logger.info("Server started");
	}

	public static void main(String[] args) {		
		// String portNumber = args[0];
		// new Server().run(portNumber);

		new RegisterService().register(new RegisterRequest(
				"Bob123",
				"teller767",
				"Bob123@email.com",
				"Bob",
				"Smith",
				"m")
		);
	}
}

