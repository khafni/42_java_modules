package fr._42.sockets.repositories;

import fr._42.sockets.models.Chatroom;
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
import java.util.List;
import java.util.Optional;

@Repository
public class ChatroomRepositoryImpl implements ChatroomRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Chatroom> chatroomRowMapper = new RowMapper<Chatroom>() {
        @Override
        public Chatroom mapRow(ResultSet rs, int rowNum) throws SQLException {
            Chatroom chatroom = new Chatroom();
            chatroom.setId(rs.getLong("id"));
            chatroom.setName(rs.getString("name"));
            return chatroom;
        }
    };

    @Autowired
    public ChatroomRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Chatroom> findById(Long id) {
        String sql = "SELECT * FROM chatrooms WHERE id = ?";
        try {
            Chatroom chatroom = jdbcTemplate.queryForObject(sql, chatroomRowMapper, id);
            return Optional.ofNullable(chatroom);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Chatroom> findByName(String name) {
        String sql = "SELECT * FROM chatrooms WHERE name = ?";
        try {
            Chatroom chatroom = jdbcTemplate.queryForObject(sql, chatroomRowMapper, name);
            return Optional.ofNullable(chatroom);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Chatroom> findAll() {
        String sql = "SELECT * FROM chatrooms ORDER BY name";
        return jdbcTemplate.query(sql, chatroomRowMapper);
    }

    @Override
    public void save(Chatroom entity) {
        String sql = "INSERT INTO chatrooms (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            // Restrict generated keys to the ID column to avoid multi-column key maps
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, entity.getName());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            entity.setId(keyHolder.getKey().longValue());
        }
    }

    @Override
    public void update(Chatroom entity) {
        String sql = "UPDATE chatrooms SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, entity.getName(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM chatrooms WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
