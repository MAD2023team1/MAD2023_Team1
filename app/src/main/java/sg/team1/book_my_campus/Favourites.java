package sg.team1.book_my_campus;

public class Favourites {
    private String userName;
    private String roomName;
    private String docid;

    public Favourites() {
    }

    public Favourites(String userName, String roomName) {
        this.userName = userName;
        this.roomName = roomName;
    }

    public Favourites(String userName, String roomName, String docid) {
        this.userName = userName;
        this.roomName = roomName;
        this.docid = docid;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
