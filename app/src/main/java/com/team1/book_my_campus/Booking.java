package com.team1.book_my_campus;

import java.sql.Time;
import java.util.Date;

public class Booking {

    public User user;

    public int roomNumber;

    public Date date;

    public Time startTime;

    public Time endTime;

    public boolean isCanceled;
    public boolean isCheckedIn;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        this.isCanceled = canceled;
    }
    public boolean isCheckedIn(){
        return isCheckedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.isCheckedIn = checkedIn;
    }

    public Booking(User user, int roomNumber, java.util.Date date, Time startTime, Time endTime, boolean isCanceled,boolean isCheckedIn) {
        this.user = user;
        this.roomNumber = roomNumber;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCanceled = isCanceled;
        this.isCheckedIn=isCheckedIn;
    }
    public Booking(){}





}
