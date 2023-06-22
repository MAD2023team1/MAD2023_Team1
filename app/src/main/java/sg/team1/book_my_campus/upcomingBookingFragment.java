package sg.team1.book_my_campus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class upcomingBookingFragment extends Fragment implements RecyclerViewInterface {

    ArrayList<Booking> bookingModel = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    User user;

    public upcomingBookingFragment() {
        // Required empty public constructor
    }

    public static upcomingBookingFragment newInstance(String param1, String param2) {
        upcomingBookingFragment fragment = new upcomingBookingFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_upcoming_booking2, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewUp);

        setUpBookingModel();

        upComingBookingAdapter adapter = new upComingBookingAdapter(getContext(), bookingModel, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return rootView;
    }
    private void setUpBookingModel() {
        //pulling variables from all sorts of places
        Bundle bookingBundle = this.getArguments();
        if (bookingBundle != null) {
            Booking bookingObject = bookingBundle.getParcelable("BookingObject");
            //delete the below if don't need idk
            String userName = bookingObject.getName();
            String room = bookingObject.getRoomName(); 
            String date = bookingObject.getDate();
            bookingModel.add(bookingObject);
            Log.d("Booking Object", "Booking Object" + bookingModel.toString());
        }


    }
    public void onItemClick(int position) {
        // Handle the item click event here
        // ...
    }
}