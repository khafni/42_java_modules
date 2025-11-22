package fr._42.sockets.repositories;

import fr._42.sockets.models.Chatroom;

import java.util.List;
import java.util.Optional;

public interface ChatroomRepository extends CrudRepository<Chatroom> {
    Optional<Chatroom> findByName(String name);
    List<Chatroom> findAll();
}
