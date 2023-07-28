package sg.team1.book_my_campus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

class top_room_adapter extends RecyclerView.Adapter<top_room_adapter.MyViewHolder> {
    Context context;
    ArrayList<top_rated_room_model> top_rated_room_modelsList;
    public top_room_adapter(Context context, ArrayList<top_rated_room_model> top_rated_room_modelsList){
        this.context = context;
        this.top_rated_room_modelsList = top_rated_room_modelsList;

    }
    @NonNull
    @Override
    public top_room_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.top_rated_rooms_recycler_row, parent,false);
        return new top_room_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull top_room_adapter.MyViewHolder holder, int position) {
        holder.tvRatings.setText(Float.toString(top_rated_room_modelsList.get(position).getRatings()));
        holder.tvRoomName.setText(top_rated_room_modelsList.get(position).getName());
        holder.imageView.setImageResource(top_rated_room_modelsList.get(position).getRoomImage());
    }

    @Override
    public int getItemCount() {
        return top_rated_room_modelsList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvRoomName;
        TextView tvRatings;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewTop);
            tvRoomName = itemView.findViewById(R.id.textViewName);
            tvRatings = itemView.findViewById(R.id.averageValue);
        }
    }
}
