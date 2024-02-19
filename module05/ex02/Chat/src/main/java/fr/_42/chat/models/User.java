package fr._42.chat.models;

import java.util.List;

public class User {
    private Long id;
    private String login;
    private String password;
    private List<Room> createdRooms;
    private List<Room> rooms; //where user socializes

    public User() {
    }

    public User(Long id, String login, String password, List<Room> createdRooms, List<Room> rooms) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdRooms = createdRooms;
        this.rooms = rooms;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Room> getCreatedRooms() {
        return createdRooms;
    }

    public void setCreatedRooms(List<Room> createdRooms) {
        this.createdRooms = createdRooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }


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

    @Override
    public String toString() {
        return "{id=" + id
                + ",login=\"" + login
                + "\",password=\"" + password
                + "\",createdRooms=" + createdRooms
                + "\",rooms=" + rooms + "}";
    }
}