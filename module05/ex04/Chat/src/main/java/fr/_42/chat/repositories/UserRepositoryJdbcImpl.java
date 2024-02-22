package fr._42.chat.repositories;
import fr._42.chat.models.Room;
import fr._42.chat.models.User;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
                                                                 	array_agg(crp.id) FILTER ( WHERE crp.id IS NOT NULL ) AS cp_id,
                                                                 	array_agg(crp.name) FILTER ( WHERE crp.name IS NOT NULL ) AS cp_name,
                                                                 	array_agg(cro.id) FILTER ( WHERE cro.id IS NOT NULL ) AS co_id,
                                                                 	array_agg(cro.name) FILTER ( WHERE cro.name IS NOT NULL ) AS co_name
                                                                 FROM "User" u
                                                                 FULL JOIN userchatroom ucr on u.id = ucr.userid
                                                                 FULL JOIN chatroom crp on ucr.chatroom = crp.id -- part of as a user
                                                                 FULL JOIN chatroom cro on cro.owner = u.id AND cro.id = crp.id -- owned by the user
                                                                  GROUP BY u.id, login, password
                                                                 )
                                  SELECT * FROM userData
                                  Order By u_id
                                   OFFSET ? LIMIT ?;
                    """)) {
            ps.setInt(1, page);
            ps.setInt(2, size);
            Map<Integer, User> userMap = new HashMap<>();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Long userId = rs.getLong("u_id");
                Boolean isUserId = rs.wasNull();
                String userLogin = rs.getString("u_login");
                String userPassword = rs.getString("u_password");
                if (!isUserId)
                    userMap.computeIfAbsent(userId.intValue(), id -> new User(userId, userLogin, userPassword, new ArrayList<>(), new ArrayList<>()));

                Integer[] chatRoomUserPartOfIds =  (Integer[]) rs.getArray("cp_id").getArray();
                String[] chatRoomUserPartOfNames =  (String[]) rs.getArray("cp_name").getArray();
                Integer[] chatRoomUserOwnsIds =  (Integer[]) rs.getArray("co_id").getArray();
                String[] chatRoomUserOwnsNames =  (String[]) rs.getArray("co_name").getArray();
                for (int i = 0; i < chatRoomUserPartOfIds.length; i++) {
                    userMap.get(userId.intValue()).getRooms().add(new Room((long)chatRoomUserPartOfIds[i], chatRoomUserPartOfNames[i], null, null));
                }
                for (int i = 0; i < chatRoomUserOwnsIds.length; i++) {
                    userMap.get(userId.intValue()).getCreatedRooms().add(new Room((long)chatRoomUserOwnsIds[i], chatRoomUserOwnsNames[i], null, null));
                }
            }
            return userMap.values().stream().toList();
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }
        return null;
    }

}
