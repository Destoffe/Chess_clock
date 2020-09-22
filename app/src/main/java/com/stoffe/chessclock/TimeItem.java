package com.stoffe.chessclock;

public class TimeItem {
    private int time;
    private String increment;

    public TimeItem(int time, String increment){
        this.time = time;
        this.increment = increment;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getIncrement() {
        return increment;
    }

    public void setIncrement(String increment) {
        this.increment = increment;
    }


}
