package sg.team1.book_my_campus;

public class Room {
    public int roomNumber;
    public String roomName;
    public String description;
    public String location;
    public int level;
    public String building;
    public int capacity;
    public String category;
    public boolean isAvail;
    public int image;

    public Room() {
    }

    public Room(int roomNumber,String roomName, String description, String location, int level, String building, int capacity, String category, boolean isAvail,int image) {
        this.roomNumber = roomNumber;
        this.roomName = roomName;
        this.description = description;
        this.location = location;
        this.level = level;
        this.building = building;
        this.capacity = capacity;
        this.category = category;
        this.isAvail = isAvail;
        this.image = image;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
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
}