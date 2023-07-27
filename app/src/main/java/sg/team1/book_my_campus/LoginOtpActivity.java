package sg.team1.book_my_campus;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserInfo;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class LoginOtpActivity extends AppCompatActivity {

    String title = "Login OTP Activity";

    // Variable declarations
    String phoneNumber;
    Long timeoutSeconds = 60L;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken  resendingToken;

    EditText otpInput;
    Button sendBtn;
    ProgressBar progressBar;
    TextView resendOtpTextView;
    Button loginBtn;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_login);

        //Finding each variables
        otpInput = findViewById(R.id.otpCode);
        sendBtn = findViewById(R.id.sendOtp);
        progressBar = findViewById(R.id.loginProgressBar);
        resendOtpTextView = findViewById(R.id.resendOtp);
        loginBtn = findViewById(R.id.loginBtn2fa);


        loginBtn.setOnClickListener(v -> {
            // Once the OTP is sent, prompt the user to enter the OTP and call signIn(credential) here
            // Retrieves the OTP entered by the user
            String enteredOtp = otpInput.getText().toString();
            Log.i(title, "login pressed");

            if (TextUtils.isEmpty(enteredOtp)) {
                Toast.makeText(LoginOtpActivity.this, "Enter OTP", Toast.LENGTH_SHORT).show();
                Log.i(title, "no otp entered");
                return;
            }
            // Creates a PhoneAuthCredential object using the verification code and the entered OTP
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, enteredOtp);
            signIn(credential);
        });

        // Get the phone number passed from the previous activity
        phoneNumber = "+65 " + UserProfile.getMobile();
        //phoneNumber = "+6500000000";
        Log.i(title, "User phone number: "+ phoneNumber);

        //make login button invisible
        finish(false);
        setInProgress(false);

        // Set click listeners for the send button and resend OTP text view
        sendBtn.setOnClickListener(v -> {
            // Call the method to send OTP to the phone number
            sendOtp(phoneNumber,false);
        });

        //resend otp
        resendOtpTextView.setOnClickListener((v)->{
            sendOtp(phoneNumber,true);
        });

    }

    //Method to send OTP to the given phone number
    void sendOtp(String phoneNumber,boolean isResend){
        //start timer
        startResendTimer();
        //set the buttons
        setInProgress(true);
        //set to true for testing
        mAuth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(false);
        //Create a PhoneAuthOptions.Builder to set up the PhoneAuthOptions
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            //Different outcomes
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                // The verification process is successfully, link the credential
                                linkCredential(phoneAuthCredential);
                                finish(true);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                // The verification process failed, show a message
                                Toast.makeText(getApplicationContext(),"OTP verification failed",Toast.LENGTH_SHORT).show();
                                finish(true);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                // The OTP sent successfully, update the verificationCode and resendingToken, show message
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendingToken = forceResendingToken;
                                Toast.makeText(getApplicationContext(),"OTP sent successfully",Toast.LENGTH_SHORT).show();
                                finish(true);
                            }
                        });

        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }else{
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }

    }

    public void linkCredential(PhoneAuthCredential credential) {
        //Unlink the provider
        mAuth.getCurrentUser().unlink(PhoneAuthProvider.PROVIDER_ID).addOnCompleteListener(this, task->{
            //Link account with provider
            mAuth.getCurrentUser().linkWithCredential(credential).addOnCompleteListener(this, task2 -> {
                //If successful, get the user and bring to home page
                if (task2.isSuccessful()) {
                    Log.d(TAG, "linkWithCredential:success");
                    FirebaseUser user = task2.getResult().getUser();
                    Toast.makeText(getApplicationContext(), "OTP Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginOtpActivity.this, HomePage.class);
                    intent.putExtra("phone", phoneNumber);
                    startActivity(intent);
                    //If fail, message "otp failure" occurs, wait to resend and login
                } else {
                    Log.w(TAG, "linkWithCredential:failure", task2.getException());
                    Toast.makeText(getApplicationContext(), "OTP Failure", Toast.LENGTH_SHORT).show();
                    finish(true);
                }
            });
        });
//        }
    }

    //Progress bar  and send otp button visibility
    void setInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            sendBtn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            sendBtn.setVisibility(View.VISIBLE);
        }
    }

    //Similar to setinprogess but including login button
    void finish(boolean finish){
        if(finish){
            loginBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            sendBtn.setVisibility(View.GONE);

        }else{
            loginBtn.setVisibility(View.GONE);
        }
    }


    //Method to sign in with the received OTP
    void signIn(PhoneAuthCredential phoneAuthCredential){
        //Login and go to next activity
        setInProgress(true);
        //Calls to link the accounts
        linkCredential(phoneAuthCredential);
        setInProgress(false);
    }

    //Resend OTP, set timer
    void startResendTimer(){
        //Disable the resendOtpTextView to prevent multiple clicks while the timer is running
        resendOtpTextView.setEnabled(false);
        // Create a timer
        Timer timer = new Timer();

        //Ensures timer run on fixed rate
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeoutSeconds--;
                //Shows the remaining time
                resendOtpTextView.setText("Resend OTP in "+timeoutSeconds +" seconds");
                //Check timer if has reached 0 seconds
                if(timeoutSeconds<=0){
                    //set 60 seconds
                    timeoutSeconds =60L;
                    //cancel timer
                    timer.cancel();
                    runOnUiThread(() -> {
                        //Switch back to the resendOtptextView
                        resendOtpTextView.setEnabled(true);
                    });
                }
            }
        },0,1000); //No delay and repeat every 1000 millisecond / 1 second
    }
}