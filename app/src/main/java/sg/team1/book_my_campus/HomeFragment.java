package sg.team1.book_my_campus;

import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    String title = "HomeFragment";
    ArrayList<Ratings> ratingsRoomArrayList = new ArrayList<>();
    ArrayList<Ratings> topRatedRoomList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View inflatedHomeView;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        this.inflatedHomeView = inflater.inflate(R.layout.fragment_home, container, false);
        TextView welcomeUser = inflatedHomeView.findViewById(R.id.textView4);
        ImageView homeProfile = inflatedHomeView.findViewById(R.id.imageView5);
        welcomeUser.setText(UserProfile.getName()+"!");
        homeProfile.setImageURI(UserProfile.getProfilePic());
        if (UserProfile.getProfilePic() != null) {
            // If the user has a profile picture, load it using Glide
            Glide.with(getContext())
                    .load(UserProfile.getProfilePic())
                    .into(homeProfile);
        } else {
            // If the user does not have a profile picture, set the default image resource
            homeProfile.setImageResource(R.drawable.baseline_person_24);
        }
        // Inflate the layout for this fragment
        return inflatedHomeView;

    }
    public void readRatingsDocument() {
        Task<QuerySnapshot> future = FirebaseFirestore.getInstance()
                .collection("ratings")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.v(title, "Getting ratings data");
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            Ratings rating = snapshot.toObject(Ratings.class);
                            Log.v(title, "onSuccess: " + snapshot.getData().toString());
                            ratingsRoomArrayList.add(rating);
                            Log.v(title, "onSuccess: " + rating.roomName);
                            Log.v(title, "onSuccess" + rating.starRatings);
                            Log.v(title, "onSuccess"+ rating.dateBooked);
                            Log.v(title,"List size"+ratingsRoomArrayList.size());

                        }

                        for(Ratings room: ratingsRoomArrayList){
                            //Find unique roomnames
                            //Match the roomnames with each room.getName
                            //add dictionary and divide by the number of roomsn, update dictionary accordingly.
                            //limit the array to only 5 rooms and sort them from largest to smallest
                            //then add them to an adapter? to show top 5 rating rooms
                        }
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v(title, "onFailure: ", e);
                    }
                });
    }
}