package sg.team1.book_my_campus;

import android.content.Context;
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
    ArrayList<Room> roomFavourites;
    Context context;

    public favourites_adapter(Context context,ArrayList<Room> roomFavourites, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.roomFavourites = roomFavourites;
        this.recyclerViewInterface = recyclerViewInterface;
    }
    @NonNull
    @Override
    public favourites_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.favourites_recycler_view_row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull favourites_adapter.MyViewHolder holder, int position) {
        if (roomFavourites != null) {
            holder.roomNameFavourites.setText(roomFavourites.get(position).getRoomName());
            holder.roomImageFavourites.setImageResource(roomFavourites.get(position).getImage());
        } else {
            holder.roomNameFavourites.setText("No Liked Rooms");
            holder.roomImageFavourites.setImageResource(0);
        }


    }

    @Override
    public int getItemCount() {
        return roomFavourites.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Define your ViewHolder components here
        TextView roomNameFavourites;
        ImageView roomImageFavourites;

        public MyViewHolder(View itemView) {
            super(itemView);
            // Initialize your ViewHolder components here
            roomNameFavourites = itemView.findViewById(R.id.favname);
            roomImageFavourites = itemView.findViewById(R.id.favimage);
        }
    }
}
