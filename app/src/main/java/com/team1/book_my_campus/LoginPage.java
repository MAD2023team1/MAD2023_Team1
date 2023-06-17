package com.team1.book_my_campus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {

    String title = "Login Page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        Log.v(title, "Create");

        Intent myRecvIntent = getIntent();
        EditText etEmail = findViewById(R.id.editTextText);
        EditText etPassword = findViewById(R.id.editTextText2);
        Button loginButtonToApp = findViewById(R.id.loginBtn);
        TextView switchToSignUp = findViewById(R.id.textView19);

        // Get email and password,click login button
        loginButtonToApp.setOnClickListener(new View.OnClickListener() {
            String myEmail;
            String myPassword;

            @Override
            public void onClick(View v) {
                Log.v(title, "Login button to app Pressed!");
                myEmail = etEmail.getText().toString();
                myPassword = etPassword.getText().toString();

                Intent myIntent = new Intent(LoginPage.this, HomePage.class);
                myIntent.putExtra("email", myEmail);
                myIntent.putExtra("password", myPassword);
                startActivity(myIntent);
                Log.v(title, "Extracted Email " + myEmail + "Password " + myPassword);
            }
        });

        // click do not have account text, switch to signup
        switchToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(title, "Do not have acc text Pressed!");
                Intent myIntent = new Intent(LoginPage.this, SignUpPage.class);
                startActivity(myIntent);
            }
        });
    }
}

