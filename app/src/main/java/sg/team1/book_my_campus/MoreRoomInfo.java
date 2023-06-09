package sg.team1.book_my_campus;

import androidx.appcompat.app.ActionBar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MoreRoomInfo extends AppCompatActivity {
    Button bookNowbt;
    boolean isRoomLiked;
    ArrayList<String>roomNameFavourites = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_room_info2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        //Extract the variables and information passed from the explore fragment
        String name = getIntent().getStringExtra("name");
        String password = getIntent().getStringExtra("password");
        String email = getIntent().getStringExtra("email");
        String roomName = getIntent().getStringExtra("roomName");
        int roomImage = getIntent().getIntExtra("roomImage",0);
        String roomLocation = getIntent().getStringExtra("roomLocation");
        int roomLevel = getIntent().getIntExtra("roomLevel",0);
        int roomCapacity = getIntent().getIntExtra("roomCapacity",0);
        isRoomLiked = getIntent().getBooleanExtra("isRoomLiked",false);

        //grab the textViews display in the MoreRoomInfo.xml file
        TextView rmTV = findViewById(R.id.textView10);
        ImageView rmImg = findViewById(R.id.imageView2);
        TextView rmLocation = findViewById(R.id.textView11);
        TextView rmLevel = findViewById(R.id.textView13);
        TextView rmCapacity = findViewById(R.id.textView12);

        //Assign the values from what we have extracted from the ExploreFragment to the MoreRoomInfo.xml file
        rmTV.setText(roomName);
        rmImg.setImageResource(roomImage);
        rmLocation.setText("Location:"+ roomLocation);
        rmLevel.setText("Level "+roomLevel);
        rmCapacity.setText("Capacity:"+roomCapacity);
        //set the title of the action bar based on each room name
        actionBar.setTitle(roomName);

        //when user click on book now button, it will redirect the user to book now page
        // Initialize the button and set the click listener

        bookNowbt =  (Button)findViewById(R.id.button3);
        bookNowbt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent bookNowGo = new Intent(MoreRoomInfo.this, bookNowPage.class);
                bookNowGo.putExtra("name",name);
                bookNowGo.putExtra("password",password);
                bookNowGo.putExtra("email",email);
                bookNowGo.putExtra("roomName",roomName);
                bookNowGo.putExtra("roomLocation",roomLocation);
                bookNowGo.putExtra("roomLevel",roomLevel);
                bookNowGo.putExtra("roomCapacity",roomCapacity);
                startActivity(bookNowGo);
            }
        });
        Button likedButton = (Button)findViewById(R.id.button5);
        likedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //set the boolean to not equal the orignial state when the button is clicked.
                isRoomLiked = !isRoomLiked;
                SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("roomName", roomName);
                editor.putBoolean("isRoomLiked", isRoomLiked);
                editor.apply();
                if(isRoomLiked == true)
                {
                    likedButton.setText("Unlike");
                }
                else{
                    likedButton.setText("Like");
                }
            }
        });
    }
;
    //to handle the back button on the tool bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home)  {
            // Title bar back press triggers onBackPressed()
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDefaultToggleClick(View view, String roomName){
        isRoomLiked = !isRoomLiked; // Toggle the boolean value
        Intent passInfoToFav = new Intent(MoreRoomInfo.this, favouritesFragment.class);
        passInfoToFav.putExtra("roomName", roomName);
        passInfoToFav.putExtra("isLiked", isRoomLiked);
        Log.d("Liked & Unliked", "Liked & Unliked");
    }

    // ltr come back and do share
    //SHARE THE SMART ROOM





}