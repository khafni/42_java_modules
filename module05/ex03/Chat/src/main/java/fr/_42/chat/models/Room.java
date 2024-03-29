package fr._42.chat.models;

import java.util.List;

public class Room {
    public Room() {
    }

    public Room(Long id, String name, User owner, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.messages = messages;
    }

    private Long id;
    private String name;
    private User owner;
    private List<Message> messages;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        if (!(o instanceof Room chatroom)) return false;
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