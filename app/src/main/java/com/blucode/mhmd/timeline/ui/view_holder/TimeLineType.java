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

    public static TimeLineType getTimeLineViewType(int position, int totalSize) {
        if(totalSize == 1) {
            return TimeLineType.ONLYONE;
        } else if(position == 0) {
            return TimeLineType.START;
        } else if(position == totalSize - 1) {
            return TimeLineType.END;
        } else {
            return TimeLineType.NORMAL;
        }
    }
}
