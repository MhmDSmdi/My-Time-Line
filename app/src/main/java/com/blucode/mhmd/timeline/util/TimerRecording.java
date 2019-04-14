package com.blucode.mhmd.timeline.util;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TimerRecording {

    private Timer timer;
    private long tick;
    private String tickString;
    private Handler handler;
    private TimerTask tickTask;

    private void init() {
        tickTask = new TimerTask() {
            @Override
            public void run() {
                tick++;
                tickString = String.format("%02d:%02d:%d", TimeUnit.MILLISECONDS.toMinutes(tick) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(tick)),
                        TimeUnit.MILLISECONDS.toSeconds(tick) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tick)),
                        (TimeUnit.MILLISECONDS.toMillis(tick) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(tick))) / 100);
                handler.obtainMessage(1).sendToTarget();
            }
        };
        timer = new Timer();
    }


    public TimerRecording(Handler handler) {
        this.handler = handler;
    }

    public String getTickString() {
        return tickString;
    }

    public void cancelTimer() {
        tick = 0;
        tickString ="";
        tickTask.cancel();
        timer.cancel();
        timer = null;
        tickTask = null;
    }

    public void startTimer() {
        init();
        timer.schedule(tickTask, 0, 1);
    }

    interface TimerListener {
        void tick(String tick);
    }

    public long getTick() {
        return tick;
    }
}
