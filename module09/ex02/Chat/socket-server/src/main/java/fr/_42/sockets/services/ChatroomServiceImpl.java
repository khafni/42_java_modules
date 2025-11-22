package fr._42.sockets.services;

import fr._42.sockets.models.Chatroom;
import fr._42.sockets.models.User;
import fr._42.sockets.repositories.ChatroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatroomServiceImpl implements ChatroomService {

    private final ChatroomRepository chatroomRepository;

    @Autowired
    public ChatroomServiceImpl(ChatroomRepository chatroomRepository) {
        this.chatroomRepository = chatroomRepository;
    }

    @Override
    public Chatroom createChatroom(String name, User owner) {
        // Owner is kept for possible future audit; not stored in DB to keep schema minimal.
        Chatroom chatroom = new Chatroom(name);
        chatroomRepository.save(chatroom);
        return chatroom;
    }

    @Override
    public Optional<Chatroom> findByName(String name) {
        return chatroomRepository.findByName(name);
    }

    @Override
    public List<Chatroom> listChatrooms() {
        return chatroomRepository.findAll();
    }
}
