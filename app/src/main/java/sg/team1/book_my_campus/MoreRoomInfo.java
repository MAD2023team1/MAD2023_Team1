package sg.team1.book_my_campus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ThrowOnExtraProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoreRoomInfo extends AppCompatActivity {
    Button bookNowbt;
    boolean isRoomLiked;
    ArrayList<Favourites>favRoomList = new ArrayList<>();
    ArrayList<Ratings> ratingList = new ArrayList<>();

    ArrayList<Ratings> roomWithRatingList = new ArrayList<>();
    String name,password,email,roomName,roomLocation;
    int roomImage,roomLevel,roomCapacity;
    Room room;
    Button likedButton;


    String title ="MoreRoomInfo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_room_info2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        //Extract the variables and information passed from the explore fragment
        name = UserProfile.getName();
        password = UserProfile.getPassword();
        email = UserProfile.getEmail();
        //Room infomation
        roomName = getIntent().getStringExtra("roomName");
        roomImage = getIntent().getIntExtra("roomImage",0);
        roomLocation = getIntent().getStringExtra("roomLocation");
        roomLevel = getIntent().getIntExtra("roomLevel",0);
        roomCapacity = getIntent().getIntExtra("roomCapacity",0);
        isRoomLiked = getIntent().getBooleanExtra("isRoomLiked",false);
        room = new Room(000, roomName, null, roomLocation, roomLevel,null, roomCapacity, null, roomImage);
        Log.d("MoreRoomInfo", "Receiving Room Info passed from explore fragment"+ roomName);

        //grab the textViews display in the MoreRoomInfo.xml file
        TextView rmTV = findViewById(R.id.textView10);
        ImageView rmImg = findViewById(R.id.imageView2);
        TextView rmLocation = findViewById(R.id.textView11);
        TextView rmLevel = findViewById(R.id.textView13);
        TextView rmCapacity = findViewById(R.id.textView12);
        TextView displayRatings = findViewById(R.id.textView22);

        //Assign the values from what we have extracted from the ExploreFragment to the MoreRoomInfo.xml file
        rmTV.setText(roomName);
        rmImg.setImageResource(roomImage);
        rmLocation.setText("Location:"+ roomLocation);
        rmLevel.setText("Level "+roomLevel);
        rmCapacity.setText("Capacity:"+roomCapacity);
        displayRatings.setText("Ratings:");
        TextView textView22 = findViewById(R.id.textView22);
        boolean isInsideScrollView = isInsideScrollView(textView22);
        Log.d("Scroll Check", "textView22 is inside a ScrollView: " + isInsideScrollView);

        //set the title of the action bar based on each room name
        actionBar.setTitle(roomName);

        //Read the ratings Firebase
        readRatingsDocument();

        //set adapter after the thing is passed in


        //when user click on book now button, it will redirect the user to book now page
        // Initialize the button and set the click listener

        bookNowbt =  (Button)findViewById(R.id.button3);
        bookNowbt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent bookNowGo = new Intent(MoreRoomInfo.this, bookNowPage.class);
                bookNowGo.putExtra("name",name);
                bookNowGo.putExtra("password",password);
                bookNowGo.putExtra("email",email);
                bookNowGo.putExtra("roomName",roomName);
                bookNowGo.putExtra("roomLocation",roomLocation);
                bookNowGo.putExtra("roomLevel",roomLevel);
                bookNowGo.putExtra("roomCapacity",roomCapacity);
                startActivity(bookNowGo);
            }
        });



        likedButton = findViewById(R.id.button5);
        readFavourites();
        likedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFavourites();
                Intent i = new Intent(MoreRoomInfo.this, MoreRoomInfo.class);
                i.putExtra("name",name);
                i.putExtra("password",password);
                i.putExtra("email",email);
                i.putExtra("roomName",roomName);
                i.putExtra("roomLocation",roomLocation);
                i.putExtra("roomLevel",roomLevel);
                i.putExtra("roomCapacity",roomCapacity);
                i.putExtra("isRoomLiked",isRoomLiked);
                i.putExtra("roomImage",roomImage);
                finish();
                overridePendingTransition(0, 0);
                startActivity(i);
                overridePendingTransition(0, 0);
            }
        });




        /*SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        // Get the serialized array string from SharedPreferences
        String serializedArray = sharedPreferences.getString("favouriteList", "");

        // Convert the serialized string back to an ArrayList using the delimiter
        String[] arrayElements = serializedArray.split(",");
        ArrayList<String> storedFavList = new ArrayList<>(Arrays.asList(arrayElements));

        likedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                checkRoomLiked(likedButton, roomReceived);
                Log.d("MoreRoomInfo", "The favourite room List contains"+ favRoomList.size() + "elements");
                onDefaultToggleClick(view, favRoomList);


            }
        });*/
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        Log.v(title,"on Resume");
        readFavourites();
    }
    private void readFavourites()
    {
        Task<QuerySnapshot> future = FirebaseFirestore.getInstance()
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
                            favRoomList.add(favourites);
                            Log.v(title,"favourite:"+favRoomList.size());

                        }
                        checkFavourites();
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v(title, "onFailure: ", e);
                    }
                });
    }
    private void checkFavourites()
    {
        for (Favourites f:favRoomList)
        {
            if (f.getUserName().equals(UserProfile.getName()))
            {
                if (f.getRoomName().equals(roomName))
                {
                    likedButton.setText("Unlike");
                    likedButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            likedButton.setText("Like");
                            removeFavourites(f);
                            Intent i = new Intent(MoreRoomInfo.this, MoreRoomInfo.class);
                            i.putExtra("name",name);
                            i.putExtra("password",password);
                            i.putExtra("email",email);
                            i.putExtra("roomName",roomName);
                            i.putExtra("roomLocation",roomLocation);
                            i.putExtra("roomLevel",roomLevel);
                            i.putExtra("roomCapacity",roomCapacity);
                            i.putExtra("isRoomLiked",isRoomLiked);
                            i.putExtra("roomImage",roomImage);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(i);
                            overridePendingTransition(0, 0);

                        }
                    });
                }

            }


        }

    }
    private void removeFavourites(Favourites f)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("favouriterooms").document(f.getDocid()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MoreRoomInfo.this,"Remove from favourites",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        likedButton.setText("Unlike");
                        Toast.makeText(MoreRoomInfo.this,"Please try again",Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void addFavourites()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> favourites = new HashMap<>();
        favourites.put("roomName",room.roomName);
        favourites.put("userName",name);
        db.collection("favouriterooms").add(favourites).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Log.v(title,"name:"+name);
                    Log.v(title, "favourites added to db");
                    likedButton.setText("Unlike");
                    Toast.makeText(MoreRoomInfo.this,"Added to Favourites", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    //to handle the back button on the tool bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home)  {
            // Title bar back press triggers onBackPressed()
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /*public void checkRoomLiked(Button likedButton, Room room){
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //set the boolean to not equal the original state when the button is clicked.
        Log.d("MoreRoomInfo", "Room Liked Boolean:"+ isRoomLiked);
        if(room.isRoomLiked == true)
        {
            likedButton.setText("Unlike");
            favRoomList.add(room);
        }else{
            //if room isLiked is false, and it the list contains favRoomList, remove the room from the favourite list
            if(favRoomList.contains(room)){
                likedButton.setText("Like");
                favRoomList.remove(room);
            }
            // Serialize the array to a string using a delimiter
            String serializedArray = TextUtils.join(",", favRoomList);
            editor.putString("favouriteList",serializedArray );
            editor.apply();
        }

    }*/

    public void onDefaultToggleClick(View view, ArrayList<Room> favRoomList){
        favouritesFragment fragment = favouritesFragment.newInstance(favRoomList);
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
                            ratingList.add(rating);
                            Log.v(title, "onSuccess: " + rating.roomName);
                            Log.v(title, "onSuccess" + rating.starRatings);
                            Log.v(title, "onSuccess"+ rating.dateBooked);
                            Log.v(title,"List size"+ratingList.size());



                        }
                        String roomName = getIntent().getStringExtra("roomName");
                        float roomRatings = 0;
                        int count = 0;
                        boolean roomHasRatings = false;
                        for(Ratings rating: ratingList){
                            String eachRoom = rating.roomName;
                            String theRoom = roomName;
                            Log.v(title, "In theloop:" + eachRoom);
                            Log.v(title, "In theloop2:"+  theRoom);
                            if(eachRoom.equals(theRoom)){
                                roomWithRatingList.add(rating);
                                /*count += 1;
                                Log.v(title, "Count:"+count);
                                Log.v(title, "Before each room ratings:" +rating.starRatings);
                                roomRatings += rating.starRatings;
                                Log.v(title, "each room ratings:" +rating.starRatings);*/
                            }
                        }

                        Log.v(title, "Room Has Ratings Boolean:"+ roomHasRatings);
                        if(roomWithRatingList.size() != 0){
                            float eachRoomRatings = 0;
                            int countRoom = 0;
                            float totalEachRoom = 0;
                            //every thing inside this roomWithRatingList is of the same type of room
                            for(Ratings eachRoom :roomWithRatingList ){
                                countRoom += 1;
                                eachRoomRatings += eachRoom.starRatings;
                                totalEachRoom = eachRoomRatings/countRoom;
                                Log.v(title,"New Ratings:"+ eachRoomRatings);
                            }
                            TextView displayRatings = findViewById(R.id.textView22);
                            displayRatings.setText(String.valueOf(totalEachRoom));
                            RecyclerView recyclerView = findViewById(R.id.commentRecycler);
                            Log.v("AdapterDebug", "RatingList size: " + roomWithRatingList.size());
                            comments_adapter adapter = new comments_adapter(MoreRoomInfo.this, roomWithRatingList);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MoreRoomInfo.this));

                        }
                        else{
                            TextView displayRatings = findViewById(R.id.textView22);
                            displayRatings.setText(String.valueOf("There are no ratings for this room so far."));
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
    public static boolean isInsideScrollView(View view) {
        ViewParent parent = view.getParent();
        while (parent != null) {
            if (parent instanceof ScrollView) {
                return true; // The view is inside a ScrollView
            }
            parent = parent.getParent();
        }
        return false; // The view is not inside a ScrollView
    }

    private void setUpCommentsModel(){

    }


    // ltr come back and do share
    //SHARE THE SMART ROOM

}