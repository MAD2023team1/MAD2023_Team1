package sg.team1.book_my_campus;

import java.sql.Time;
import java.util.Date;


import java.sql.Time;
import java.util.Date;

public class Booking {

    public User user;

    public Room room;

    public String date;

    public TimeSlot timeSlot;

    public boolean isCanceled;
    public boolean isCheckedIn;

    public Booking() {
    }

    public Booking(User user, Room room, String date, TimeSlot timeSlot, boolean isCanceled, boolean isCheckedIn) {
        this.user = user;
        this.room = room;
        this.date = date;
        this.timeSlot = timeSlot;
        this.isCanceled = isCanceled;
        this.isCheckedIn = isCheckedIn;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
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