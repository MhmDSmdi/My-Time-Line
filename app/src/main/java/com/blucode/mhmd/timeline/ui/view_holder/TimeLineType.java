package com.blucode.mhmd.timeline.ui.view_holder;

public enum TimeLineType {
    NORMAL(0),
    START(1),
    END(2),
    ONLYONE(3);
    public int type;
    TimeLineType(int type) {
        this.type = type;
    }

    public static TimeLineType getTimelineType(int size, int position) {
        if (size == 1)
            return TimeLineType.ONLYONE;
        else if (position == size - 1)
            return TimeLineType.END;
        else if (position  == 0)
            return TimeLineType.START;
        else
            return TimeLineType.NORMAL;
    }
}
