package sg.team1.book_my_campus;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyTimeSlotViewHolder extends RecyclerView.ViewHolder {
    TextView txt_time_slot;
    TextView txt_time_slot_description;
    CardView card_time_slot;

    public MyTimeSlotViewHolder(@NonNull View itemView) {
        super(itemView);
        card_time_slot = itemView.findViewById(R.id.card_time_slot);
        txt_time_slot = itemView.findViewById(R.id.txt_time_slot);
        txt_time_slot_description = itemView.findViewById(R.id.txt_time_slot_description);

    }
}
