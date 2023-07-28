package sg.team1.book_my_campus;


import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link favouritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class favouritesFragment extends Fragment implements RecyclerViewInterface{
    ArrayList<Favourites> roomFavourites = new ArrayList<>();
    ArrayList<Room> roomList = new ArrayList<>();
    String title = "Favourites Fragment";
    favourites_adapter adapter;
    String userName;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public favouritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment favouritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static favouritesFragment newInstance(ArrayList<Room> favoriteRoomsList) {
        favouritesFragment fragment = new favouritesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("favRoomList", favoriteRoomsList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setUpRoomModels();
        /*if (getArguments() != null) {
            roomFavourites = getArguments().getParcelableArrayList("favRoomList");
        }
        else{
            Log.d("Favourite Fragment", "Room List is empty");
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);
        //get name
        userName = UserProfile.getName();
        //read favouriterooms db
        readFavourites();
        // Retrieve room information and liked status from the intent
        RecyclerView recyclerView = rootView.findViewById(R.id.favRecyclerView);
        //Create adapter after setting up models
        adapter = new favourites_adapter(getContext(),roomFavourites,this,roomList,userName);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter.checkFavourites();
        adapter.notifyDataSetChanged();
        return rootView;

    }
    public void onItemClick(int position) {
        // Handle the item click event here
        // You can implement the desired behavior when an item is clicked
    }

    @Override
    public void onItemClicked(Booking booking) {

    }
    private void readFavourites()
    {
        Task<QuerySnapshot> db = FirebaseFirestore.getInstance()
                .collection("favouriterooms")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.v(title, "Getting favourite rooms data");
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            Favourites favourites = snapshot.toObject(Favourites.class);
                            favourites.setDocid(snapshot.getId());
                            Log.v(title, "favourite: " + snapshot.getData().toString());
                            roomFavourites.add(favourites);
                            Log.v(title,"favourite:"+roomFavourites.size());

                        }
                        adapter.checkFavourites();
                        adapter.notifyDataSetChanged();
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v(title, "onFailure: ", e);
                    }
                });
    }
    private void setUpRoomModels() {
        //pulling the text inside the string[] that I have created in the string.xml file
        int[] roomImages = {R.drawable.ispace, R.drawable.smartcube1and2, R.drawable.smartcube1and2, R.drawable.smartcube3and4, R.drawable.smartcube3and4};
        int[] roomID = getResources().getIntArray(R.array.room_ID);
        String[] roomNamesFromString = getResources().getStringArray(R.array.room_name_full_text);
        String[] roomLocation = getResources().getStringArray(R.array.room_location);
        int[] roomLevel = getResources().getIntArray(R.array.room_level);
        int[] roomCapacity = getResources().getIntArray(R.array.room_capacity);


        for (int i = 0; i < roomNamesFromString.length; i++) {
            Log.v("Explore Fragment loop", Integer.toString(roomID[i]));
            roomList.add(new Room(roomID[i], roomNamesFromString[i], null, roomLocation[i], roomLevel[i], null, roomCapacity[i], null, roomImages[i]));
        }
    }


}