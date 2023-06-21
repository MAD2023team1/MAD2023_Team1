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
    ArrayList<CardView> cardViewList = new ArrayList<>();
    private ItemClickListener mItemClickListener;


    public MyTimeSlotAdapter(ArrayList<TimeSlot> timeSlotList, ItemClickListener itemClickListener) {

        this.timeSlotList = timeSlotList;


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


        if (timeSlotList.get(position).isAvail()) {
            holder.txt_time_slot_description.setText("Available");

        } //if all slots is available,show
        else {
            holder.txt_time_slot_description.setText("Booked");

        }//if some are booked
        holder.itemView.setOnClickListener(view -> {
            mItemClickListener.onItemClick(timeSlotList.get(position));//get position of item in recyclerview
        });

    }


    @Override
    public int getItemCount() {
        return timeSlotList.size();
    }

    public interface ItemClickListener {
        void onItemClick(TimeSlot timeslot);
    }

}


