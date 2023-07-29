package sg.team1.book_my_campus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class viewRatings extends AppCompatActivity {
    String title = "View Ratings";
    ArrayList<Ratings> ratingList = new ArrayList<>();
    ArrayList<Ratings> roomWithRatingList = new ArrayList<>();
    BigDecimal roundedRatings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ratings2);
        Log.d(title, "onCreate() called");
        Log.d(title, "HI this is created");
        //Receive intent from moreRoomInfo
        Intent receivedIntent = getIntent();
        String roomName = receivedIntent.getStringExtra("roomName");
        readRatingsDocument(roomName);
    }
    public void readRatingsDocument(String roomName) {
        Task<QuerySnapshot> future = FirebaseFirestore.getInstance()
                .collection("ratings")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.v(title, "Getting ratings data");
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            Ratings rating = snapshot.toObject(Ratings.class);
                            Log.v(title, "onSuccess: " + snapshot.getData().toString());
                            ratingList.add(rating);
                            Log.v(title, "onSuccess: " + rating.roomName);
                            Log.v(title, "onSuccess" + rating.starRatings);
                            Log.v(title, "onSuccess"+ rating.dateBooked);
                            Log.v(title,"List size"+ratingList.size());

                        }

                        float roomRatings = 0;
                        int count = 0;
                        boolean roomHasRatings = false;
                        for(Ratings rating: ratingList){
                            String eachRoom = rating.roomName;
                            String theRoom = roomName;
                            Log.v(title, "In theloop:" + eachRoom);
                            Log.v(title, "In theloop2:"+  theRoom);
                            if(eachRoom.equals(roomName)){
                                roomWithRatingList.add(rating);
                            }
                        }

                        Log.v(title, "Room Has Ratings Boolean:"+ roomHasRatings);
                        if(roomWithRatingList.size() != 0){
                            float eachRoomRatings = 0;
                            int countRoom = 0;
                            float totalEachRoom = 0;
                            //every thing inside this roomWithRatingList is of the same type of room
                            for(Ratings eachRoom :roomWithRatingList ){
                                countRoom += 1;
                                eachRoomRatings += eachRoom.starRatings;
                                totalEachRoom = eachRoomRatings/countRoom;
                                Log.v(title,"New Ratings:"+ eachRoomRatings);
                            }
                            TextView displayRatings = findViewById(R.id.Ratings);
                            //round the average ratings to two decimal places
                            roundedRatings = round(totalEachRoom,1);
                            displayRatings.setText(String.valueOf(roundedRatings));
                            RatingBar ratingBar = findViewById(R.id.ratingBar3);
                            ratingBar.setRating(totalEachRoom);
                            TextView displayNumOfPeopleWhoVoted = findViewById(R.id.textView22);
                            displayNumOfPeopleWhoVoted.setText("Based on "+ String.valueOf(countRoom)+" ratings.");
                            RecyclerView recyclerView = findViewById(R.id.commentRecycler);
                            Log.v("AdapterDebug", "RatingList size: " + roomWithRatingList.size());
                            comments_adapter adapter = new comments_adapter(viewRatings.this, roomWithRatingList);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(viewRatings.this));

                        }
                        //if there are no ratings and comments, display another layout.
                        else{
                            setContentView(R.layout.no_ratings_display);

                        }


                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v(title, "onFailure: ", e);
                    }
                });

    }
    //function to round float to the nearest dp
    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }
}