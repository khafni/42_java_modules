package fr._42.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr._42.chat.models.Message;
import fr._42.chat.models.Room;
import fr._42.chat.models.User;
import fr._42.chat.repositories.MessagesRepository;
import fr._42.chat.repositories.MessagesRepositoryJdbcImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class Program {
    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        HikariDataSource ds;
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        ds = new HikariDataSource(config);

        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(ds);
//        Optional<Message> o =  messagesRepository.findById(1L);
//        if (o.isPresent())
//            System.out.println(o.get().toString());
//        User creator = new User(1L, "user", "user", new ArrayList(), new ArrayList());
//        User author = creator;
//        Room room = new Room(1L, "room", creator, new ArrayList());
//        Message message = new Message(null, author, room, "Hello!", LocalDateTime.now());
//        messagesRepository.save(message);
//        System.out.println(message.getId());
        Optional<Message> messageOptional = messagesRepository.findById(2L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText("Bye 💀");
//            message.setDate(null);
            messagesRepository.update(message);
        }
    }
}
