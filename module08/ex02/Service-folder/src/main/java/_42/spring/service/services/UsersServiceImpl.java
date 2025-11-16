package _42.spring.service.services;

import _42.spring.service.models.User;
import _42.spring.service.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class UsersServiceImpl implements UsersService {
    private static final String PASSWORD_SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 12;

    private final UsersRepository usersRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepositoryJdbc") UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public String signUp(String email) {
        User user = new User();
        user.setEmail(email);
        usersRepository.save(user);

        String temporaryPassword = generateTemporaryPassword();
        user.setPassword(temporaryPassword);
        return temporaryPassword;
    }

    private String generateTemporaryPassword() {
        StringBuilder builder = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = secureRandom.nextInt(PASSWORD_SYMBOLS.length());
            builder.append(PASSWORD_SYMBOLS.charAt(index));
        }
        return builder.toString();
    }
}
