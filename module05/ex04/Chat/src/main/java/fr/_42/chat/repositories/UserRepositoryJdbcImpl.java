package fr._42.chat.repositories;

import fr._42.chat.models.Message;
import fr._42.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryJdbcImpl implements UserRepository {

    private final DataSource dataSource;

    public UserRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll(int page, int size) {
        try (Connection conn = dataSource.getConnection(); var ps = conn.prepareStatement("""
               WITH userData AS (
                               SELECT
                               	u.id AS u_id,
                               	login AS u_login,
                               	password AS u_password,
                               	crp.id AS cp_id,
                               	crp.name AS cp_name,
                               	crp.owner AS cp_owner,
                               	cro.id AS co_id,
                               	cro.name AS co_name,
                               	cro.owner AS co_owner
                               FROM "User" u
                               JOIN userchatroom ucr on u.id = ucr.userid
                               JOIN chatroom crp on ucr.chatroom = crp.id -- part of as a user
                               FULL JOIN chatroom cro on cro.owner = u.id AND cro.id = crp.id -- owned by the user
                               )
                               SELECT * FROM userData
                               Order By u_id;
                 """)) {
            Map<Integer, User> userMap = new HashMap<>();

        } catch (SQLException sqlException) {

        }
        return List.of(new User());
    }

}
