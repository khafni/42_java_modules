package fr._42.services;

import fr._42.exceptions.AlreadyAuthenticatedException;
import fr._42.models.User;
import fr._42.repositories.UsersRepository;

public class UsersServiceImpl {
    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean authenticate(String login, String password) throws AlreadyAuthenticatedException {
        User user = usersRepository.findByLogin(login);
        if (user == null) return false;
        if (user.isAuthenticationSuccessStatus())
            throw new AlreadyAuthenticatedException("User " + login + " already authenticated!");
        if (password == user.getPassword()) {
            usersRepository.update(new User(user.getIdentifier(), login, password, true));
            return true;
        }
        return false;
    }
}
