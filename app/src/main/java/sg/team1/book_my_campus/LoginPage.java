package sg.team1.book_my_campus;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginPage extends AppCompatActivity {

    String title = "Login Page";
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        Log.v(title, "Create");
        Log.i(title, String.valueOf(firebaseAuth));

        // Find all text and buttons in page
        EditText etEmail = findViewById(R.id.editTextText);
        EditText etPassword = findViewById(R.id.editTextText2);
        Button loginButtonToApp = findViewById(R.id.loginBtn);
        TextView switchToSignUp = findViewById(R.id.textView19);
        TextView forgotPassword = findViewById(R.id.textView9);

        // Get email and password,click login button
        loginButtonToApp.setOnClickListener(new View.OnClickListener() {
            String myEmail;
            String myPassword;

            public void onClick(View v) {
                Log.v(title,"Log in button to app Pressed!");
                // Add toast message if either email or password is empty
                if(TextUtils.isEmpty(myEmail)){
                    Toast.makeText(LoginPage.this,"Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(myPassword)){
                    Toast.makeText(LoginPage.this,"Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                //sign in feature with email and password using firebase authentication
                firebaseAuth.signInWithEmailAndPassword(myEmail, myPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    // If sign in successful, display a message to the user.
                                    Toast.makeText(LoginPage.this, "Login was successful", Toast.LENGTH_SHORT).show();
                                    Log.i(title, "signInWithEmail:success");
                                    Intent myIntent = new Intent(LoginPage.this, HomePage.class);

                                    // Updating password in Firestore
                                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                                    CollectionReference usersCollection = firestore.collection("users");
                                    // Get userID of the user
                                    String userID = firebaseAuth.getCurrentUser().getUid();
                                    Log.w(title, "myuserid: "+userID);
                                    String myName = firebaseAuth.getCurrentUser().getDisplayName();

                                    // Get the previous password from Firestore using userID
                                    usersCollection.document(userID).get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    String previousPassword = documentSnapshot.getString("Password");

                                                    // Delete the previous password from Firestore
                                                    Map<String, Object> updates = new HashMap<>();
                                                    updates.put("Password", FieldValue.delete());

                                                    usersCollection.document(userID).update(updates)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Log.i(title, "Previous password deleted successfully in Firestore");

                                                                    // Update the password with the new value
                                                                    Map<String, Object> newUpdates = new HashMap<>();
                                                                    // Insert new password value
                                                                    newUpdates.put("Password", myPassword);

                                                                    usersCollection.document(userID).update(newUpdates)
                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {
                                                                                    Log.i(title, "Password updated successfully in Firestore");
                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    Log.w(title, "Failed to update password in Firestore", e);
                                                                                }
                                                                            });
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.w(title, "Failed to delete previous password in Firestore", e);
                                                                }
                                                            });
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(title, "Failed to retrieve previous password from Firestore", e);
                                                }
                                            });

                                    //Get user info and pass to home page
                                    usersCollection.document(userID).get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    if (documentSnapshot.exists()) {
                                                        //Getting name,email and password
                                                        String name = documentSnapshot.getString("Name");
                                                        String email = documentSnapshot.getString("Email");
                                                        String password = documentSnapshot.getString("Password");
                                                        //Add into intent
                                                        myIntent.putExtra("userId", userID);
                                                        myIntent.putExtra("name", name);
                                                        myIntent.putExtra("email", email);
                                                        myIntent.putExtra("password", password);
                                                          
                                                        //Pass the whole user class
                                                        User user = new User(name,email,password);
                                                        //Need to send to a fragment so must use bundle
                                                        Fragment upcomingBookingFragment = new upcomingBookingFragment();
                                                        Bundle bundle = new Bundle();
                                                        bundle.putParcelable("User", user);
                                                        upcomingBookingFragment.setArguments(bundle);
                                                      
                                                        // Start the home page activity
                                                        startActivity(myIntent);
                                                        finish();
                                                    } else {
                                                        // User document does not exist
                                                        Log.w(title, "User document does not exist");
                                                    }
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(title, "Failed to retrieve user info from Firestore");
                                                }
                                            });
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

        // Click do not have account text, switch to signup
        switchToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(title, "Do not have acc text Pressed!");
                Intent myIntent = new Intent(LoginPage.this, SignUpPage.class);
                startActivity(myIntent);
            }
        });

        //Click forgot password to reset password
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginPage.this);
                View dialogView = getLayoutInflater().inflate(R.layout.forget_password,null);
                EditText emailBox = dialogView.findViewById(R.id.editTextText3);

                builder.setView(dialogView);
                AlertDialog dialog= builder.create();

                //Click reset button on pop up
                dialogView.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userEmail = emailBox.getText().toString();

                        //Ensure correct email addresss is filled in and is not blank
                        if(TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                            Toast.makeText(LoginPage.this,"Enter your registered email", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // Using firebase, send password reset email to email address filled in
                        firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    // If email is correct, display a message to the user to check email
                                    Toast.makeText(LoginPage.this,"Check your email", Toast.LENGTH_SHORT).show();
                                    Log.i(title, "Reset password email sent:success");
                                    dialog.dismiss();
                                }

                                else {
                                    // If email is wrong, display a message to the user.
                                    Log.w(title, "Reset password email sent:failure");
                                    Toast.makeText(LoginPage.this, "Unable to send.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                //Click on cancel button to close pop up
                dialogView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if (dialog.getWindow() != null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });
    }
}

