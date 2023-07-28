package sg.team1.book_my_campus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class WeatherFragment extends Fragment {

    private final String url = "https://api.openweathermap.org/data/3.0/onecall?lat=1.314&lon=-103.762&appid=55ded9357aaa11078213c18b28d80eaf";
    DecimalFormat df = new DecimalFormat("#.##");
    String TITLE = "Weather Page";
    ArrayList todayHourly  = new ArrayList(24);
    private View inflatedView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.inflatedView = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView TodayConditionText = inflatedView.findViewById(R.id.todayCondition);
        TextView TodayTempText = inflatedView.findViewById(R.id.todayTemperature);

        String TITLE = "Weather Page";

        final String url = "https://api.openweathermap.org/data/2.5/forecast?lat=1.314&lon=-103.762&appid=55ded9357aaa11078213c18b28d80eaf&units=metric";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TITLE, response);
                try {
                    // storing the whole JSON in a variable
                    JSONObject jsonresponse = new JSONObject(response);

                    // narrowing down bit by bit from whole json to "list" to first list item
                    JSONArray list = jsonresponse.getJSONArray("list");
                    Log.d(TITLE, list.toString());

                    JSONObject jsonTodayArray = list.getJSONObject(0);
                    Log.v(TITLE, jsonTodayArray.toString());

                    JSONObject TodayMain = jsonTodayArray.getJSONObject("main");
                    JSONArray TodayWeather = jsonTodayArray.getJSONArray("weather");
                    JSONObject TodayWeatherObject = TodayWeather.getJSONObject(0);

                    Double todayTemp = TodayMain.getDouble("temp");
                    String todayMainCondition = TodayWeatherObject.getString("main");
                    String todayCondition = TodayWeatherObject.getString("description");

                    TodayConditionText.setText(todayCondition);
                    TodayTempText.setText(todayTemp.toString());

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


        return null;
    }
}
