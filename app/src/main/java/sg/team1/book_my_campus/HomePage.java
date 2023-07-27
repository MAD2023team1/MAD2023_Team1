package sg.team1.book_my_campus;

import sg.team1.book_my_campus.databinding.HomePageBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import sg.team1.book_my_campus.R;

public class HomePage extends AppCompatActivity {
    private String title = "Home Page";
    private HomePageBinding binding;
    private static final int home_id = R.id.Home;
    private static final int explore_id = R.id.explore;
    private static final int mybooking_id = R.id.mybookings;

    private static final int profile_id = R.id.profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        //Log.v(title, "On Create! hello");
        binding.bottomNavigationView2.setOnItemSelectedListener(item->{
            int item_id = item.getItemId();
            if(item_id == home_id){
                replaceFragment(new HomeFragment());
            }else if (item_id == explore_id){
                replaceFragment(new ExploreFragment());
            }else if(item_id == mybooking_id){
                replaceFragment(new myBookingsFragment());
            }else{//should i add else if profile_id let's test out
                replaceFragment(new ProfileFragment());
            }
            return true;
        });
    }
    //replace fragment method
    void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



}