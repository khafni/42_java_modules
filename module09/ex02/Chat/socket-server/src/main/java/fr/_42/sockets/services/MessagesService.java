package fr._42.sockets.services;

import fr._42.sockets.models.Chatroom;
import fr._42.sockets.models.Message;
import fr._42.sockets.models.User;

import java.util.List;

public interface MessagesService {
    void saveMessage(User sender, Chatroom chatroom, String text);
    List<Message> getAllMessages();
    List<Message> getMessagesForChatroom(Chatroom chatroom);
    List<Message> getRecentMessagesForChatroom(Chatroom chatroom, int limit);
}
