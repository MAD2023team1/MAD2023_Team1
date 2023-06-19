package com.team1.book_my_campus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;


public class ViewProfile extends AppCompatActivity {

    String title = "View Profile page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        Log.v(title, "Created ViewProfile layout");

        Button editProfile = findViewById(R.id.editProfileButton);


    }



}