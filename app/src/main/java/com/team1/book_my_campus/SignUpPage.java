package com.team1.book_my_campus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpPage extends AppCompatActivity{

    String title = "Sign Up Page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);
        Log.v(title, "Create");

        Intent myRecvIntent = getIntent();
        EditText etFirstName = findViewById(R.id.editTextText3);
        EditText etLastName = findViewById(R.id.editTextText4);
        EditText etEmail = findViewById(R.id.editTextText7);
        EditText etPassword = findViewById(R.id.editTextText8);
        Button signUpButtonToApp = findViewById(R.id.button2);
        TextView switchToLogin = findViewById(R.id.textView14);

        signUpButtonToApp.setOnClickListener(new View.OnClickListener() {
            String myFirstName;

            String myLastName;
            String myEmail;
            String myPassword;

            @Override
            public void onClick(View v) {
                Log.v(title,"Login button to app Pressed!");
                myFirstName = etFirstName.getText().toString();
                myLastName = etLastName.getText().toString();
                myEmail = etEmail.getText().toString();
                myPassword = etPassword.getText().toString();

                Intent myIntent = new Intent(SignUpPage.this,HomePage.class);
                myIntent.putExtra("first name",myFirstName);
                myIntent.putExtra("last name",myLastName);
                myIntent.putExtra("email",myEmail);
                myIntent.putExtra("password", myPassword);
                startActivity(myIntent);
                Log.v(title,"Extrcted First name " + myFirstName + "Last name "+ myLastName +
                        "Email " + myEmail + "Password "+ myPassword);
            }
        });

        // click already have account text, switch to login
        switchToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(title, "Already have acc text Pressed!");
                Intent myIntent = new Intent(SignUpPage.this, LoginPage.class);
                startActivity(myIntent);
            }
        });
    }
}

