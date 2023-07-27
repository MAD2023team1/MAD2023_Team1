package sg.team1.book_my_campus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import android.view.WindowManager;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment implements RecyclerViewInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExploreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExploreFragment newInstance(String param1, String param2) {
        ExploreFragment fragment = new ExploreFragment();
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


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);
        searchView = rootView.findViewById(R.id.searchView);
        //searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });

        RecyclerView recyclerView = rootView.findViewById(R.id.mRecyclerView);

        // Create the new array of rooms to loop through
        setUpRoomModels();
        //Create adapter after setting up models
        room_recyclerviewadapter adapter = new room_recyclerviewadapter(getContext(), roomModels, this);

        recyclerView.setAdapter(adapter);
        //initially i put this instead of requireContext()
        //but there was an error like LinearLayoutManager cannot be applied to ExploreFragment
        //This is because fragment cannot be casted as a context.
        //get Context returns the context view only current running activity.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Adjust windowSoftInputMode for the activity
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        return rootView;






    }

    //this will hold all of our models, and we will send this list to the recycler's view adpater later on
    ArrayList<Room> roomModels = new ArrayList<>();
    //images array
    int[] roomImages = {R.drawable.ispace, R.drawable.smartcube1and2, R.drawable.smartcube1and2, R.drawable.smartcube3and4, R.drawable.smartcube3and4};
    private SearchView searchView;

    private void setUpRoomModels() {
        //pulling the text inside the string[] that I have created in the string.xml file
        int[] roomID = getResources().getIntArray(R.array.room_ID);
        String[] roomNamesFromString = getResources().getStringArray(R.array.room_name_full_text);
        String[] roomLocation = getResources().getStringArray(R.array.room_location);
        int[] roomLevel = getResources().getIntArray(R.array.room_level);
        int[] roomCapacity = getResources().getIntArray(R.array.room_capacity);


        for (int i = 0; i < roomNamesFromString.length; i++) {
            Log.v("Explore Fragment loop", Integer.toString(roomID[i]));
            roomModels.add(new Room(roomID[i], roomNamesFromString[i], null, roomLocation[i], roomLevel[i], null, roomCapacity[i], null, true, roomImages[i],false));
        }
    }

    private void filterList(String text) {
        ArrayList<Room> filteredList = new ArrayList<>();
        //for each room object in roomModels
        for (Room room : roomModels) {
            if (room.getRoomName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(room);
            }

        }
        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        } else {
            room_recyclerviewadapter adapter = new room_recyclerviewadapter(getContext(), filteredList, this);//because we implemented the recyclerView on top, we can just pass it as this
            RecyclerView recyclerView = getView().findViewById(R.id.mRecyclerView);
            recyclerView.setAdapter(adapter);
        }

    }


    @Override
    public void onItemClick(int position) {
        //I cannot put ExploreFragment.this because it is a fragment not an activity
        Intent moreInfoPageIntent = new Intent(getActivity(), MoreRoomInfo.class);
        //the below code will pass the information to our new activity page, MoreRoomInfo
        String myName = UserProfile.getName();
        String myEmail = UserProfile.getEmail();
        String myPassword = UserProfile.getPassword();
        moreInfoPageIntent.putExtra("name",myName);
        moreInfoPageIntent.putExtra("password",myPassword);
        moreInfoPageIntent.putExtra("email",myEmail);
        moreInfoPageIntent.putExtra("roomID", roomModels.get(position).getRoomID());
        Log.d("Explore Fragment","Passing Info to MoreRoomInfo class" + Integer.toString(roomModels.get(position).getRoomID()));
        moreInfoPageIntent.putExtra("roomName", roomModels.get(position).getRoomName());
        moreInfoPageIntent.putExtra("roomImage", roomModels.get(position).getImage());
        moreInfoPageIntent.putExtra("roomLocation", roomModels.get(position).getLocation());
        moreInfoPageIntent.putExtra("roomCapacity", roomModels.get(position).getCapacity());
        moreInfoPageIntent.putExtra("roomLevel",roomModels.get(position).getLevel());
        moreInfoPageIntent.putExtra("roomLiked",roomModels.get(position).isRoomLiked());
        startActivity(moreInfoPageIntent);
    }

    @Override
    public void onItemClicked(Booking booking) {

    }
}
