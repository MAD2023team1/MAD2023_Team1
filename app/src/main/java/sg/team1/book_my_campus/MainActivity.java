package sg.team1.book_my_campus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.common.value.qual.BoolVal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import sg.team1.book_my_campus.R;

public class MainActivity extends AppCompatActivity {

    String title = "Landing Page";
    ArrayList<Booking> bookingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        readDoc();


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(title, "Starting App");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.v(title, "On Pause!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(title, "On Resume!");

        // find login button on create page
        Button loginButton = findViewById(R.id.landingLogin);

        // if clicked, go to login page
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(title, "Login button is pressed");
                Intent myIntent = new Intent(MainActivity.this,LoginPage.class);
                startActivity(myIntent);

            }
        });

        // find sign up button on create page
        Button signUpButton = findViewById(R.id.landingSignup);

        // if clicked, go to sign up page
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(title, "Sign Up button is pressed");
                Intent myIntent = new Intent(MainActivity.this,SignUpPage.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(title, "On Stop!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(title, "On Destroy!");
    }
    public void purgingDb()
    {
        FirebaseFirestore db =FirebaseFirestore.getInstance();

        String currentDate  =  new SimpleDateFormat("dd/M/yyyy", Locale.getDefault()).format(new Date());
        Log.v(title,"Current Date:"+currentDate);
        for (Booking booking:bookingList)
        {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy");
            Date bookingDate = null;
            Date date = null;
            try {
                bookingDate = simpleDateFormat.parse(booking.getDate());
                date = simpleDateFormat.parse(currentDate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Log.v(title,"Booking date:"+bookingDate);
            Log.v(title,"Current date:"+date);
            long bDate = bookingDate.getTime();
            long d = date.getTime();

            long timeDiff = Math.abs(d - bDate);

            long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
            Log.v(title,"days Diff:"+ daysDiff);
            if (daysDiff >= 7)
            {
                db.collection("bookings")
                        .document(booking.docid)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.i(title,"Purged:" +booking );
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i(title, "Purging Failure");
                            }
                        });
            }

        }

    }
    public void readDoc(){
        Task<QuerySnapshot> future = FirebaseFirestore.getInstance()
                .collection("bookings")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.v(title,"retrieving data");
                        List<DocumentSnapshot> docsnapList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot:docsnapList
                        ) { Booking booking=snapshot.toObject(Booking.class);
                            Log.v(title,"Success "+snapshot.getData().toString());
                            Log.v(title,"Success "+booking.name);
                            booking.docid = snapshot.getId();
                            bookingList.add(booking);
                            Log.v(title,"Success "+bookingList.size());
                            Log.v(title,"Success "+snapshot.getId());

                        }
                        purgingDb();
                    }
                });

    }



}