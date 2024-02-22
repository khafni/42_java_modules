package fr._42.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr._42.chat.models.User;
import fr._42.chat.repositories.UserRepository;
import fr._42.chat.repositories.UserRepositoryJdbcImpl;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        HikariDataSource ds;
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("1234");
        ds = new HikariDataSource(config);
        UserRepository userRepository = new UserRepositoryJdbcImpl(ds);
        List<User> userList = userRepository.findAll(0, 2);
        for (User user: userList) {
            System.out.println(userList.toString());
        }
        }
    }
