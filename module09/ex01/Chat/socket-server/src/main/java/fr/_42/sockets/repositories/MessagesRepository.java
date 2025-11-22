package fr._42.sockets.repositories;

import fr._42.sockets.models.Message;

import java.util.List;

public interface MessagesRepository extends CrudRepository<Message> {
    List<Message> findAll();
}
