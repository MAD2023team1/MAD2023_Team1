package sg.team1.book_my_campus;

import sg.team1.book_my_campus.databinding.HomePageBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


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

        //receive user info
        getIntent().getStringExtra("name");
        getIntent().getStringExtra("userId");
        getIntent().getStringExtra("email");
        getIntent().getStringExtra("password");
        Log.i(title, "User info: "+ getIntent().getStringExtra("name")+ getIntent().getStringExtra("userId")+
                getIntent().getStringExtra("email") + getIntent().getStringExtra("password"));


    }
    //replace fragment method
    void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }

}
