package sg.team1.book_my_campus;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class bookingHistory_adapter extends RecyclerView.Adapter<bookingHistory_adapter.MyViewHolder> {
    String title = "booking hist adapter";
    Context context;
    ArrayList<Booking>bookingHistModels;
    ArrayList<Booking>bookingHistList;
    String myName;
    private final RecyclerViewInterface recyclerViewInterface;
    public bookingHistory_adapter(Context context, ArrayList<Booking> bookingHistModels, RecyclerViewInterface recyclerViewInterface, ArrayList<Booking>bookingHistList,String myName){
        this.context = context;
        this.bookingHistModels = bookingHistModels;
        this.recyclerViewInterface = recyclerViewInterface;
        this.bookingHistList = bookingHistList;
        this.myName = myName;

    }
    @NonNull
    @Override
    public bookingHistory_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row_history,parent,false);
        return new bookingHistory_adapter.MyViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull bookingHistory_adapter.MyViewHolder holder, int position) {
        //assigning values to each of our rows
        holder.roomName.setText(bookingHistList.get(position).getRoomName());
        holder.DateBooked.setText(bookingHistList.get(position).getDate());
        holder.Timeslot.setText(bookingHistList.get(position).getTimeSlot());
        if (bookingHistList.get(position).isCanceled)
        {
            holder.Status.setText("Cancelled");
        }
        else if (bookingHistList.get(position).isCheckedIn)
        {
            holder.Status.setText("Checked In");

        }
        else
        {
            holder.Status.setText("Booked");
        }

    }

    @Override
    public int getItemCount() {
        return bookingHistList.size();
    }
    public void checkBookings(){
        for (Booking booking:bookingHistModels) {
            Log.v(title, "booking Hist" + booking.name);
            Log.v(title, "booking Hist" + myName);
            if (booking.getName() != null)
            {
                if(booking.getName().equals(myName))
                {
                    bookingHistList.add(booking);
                    Log.v(title,"booking added to history" + booking.name);
                }
            }

        }

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView roomName, DateBooked, Timeslot, Status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            roomName = itemView.findViewById(R.id.roomHist);
            DateBooked = itemView.findViewById(R.id.datehist);
            Timeslot = itemView.findViewById(R.id.timeslothist);
            Status = itemView.findViewById(R.id.status);
        }
    }
}
