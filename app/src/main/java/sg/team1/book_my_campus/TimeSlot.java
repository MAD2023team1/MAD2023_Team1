package sg.team1.book_my_campus;

import android.content.Intent;

public class TimeSlot {
    private int slot;
    public  TimeSlot(){}

    public TimeSlot(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
