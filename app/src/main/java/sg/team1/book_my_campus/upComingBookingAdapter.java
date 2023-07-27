package sg.team1.book_my_campus;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.CalendarContract;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        holder.checkInCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInOrCancelAlertBox(holder);
            }
        });
        holder.calendarInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarIntegration(holder);

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
    private void calendarIntegration(upComingBookingAdapter.MyViewHolder holder)
    {
        String date = holder.tvdateBooked.getText().toString();
        String startHour = holder.tvtimeslot.getText().toString().substring(0, 2);
        Log.v(title, "Start time:" + startHour);
        String endHour = holder.tvtimeslot.getText().toString().substring(6, 8);
        Log.v(title, "End time:" + endHour);
        String startDate = date  + ";" + startHour;
        String endDate = date + ";" + endHour;
        Log.v(title, "Start Date: " + startDate);
        Log.v(title, "End Date: " + endDate);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy;HH");
        Date startDateTime = null;
        Date endDateTime = null;
        try {
            startDateTime = simpleDateFormat.parse(startDate);
            endDateTime = simpleDateFormat.parse(endDate);


        } catch (ParseException e)
        {
            throw new RuntimeException(e);
        }

        Calendar startingDateTime =  Calendar.getInstance();
        startingDateTime.setTime(startDateTime);
        Calendar endingDateTime =  Calendar.getInstance();
        endingDateTime.setTime(endDateTime);
        long sdt = startingDateTime.getTimeInMillis();
        long edt = endingDateTime.getTimeInMillis();


        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE,"Booking");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,holder.tvroomName.getText().toString());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, holder.tvroomName.getText().toString());
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, sdt);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, edt);
        try {
            context.startActivity(intent);
        }
        catch (ActivityNotFoundException e)
        {
            Toast.makeText(context,"There is no app that can support this action",Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
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
                                holder.checkInCancel.setClickable(false);

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

                                Toast.makeText(context,"Cancelled Successfully",Toast.LENGTH_SHORT).show();upcomingList.get(holder.getAdapterPosition()).setCanceled(true);
                                holder.itemView.setClickable(false);

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
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String currentDate  =  new SimpleDateFormat("dd/M/yyyy", Locale.getDefault()).format(new Date());
        for (Booking booking:bookingList)
        {
            Log.v(title,"My name:" +myName);
            Log.v(title,"booking name:" +booking.getName());
            Log.v(title,"Timeslot:" +booking.getTimeSlot());
            int startHour = Integer.parseInt(booking.getTimeSlot().substring(0, 2));
            if (booking.getName() != null)
            {
                if(booking.getName().equals(myName))
                {
                    if (!booking.isCheckedIn())
                    {
                        if (!booking.isCanceled())
                        {

                            if ( (startHour<currentHour || startHour==currentHour) && booking.getDate().equals(currentDate)){
                                upcomingList.add(booking);
                                Log.v(title,"booking added to upcoming" + booking.name);
                            }
                            else{
                                booking.setCanceled(true);
                                Log.v(title,"booking cancelled" + booking.isCanceled());
                            }
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy");
                            Date bookingDate = null;
                            Date date = null;
                            try {
                                 bookingDate = simpleDateFormat.parse(booking.getDate());
                                 date = simpleDateFormat.parse(currentDate);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            if (bookingDate!=null & date!= null)
                            {
                                if (bookingDate.compareTo(date) > 0)
                                {
                                    booking.setCanceled(true);
                                    Log.v(title,"booking expired: " + booking.getDate());

                                }
                                else {
                                    upcomingList.add(booking);
                                }

                            }

                        }

                    }


                }
            }

        }

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvroomName, tvtimeslot;
        TextView tvdateBooked,tvstatus;
        Button checkInCancel, direction, calendarInt;





        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tvroomName = itemView.findViewById(R.id.roomHist);
            tvtimeslot = itemView.findViewById(R.id.timeslothist);
            tvdateBooked = itemView.findViewById(R.id.datehist);
            tvstatus = itemView.findViewById(R.id.statustxt);
            checkInCancel = itemView.findViewById(R.id.checkincancel);
            direction = itemView.findViewById(R.id.direction);
            calendarInt = itemView.findViewById(R.id.calendarint);



        }
    }
}
