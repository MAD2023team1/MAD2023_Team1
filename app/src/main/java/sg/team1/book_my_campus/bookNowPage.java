package sg.team1.book_my_campus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class bookNowPage extends AppCompatActivity {
    ArrayList<TimeSlot> timeSlots = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now_page);
        createTimeSlots();

        MyTimeSlotAdapter myTimeSlotAdapter = new MyTimeSlotAdapter(timeSlots, new MyTimeSlotAdapter.ItemClickListener() {
            @Override
            public void onItemClick(TimeSlot timeslot) {
                openAlertBox(timeslot);


            }
        });
        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        MyTimeSlotAdapter adapter = new MyTimeSlotAdapter(timeSlots, new MyTimeSlotAdapter.ItemClickListener() {
            @Override
            public void onItemClick(TimeSlot timeslot) {
                openAlertBox(timeslot);

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myTimeSlotAdapter);

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setMinDate((new Date().getTime()));
    }


    private void createTimeSlots() {
        for (int i = 0; i < 9; i++) {
            TimeSlot timeSlot = new TimeSlot(i);
            timeSlots.add(timeSlot);
        }

    }

    private void openAlertBox(TimeSlot timeSlot) {
        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView=findViewById(R.id.calendarView);
        TextView tiger=findViewById(R.id.textView3);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange( CalendarView view, int year, int month, int dayOfMonth) {
                String date= year+"/"+month+"/"+dayOfMonth;
                tiger.setText(date);


            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(bookNowPage.this);
        builder.setTitle("Booking Confirmation");

        builder.setMessage("Timeslot: "+Variables.convertTimeSlot(timeSlot.getSlot()));
        builder.setPositiveButton("Confirm Booking", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
}