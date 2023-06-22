package sg.team1.book_my_campus;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.sql.Time;
import java.util.Date;


import java.sql.Time;
import java.util.Date;

public class Booking implements Parcelable {

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

    protected Booking(Parcel in) {
        name = in.readString();
        roomName = in.readString();
        date = in.readString();
        timeSlot = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(roomName);
        parcel.writeString(date);
        parcel.writeString(timeSlot);
        parcel.writeByte((byte) (isCanceled ? 1 : 0));
        parcel.writeByte((byte) (isCheckedIn ? 1 : 0));
    }
}