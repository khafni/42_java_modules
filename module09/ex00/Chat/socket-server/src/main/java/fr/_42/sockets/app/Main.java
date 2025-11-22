package fr._42.sockets.app;

import fr._42.sockets.config.SocketsApplicationConfig;
import fr._42.sockets.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        // Default port
        int port = 8081;

        // Parse command line arguments
        for (String arg : args) {
            if (arg.startsWith("--port=")) {
                try {
                    port = Integer.parseInt(arg.substring("--port=".length()));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid port number: " + arg);
                    System.exit(1);
                }
            }
        }

        // Initialize Spring context
        ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);

        // Get Server bean and start
        Server server = context.getBean(Server.class);
        server.start(port);
    }
}
