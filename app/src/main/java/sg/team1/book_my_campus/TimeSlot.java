package sg.team1.book_my_campus;

import android.content.Intent;

public class TimeSlot {
    private boolean isAvail;
    private int slot;
    public  TimeSlot(){}

    public TimeSlot(boolean isAvail, int slot) {
        this.isAvail = isAvail;
        this.slot = slot;
    }

    public boolean isAvail() {
        return isAvail;
    }

    public void setAvail(boolean avail) {
        isAvail = avail;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
