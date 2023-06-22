package sg.team1.book_my_campus;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.sql.Time;
import java.util.Date;


import java.sql.Time;
import java.util.Date;

public class Booking implements Parcelable {

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


    protected Booking(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        room = in.readParcelable(Room.class.getClassLoader());
        date = in.readString();
        isCanceled = in.readByte() != 0;
        isCheckedIn = in.readByte() != 0;
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeParcelable(user, i);
        parcel.writeParcelable(room, i);
        parcel.writeString(date);
        parcel.writeByte((byte) (isCanceled ? 1 : 0));
        parcel.writeByte((byte) (isCheckedIn ? 1 : 0));
    }
}