package com.blucode.mhmd.timeline.data;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class VoiceMessage {

    private String bodyMessage;
    private Date time;
    private long duration;
    private String durationFormative;
    private String path;

    public VoiceMessage(String bodyMessage, Date time, String path) {
        this.bodyMessage = bodyMessage;
        this.time = time;
        this.path = path;
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
        durationFormative = String.format("%02d:%d",
                 TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)),
                (TimeUnit.MILLISECONDS.toMillis(duration) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(duration))) / 100);
        return durationFormative;
    }

    public void setBodyMessage(String bodyMessage) {
        this.bodyMessage = bodyMessage;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
