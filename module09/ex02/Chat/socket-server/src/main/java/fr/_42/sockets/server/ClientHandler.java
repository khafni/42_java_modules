package fr._42.sockets.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr._42.sockets.dto.ClientCommand;
import fr._42.sockets.dto.ServerEvent;
import fr._42.sockets.models.Chatroom;
import fr._42.sockets.models.Message;
import fr._42.sockets.models.User;
import fr._42.sockets.services.ChatroomService;
import fr._42.sockets.services.MessagesService;
import fr._42.sockets.services.UsersService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final UsersService usersService;
    private final MessagesService messagesService;
    private final ChatroomService chatroomService;
    private final List<ClientHandler> connectedClients;
    private PrintWriter out;
    private BufferedReader in;
    private User currentUser;
    private Chatroom currentChatroom;
    private volatile boolean running;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public ClientHandler(Socket socket, UsersService usersService, MessagesService messagesService,
                        List<ClientHandler> connectedClients, ChatroomService chatroomService) {
        this.clientSocket = socket;
        this.usersService = usersService;
        this.messagesService = messagesService;
        this.chatroomService = chatroomService;
        this.connectedClients = connectedClients;
        this.running = true;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            sendInfo("Hello from Server!");

            String firstLine = in.readLine();
            if (firstLine == null) {
                return;
            }

            ClientCommand initialCommand = parseCommand(firstLine);
            if (initialCommand == null) {
                sendError("Invalid command");
                return;
            }

            if ("signUp".equalsIgnoreCase(initialCommand.getType())) {
                handleSignUp(initialCommand);
            } else if ("signIn".equalsIgnoreCase(initialCommand.getType())) {
                handleSignIn(initialCommand);
                if (currentUser != null) {
                    handleMessaging();
                }
            } else {
                sendError("Unknown command");
            }

        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    private void handleSignUp(ClientCommand command) {
        String username = command.getUsername();
        String password = command.getPassword();
        try {
            usersService.signUp(username, password);
            sendEvent(buildAuthEvent("signUp", "Successful!"));
        } catch (Exception e) {
            sendError("Error: " + e.getMessage());
        }
    }

    private void handleSignIn(ClientCommand command) {
        String username = command.getUsername();
        String password = command.getPassword();

        Optional<User> userOptional = usersService.signIn(username, password);

        if (userOptional.isPresent()) {
            currentUser = userOptional.get();
            synchronized (connectedClients) {
                connectedClients.add(this);
            }
            sendEvent(buildAuthEvent("signIn", "Start messaging"));
            restoreLastChatroom();
        } else {
            sendError("Authentication failed");
        }
    }

    private void handleMessaging() throws IOException {
        sendChatroomHelp();
        String line;
        while (running && (line = in.readLine()) != null) {
            ClientCommand command = parseCommand(line);
            if (command == null) {
                sendError("Invalid command");
                continue;
            }

            switch (command.getType()) {
                case "Exit":
                    sendInfo("You have left the chat.");
                    return;
                case "/create":
                    createChatroom(command.getRoom());
                    break;
                case "/join":
                    joinChatroom(command.getRoom());
                    break;
                case "/rooms":
                    listChatrooms();
                    break;
                case "/leave":
                    leaveChatroom();
                    break;
                case "message":
                    handleChatMessage(command.getMessage());
                    break;
                default:
                    sendError("Unknown command");
            }
        }
    }

    private void handleChatMessage(String messageText) {
        if (currentChatroom == null) {
            sendInfo("Join or create a chatroom first using /create <name> or /join <name>.");
            return;
        }

        messagesService.saveMessage(currentUser, currentChatroom, messageText);

        ServerEvent event = new ServerEvent();
        event.setType("message");
        event.setRoom(currentChatroom.getName());
        event.setFrom(currentUser.getUsername());
        event.setFromId(currentUser.getId());
        event.setTimestamp(LocalDateTime.now().format(dateFormatter));
        event.setMessage(messageText);
        broadcastToChatroom(event, currentChatroom);
    }

    private void createChatroom(String roomName) {
        if (roomName == null || roomName.isBlank()) {
            sendError("Chatroom name cannot be empty.");
            return;
        }

        Optional<Chatroom> existing = chatroomService.findByName(roomName);
        if (existing.isPresent()) {
            currentChatroom = existing.get();
            usersService.updateLastChatroom(currentUser, currentChatroom);
            sendInfo("Chatroom '" + roomName + "' already exists, joining it.");
            broadcastSystem(currentUser.getUsername() + " joined the chatroom.", currentChatroom);
            sendRecentMessages(currentChatroom);
            return;
        }

        Chatroom chatroom = chatroomService.createChatroom(roomName, currentUser);
        currentChatroom = chatroom;
        usersService.updateLastChatroom(currentUser, currentChatroom);
        sendInfo("Chatroom '" + roomName + "' created. You joined it.");
    }

    private void joinChatroom(String roomName) {
        if (roomName == null || roomName.isBlank()) {
            sendError("Please provide a chatroom name to join.");
            return;
        }

        Optional<Chatroom> chatroomOptional = chatroomService.findByName(roomName);
        if (chatroomOptional.isEmpty()) {
            sendError("Chatroom '" + roomName + "' does not exist. Create it with /create " + roomName);
            return;
        }

        currentChatroom = chatroomOptional.get();
        usersService.updateLastChatroom(currentUser, currentChatroom);
        sendInfo("Joined chatroom '" + roomName + "'.");
        broadcastSystem(currentUser.getUsername() + " joined the chatroom.", currentChatroom);
        sendRecentMessages(currentChatroom);
    }

    private void leaveChatroom() {
        if (currentChatroom == null) {
            sendError("You are not in a chatroom.");
            return;
        }

        String roomName = currentChatroom.getName();
        broadcastSystem(currentUser.getUsername() + " left the chatroom.", currentChatroom);
        currentChatroom = null;
        sendInfo("Left chatroom '" + roomName + "'.");
    }

    private void listChatrooms() {
        List<Chatroom> chatrooms = chatroomService.listChatrooms();
        ServerEvent event = new ServerEvent();
        event.setType("rooms");
        event.setRooms(chatrooms.stream().map(Chatroom::getName).toList());
        sendEvent(event);
    }

    private void sendChatroomHelp() {
        sendInfo("Chat commands:");
        sendInfo("  /create <name> - create and join a chatroom");
        sendInfo("  /join <name>   - join an existing chatroom");
        sendInfo("  /rooms         - list chatrooms");
        sendInfo("  /leave         - leave current chatroom");
        sendInfo("  Exit           - disconnect");
    }

    private void broadcastToChatroom(ServerEvent event, Chatroom chatroom) {
        synchronized (connectedClients) {
            for (ClientHandler client : connectedClients) {
                if (client.currentChatroom != null && client.currentChatroom.equals(chatroom)) {
                    client.sendEvent(event);
                }
            }
        }
    }

    private void broadcastSystem(String message, Chatroom chatroom) {
        ServerEvent event = buildInfo(message);
        event.setRoom(chatroom.getName());
        broadcastToChatroom(event, chatroom);
    }

    private void send(String message) {
        sendInfo(message);
    }

    private void sendRecentMessages(Chatroom chatroom) {
        List<Message> recent = messagesService.getRecentMessagesForChatroom(chatroom, 30);
        if (recent.isEmpty()) {
            sendInfo("No recent messages in '" + chatroom.getName() + "'.");
            return;
        }

        for (Message msg : recent.stream().sorted((a, b) -> a.getTimestamp().compareTo(b.getTimestamp())).toList()) {
            ServerEvent event = new ServerEvent();
            event.setType("message");
            event.setHistory(true);
            event.setRoom(chatroom.getName());
            event.setFrom(msg.getSender().getUsername());
            event.setFromId(msg.getSender().getId());
            event.setTimestamp(msg.getTimestamp().format(dateFormatter));
            event.setMessage(msg.getText());
            sendEvent(event);
        }
    }

    private void restoreLastChatroom() {
        if (currentUser == null || currentUser.getLastChatroom() == null) {
            return;
        }

        Optional<Chatroom> chatroom = chatroomService.findByName(currentUser.getLastChatroom().getName());
        if (chatroom.isEmpty()) {
            return;
        }

        currentChatroom = chatroom.get();
        sendInfo("Rejoined your last chatroom '" + currentChatroom.getName() + "'.");
        sendRecentMessages(currentChatroom);
    }

    private void closeConnection() {
        running = false;
        if (currentUser != null && currentChatroom != null) {
            broadcastSystem(currentUser.getUsername() + " left the chatroom.", currentChatroom);
        }
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

    private ClientCommand parseCommand(String json) {
        try {
            return objectMapper.readValue(json, ClientCommand.class);
        } catch (Exception e) {
            return null;
        }
    }

    private void sendEvent(ServerEvent event) {
        if (out == null || event == null) return;
        try {
            out.println(objectMapper.writeValueAsString(event));
        } catch (Exception e) {
            System.err.println("Failed to send event: " + e.getMessage());
        }
    }

    private void sendError(String message) {
        ServerEvent event = new ServerEvent();
        event.setType("error");
        event.setMessage(message);
        sendEvent(event);
    }

    private void sendInfo(String message) {
        sendEvent(buildInfo(message));
    }

    private ServerEvent buildInfo(String message) {
        ServerEvent event = new ServerEvent();
        event.setType("info");
        event.setMessage(message);
        return event;
    }

    private ServerEvent buildAuthEvent(String type, String message) {
        ServerEvent event = new ServerEvent();
        event.setType(type);
        event.setMessage(message);
        return event;
    }
}
