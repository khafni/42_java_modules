package main.java.fr._42.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private int id;
    private String login;
    private String password;
    private List<Chatroom> createdRooms;
    private List<Chatroom> rooms; //where user socializes
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return  true;
        }
        if (!(o instanceof User user))
            return false;
        return id == user.id
                && login == user.login
                && password == user.password
                && createdRooms.equals(user.createdRooms)
                && rooms.equals(user.rooms);
    }

    @Override
    public int hashCode() {
        int code = 31;

        code += id;
        code += (login != null? login.hashCode() : 0);
        code += (password != null? password.hashCode() : 0);
        code += (createdRooms != null? createdRooms.hashCode() : 0);
        code += (rooms != null? rooms.hashCode() : 0);
        return code;
    }
}