package fr._42.chat.repositories;

import fr._42.chat.models.Message;

import java.util.Optional;

public interface MessagesRepository {
    public Optional<Message> findById(Long id);
    public void save(Message message);

    public void update(Message message);
}