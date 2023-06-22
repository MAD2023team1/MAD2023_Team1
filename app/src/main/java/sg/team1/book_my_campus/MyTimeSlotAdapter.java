package sg.team1.book_my_campus;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyTimeSlotAdapter extends RecyclerView.Adapter<MyTimeSlotViewHolder> {

    String Title = "Time Slot Adapter";
    List<TimeSlot> timeSlotList;
    List<Booking> bookingList;
    String date;
    String roomName;
    private ItemClickListener mItemClickListener;

    public void setDate(String date) {
        this.date = date;
    }

    public MyTimeSlotAdapter(ArrayList<TimeSlot> timeSlotList, ItemClickListener itemClickListener, String date, List<Booking> bookingList, String roomName) {

        this.timeSlotList = timeSlotList;
        this.bookingList = bookingList;
        this.date = date;
        this.roomName = roomName;


        this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyTimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookingslots, parent, false);
        return new MyTimeSlotViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTimeSlotViewHolder holder, int position) {
        holder.txt_time_slot.setText(new StringBuilder(Variables.convertTimeSlot(position)).toString());
        CheckTimeSlots();


        if (timeSlotList.get(position).isAvail() == true) {
            holder.txt_time_slot_description.setText("Available");
            holder.itemView.setOnClickListener(view -> {
                mItemClickListener.onItemClick(timeSlotList.get(position));//get position of item in recyclerview
            });


        } //if all slots is available,show
        else {
            holder.txt_time_slot_description.setText("Booked");

        }//if some are booked


    }


    @Override
    public int getItemCount() {
        return timeSlotList.size();
    }

    public interface ItemClickListener {
        void onItemClick(TimeSlot timeslot);
    }

    public void CheckTimeSlots() {
        Log.v(Title, "checktimecrate" + bookingList.size());
        for (int j = 0; j < timeSlotList.size(); j++) {
            timeSlotList.get(j).setAvail(true);
            for (int i = 0; i < bookingList.size(); i++) {
                if (bookingList.get(i).getTimeSlot().equals(timeSlotList.get(j).getSlot()) && bookingList.get(i).getDate().equals(date)) {
                    timeSlotList.get(j).setAvail(false);
                    Log.v(Title, bookingList.get(i).getTimeSlot()+" is booked");
                }
            }
        }

            /*if (roomName == bookingList.get(i).roomName && date.getText().toString() == bookingList.get(i).getDate()) {
                {
                    for (int z = 0; i < timeSlots.size(); z++) {
                        if (timeSlots.get(z).getSlot() == bookingList.get(i).getTimeSlot()) {
                            timeSlots.get(z).setAvail(false);
                        }

                    }
                }
            }
        for (Booking booking : bookingList) {
            Log.v(title,"booker");
            if (roomName.equals(booking.getRoomName())) {
                Log.v(title,"checkdateif");
                if (date.getText().toString().equals(booking.getDate()))
                {
                    Log.v(title,"ifdate");
                    for (TimeSlot time:timeSlots)
                    {
                        Log.v(title,"timeloop");
                        if(time.getSlot()==booking.getTimeSlot())
                        {
                            time.setAvail(false);
                            Log.v(title,"setava false");
                        }
                        else {
                            time.setAvail(true);
                            Log.v(title,"setava true");

                        }
                    }
                }

            }


        }*/


    }
}


