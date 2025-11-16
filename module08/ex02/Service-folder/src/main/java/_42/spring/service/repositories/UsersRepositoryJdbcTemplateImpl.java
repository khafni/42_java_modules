package _42.spring.service.repositories;

import _42.spring.service.models.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("usersRepositoryJdbcTemplate")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository{
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedTemplate;

    private static final String SQL_FIND_BY_ID = "SELECT * FROM users WHERE id = :id";
    private static final String SQL_FIND_ALL = "SELECT * FROM users";
    private static final String SQL_INSERT = "INSERT INTO users (email) VALUES (:email)";
    private static final String SQL_UPDATE = "UPDATE users SET email = :email WHERE id = :id";
    private static final String SQL_DELETE = "DELETE FROM users WHERE id = :id";
    private static final String SQL_FIND_BY_EMAIL = "SELECT * FROM users WHERE email = :email";

    private final RowMapper<User> rowMapper = (rs, rowNum) ->
            new User(rs.getLong("id"), rs.getString("email"));


    public UsersRepositoryJdbcTemplateImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findById(Long id) {
        Map<String, Object> params = Map.of("id", id);
        List<User> list = namedTemplate.query(SQL_FIND_BY_ID, params, rowMapper);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }

    @Override
    public void save(User entity) {
        Map<String, Object> params = Map.of("email", entity.getEmail());
        MapSqlParameterSource source = new MapSqlParameterSource(params);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedTemplate.update(SQL_INSERT, source, keyHolder, new String[]{"id"});
        Number key = keyHolder.getKey();
        if (key != null) {
            entity.setId(key.longValue());
        }
    }

    @Override
    public void update(User entity) {
        Map<String, Object> params = Map.of(
                "id", entity.getId(),
                "email", entity.getEmail()
        );
        namedTemplate.update(SQL_UPDATE, params);
    }

    @Override
    public void delete(Long id) {
        Map<String, Object> params = Map.of("id", id);
        namedTemplate.update(SQL_DELETE, params);
    }
}
