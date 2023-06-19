package sg.team1.book_my_campus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ViewProfile extends AppCompatActivity {

    String title = "View Profile Page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        Log.v(title, "ViewProfile Created");

        Button editProfButton = findViewById(R.id.editProfileButton);

        editProfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}