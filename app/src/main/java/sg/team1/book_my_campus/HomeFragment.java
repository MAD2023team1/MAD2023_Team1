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

import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    String title = "HomeFragment";
    ArrayList<Ratings> ratingsRoomArrayList = new ArrayList<>();
    ArrayList<Ratings> topRatedRoomList = new ArrayList<>();
    ArrayList<top_rated_room_model> top_rated_room_modelsList = new ArrayList<>();

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

        readRatingsDocument();

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
                        HashMap<String, Float> sumRatings = new HashMap<String, Float>();
                        HashMap<String, Integer> countRatings = new HashMap<String,Integer>();

                        for(Ratings rating:ratingsRoomArrayList){
                            String roomName = rating.getRoomName();
                            sumRatings.put(roomName,0.0f);
                            countRatings.put(roomName,0);
                        }

                        for(Ratings rating: ratingsRoomArrayList){
                            String roomName = rating.getRoomName();
                            float starRating = rating.getStarRatings();
                            Log.v(title, "Room Name:" + roomName);
                            Log.v(title, "Star Rating" +starRating);
                            if(sumRatings.containsKey(roomName)){
                                Log.v(title, "While Loop: Before Count Ratings Hash Map Mapping:" + countRatings);
                                float previousSumValue = sumRatings.get(roomName);
                                int previousCount = countRatings.get(roomName);
                                Log.v(title, "Before Addition:" + previousCount);
                                previousCount += 1;
                                previousSumValue += starRating;
                                Log.v(title, "After addition:" +previousCount);
                                sumRatings.put(roomName,previousSumValue);
                                countRatings.put(roomName,previousCount);
                                Log.v(title, "While Loop: After Count Ratings Hash Map Mapping:" + countRatings);
                            }
                        }
                        Log.v(title, "Sum Ratings Hash Map Mapping:" + sumRatings);
                        Log.v(title, "Count Ratings Hash Map Mapping:" + countRatings);
                        //calculate the average by matching they keys together and then dividing the values
                        HashMap<String, Float> averageRatings = new HashMap<>();
                        LinkedHashMap<String,Float> sortedAverage = new LinkedHashMap<>();
                        ArrayList<Float> list = new ArrayList<>();
                        for (String room : sumRatings.keySet()) {
                            if (countRatings.containsKey(room)) {
                                float sum = sumRatings.get(room);
                                int count = countRatings.get(room);
                                float average = sum / count;
                                averageRatings.put(room, average);
                            }
                        }
                        Log.v(title, "Average Ratings Hash Map Mapping:" + averageRatings);
                        //Sort the average list from largest to smallest
                        for (Map.Entry<String, Float> entry : averageRatings.entrySet()) {
                            list.add(entry.getValue());
                        }
                        Collections.sort(list);
                        for (float num : list) {
                            for (Map.Entry<String, Float> entry : averageRatings.entrySet()) {
                                if (entry.getValue().equals(num)) {
                                    sortedAverage.put(entry.getKey(), num);
                                }
                            }
                        }
                        Log.v(title, "Average Ratings Hash Map MSorted:" + sortedAverage);
                        //convert
                        List<Map.Entry<String,Float>> entryAvg = new ArrayList<>(sortedAverage.entrySet());
                        Log.v(title, "This is the list of sorted:" + entryAvg.toString());
                        Log.v(title, "This is the first index:" + entryAvg.get(0).getValue());
                        //Extract the top 3 values(the last entry is the highest value)
                        int lastIndex = sortedAverage.size()-1;



                        //obtaining room Names from the room dataset stored in strings.xml
                        String[] roomNamesFromString = getResources().getStringArray(R.array.room_name_full_text);
                        int[] roomImages = {R.drawable.ispace, R.drawable.smartcube1and2, R.drawable.smartcube1and2, R.drawable.smartcube3and4, R.drawable.smartcube3and4, R.drawable.swimmingpool, R.drawable.gymwerkz,R.drawable.musicroomcropped};
                        // loop through this if both name matches with the top 3, create a top3 room model.
                        // Loop through the entries to find the top 3 rated rooms
                        RecyclerView recyclerView = inflatedHomeView.findViewById(R.id.topRoomRecyclerView);
                        for (int i = lastIndex; i > lastIndex - 3 && i >= 0; i--) {
                            String topRatedRoom = entryAvg.get(i).getKey();
                            if (Arrays.asList(roomNamesFromString).contains(topRatedRoom)) {
                                float topRatedRoomRatings = entryAvg.get(i).getValue();
                                int indexTopRatedRoom = Arrays.asList(roomNamesFromString).indexOf(topRatedRoom);
                                top_rated_room_model roomRating = new top_rated_room_model(topRatedRoomRatings, roomImages[indexTopRatedRoom], topRatedRoom);
                                top_rated_room_modelsList.add(roomRating);
                                Log.v(title, "Name in our database:" + topRatedRoom);
                            }
                        }

// Set up the adapter after the loop
                        top_room_adapter adapter = new top_room_adapter(getContext(), top_rated_room_modelsList);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));





                    }



                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v(title, "onFailure: ", e);
                    }
                });
    }

    private void setUpRateRoomModel(){

    }
}