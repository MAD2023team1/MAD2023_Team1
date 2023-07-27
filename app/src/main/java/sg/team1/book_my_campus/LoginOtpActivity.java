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
        //phoneNumber = "+65 " + UserProfile.getMobile();
        phoneNumber = "+65 " + UserProfile.getMobile();
        Log.i(title, "User phone number: "+ phoneNumber);

        //make login button invisible
        finish(false);
        setInProgress(false);

        // Set click listeners for the send button and resend OTP text view
        sendBtn.setOnClickListener(v -> {
            // Call the method to send OTP to the phone number
            sendOtp(phoneNumber,false);
        });

        resendOtpTextView.setOnClickListener((v)->{
            sendOtp(phoneNumber,true);
        });

    }

    // Method to send OTP to the given phone number
    void sendOtp(String phoneNumber,boolean isResend){
        startResendTimer();
        setInProgress(true);
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                signIn(phoneAuthCredential);
                                linkCredential(phoneAuthCredential);
                                setInProgress(true);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(getApplicationContext(),"OTP verification failed",Toast.LENGTH_SHORT).show();
                                setInProgress(true);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
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
//        if (!providerExists()) {
        mAuth.getCurrentUser().unlink(PhoneAuthProvider.PROVIDER_ID).addOnCompleteListener(this, task->{
            mAuth.getCurrentUser().linkWithCredential(credential).addOnCompleteListener(this, task2 -> {
                if (task2.isSuccessful()) {
                    Log.d(TAG, "linkWithCredential:success");
                    FirebaseUser user = task.getResult().getUser();
                    Toast.makeText(getApplicationContext(), "OTP Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginOtpActivity.this, HomePage.class);
                    intent.putExtra("phone", phoneNumber);
                    startActivity(intent);

                } else {
                    Log.w(TAG, "linkWithCredential:failure", task2.getException());
                    Toast.makeText(getApplicationContext(), "OTP Failure", Toast.LENGTH_SHORT).show();
                }
            });
        });
//        }
    }

    private boolean providerExists() {
        List<String> providerIdList = new ArrayList();
        for(UserInfo userInfo:mAuth.getCurrentUser().getProviderData()){
            providerIdList.add(userInfo.getProviderId());
        }
        return providerIdList.contains(PhoneAuthProvider.PROVIDER_ID);
    }

    //Progress bar visibility
    void setInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            sendBtn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            sendBtn.setVisibility(View.VISIBLE);
        }
    }

    void finish(boolean finish){
        if(finish){
            loginBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            sendBtn.setVisibility(View.GONE);

        }else{
            loginBtn.setVisibility(View.GONE);
        }
    }


    // Method to sign in with the received OTP
    void signIn(PhoneAuthCredential phoneAuthCredential){
        //login and go to next activity
        setInProgress(true);
        linkCredential(phoneAuthCredential);
        setInProgress(false);
//        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(task -> {
//            setInProgress(false);
//            if(task.isSuccessful()){
//                linkCredential(phoneAuthCredential);
////                    Intent intent = new Intent(LoginOtpActivity.this,HomePage.class);
////                    intent.putExtra("phone",phoneNumber);
////                    startActivity(intent);
//            }else{
//                Toast.makeText(getApplicationContext(),"OTP verification failed",Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    //Resend OTP, set timer
    void startResendTimer(){
        resendOtpTextView.setEnabled(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeoutSeconds--;
                resendOtpTextView.setText("Resend OTP in "+timeoutSeconds +" seconds");
                if(timeoutSeconds<=0){
                    timeoutSeconds =60L;
                    timer.cancel();
                    runOnUiThread(() -> {
                        resendOtpTextView.setEnabled(true);
                    });
                }
            }
        },0,1000);
    }


}