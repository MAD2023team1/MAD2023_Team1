package sg.team1.book_my_campus;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class WeatherFragment extends Fragment {

    DecimalFormat df = new DecimalFormat("#.##");
    String TITLE = "Weather Page";
    ArrayList todayHourly  = new ArrayList(24);
    private View inflatedView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.inflatedView = inflater.inflate(R.layout.activity_weather_fragment, container, false);

        TextView CurrentConditionText = inflatedView.findViewById(R.id.currentConditionText);
        TextView CurrentTempText = inflatedView.findViewById(R.id.currentTemperatureText);
        ImageView CurrentWeatherImage = inflatedView.findViewById(R.id.currentWeatherImage);

        String TITLE = "Weather Page";

        final String url = "https://api.openweathermap.org/data/2.5/weather?lat=1.314&lon=-103.762&appid=55ded9357aaa11078213c18b28d80eaf&units=metric";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TITLE, response);
                try {
                    // storing the whole JSON in a variable
                    JSONObject jsonresponse = new JSONObject(response);

                    // narrowing down bit by bit from whole json to "list" to first list item
                    JSONArray CurrentWeather = jsonresponse.getJSONArray("weather");
                    Log.d(TITLE, CurrentWeather.toString());

                    JSONObject jsonCurrentWeatherObject = CurrentWeather.getJSONObject(0);
                    Log.v(TITLE, jsonCurrentWeatherObject.toString());

                    JSONObject JSONCurrentMainObject = jsonresponse.getJSONObject("main");
                    Log.i(TITLE, JSONCurrentMainObject.toString());

                    String CurrentMainCondition = jsonCurrentWeatherObject.getString("main");
                    Log.v(TITLE, "Current Main Condition:" + CurrentMainCondition);
                    String CurrentSubCondition = jsonCurrentWeatherObject.getString("description");
                    Double CurrentTemperature =  JSONCurrentMainObject.getDouble("temp");
                    Log.v(TITLE, "Current Temperature:" + CurrentTemperature);
                    if(CurrentTemperature!= null  && CurrentMainCondition != null){
                        CurrentTempText.setText(CurrentTemperature.toString()  + "℃");
                        CurrentConditionText.setText(CurrentMainCondition);
                    }
                    else{
                        CurrentTempText.setText("℃urrent Temp Text Null");
                        CurrentConditionText.setText("current condition text is null");
                    }



                    if (CurrentMainCondition.contentEquals("Clear")){
                        CurrentWeatherImage.setImageResource(R.drawable.sunny);
                    }


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

        return inflatedView;
    }
}
