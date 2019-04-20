package com.blucode.mhmd.timeline.data.model;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class TextMessage extends BasicMessage {

    @Id
    public long id;
    private String text;

    public TextMessage() {
        messageType = MessageType.TEXT_MESSAGE;
    }

    public TextMessage(String text) {
        this.text = text;
        messageType = MessageType.TEXT_MESSAGE;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
