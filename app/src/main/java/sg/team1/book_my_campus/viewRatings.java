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

import java.util.ArrayList;
import java.util.List;

public class viewRatings extends AppCompatActivity {
    String title = "View Ratings";
    ArrayList<Ratings> ratingList = new ArrayList<>();
    ArrayList<Ratings> roomWithRatingList = new ArrayList<>();

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
                            TextView displayRatings = findViewById(R.id.value);
                            displayRatings.setText(String.valueOf(totalEachRoom));
                            RatingBar ratingBar = findViewById(R.id.ratingBar3);
                            ratingBar.setRating(totalEachRoom);
                            RecyclerView recyclerView = findViewById(R.id.commentRecycler);
                            Log.v("AdapterDebug", "RatingList size: " + roomWithRatingList.size());
                            comments_adapter adapter = new comments_adapter(viewRatings.this, roomWithRatingList);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(viewRatings.this));

                        }
                        else{
                            TextView displayRatings = findViewById(R.id.value);
                            displayRatings.setText(String.valueOf("There are no ratings for this room so far."));
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
}