package fr._42.sockets.services;

import fr._42.sockets.models.Chatroom;
import fr._42.sockets.models.User;

import java.util.List;
import java.util.Optional;

public interface ChatroomService {
    Chatroom createChatroom(String name, User owner);
    Optional<Chatroom> findByName(String name);
    List<Chatroom> listChatrooms();
}
