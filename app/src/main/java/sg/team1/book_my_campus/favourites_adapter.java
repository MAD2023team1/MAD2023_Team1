package sg.team1.book_my_campus;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class favourites_adapter extends RecyclerView.Adapter<favourites_adapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    String title = "favourites Adapter";
    ArrayList<Favourites> roomFavourites;

    ArrayList<Room> displayFavouritesList = new ArrayList<>();
    ArrayList<Room> roomsList;
    String userName;
    Context context;

    public favourites_adapter(Context context,ArrayList<Favourites> roomFavourites, RecyclerViewInterface recyclerViewInterface,ArrayList<Room> roomsList,String userName){
        this.context = context;
        this.roomFavourites = roomFavourites;
        this.recyclerViewInterface = recyclerViewInterface;
        this.roomsList = roomsList;
        this.userName = userName;
    }
    @NonNull
    @Override
    public favourites_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.favourites_recycler_view_row, parent,false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull favourites_adapter.MyViewHolder holder, int position) {

        holder.roomName.setText(displayFavouritesList.get(holder.getAdapterPosition()).roomName);
        holder.roomImage.setImageResource(displayFavouritesList.get(holder.getAdapterPosition()).image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewInterface.favouritesOnClicked(displayFavouritesList.get(holder.getAdapterPosition()));

            }
        });

        /*if (roomFavourites != null) {
            holder.roomNameFavourites.setText(roomFavourites.get(position).getRoomName());
            holder.roomImageFavourites.setImageResource(roomFavourites.get(position).getImage());
        } else {
            holder.roomNameFavourites.setText("No Liked Rooms");
            holder.roomImageFavourites.setImageResource(0);
        }*/


    }
    public void checkFavourites()
    {
        for(Favourites favourites: roomFavourites)
        {
            if (userName.equals(favourites.getUserName()))
            {
                for (Room room: roomsList)
                {
                    if (room.roomName.equals(favourites.getRoomName()))
                    {
                        displayFavouritesList.add(room);
                        Log.v(title,"Rooms added to display fav List");
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return displayFavouritesList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Define your ViewHolder components here
        TextView roomName;
        ImageView roomImage;

        public MyViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            // Initialize your ViewHolder components here
            roomName = itemView.findViewById(R.id.favname);
            roomImage = itemView.findViewById(R.id.favimage);
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface!= null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });*/
        }
    }
}
