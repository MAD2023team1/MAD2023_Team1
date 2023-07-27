package sg.team1.book_my_campus;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        View view = null;
        if (viewType == 1) {
            view = inflater.inflate(R.layout.recycler_view_row_cancelled,parent,false);
        } else{
            //only allow those who went to check in
            view = inflater.inflate(R.layout.recycler_view_row_history,parent,false);
        }
        return new bookingHistory_adapter.MyViewHolder(view) ;
    }
    @Override
    public int getItemViewType(int position) {
        if (bookingHistList.get(position).isCanceled) {
            // View type for cancelled bookings
            return 1;
        } else {
            // View type non-cancelled bookings
            return 0;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull bookingHistory_adapter.MyViewHolder holder,int position) {
        //assigning values to each of our rows
        holder.roomName.setText(bookingHistList.get(holder.getAdapterPosition()).getRoomName());
        holder.DateBooked.setText(bookingHistList.get(holder.getAdapterPosition()).getDate());
        holder.Timeslot.setText(bookingHistList.get(holder.getAdapterPosition()).getTimeSlot());
        if (bookingHistList.get(position).isCanceled)
        {
            holder.Status.setText("Cancelled");
        }
        else if (bookingHistList.get(position).isCheckedIn)
        {
            holder.Status.setText("Checked In");
            holder.rateNowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent rateNowPage = new Intent(holder.itemView.getContext(), rateNow.class);
                    //pass the information of roomID, datebook and timeslot onto the next page
                    rateNowPage.putExtra("dateBooked", bookingHistList.get(holder.getAdapterPosition()).getDate());
                    rateNowPage.putExtra("Timeslot", bookingHistList.get(holder.getAdapterPosition()).getTimeSlot());
                    // Start the rateNow activity
                    holder.itemView.getContext().startActivity(rateNowPage);

                }
            });

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
        Button rateNowBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            roomName = itemView.findViewById(R.id.roomHist);
            DateBooked = itemView.findViewById(R.id.datehist);
            Timeslot = itemView.findViewById(R.id.timeslothist);
            Status = itemView.findViewById(R.id.status);
            rateNowBtn = itemView.findViewById(R.id.button4);
        }
    }
}
