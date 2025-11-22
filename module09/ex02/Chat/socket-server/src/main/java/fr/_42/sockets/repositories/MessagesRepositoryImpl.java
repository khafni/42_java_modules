package fr._42.sockets.repositories;

import fr._42.sockets.models.Chatroom;
import fr._42.sockets.models.Message;
import fr._42.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class MessagesRepositoryImpl implements MessagesRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UsersRepository usersRepository;
    private final ChatroomRepository chatroomRepository;

    @Autowired
    public MessagesRepositoryImpl(JdbcTemplate jdbcTemplate, UsersRepository usersRepository, ChatroomRepository chatroomRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.usersRepository = usersRepository;
        this.chatroomRepository = chatroomRepository;
    }

    private final RowMapper<Message> messageRowMapper = new RowMapper<Message>() {
        @Override
        public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
            Message message = new Message();
            message.setId(rs.getLong("id"));

            Long senderId = rs.getLong("sender_id");
            Optional<User> sender = usersRepository.findById(senderId);
            sender.ifPresent(message::setSender);

            Long chatroomId = rs.getLong("chatroom_id");
            Optional<Chatroom> chatroom = chatroomRepository.findById(chatroomId);
            chatroom.ifPresent(message::setChatroom);

            message.setText(rs.getString("text"));

            Timestamp timestamp = rs.getTimestamp("timestamp");
            if (timestamp != null) {
                message.setTimestamp(timestamp.toLocalDateTime());
            }

            return message;
        }
    };

    @Override
    public Optional<Message> findById(Long id) {
        String sql = "SELECT * FROM messages WHERE id = ?";
        try {
            Message message = jdbcTemplate.queryForObject(sql, messageRowMapper, id);
            return Optional.ofNullable(message);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Message> findAll() {
        String sql = "SELECT * FROM messages ORDER BY timestamp ASC";
        return jdbcTemplate.query(sql, messageRowMapper);
    }

    @Override
    public List<Message> findByChatroomId(Long chatroomId) {
        String sql = "SELECT * FROM messages WHERE chatroom_id = ? ORDER BY timestamp ASC";
        return jdbcTemplate.query(sql, messageRowMapper, chatroomId);
    }

    @Override
    public List<Message> findRecentByChatroomId(Long chatroomId, int limit) {
        String sql = "SELECT * FROM messages WHERE chatroom_id = ? ORDER BY timestamp DESC LIMIT ?";
        return jdbcTemplate.query(sql, ps -> {
            ps.setLong(1, chatroomId);
            ps.setInt(2, limit);
        }, messageRowMapper);
    }

    @Override
    public void save(Message entity) {
        String sql = "INSERT INTO messages (sender_id, chatroom_id, text, timestamp) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, entity.getSender().getId());
            ps.setLong(2, entity.getChatroom().getId());
            ps.setString(3, entity.getText());
            ps.setTimestamp(4, Timestamp.valueOf(entity.getTimestamp()));
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            entity.setId(key.longValue());
        }
    }

    @Override
    public void update(Message entity) {
        String sql = "UPDATE messages SET sender_id = ?, chatroom_id = ?, text = ?, timestamp = ? WHERE id = ?";
        jdbcTemplate.update(sql,
            entity.getSender().getId(),
            entity.getChatroom().getId(),
            entity.getText(),
            Timestamp.valueOf(entity.getTimestamp()),
            entity.getId()
        );
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM messages WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
