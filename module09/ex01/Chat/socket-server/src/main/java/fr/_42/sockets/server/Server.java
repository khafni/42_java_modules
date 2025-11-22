package fr._42.sockets.server;

import fr._42.sockets.services.MessagesService;
import fr._42.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Server {

    private final UsersService usersService;
    private final MessagesService messagesService;
    private final List<ClientHandler> connectedClients;
    private int port;

    @Autowired
    public Server(UsersService usersService, MessagesService messagesService) {
        this.usersService = usersService;
        this.messagesService = messagesService;
        this.connectedClients = Collections.synchronizedList(new ArrayList<>());
    }

    public void start(int port) {
        this.port = port;
        System.out.println("Server started on port " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");

                // Create a new thread for each client
                ClientHandler clientHandler = new ClientHandler(
                    clientSocket,
                    usersService,
                    messagesService,
                    connectedClients
                );

                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }

    public List<ClientHandler> getConnectedClients() {
        return connectedClients;
    }
}
