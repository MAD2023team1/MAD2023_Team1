package sg.team1.book_my_campus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import sg.team1.book_my_campus.R;

public class LoginPage extends AppCompatActivity {

    String title = "Login Page";
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

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

            public void onClick(View v) {
                Log.v(title,"Sign up button to app Pressed!");
                myEmail = String.valueOf(etEmail.getText());
                myPassword = String.valueOf(etPassword.getText());

                if(TextUtils.isEmpty(myEmail)){
                    Toast.makeText(LoginPage.this,"Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(myPassword)){
                    Toast.makeText(LoginPage.this,"Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(myEmail, myPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    // If sign in successful, display a message to the user.
                                    Toast.makeText(LoginPage.this,"Login was successful", Toast.LENGTH_SHORT).show();
                                    Log.i(title, "signInWithEmail:success");
                                    Intent myIntent = new Intent(LoginPage.this,HomePage.class);
                                    startActivity(myIntent);
                                    finish();
                                }
                                else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(title, "signInWithEmail:failure");
                                    Toast.makeText(LoginPage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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

