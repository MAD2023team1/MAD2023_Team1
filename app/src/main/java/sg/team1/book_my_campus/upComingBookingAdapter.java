package sg.team1.book_my_campus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class upComingBookingAdapter extends RecyclerView.Adapter<upComingBookingAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Booking> bookingModel;

    public upComingBookingAdapter(Context context, ArrayList<Booking> bookingModels, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.bookingModel = bookingModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public upComingBookingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row_upcoming, parent, false);
        return new upComingBookingAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull upComingBookingAdapter.MyViewHolder holder, int position) {
        holder.tvroomName.setText(bookingModel.get(position).getRoomName());
    }

    @Override
    public int getItemCount() {
        int count = bookingModel.size();
        if(bookingModel != null){
            return count;
        }else{//the user can choose not to book anything
            return 0;
        }

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvroomName;
        TextView tvdateBooked;

        Button checkInBtn;

        Button cancelBtn;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tvroomName = itemView.findViewById(R.id.textViewUP);
            tvdateBooked = itemView.findViewById(R.id.textView14);
            checkInBtn = itemView.findViewById(R.id.button4);
            cancelBtn = itemView.findViewById(R.id.button6);


            checkInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });
        }
    }
}
