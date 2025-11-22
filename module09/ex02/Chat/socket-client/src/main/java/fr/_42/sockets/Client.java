package fr._42.sockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr._42.sockets.dto.ClientCommand;
import fr._42.sockets.dto.ServerEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    public static void main(String[] args) {
        int serverPort = 8081;
        String serverHost = "localhost";

        for (String arg : args) {
            if (arg.startsWith("--server-port=")) {
                try {
                    serverPort = Integer.parseInt(arg.substring("--server-port=".length()));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid port number: " + arg);
                    System.exit(1);
                }
            } else if (arg.startsWith("--server-host=")) {
                String value = arg.substring("--server-host=".length()).trim();
                if (value.isEmpty()) {
                    System.err.println("Server host cannot be empty.");
                    System.exit(1);
                }
                serverHost = value;
            }
        }

        ObjectMapper mapper = new ObjectMapper();

        try (
            Socket socket = new Socket(serverHost, serverPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in)
        ) {
            ServerEvent welcome = readEvent(mapper, in);
            if (welcome != null && welcome.getMessage() != null) {
                System.out.println(welcome.getMessage());
            }

            String authChoice = prompt(scanner, "Type 'signUp' or 'signIn': ");
            if (!"signUp".equalsIgnoreCase(authChoice) && !"signIn".equalsIgnoreCase(authChoice)) {
                System.out.println("Unknown choice, exiting.");
                return;
            }

            String username = prompt(scanner, "Username: ");
            String password = prompt(scanner, "Password: ");
            sendCommand(out, mapper, new ClientCommand(authChoice, username, password, null, null));

            ServerEvent authResult = readEvent(mapper, in);
            if (authResult == null || "error".equalsIgnoreCase(authResult.getType())) {
                System.out.println(authResult != null ? authResult.getMessage() : "Authentication failed.");
                return;
            }
            System.out.println(authResult.getMessage());

            // Server closes the connection right after a sign-up; ask the user to reconnect for sign-in.
            if ("signUp".equalsIgnoreCase(authChoice)) {
                System.out.println("Please restart the client and sign in to start chatting.");
                return;
            }

            AtomicBoolean connected = new AtomicBoolean(true);
            Thread readerThread = startReaderThread(mapper, in, connected);

            while (connected.get()) {
                String userInput = prompt(scanner, "> ");
                String normalized = userInput == null ? "" : userInput.trim();

                if (normalized.isEmpty()) {
                    continue;
                } else if (normalized.startsWith("/create ")) {
                    sendCommand(out, mapper, new ClientCommand("/create", null, null, normalized.substring(8).trim(), null));
                } else if (normalized.startsWith("/join ")) {
                    sendCommand(out, mapper, new ClientCommand("/join", null, null, normalized.substring(6).trim(), null));
                } else if ("/rooms".equalsIgnoreCase(normalized)) {
                    sendCommand(out, mapper, new ClientCommand("/rooms", null, null, null, null));
                } else if ("/leave".equalsIgnoreCase(normalized)) {
                    sendCommand(out, mapper, new ClientCommand("/leave", null, null, null, null));
                } else if ("exit".equalsIgnoreCase(normalized) || "/exit".equalsIgnoreCase(normalized) || "quit".equalsIgnoreCase(normalized)) {
                    sendCommand(out, mapper, new ClientCommand("Exit", null, null, null, null));
                    break;
                } else {
                    sendCommand(out, mapper, new ClientCommand("message", null, null, null, userInput));
                }
            }

            try {
                readerThread.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            System.exit(1);
        }
    }

    private static Thread startReaderThread(ObjectMapper mapper, BufferedReader in, AtomicBoolean connected) {
        Thread readerThread = new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    ServerEvent event = mapper.readValue(line, ServerEvent.class);
                    if (event == null) continue;

                    switch (event.getType()) {
                        case "message":
                            String historyLabel = Boolean.TRUE.equals(event.getHistory()) ? "(history) " : "";
                            System.out.println(historyLabel + "[" + event.getRoom() + "] " + event.getFrom() + ": " + event.getMessage());
                            break;
                        case "rooms":
                            List<String> rooms = event.getRooms();
                            System.out.println("Rooms: " + (rooms == null || rooms.isEmpty() ? "(none)" : String.join(", ", rooms)));
                            break;
                        default:
                            if (event.getMessage() != null) {
                                System.out.println(event.getMessage());
                            }
                    }
                }
            } catch (IOException e) {
                System.out.println("Connection closed: " + e.getMessage());
            } finally {
                connected.set(false);
            }
        });
        readerThread.setDaemon(true);
        readerThread.start();
        return readerThread;
    }

    private static ServerEvent readEvent(ObjectMapper mapper, BufferedReader in) throws IOException {
        String line = in.readLine();
        if (line == null) {
            return null;
        }
        return mapper.readValue(line, ServerEvent.class);
    }

    private static void sendCommand(PrintWriter out, ObjectMapper mapper, ClientCommand command) throws IOException {
        out.println(mapper.writeValueAsString(command));
        if (out.checkError()) {
            throw new IOException("Failed to send command to server.");
        }
    }

    private static String prompt(Scanner scanner, String label) {
        System.out.print(label);
        return scanner.nextLine();
    }
}
