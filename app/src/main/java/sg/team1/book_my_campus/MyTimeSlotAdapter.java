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
    private SelectListener listener;

    public void setDate(String date) {
        this.date = date;
    }

    public MyTimeSlotAdapter(ArrayList<TimeSlot> timeSlotList, SelectListener listener, String date, List<Booking> bookingList, String roomName) {

        this.timeSlotList = timeSlotList;
        this.bookingList = bookingList;
        this.date = date;
        this.roomName = roomName;
        this.listener = listener;


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


        } //if all slots is available,show
        else {
            timeSlotList.get(position).setAvail(false);
            holder.txt_time_slot_description.setText("Booked");

        }//if some are booked
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(timeSlotList.get(holder.getAdapterPosition()));
            }
        });

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
            Log.v(Title, "check1" + bookingList.size());
            timeSlotList.get(j).setAvail(true);
            for (int i = 0; i < bookingList.size(); i++) {
                Log.v(Title, "check2" + roomName);
                if (bookingList.get(i).getRoomName().equals(roomName)){
                    Log.v(Title, "checkroom" );

                    if (bookingList.get(i).getTimeSlot().equals(timeSlotList.get(j).getSlot()) && bookingList.get(i).getDate().equals(date)) {
                        Log.v(Title, "check3" + bookingList.size());
                        timeSlotList.get(j).setAvail(false);
                        Log.v(Title, bookingList.get(i).getTimeSlot()+" is booked");
                    }
                }
            }

        }

    }

}

