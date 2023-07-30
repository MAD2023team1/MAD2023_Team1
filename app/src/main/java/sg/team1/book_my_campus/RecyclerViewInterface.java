package sg.team1.book_my_campus;

import kotlin.experimental.BitwiseOperationsKt;

public interface RecyclerViewInterface {
    void onItemClick(int position);
    void onItemClicked(Booking booking);
    void favouritesOnClicked(Room room);
}
