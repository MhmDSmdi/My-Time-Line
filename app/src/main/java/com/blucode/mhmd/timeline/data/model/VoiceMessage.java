package com.blucode.mhmd.timeline.data.model;

import java.util.concurrent.TimeUnit;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class VoiceMessage extends BasicMessage{

    @Id
    public long id;
    private String bodyMessage;
    private long duration;
    private String path;
    private boolean isPlay = false;

    public VoiceMessage() {
        messageType = MessageType.VOICE_MESSAGE;
    }

    public VoiceMessage(String bodyMessage, String path) {
        this.bodyMessage = bodyMessage;
        this.path = path;
        messageType = MessageType.VOICE_MESSAGE;
    }

    public String getBodyMessage() {
        return bodyMessage;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getDurationFormative() {
        String durationFormative = String.format("%02d:%d",
                TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)),
                (TimeUnit.MILLISECONDS.toMillis(duration) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(duration))) / 100);
        return durationFormative;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public void setBodyMessage(String bodyMessage) {
        this.bodyMessage = bodyMessage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
