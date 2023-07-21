package sg.team1.book_my_campus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

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
        holder.tvstatus.setText("Booked");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInOrCancelAlertBox(holder);
            }
        });



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
    private void checkInOrCancelAlertBox(upComingBookingAdapter.MyViewHolder holder) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Check In / Cancel Confirmation");

        builder.setPositiveButton("Check In Booking", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("bookings").document(upcomingList.get(holder.getAdapterPosition()).docid)
                        .update("isCheckedIn",true)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                holder.tvstatus.setText("Checked In");

                                Toast.makeText(context,"Check in Successfully",Toast.LENGTH_SHORT).show();
                                upcomingList.get(holder.getAdapterPosition()).setCheckedIn(true);
                                holder.itemView.setClickable(false);
                                holder.tvinfo.setVisibility(View.GONE);


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                holder.tvstatus.setText("Booked");
                                Toast.makeText(context,"Check in Unsuccessfully",Toast.LENGTH_SHORT).show();
                                upcomingList.get(holder.getAdapterPosition()).setCheckedIn(false);

                            }
                        });





            }
        });
        builder.setNegativeButton("Cancel Booking", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("bookings").document(upcomingList.get(holder.getAdapterPosition()).docid)
                        .update("isCanceled",true)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                holder.tvstatus.setText("Cancelled");

                                Toast.makeText(context,"Cancelled Successfully",Toast.LENGTH_SHORT).show();
                                upcomingList.get(holder.getAdapterPosition()).setCanceled(true);
                                holder.itemView.setClickable(false);
                                holder.tvinfo.setVisibility(View.GONE);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                holder.tvstatus.setText("Booked");
                                Toast.makeText(context,"Cancelled Unsuccessfully",Toast.LENGTH_SHORT).show();
                                upcomingList.get(holder.getAdapterPosition()).setCanceled(false);

                            }
                        });

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void checkUpcomingBookings(){
        upcomingList.clear();
        for (Booking booking:bookingList)
        {
            if(booking.getName().equals(myName))
            {
                if (!booking.isCheckedIn())
                {
                    if (!booking.isCanceled())
                    {
                        upcomingList.add(booking);

                        Log.v(title,"booking added to upcoming" + booking.name);
                    }

                }


            }
        }

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvroomName, tvtimeslot;
        TextView tvdateBooked,tvstatus, tvinfo;





        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tvroomName = itemView.findViewById(R.id.roomHist);
            tvtimeslot = itemView.findViewById(R.id.timeslothist);
            tvdateBooked = itemView.findViewById(R.id.datehist);
            tvstatus = itemView.findViewById(R.id.statustxt);
            tvinfo = itemView.findViewById(R.id.textView21);


        }
    }
}
