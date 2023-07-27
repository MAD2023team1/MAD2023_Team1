package sg.team1.book_my_campus;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Room implements Parcelable {
    public int roomID;
    public String roomName;
    public String description;
    public String location;
    public int level;
    public String building;
    public int capacity;
    public String category;
    public boolean isAvail;
    public int image;
    public boolean isRoomLiked;

    public Room() {
    }

    public Room(int roomID,String roomName, String description, String location, int level, String building, int capacity, String category, boolean isAvail,int image, boolean isRoomLiked) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.description = description;
        this.location = location;
        this.level = level;
        this.building = building;
        this.capacity = capacity;
        this.category = category;
        this.isAvail = isAvail;
        this.image = image;
        this.isRoomLiked = isRoomLiked;
    }

    protected Room(Parcel in) {
        roomID = in.readInt();
        roomName = in.readString();
        description = in.readString();
        location = in.readString();
        level = in.readInt();
        building = in.readString();
        capacity = in.readInt();
        category = in.readString();
        isAvail = in.readByte() != 0;
        image = in.readInt();
        isRoomLiked = in.readByte() != 0;
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    public int getRoomID() {
        return roomID;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomID = roomID;
    }
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvail() {
        return isAvail;
    }

    public void setAvail(boolean avail) {
        isAvail = avail;
    }
    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }

    public boolean isRoomLiked() {
        return isRoomLiked;
    }

    public void setRoomLiked(boolean roomLiked) {
        isRoomLiked = isRoomLiked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(roomID);
        parcel.writeString(roomName);
        parcel.writeString(description);
        parcel.writeString(location);
        parcel.writeInt(level);
        parcel.writeString(building);
        parcel.writeInt(capacity);
        parcel.writeString(category);
        parcel.writeByte((byte) (isAvail ? 1 : 0));
        parcel.writeInt(image);
        parcel.writeByte((byte) (isRoomLiked ? 1 : 0));
    }
}