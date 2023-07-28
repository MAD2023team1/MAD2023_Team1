package sg.team1.book_my_campus;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class comments_adapter extends RecyclerView.Adapter<comments_adapter.MyViewHolder> {
    String title = "comments_adapter";
    Context context;
    ArrayList<Ratings> ratingList;

    public comments_adapter(Context context, ArrayList<Ratings> ratingList ){
        this.context = context;
        this.ratingList = ratingList;
    }


    @NonNull
    @Override
    public comments_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.comments_recyclerview,parent,false);

        return new comments_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull comments_adapter.MyViewHolder holder, int position) {
        holder.userName.setText("By:"+ratingList.get(position).getUserName());
        holder.comments.setText(ratingList.get(position).getComment());
        holder.ratingBar.setRating(ratingList.get(position).getStarRatings());
        holder.ratingBar.setEnabled(false);
        Log.v(title, "userID:" + ratingList.get(position).getUserID());
        Log.v(title, "comments:" + ratingList.get(position).getComment());
        Log.v(title, "ratingBar:" + ratingList.get(position).getStarRatings());
    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView userName;
        TextView comments;
        RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.username);
            comments = itemView.findViewById(R.id.comments);
            ratingBar = itemView.findViewById(R.id.ratingBar2);
        }
    }
}
