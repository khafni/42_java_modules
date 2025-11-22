package fr._42.sockets.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long id;
    private User sender;
    private Chatroom chatroom;
    private String text;
    private LocalDateTime timestamp;

    public Message() {
    }

    public Message(Long id, User sender, Chatroom chatroom, String text, LocalDateTime timestamp) {
        this.id = id;
        this.sender = sender;
        this.chatroom = chatroom;
        this.text = text;
        this.timestamp = timestamp;
    }

    public Message(User sender, Chatroom chatroom, String text, LocalDateTime timestamp) {
        this.sender = sender;
        this.chatroom = chatroom;
        this.text = text;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Chatroom getChatroom() {
        return chatroom;
    }

    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) &&
               Objects.equals(sender, message.sender) &&
               Objects.equals(chatroom, message.chatroom) &&
               Objects.equals(text, message.text) &&
               Objects.equals(timestamp, message.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender, chatroom, text, timestamp);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender=" + sender +
                ", chatroom=" + chatroom +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
