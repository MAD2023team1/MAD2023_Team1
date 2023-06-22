package sg.team1.book_my_campus;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatToggleButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link favouritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class favouritesFragment extends Fragment implements RecyclerViewInterface{
    ArrayList<String> roomNameFavourites = new ArrayList<>();

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
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment favouritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static favouritesFragment newInstance(String param1, String param2) {
        favouritesFragment fragment = new favouritesFragment();
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


        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);
        // Retrieve room information and liked status from the intent
        // Retrieve room information and liked status from shared preferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String roomName = preferences.getString("roomName", "");
        boolean isLiked = preferences.getBoolean("isRoomLiked", true);
        Log.d("FavouritesFragment", "roomName: " + roomName.toString());
        Log.d("FavouritesFragment", "isLiked: " + String.valueOf(isLiked));
        // Update the liked rooms based on the status
        if (isLiked == true) {
            roomNameFavourites.add(roomName);
            Log.d("FavouritesFragment", "Room Favourites: " + roomNameFavourites.toString());

        } else {
            roomNameFavourites.remove(roomName);
            Log.d("FavouritesFragment", "Room Favourites: " + roomNameFavourites.toString());
        }

        //Toggle button
        RecyclerView recyclerView = rootView.findViewById(R.id.favRecyclerView);
        //Create adapter after setting up models
        favourites_adapter adapter = new favourites_adapter(getContext(),roomNameFavourites,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return rootView;

    }
    public void onItemClick(int position) {
        // Handle the item click event here
        // You can implement the desired behavior when an item is clicked
    }



}