package fr._42.chat.repositories;


import fr._42.chat.models.Chatroom;
import fr._42.chat.models.Message;
import fr._42.chat.models.User;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        try (Connection conn = dataSource.getConnection(); var ps = conn.prepareStatement("""
                                                            SELECT
                                                            message.id AS message_id,
                                                            "User".id AS author_id,
                                                            login AS author_login,
                                                            "password" AS author_password,
                                                            chatroom.id AS room_id,
                                                            "name" AS room_name,
                                                            "text" AS message_text,
                                                            date_time AS message_date_time
                                                            FROM message
                                                            JOIN "User" ON message.author = "User".id
                                                            JOIN chatroom ON message.room = chatroom.id;
                """)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Message message = new Message();
                User user = new User();
                Chatroom chatroom = new Chatroom();
                message.setId(rs.getInt("message_id"));
                user.setId(rs.getInt("author_id"));
                user.setLogin(rs.getString("author_login"));
                user.setPassword(rs.getString("author_password"));
                message.setAuthor(user);

                chatroom.setId(rs.getInt("room_id"));
                chatroom.setName(rs.getString("room_name"));
                message.setRoom(chatroom);

                message.setText(rs.getString("message_text"));
                message.setDate(rs.getTimestamp("message_date_time"));
               return Optional.of(message);
            }
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
            return Optional.empty();
        }
        return Optional.empty();
    }
}
