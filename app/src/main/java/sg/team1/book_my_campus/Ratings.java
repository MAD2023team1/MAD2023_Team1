package sg.team1.book_my_campus;

public class Ratings {
    public String userName;
    public String roomName;
    public String userID;
    public String dateBooked;
    public String timeSlot;
    public String comment;
    public float starRatings;

    public Ratings(){

    }

    public Ratings(String userName, String roomName, String userID, String dateBooked, String timeSlot, String comment, float starRatings) {
        this.userName = userName;
        this.roomName = roomName;
        this.userID = userID;
        this.dateBooked = dateBooked;
        this.timeSlot = timeSlot;
        this.comment = comment;
        this.starRatings = starRatings;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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




}
