package com.stoffe.chessclock;

public class TimeItem {
    private int time;
    private int increment;

    public TimeItem(int time, int increment) {
        this.time = time;
        this.increment = increment;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getIncrement() {
        return increment;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }

}
