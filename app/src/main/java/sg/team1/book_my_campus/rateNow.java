package sg.team1.book_my_campus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class rateNow extends AppCompatActivity {
    String title = "Rate Now";
    TextView tvFeedback;
    RatingBar rbStars;
    Button submitBtn;

    TextView tvFeedbackBar;

    String datebooked;

    String timeslot;



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
                if(rating == 0){
                    tvFeedback.setText("Very Dissatisfied");
                }
                else if(rating <=1.5){
                    tvFeedback.setText("Dissatisfied");
                }
                else if(rating >=2 && rating <=3){
                    tvFeedback.setText("Neutral");
                }
                else if(rating > 3 && rating  <= 4){
                    tvFeedback.setText("Satisfied");
                }
                else{
                    tvFeedback.setText("Very Satisfied");
                }

            }
        });
        //click on submit button
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //see the feedback that the user typed
                String feedbackText = tvFeedbackBar.getText().toString();
                Log.v(title, "Feedback:" +feedbackText );
                //num stars is the highest possible a user can rate
                int numStars = rbStars.getNumStars();
                Log.v(title, "Num of Stars:" + numStars);
                //get ratings is the num of stars the user rated
                float getRatings = rbStars.getRating();
                Log.v(title, "getRatings:"+ getRatings);
                //get the roomID, datebooked and timeslot
                Intent intentFromBookingHist = getIntent();
                datebooked = intentFromBookingHist.getStringExtra("dateBooked");
                timeslot = intentFromBookingHist.getStringExtra("Timeslot");
                Log.v(title, "Timeslot:"+timeslot);

            }
        });

    }
}