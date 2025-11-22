package fr._42.sockets.services;

import fr._42.sockets.models.User;

public interface UsersService {
    void signUp(String username, String password) throws Exception;
}
