package fr._42.sockets.services;

import fr._42.sockets.models.Message;
import fr._42.sockets.models.User;

import java.util.List;

public interface MessagesService {
    void saveMessage(User sender, String text);
    List<Message> getAllMessages();
}
