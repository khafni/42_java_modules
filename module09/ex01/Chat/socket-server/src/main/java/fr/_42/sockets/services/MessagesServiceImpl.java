package fr._42.sockets.services;

import fr._42.sockets.models.Message;
import fr._42.sockets.models.User;
import fr._42.sockets.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessagesServiceImpl implements MessagesService {

    private final MessagesRepository messagesRepository;

    @Autowired
    public MessagesServiceImpl(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    @Override
    public void saveMessage(User sender, String text) {
        Message message = new Message(sender, text, LocalDateTime.now());
        messagesRepository.save(message);
    }

    @Override
    public List<Message> getAllMessages() {
        return messagesRepository.findAll();
    }
}
