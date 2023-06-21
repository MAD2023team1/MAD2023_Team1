package sg.team1.book_my_campus;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class bookNowPage extends AppCompatActivity {
    ArrayList<TimeSlot> timeSlots = new ArrayList<>();
    ArrayList<Booking> bookingList = new ArrayList<>();
    String title = "book now page";
    CalendarView calendarView;
    TextView date;
    String name,password,email,roomName,roomLocation;
    int roomLevel,roomCapacity;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now_page);
        createTimeSlots();
        selectDate();
        readDocument();



        MyTimeSlotAdapter myTimeSlotAdapter = new MyTimeSlotAdapter(timeSlots, new MyTimeSlotAdapter.ItemClickListener() {
            @Override
            public void onItemClick(TimeSlot timeslot) {
                openAlertBox(timeslot);


            }
        });
        RecyclerView recyclerView = findViewById(R.id.RecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myTimeSlotAdapter);

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setMinDate((new Date().getTime()));

        date =findViewById(R.id.date);
        date.setText(getCurrentDate());

        name = getIntent().getStringExtra("name");
        password = getIntent().getStringExtra("password");
        email = getIntent().getStringExtra("email");
        roomName = getIntent().getStringExtra("roomName");
        roomLocation = getIntent().getStringExtra("roomLocation");
        roomLevel = getIntent().getIntExtra("roomLevel",0);
        roomCapacity = getIntent().getIntExtra("roomCapacity",0);


    }
    private String getCurrentDate()
    {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    }

    private void selectDate()
    {
        calendarView = findViewById(R.id.calendarView);
        date =findViewById(R.id.date);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange( CalendarView view, int year, int month, int dayOfMonth) {
                String selected_Date= dayOfMonth+"/"+(month+1) + "/"+year;
                date.setText(selected_Date);


            }
        });

    }


    private void createTimeSlots() {
        for (int i = 0; i < 9; i++) {
            TimeSlot timeSlot = new TimeSlot(true,i);
            timeSlots.add(timeSlot);
        }

    }

    private void openAlertBox(TimeSlot timeSlot) {


        AlertDialog.Builder builder = new AlertDialog.Builder(bookNowPage.this);
        builder.setTitle("Booking Confirmation");

        builder.setMessage("Date: "+date.getText().toString() +"\nTimeslot: "+ Variables.convertTimeSlot(timeSlot.getSlot()));
        builder.setPositiveButton("Confirm Booking", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                bookRoom(timeSlot);
                Toast.makeText(bookNowPage.this,"Booking made", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });



        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void bookRoom(TimeSlot timeSlot){
        Room room = new Room();
        room.setRoomName(roomName);
        room.setLocation(roomLocation);
        room.setLevel(roomLevel);
        room.setCapacity(roomCapacity);
        timeSlot.setAvail(false);
        Booking booking = new Booking(name,roomName,date.getText().toString(),timeSlot.getSlot(),false,false);
        bookingToDB(booking);
    }
    private void bookingToDB(Booking booking)
    {
        FirebaseFirestore db =  FirebaseFirestore.getInstance();
        Map<String, Object> bookings = new HashMap<>();
        //bookings.put("booking",booking);
        bookings.put("name", booking.name);
        bookings.put("roomName", booking.roomName);
        bookings.put("date",booking.date);
        bookings.put("timeSlot", booking.timeSlot);
        bookings.put("isCheckedIn",booking.isCheckedIn());
        bookings.put("isCanceled",booking.isCanceled());
        db.collection("bookings").add(bookings).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful())
                {
                    Log.v(title,"booking added to db");
                }
            }
        });
    }
    public void readDocument(){
        FirebaseFirestore.getInstance()
                .collection("bookings")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.v(title,"Getting booking data");
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot:snapshotList)
                        {
                            Booking booking = snapshot.toObject(Booking.class);
                            Log.v(title,"onSuccess: "+ snapshot.getData().toString());
                            bookingList.add(booking);
                            Log.v(title,"onSuccess: "+ booking.name);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v(title,"onFailure: ",e);
                    }
                });
    }




}