import java.io.*;
import java.net.*;
import java.util.logging.Logger;

import com.sun.net.httpserver.*;
import handlers.*;

/**
 * The class that sets up and runs the server.
 */
public class Server {
	private static final int MAX_WAITING_CONNECTIONS = 12;
	private static final Logger logger = Logger.getLogger("Server");

	private void run(String portNumber) {
		logger.info(String.format("Initializing HTTP Server on port %s", portNumber));

		// Setup server
		HttpServer server;
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

		logger.info("Registering handlers");
		server.createContext("/event", new EventsHandler());
		server.createContext("/event/", new EventHandler());
		server.createContext("/person", new PersonsHandler());
		server.createContext("/person/", new PersonHandler());
		server.createContext("/user/login", new LoginHandler());
		server.createContext("/user/register", new RegisterHandler());
		server.createContext("/fill", new FillHandler());
		server.createContext("/load", new LoadHandler());
		server.createContext("/clear", new ClearHandler());
		server.createContext("/", new FileHandler());

		logger.info("Starting server");
		server.start();
		logger.info("Server started");
	}

	/**
	 * The main class that runs the server.
	 *
	 * @param args the program arguments, the first argument is used as the
	 *             port on which to run the server.
	 */
	public static void main(String[] args) {		
		String portNumber = args[0];
		new Server().run(portNumber);
	}
}

