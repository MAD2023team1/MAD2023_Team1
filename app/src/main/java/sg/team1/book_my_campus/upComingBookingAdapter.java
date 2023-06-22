package sg.team1.book_my_campus;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class upComingBookingAdapter extends RecyclerView.Adapter<upComingBookingAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    String title ="upcomingAdapter";
    ArrayList<Booking>bookingList;
    List<Booking> upcomingList;
    String myName;


    public upComingBookingAdapter(Context context, RecyclerViewInterface recyclerViewInterface, String myName,List<Booking> upcomingList,ArrayList<Booking>bookingList) {
        this.context = context;
        this.bookingList = bookingList;
        this.recyclerViewInterface = recyclerViewInterface;
        this.myName = myName;
        this.upcomingList = upcomingList;
    }

    @NonNull
    @Override
    public upComingBookingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcomingbooking_recycler_view_row, parent, false);
        return new upComingBookingAdapter.MyViewHolder(itemView, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull upComingBookingAdapter.MyViewHolder holder, int position) {

        holder.tvroomName.setText(upcomingList.get(position).getRoomName());
        holder.tvtimeslot.setText(upcomingList.get(position).getTimeSlot());
        holder.tvdateBooked.setText(upcomingList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        int count = upcomingList.size();
        if(upcomingList != null){
            return count;
        }else{//the user can choose not to book anything
            return 0;
        }

    }
    public void checkUpcomingBookings(){
        for (Booking booking:bookingList)
        {
            if(booking.getName().equals(myName)&& booking.isCheckedIn()==false&& booking.isCanceled()==false)
            {
                upcomingList.add(booking);

                Log.v(title,"booking added to upcoming" + booking.name);

            }
        }

    }




    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvroomName, tvtimeslot;
        TextView tvdateBooked;

        Button checkInBtn;

        Button cancelBtn;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tvroomName = itemView.findViewById(R.id.roomName);
            tvtimeslot = itemView.findViewById(R.id.timeslot);
            tvdateBooked = itemView.findViewById(R.id.date2);
            checkInBtn = itemView.findViewById(R.id.button8);
            cancelBtn = itemView.findViewById(R.id.button7);


            checkInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                }
            });
        }
    }
}
