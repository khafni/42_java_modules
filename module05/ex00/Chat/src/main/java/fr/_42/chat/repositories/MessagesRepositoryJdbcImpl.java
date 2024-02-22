package fr._42.chat.repositories;

import fr._42.chat.models.Message;

import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository{
    @Override
    public Optional<Message> findById(Long id)
    {
       return Optional.empty();
    }
}
