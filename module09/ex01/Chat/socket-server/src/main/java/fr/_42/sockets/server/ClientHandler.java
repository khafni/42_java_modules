package fr._42.sockets.server;

import fr._42.sockets.models.Message;
import fr._42.sockets.models.User;
import fr._42.sockets.services.MessagesService;
import fr._42.sockets.services.UsersService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final UsersService usersService;
    private final MessagesService messagesService;
    private final List<ClientHandler> connectedClients;
    private PrintWriter out;
    private BufferedReader in;
    private User currentUser;
    private volatile boolean running;

    public ClientHandler(Socket socket, UsersService usersService, MessagesService messagesService,
                        List<ClientHandler> connectedClients) {
        this.clientSocket = socket;
        this.usersService = usersService;
        this.messagesService = messagesService;
        this.connectedClients = connectedClients;
        this.running = true;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Send welcome message
            out.println("Hello from Server!");

            // Read command
            String command = in.readLine();

            if ("signUp".equals(command)) {
                handleSignUp();
            } else if ("signIn".equals(command)) {
                handleSignIn();
                if (currentUser != null) {
                    handleMessaging();
                }
            } else {
                out.println("Unknown command");
            }

        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    private void handleSignUp() throws IOException {
        // Read username
        out.println("Enter username:");
        String username = in.readLine();

        // Read password
        out.println("Enter password:");
        String password = in.readLine();

        try {
            usersService.signUp(username, password);
            out.println("Successful!");
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }

    private void handleSignIn() throws IOException {
        // Read username
        out.println("Enter username:");
        String username = in.readLine();

        // Read password
        out.println("Enter password:");
        String password = in.readLine();

        Optional<User> userOptional = usersService.signIn(username, password);

        if (userOptional.isPresent()) {
            currentUser = userOptional.get();
            synchronized (connectedClients) {
                connectedClients.add(this);
            }
            out.println("Start messaging");
        } else {
            out.println("Authentication failed");
        }
    }

    private void handleMessaging() throws IOException {
        String messageText;
        while (running && (messageText = in.readLine()) != null) {
            if ("Exit".equals(messageText)) {
                out.println("You have left the chat.");
                break;
            }

            // Save message to database
            messagesService.saveMessage(currentUser, messageText);

            // Broadcast message to all connected clients
            String formattedMessage = currentUser.getUsername() + ": " + messageText;
            broadcastMessage(formattedMessage);
        }
    }

    public void broadcastMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    private void closeConnection() {
        running = false;
        synchronized (connectedClients) {
            connectedClients.remove(this);
        }

        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
            System.out.println("Client disconnected");
        } catch (IOException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    public void shutdown() {
        running = false;
        closeConnection();
    }
}
