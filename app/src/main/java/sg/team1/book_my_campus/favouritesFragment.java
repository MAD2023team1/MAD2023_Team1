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
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link favouritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class favouritesFragment extends Fragment implements RecyclerViewInterface{
    ArrayList<Room> roomFavourites = new ArrayList<>();

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
        if (getArguments() != null) {
            roomFavourites = getArguments().getParcelableArrayList("favRoomList");
        }
        else{
            Log.d("Favourite Fragment", "Room List is empty");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);
        // Retrieve room information and liked status from the intent
            RecyclerView recyclerView = rootView.findViewById(R.id.favRecyclerView);
            //Create adapter after setting up models
            favourites_adapter adapter = new favourites_adapter(getContext(),roomFavourites,this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        return rootView;

    }
    public void onItemClick(int position) {
        // Handle the item click event here
        // You can implement the desired behavior when an item is clicked
    }



}