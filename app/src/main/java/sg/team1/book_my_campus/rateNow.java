package sg.team1.book_my_campus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class rateNow extends AppCompatActivity {
    String title = "Rate Now";
    TextView tvFeedback;
    TextView Header;
    RatingBar rbStars;
    Button submitBtn;

    TextView tvFeedbackBar;

    String roomName;

    String datebooked;

    String timeslot;

    String documentUserID;

    boolean submittedFeedback = false;

    private boolean feedbackSubmissionListener = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_now);
        tvFeedback = findViewById(R.id.tvFeedback);
        rbStars = findViewById(R.id.ratingBar);
        submitBtn = findViewById(R.id.submitBtn);
        tvFeedbackBar = findViewById(R.id.tvFeedbackBar);

        //listen to the changes made on the rating bar
        rbStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating == 0) {
                    tvFeedback.setText("Very Dissatisfied");
                } else if (rating <= 1.5) {
                    tvFeedback.setText("Dissatisfied");
                } else if (rating >= 2 && rating <= 3) {
                    tvFeedback.setText("Neutral");
                } else if (rating > 3 && rating <= 4) {
                    tvFeedback.setText("Satisfied");
                } else {
                    tvFeedback.setText("Very Satisfied");
                }

            }
        });
        //click on submit button
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set submitted feedback to true
                submittedFeedback = true;
                //see the feedback that the user typed
                String feedbackText = tvFeedbackBar.getText().toString().trim();
                Log.v(title, "Feedback:" + feedbackText);
                //num stars is the highest possible a user can rate
                int numStars = rbStars.getNumStars();
                Log.v(title, "Num of Stars:" + numStars);
                //get ratings is the num of stars the user rated
                float getRatings = rbStars.getRating();
                Log.v(title, "getRatings:" + getRatings);
                //get the roomID, datebooked and timeslot from booking History
                Intent intentFromBookingHist = getIntent();
                roomName = intentFromBookingHist.getStringExtra("roomName");
                Log.v(title, "roomName:" + roomName);
                datebooked = intentFromBookingHist.getStringExtra("dateBooked");
                Log.v(title, "DateBooked:" + datebooked);
                timeslot = intentFromBookingHist.getStringExtra("Timeslot");
                Log.v(title, "Timeslot:" + timeslot);
                /*//pass data to booking history fragment
                bookingHistoryFragment bookingHistoryFragment = new bookingHistoryFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("submittedFeedback", submittedFeedback);
                bookingHistoryFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.consCan, bookingHistoryFragment).commit();*/
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // Step 2: Get the currently logged-in user
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    // Step 3: Get the UID of the currently logged-in user
                    String uid = currentUser.getUid();

                    // Step 4: Reference the document for the logged-in user using the UID
                    DocumentReference userDocumentRef = db.collection("users").document(uid);

                    // Step 5: Read the document
                    userDocumentRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                // Step 6: Get the document ID
                                String documentId = documentSnapshot.getId();
                                documentUserID = documentId;
                                Log.d("User DocumentID in rate", documentId);

                                // Here, you can access the document data using documentSnapshot.getData()
                                // For example, if you have a "username" field in the document, you can retrieve it like this:
                                // String username = documentSnapshot.getString("username");
                                // Do whatever you want with the document ID and data.
                                if (TextUtils.isEmpty(feedbackText) || feedbackText.isEmpty()) {
                                    Toast.makeText(rateNow.this, "Please type something.", Toast.LENGTH_SHORT).show();

                                } else {
                                    Log.v(title,"UserDoc in else:" + documentUserID);
                                    Ratings ratings = new  Ratings(roomName,documentUserID,feedbackText,getRatings,datebooked,timeslot);
                                    ratingsToDB(ratings);
                                    finish();
                                    Toast.makeText(rateNow.this, "Feedback posted successfully!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.d("User Document", "Document does not exist.");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle any errors that occurred while reading the document
                            Log.e("Error", "Error getting user document: " + e.getMessage());
                        }
                    });
                }




            }
        });


    }



   private void ratingsToDB(Ratings rating) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> ratings = new HashMap<>();
        ratings.put("roomName", rating.roomName);
       ratings.put("comment", rating.comment);
       ratings.put("starRating", rating.starRatings);
       ratings.put("userID", rating.userID);
       ratings.put("dateBooked", rating.dateBooked);
       ratings.put("timeSlot", rating.timeSlot);
        db.collection("ratings").add(ratings).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Log.v(title, "booking added to db");
                }
            }
        });
    }

}