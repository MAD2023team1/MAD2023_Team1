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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import sg.team1.book_my_campus.R;

import java.util.HashMap;
import java.util.Map;

public class SignUpPage extends AppCompatActivity{

    String title = "Sign Up Page";
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String userID;


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
                myName = String.valueOf(etName.getText());
                myEmail = String.valueOf(etEmail.getText());
                myPassword = String.valueOf(etPassword.getText());

                if(TextUtils.isEmpty(myName)){
                    Toast.makeText(SignUpPage.this,"Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(myEmail)){
                    Toast.makeText(SignUpPage.this,"Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(myPassword)){
                    Toast.makeText(SignUpPage.this,"Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(myEmail, myPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUpPage.this,"Sign Up was successful", Toast.LENGTH_SHORT).show();
                                    Log.i(title, "signInWithEmail:success");
                                    // retrieve userID of current user
                                    userID= firebaseAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference= firestore.collection("users").document(userID);
                                    //Hashmap for doc
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("Name", myName);
                                    user.put("Email", myEmail);
                                    user.put("Password", myPassword);
                                    //insert into database
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.i(title, "Success: user profile created" + userID);
                                        }
                                    });
                                    Intent myIntent = new Intent(SignUpPage.this,HomePage.class);
                                    startActivity(myIntent);
                                    finish();
                                }
                                else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(title, "createUserWithEmailAndPassword:failure", task.getException());
                                    Toast.makeText(SignUpPage.this, "Authentication failed: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Log.i(title, "createUserWithEmailAndPassword:success");
                            }
                        });

                /*firestore.collection("Users").add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    }
                });
                */

                /*Intent myIntent = new Intent(SignUpPage.this,HomePage.class);
                myIntent.putExtra("name",myName);
                myIntent.putExtra("email",myEmail);
                myIntent.putExtra("password", myPassword);
                startActivity(myIntent);
                Log.v(title,"Extrcted name " + myName +
                        "Email " + myEmail + "Password "+ myPassword);*/
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

