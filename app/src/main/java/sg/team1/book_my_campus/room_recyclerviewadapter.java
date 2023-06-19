package sg.team1.book_my_campus;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class room_recyclerviewadapter extends RecyclerView.Adapter<room_recyclerviewadapter.MyViewHolder>{
    private OnRoomListener myOnRoomListener;
    Context context;
    ArrayList<Room> roomModels;

    private AdapterView.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Room room);
    }
    public room_recyclerviewadapter(Context context, ArrayList<Room> roomModels, OnRoomListener onRoomListener){
        this.context = context;
        this.roomModels = roomModels;
        this.myOnRoomListener = onRoomListener;

    }
    public void setFilteredList(ArrayList<Room>filteredList){
        this.roomModels = filteredList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public room_recyclerviewadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout (giving a look to our rows)
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent,false);
        return new room_recyclerviewadapter.MyViewHolder(view, myOnRoomListener);
    }

    @Override
    public void onBindViewHolder(@NonNull room_recyclerviewadapter.MyViewHolder holder, int position) {
        //assigning values to the views we created in the recycler_view_row layout file
        //based on the position of the recycler view
        holder.tvroomName.setText(roomModels.get(position).getRoomName());
        holder.imageView.setImageResource(roomModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        //the recycler view just want to know the number of items that you have displayed
        return roomModels.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //grabbing the views(each element) from out recycler_view_row layout file
        ImageView imageView;
        TextView tvroomName;

        OnRoomListener onRoomListener;
        public MyViewHolder(@NonNull View itemView, OnRoomListener onRoomListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvroomName = itemView.findViewById(R.id.textView);
            this.onRoomListener = onRoomListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onRoomListener.onRoomClick(getAdapterPosition());
        }
    }
    public interface OnRoomListener{
        void onRoomClick(int position);
    }
}
