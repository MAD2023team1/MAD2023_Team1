package sg.team1.book_my_campus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
    ArrayList<Booking> bookingHistModels = new ArrayList<>();

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
        View rootView = inflater.inflate(R.layout.fragment_booking_history, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewHist);
        //obtain the items
        //setUpBookingHistModel();
        bookingHistory_adapter adapter = new bookingHistory_adapter(getContext(), bookingHistModels, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return rootView;
    }
    //private void setUpBookingHistModel() {
        //Bundle bookingHistBundle = this.getArguments();
        //Log.v("bookdate", "this is checking before checking if bundle is null");
        //if (bookingHistBundle != null) {
            //Booking book = bookingHistBundle.getParcelable("history");
            //Obtain booking date
            //String date = book.getDate();
            //Log.v("bookdate", date);

        //}else{
            //Log.v("BookDate", "Book Object is null");
        //}

    //}

    @Override
    public void onItemClick(int position) {

    }
}