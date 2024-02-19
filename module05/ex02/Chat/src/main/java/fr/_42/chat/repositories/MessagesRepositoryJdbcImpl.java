package fr._42.chat.repositories;


import fr._42.chat.exceptions.NotSavedSubEntityException;
import fr._42.chat.models.Message;
import fr._42.chat.models.Room;
import fr._42.chat.models.User;


import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource dataSource;

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
                                                            JOIN chatroom ON message.room = chatroom.id
                                                            WHERE message.id = ?
                """)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Message message = new Message();
                User user = new User();
                Room chatroom = new Room();
                message.setId(rs.getLong("message_id"));

                user.setId(rs.getLong("author_id"));
                user.setLogin(rs.getString("author_login"));
                user.setPassword(rs.getString("author_password"));
                message.setAuthor(user);

                chatroom.setId(rs.getLong("room_id"));
                chatroom.setName(rs.getString("room_name"));
                message.setRoom(chatroom);

                message.setText(rs.getString("message_text"));
                message.setDate(rs.getTimestamp("message_date_time").toLocalDateTime());
                return Optional.of(message);
            }
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public void save(Message message) {
        try (Connection conn = dataSource.getConnection();
             var ps = conn.prepareStatement("""
                     INSERT INTO message (author, room, text, date_time) VALUES(?, ?, ?, ?)
                         """, PreparedStatement.RETURN_GENERATED_KEYS);
             var checkAuthorIdPs = conn.prepareStatement("""
                     SELECT COUNT(*) FROM "User"
                     WHERE id = ?
                     """);
             var checkRoomIdPs = conn.prepareStatement("""
                     SELECT COUNT(*) FROM chatroom
                     WHERE id = ?
                     """)) {

            checkAuthorIdPs.setLong(1, message.getAuthor().getId());
            checkRoomIdPs.setLong(1, message.getRoom().getId());
            ResultSet isAuthorIdValid = checkAuthorIdPs.executeQuery();
            ResultSet isChatRoomIdValid = checkRoomIdPs.executeQuery();
            isAuthorIdValid.next();
            if (isAuthorIdValid.getInt(1) == 0)
                throw new NotSavedSubEntityException("author has no ID assigned in the database");
            isChatRoomIdValid.next();
            if (isChatRoomIdValid.getInt(1) == 0)
                throw new NotSavedSubEntityException("chatRoom has no ID assigned in the database");

            ps.setLong(1, message.getAuthor().getId());
            ps.setLong(2, message.getRoom().getId());
            ps.setString(3, message.getText());
            ps.setTimestamp(4, Timestamp.valueOf(message.getDate()));
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            message.setId(generatedKeys.getLong(1));
        } catch (SQLException | NotSavedSubEntityException e) {
            System.err.println(e.getMessage());
        }
    }
}
