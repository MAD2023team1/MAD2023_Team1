package sg.team1.book_my_campus;

import android.content.Intent;

public class TimeSlot {
    public boolean isAvail;
    private String slot;

    public boolean checkTime;

    public TimeSlot(){}

    public TimeSlot(boolean isAvail, String slot,boolean checkTime) {
        this.isAvail = isAvail;
        this.slot = slot;
        this.checkTime=checkTime;

    }

    public boolean isCheckTime() {
        return checkTime;
    }

    public void setCheckTime(boolean checkTime) {
        this.checkTime = checkTime;
    }

    public boolean isAvail() {
        return isAvail;
    }

    public void setAvail(boolean avail) {
        isAvail = avail;
    }

    public String getSlot() {
        return slot;
    }


    public void setSlot(String slot) {
        this.slot = slot;
    }

}

