package fr._42.chat.models;

import java.util.List;
import java.util.Objects;

public class Chatroom {
    private int id;
    private String name;
    private User owner;
    private List<Message> messages;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }



    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Chatroom chatroom)) return false;
        return id == chatroom.id && name.equals(chatroom.name) && owner.equals(chatroom.owner) && messages.equals(chatroom.messages);
    }

    @Override
    public int hashCode() {
        int code = 31;

        code += id;
        code += name.hashCode();
        code += owner.hashCode();
        code += messages.hashCode();
        return code;
    }

    @Override
    public String toString() {
        return "{id=" + id
                + ",name=\"" + name
                + "\",creator=\"" + owner
                + "\",messages=" + messages + "}";
    }
}