package fr._42.chat.repositories;

import fr._42.chat.models.Message;
import fr._42.chat.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
//    public Optional<Message> findById(Long id);
    public List<User> findAll(int page, int size);
}
