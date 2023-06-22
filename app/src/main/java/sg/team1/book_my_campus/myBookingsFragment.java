package sg.team1.book_my_campus;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import sg.team1.book_my_campus.databinding.FragmentMyBookingsBinding;
import sg.team1.book_my_campus.databinding.HomePageBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link myBookingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class myBookingsFragment extends Fragment {

    private FragmentMyBookingsBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public myBookingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment myBookingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static myBookingsFragment newInstance(String param1, String param2) {
        myBookingsFragment fragment = new myBookingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyBookingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        //first page when we open up my bookings
        replaceFragment(new upcomingBookingFragment());
        binding.upperNavigationView2.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.upcomingbookings) {
                replaceFragment(new upcomingBookingFragment());
                Log.v("Upcoming Bookings!", "Upcoming Bookings!");
            } else if (itemId == R.id.bookinghistory) {
                replaceFragment(new bookingHistoryFragment());
                Log.v("Bookings History!", "Bookings History!");
            } else if (itemId == R.id.favourites) {
                replaceFragment(new favouritesFragment());
                Log.v("Favourites!", "Favourites!");
            }
            return true;
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Log.d("FragmentTransaction", "Replacing fragment");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout2, fragment);
        fragmentTransaction.commit();
        Log.d("FragmentTransaction", "Fragment replaced");
    }

}