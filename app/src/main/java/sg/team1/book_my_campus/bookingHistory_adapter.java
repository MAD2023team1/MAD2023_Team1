package sg.team1.book_my_campus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class bookingHistory_adapter extends RecyclerView.Adapter<bookingHistory_adapter.MyViewHolder> {
    Context context;
    ArrayList<Booking>bookingHistModels;
    private final RecyclerViewInterface recyclerViewInterface;
    public bookingHistory_adapter(Context context, ArrayList<Booking> bookingHistModels, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.bookingHistModels = bookingHistModels;
        this.recyclerViewInterface = recyclerViewInterface;

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
        holder.roomName.setText(bookingHistModels.get(position).getName());
        holder.DateBooked.setText(bookingHistModels.get(position).getDate());
        holder.Timeslot.setText(bookingHistModels.get(position).getTimeSlot());
    }

    @Override
    public int getItemCount() {
        return bookingHistModels.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView roomName, DateBooked, Timeslot;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            roomName = itemView.findViewById(R.id.textViewHist);
            DateBooked = itemView.findViewById(R.id.textViewHist1);
            Timeslot = itemView.findViewById(R.id.textView16);
        }
    }
}
