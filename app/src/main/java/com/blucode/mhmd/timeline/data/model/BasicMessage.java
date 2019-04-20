package com.blucode.mhmd.timeline.data.model;

import java.util.Comparator;
import java.util.Date;

import io.objectbox.annotation.BaseEntity;
import io.objectbox.annotation.Convert;
import io.objectbox.converter.PropertyConverter;

@BaseEntity
public class BasicMessage implements Comparator<BasicMessage> {


    @Convert(converter = MessageTypeConverter.class, dbType = String.class)
    protected MessageType messageType;
    protected Date date;

    public BasicMessage() {
        date = new Date();
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compare(BasicMessage o1, BasicMessage o2) {
        return (int) (o2.date.getTime() - o1.date.getTime());
    }
}

enum MessageType{
    IMAGE_MESSAGE(0),
    TEXT_MESSAGE(1),
    ALBUM_MESSAGE(2),
    VOICE_MESSAGE(3);

    private int messageType;
    MessageType(int type) {
        this.messageType = type;
    }
    public int getMessageType() {
        return messageType;
    }
}

class MessageTypeConverter implements PropertyConverter<MessageType, String> {

    @Override
    public MessageType convertToEntityProperty(String databaseValue) {
        return MessageType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(MessageType entityProperty) {
        return entityProperty.name();
    }
}