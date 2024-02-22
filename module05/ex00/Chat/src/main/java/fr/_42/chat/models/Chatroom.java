package fr._42.chat.models;

import java.util.List;

public class Chatroom {
    private int id;
    private String name;
    private User owner;
    private List<Message> messages;

    @Override
    public boolean equals(Object o)
    {
        if (o == this) {
            return  true;
        }
        if (!(o instanceof Chatroom chatroom))
            return false;
        return id == chatroom.id
                && name.equals(chatroom.name)
                && owner.equals(chatroom.owner)
                && messages.equals(chatroom.messages);
    }

    @Override
    public int hashCode() {
        int code = 31;

        code += id;
        code += name.hashCode();
        code += owner.hashCode();
        code += messages.hashCode();
        return  code;
    }
}