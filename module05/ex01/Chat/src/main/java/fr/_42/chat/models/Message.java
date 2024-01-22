package fr._42.chat.models;

import java.sql.Time;
import java.sql.Timestamp;

public class Message {
    private int id;
    private User author;
    private Chatroom room;
    private String text;
    private Timestamp date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }



    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Message m)) return false;
        return id == m.id && author == m.author && room == m.room && text == m.text && date == m.date;
    }

    @Override
    public int hashCode() {
        int code = 31;

        code += id;
        code += (author != null ? author.hashCode() : 0);
        code += (room != null ? room.hashCode() : 0);
        code += (text != null ? text.hashCode() : 0);
        code += (date != null ? date.hashCode() : 0);
        return code;
    }
    @Override
    public String toString() {
        return "Message : { \nid="+ id
                + ",\nauthor=" + getAuthor().toString() + ",\n"
                + "room=" + getRoom().toString() + ",\n"
                + "text=\"" + text + "\"\n"
                + "dateTime=" + date.get + "\n}";

    }
}