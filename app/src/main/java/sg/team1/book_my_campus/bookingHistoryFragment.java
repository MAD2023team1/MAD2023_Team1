package sg.team1.book_my_campus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link bookingHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class bookingHistoryFragment extends Fragment implements RecyclerViewInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String title = "Booking History";
    ArrayList<Booking> bookingHistModels = new ArrayList<>();
    ArrayList<Booking> bookinghistList = new ArrayList<>();
    bookingHistory_adapter adapter;
    private boolean submittedFeedback = false;
    String myName;

    public bookingHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment bookingHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static bookingHistoryFragment newInstance(String param1, String param2) {
        bookingHistoryFragment fragment = new bookingHistoryFragment();
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

        myName = UserProfile.getName();
        readDoc();
        View rootView = inflater.inflate(R.layout.fragment_booking_history, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewHist);
        Bundle bundle = getArguments();
        if(bundle != null){
            Boolean submittedFeedback = bundle.getBoolean("submittedFeedback");
        }
        else{
            Log.v(title, "Bundle is Null");
        }
        Log.v(title, "submitted feedback from rate now:" + submittedFeedback);
        adapter = new bookingHistory_adapter(getContext(), bookingHistModels, this,bookinghistList,myName, submittedFeedback);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter.checkBookings();
        return rootView;
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
                            bookingHistModels.add(booking);
                            Log.v(title,"Success"+bookingHistModels.size());
                            Log.v(title,"Success"+snapshot.getId());
                        }
                        adapter.notifyDataSetChanged();
                        adapter.checkBookings();
                    }
                });

    }






    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemClicked(Booking booking) {

    }
}