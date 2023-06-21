package sg.team1.book_my_campus;

import java.sql.Time;
import java.util.Date;


import java.sql.Time;
import java.util.Date;

public class Booking {

    public String name;

    public String roomName;

    public String date;

    public String timeSlot;

    public boolean isCanceled;
    public boolean isCheckedIn;

    public Booking() {
    }

    public Booking(String name, String roomName, String date, String timeSlot, boolean isCanceled, boolean isCheckedIn) {
        this.name = name;
        this.roomName = roomName;
        this.date = date;
        this.timeSlot = timeSlot;
        this.isCanceled = isCanceled;
        this.isCheckedIn = isCheckedIn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public boolean isCheckedIn() {
        return isCheckedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        isCheckedIn = checkedIn;
    }
}