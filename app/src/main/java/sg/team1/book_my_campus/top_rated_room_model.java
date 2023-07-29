package sg.team1.book_my_campus;

public class top_rated_room_model {
    public top_rated_room_model(float ratings, int roomImage, String name) {
        this.ratings = ratings;
        this.roomImage = roomImage;
        this.name = name;
    }

    float ratings;
    int roomImage;
    String name;

    public float getRatings() {
        return ratings;
    }

    public int getRoomImage() {
        return roomImage;
    }

    public String getName() {
        return name;
    }
}
