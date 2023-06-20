package sg.team1.book_my_campus;

import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MoreRoomInfo extends AppCompatActivity {
    Button bookNowbt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_room_info2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        //Extract the variables and information passed from the explore fragment
        String roomName = getIntent().getStringExtra("roomName");
        int roomImage = getIntent().getIntExtra("roomImage",0);

        //grab the textViews display in the MoreRoomInfo.xml file
        TextView rmTV = findViewById(R.id.textView10);
        ImageView rmImg = findViewById(R.id.imageView2);

        //Assign the values from what we have extracted from the ExploreFragment to the MoreRoomInfo.xml file
        rmTV.setText(roomName);
        rmImg.setImageResource(roomImage);
        //set the title of the action bar based on each room name
        actionBar.setTitle(roomName);

        //when user click on book now button, it will redirect the user to book now page
        // Initialize the button and set the click listener

        bookNowbt =  (Button)findViewById(R.id.button3);
        bookNowbt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent bookNowGo = new Intent(MoreRoomInfo.this, bookNowPage.class);
                startActivity(bookNowGo);
            }
        });
    }
    //to handle the back button on the tool bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Title bar back press triggers onBackPressed()
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}