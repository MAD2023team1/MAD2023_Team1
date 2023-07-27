package sg.team1.book_my_campus;

public class Ratings {
    public Ratings(String roomName, String userID, String comment, float starRatings,String dateBooked, String timeSlot) {
        this.roomName = roomName;
        this.userID = userID;
        this.comment = comment;
        this.starRatings = starRatings;
        this.dateBooked = dateBooked;
        this.timeSlot = timeSlot;
    }

    public String roomName;
    public String userID;

    public String getDateBooked() {
        return dateBooked;
    }

    public void setDateBooked(String dateBooked) {
        this.dateBooked = dateBooked;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String dateBooked;
    public String timeSlot;
    public String comment;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomID(String roomName) {
        this.roomName = roomName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getStarRatings() {
        return starRatings;
    }

    public void setStarRatings(float starRatings) {
        this.starRatings = starRatings;
    }

    public float starRatings;


}
