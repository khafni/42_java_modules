package fr._42.sockets.services;

import fr._42.sockets.models.User;
import fr._42.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(String username, String password) throws Exception {
        // Check if user already exists
        Optional<User> existingUser = usersRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new Exception("User already exists");
        }

        // Hash the password using BCrypt
        String hashedPassword = passwordEncoder.encode(password);

        // Create and save the user
        User newUser = new User(username, hashedPassword);
        usersRepository.save(newUser);
    }
}
