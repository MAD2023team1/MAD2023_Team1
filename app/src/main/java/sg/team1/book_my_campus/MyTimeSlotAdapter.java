package sg.team1.book_my_campus;

import android.content.Context;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class MyTimeSlotAdapter extends RecyclerView.Adapter<MyTimeSlotViewHolder> {

    String Title = "Time Slot Adapter";
    ArrayList<TimeSlot> timeSlotList;
    ArrayList<Booking> bookingList;
    String date;
    String roomName;
    private ItemClickListener mItemClickListener;


    public MyTimeSlotAdapter(ArrayList<TimeSlot> timeSlotList, ItemClickListener itemClickListener, String date, ArrayList<Booking> bookingList, String roomName) {

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
        for (int i = 0; i < bookingList.size(); i++) {
            timeSlotList.get(i).setAvail(false);
            Log.v(Title, "che");
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


