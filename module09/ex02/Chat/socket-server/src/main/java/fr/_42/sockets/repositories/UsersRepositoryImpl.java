package fr._42.sockets.repositories;

import fr._42.sockets.models.Chatroom;
import fr._42.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.Optional;

@Repository
public class UsersRepositoryImpl implements UsersRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ChatroomRepository chatroomRepository;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));

        Long lastChatroomId = rs.getLong("last_chatroom_id");
        if (!rs.wasNull()) {
            chatroomRepository.findById(lastChatroomId).ifPresent(user::setLastChatroom);
        }
        return user;
    };

    @Autowired
    public UsersRepositoryImpl(JdbcTemplate jdbcTemplate, ChatroomRepository chatroomRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.chatroomRepository = chatroomRepository;
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            String sql = "SELECT id, username, password, last_chatroom_id FROM users WHERE id = ?";
            User user = jdbcTemplate.queryForObject(sql, userRowMapper, id);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            String sql = "SELECT id, username, password, last_chatroom_id FROM users WHERE username = ?";
            User user = jdbcTemplate.queryForObject(sql, userRowMapper, username);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(User entity) {
        String sql = "INSERT INTO users (username, password, last_chatroom_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getPassword());
            if (entity.getLastChatroom() != null) {
                ps.setLong(3, entity.getLastChatroom().getId());
            } else {
                ps.setNull(3, java.sql.Types.BIGINT);
            }
            return ps;
        }, keyHolder);

        entity.setId(keyHolder.getKey().longValue());
    }

    @Override
    public void update(User entity) {
        String sql = "UPDATE users SET username = ?, password = ?, last_chatroom_id = ? WHERE id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getPassword());
            if (entity.getLastChatroom() != null) {
                ps.setLong(3, entity.getLastChatroom().getId());
            } else {
                ps.setNull(3, Types.BIGINT);
            }
            ps.setLong(4, entity.getId());
            return ps;
        });
    }

    @Override
    public void updateLastChatroom(Long userId, Long chatroomId) {
        String sql = "UPDATE users SET last_chatroom_id = ? WHERE id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (chatroomId != null) {
                ps.setLong(1, chatroomId);
            } else {
                ps.setNull(1, Types.BIGINT);
            }
            ps.setLong(2, userId);
            return ps;
        });
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
