package sg.team1.book_my_campus;

import android.content.Intent;

public class TimeSlot {
    public boolean isAvail;
    private String slot;

    public TimeSlot(){}

    public TimeSlot(boolean isAvail, String slot) {
        this.isAvail = isAvail;
        this.slot = slot;
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

