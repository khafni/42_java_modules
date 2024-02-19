package fr._42.chat.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Message {
    private Long id;
    private User author;
    private Room room;
    private String text;
    private LocalDateTime date;

    public Message() {
    }

    public Message(Long id, User author, Room room, String text, LocalDateTime date) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }



    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Message m)) return false;
        return id == m.id && author == m.author && room == m.room && text.equals(m.text) && date == m.date;
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
                + "dateTime=" + getDate().toString() + "\n}";
    }
}