package sg.team1.book_my_campus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import sg.team1.book_my_campus.R;

public class MainActivity extends AppCompatActivity {

    String title = "Landing Page";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);;
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

}