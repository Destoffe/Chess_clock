package com.stoffe.chessclock;

public class TimeItem {

    public TimeItem(String time, String increment){
        this.time = time;
        this.increment = increment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIncrement() {
        return increment;
    }

    public void setIncrement(String increment) {
        this.increment = increment;
    }

    private String time;
    private String increment;
}
