package sg.team1.book_my_campus;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.team1.book_my_campus.R;

public class HomePage extends AppCompatActivity {
    String title = "Home Page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        Log.v(title, "On Create! hello");

    }

}
