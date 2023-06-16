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
        EditText etName = findViewById(R.id.editTextText4);
        EditText etEmail = findViewById(R.id.editTextText);
        EditText etPassword = findViewById(R.id.editTextText2);
        Button signUpButtonToApp = findViewById(R.id.signupBtn);
        TextView switchToLogin = findViewById(R.id.textView19);

        signUpButtonToApp.setOnClickListener(new View.OnClickListener() {
            String myName;
            String myEmail;
            String myPassword;

            @Override
            public void onClick(View v) {
                Log.v(title,"Login button to app Pressed!");
                myName = etName.getText().toString();
                myEmail = etEmail.getText().toString();
                myPassword = etPassword.getText().toString();

                Intent myIntent = new Intent(SignUpPage.this,HomePage.class);
                myIntent.putExtra("name",myName);
                myIntent.putExtra("email",myEmail);
                myIntent.putExtra("password", myPassword);
                startActivity(myIntent);
                Log.v(title,"Extrcted name " + myName +
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

