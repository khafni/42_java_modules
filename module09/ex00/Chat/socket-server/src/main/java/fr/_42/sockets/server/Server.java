package fr._42.sockets.server;

import fr._42.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class Server {

    private final UsersService usersService;
    private int port;

    @Autowired
    public Server(UsersService usersService) {
        this.usersService = usersService;
    }

    public void start(int port) {
        this.port = port;
        System.out.println("Server started on port " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }

    private void handleClient(Socket clientSocket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            // Send welcome message
            out.println("Hello from Server!");

            // Read command
            String command = in.readLine();

            if ("signUp".equals(command)) {
                // Read username
                out.println("Enter username:");
                String username = in.readLine();

                // Read password
                out.println("Enter password:");
                String password = in.readLine();

                try {
                    // Attempt to register user
                    usersService.signUp(username, password);
                    out.println("Successful!");
                } catch (Exception e) {
                    out.println("Error: " + e.getMessage());
                }
            } else {
                out.println("Unknown command");
            }

            // Close connection
            clientSocket.close();
            System.out.println("Client disconnected");

        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }
}
