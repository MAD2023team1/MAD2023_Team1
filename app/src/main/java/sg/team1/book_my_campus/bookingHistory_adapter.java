package sg.team1.book_my_campus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class bookingHistory_adapter extends RecyclerView.Adapter<bookingHistory_adapter.MyViewHolder> {
    String title = "booking hist adapter";
    Context context;
    ArrayList<Booking>bookingHistModels;
    ArrayList<Booking>bookingHistList;

    ArrayList<Ratings>ratingsList;



    String myName;

    private final RecyclerViewInterface recyclerViewInterface;
    public bookingHistory_adapter(Context context, ArrayList<Booking> bookingHistModels, RecyclerViewInterface recyclerViewInterface, ArrayList<Booking>bookingHistList,String myName, ArrayList<Ratings> ratingsList){
        this.context = context;
        this.bookingHistModels = bookingHistModels;
        this.recyclerViewInterface = recyclerViewInterface;
        this.bookingHistList = bookingHistList;
        this.myName = myName;
        this.ratingsList = ratingsList;
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
        if (bookingHistList.get(position).isCheckedIn) {
            // View type for checked-in bookings
            return 0;
        }
        else {
            // View type for cancelled and non-checked-in  bookings
            return 1;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull bookingHistory_adapter.MyViewHolder holder,int position) {
        //assigning values to each of our rows
        holder.roomName.setText(bookingHistList.get(holder.getAdapterPosition()).getRoomName());
        holder.DateBooked.setText(bookingHistList.get(holder.getAdapterPosition()).getDate());
        holder.Timeslot.setText(bookingHistList.get(holder.getAdapterPosition()).getTimeSlot());

        if (bookingHistList.get(holder.getAdapterPosition()).isCanceled)
        {
            holder.Status.setText("Cancelled");
        }
        else if (bookingHistList.get(holder.getAdapterPosition()).isCheckedIn)
        {

            holder.Status.setText("Checked In");
            if (!bookingHistList.get(holder.getAdapterPosition()).isRated())
            {
                holder.rateNowBtn.setBackgroundColor(Color.parseColor("#59738d"));
                holder.rateNowBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent rateNowPage = new Intent(context, rateNow.class);
                        //pass the information of roomID, datebook and timeslot onto the next page
                        rateNowPage.putExtra("userName", myName);
                        rateNowPage.putExtra("roomName", bookingHistList.get(holder.getAdapterPosition()).getRoomName());
                        rateNowPage.putExtra("dateBooked", bookingHistList.get(holder.getAdapterPosition()).getDate());
                        rateNowPage.putExtra("Timeslot", bookingHistList.get(holder.getAdapterPosition()).getTimeSlot());
                        // Start the rateNow activity
                        context.startActivity(rateNowPage);
                    }
                });
            }
            else
            {
                Log.v(title,"bookinghistlist:"+bookingHistList.get(holder.getAdapterPosition()).isRated + bookingHistList.get(holder.getAdapterPosition()).name
                        +bookingHistList.get(holder.getAdapterPosition()).date +bookingHistList.get(holder.getAdapterPosition()).timeSlot);
                holder.rateNowBtn.setBackgroundColor(Color.parseColor("#D3D3D3"));



            }
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
        bookingHistList.clear();
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
    public void checkRatings()
    {
        for (Booking booking:bookingHistList)
        {
            if (booking.isCheckedIn)
            {
                for (Ratings r: ratingsList)
                {
                    if (r.userName!=null)
                    {
                        if (r.userName.equals(booking.name) & r.dateBooked.equals(booking.date) & r.timeSlot.equals(booking.timeSlot) & r.roomName.equals(booking.roomName))
                        {
                            booking.setRated(true);
                            Log.v(title,"Ratings:"+r.userName+" "+ r.roomName +" "+r.timeSlot +" "+ r.dateBooked );
                            Log.v(title,"Booking:"+booking.name+" "+ booking.roomName +" "+booking.timeSlot +" "+ booking.date+" "+booking.isRated());
                        }
                    }

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
