package fr._42.chat.models;

import java.util.Date;

public class Message {
    private int id;
    private String author;
    private String room;
    private String text;
    private Date date;

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Message m))
            return  false;
        return id == m.id
                && author == m.author
                && room == m.room
                && text == m.text
                && date == m.date;
    }
    @Override
    public int hashCode() {
        int code = 31;

        code += id;
        code += (author != null ? author.hashCode(): 0);
        code += (room != null ? room.hashCode(): 0);
        code += (text != null ? text.hashCode(): 0);
        code += (date != null ? date.hashCode(): 0);
        return code;
    }
}