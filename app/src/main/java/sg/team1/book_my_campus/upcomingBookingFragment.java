package sg.team1.book_my_campus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class upcomingBookingFragment extends Fragment implements RecyclerViewInterface {

    ArrayList<Booking> upcomingList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String title="upcomingFragment";
    User user;
    ArrayList<Booking>bookingList = new ArrayList<>();
    String myName;
    upComingBookingAdapter upComingBookingAdapter;

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
        myName =getActivity().getIntent().getStringExtra("name");
        readDoc();
        View rootView = inflater.inflate(R.layout.fragment_upcoming_booking2, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewUp);


        upComingBookingAdapter = new upComingBookingAdapter(getContext(), this,myName,upcomingList,bookingList);
        recyclerView.setAdapter(upComingBookingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        upComingBookingAdapter.checkUpcomingBookings();
        return rootView;
    }
    /*private void setUpBookingModel() {
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


    }*/
    public void onItemClick(int position) {
        // Handle the item click event here
        // ...
    }

    public void readDoc(){
        Task<QuerySnapshot> future = FirebaseFirestore.getInstance()
                .collection("bookings")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.v(title,"retrieving data");
                        List<DocumentSnapshot> docsnapList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot:docsnapList
                        ) { Booking booking=snapshot.toObject(Booking.class);
                            Log.v(title,"Success"+snapshot.getData().toString());
                            Log.v(title,"Success"+booking.name);
                            booking.docid = snapshot.getId();
                            bookingList.add(booking);
                            Log.v(title,"Success"+bookingList.size());
                            Log.v(title,"Success"+snapshot.getId());



                        }
                        upComingBookingAdapter.notifyDataSetChanged();
                        upComingBookingAdapter.checkUpcomingBookings();
                    }
                });

    }
    public void checkUpcomingBookings(){


        for (Booking booking:bookingList)
        {
            if(booking.getName().equals(myName)&& booking.isCheckedIn()==false&& booking.isCanceled()==false)
            {
                upcomingList.add(booking);

            }
        }

    }
}