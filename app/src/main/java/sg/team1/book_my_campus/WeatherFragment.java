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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        final String ForecastUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=1.314&lon=103.762&appid=55ded9357aaa11078213c18b28d80eaf&units=metric";

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
                    } else if (CurrentMainCondition.contentEquals("Clouds")) {
                        CurrentWeatherImage.setImageResource(R.drawable.cloudy);
                    } else if (CurrentMainCondition.contentEquals("Rain")){
                        CurrentWeatherImage.setImageResource(R.drawable.rainy);
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

        StringRequest stringRequestForecast = new StringRequest(Request.Method.POST, ForecastUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TITLE, response);
                try {
                    // storing the whole JSON in a variable
                    JSONObject jsonresponseForecast = new JSONObject(response);

                    JSONArray list = jsonresponseForecast.getJSONArray("list");
                    Integer tally = 0;
                    for(int i = 0; i<list.length(); i++){
                        //checking if length of response received matches that in online json viewer
                        tally+=1;
                    }
                    Log.v(TITLE,"TOTAL JSONOBJECTS = " + tally);

                    for(int i = 0; i<list.length(); i++){
                        String datetime = list.getJSONObject(i).getString("dt_txt");
                        System.out.println(datetime);
                    }


                    /** here i settle all the variables i'll need to get the data needed for the next day and the day after that
                     * first i  get the current date and format it into the same format as the DateTime strings i get from the JSON
                     * then i turn it into a date object and obtain the next day in a date object before turning that into a string to store
                     *
                     */
                    Date currenDate = new Date();
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                    String currentDateString = dateFormatter.format(currenDate);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currenDate);
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    Date nexDayDate = calendar.getTime();
                    String nextDateString = dateFormatter.format(nexDayDate);

                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    Date nex2DayDate = calendar.getTime();
                    String next2DateString = dateFormatter.format(nex2DayDate);

                    ArrayList dateStringArray = new ArrayList();
                    dateStringArray.add(currentDateString);
                    dateStringArray.add(nextDateString);
                    dateStringArray.add(next2DateString);

                    ArrayList todayTimeWeather = new ArrayList();
                    ArrayList tomorrowTimeWeather = new ArrayList();
                    ArrayList nextTomorrowTimeWeather = new ArrayList();
                    ArrayList allWeather = new ArrayList();
                    allWeather.add(todayTimeWeather);
                    allWeather.add(tomorrowTimeWeather);
                    allWeather.add(nextTomorrowTimeWeather);

                    ArrayList timings = new ArrayList();
                    timings.add("09:00:00");
                    timings.add("12:00:00");
                    timings.add("15:00:00");
                    timings.add("18:00:00");
                    timings.add("21:00:00");
                    Log.v("TIMING LIST", timings.toString());

                    for(int y=0; y<allWeather.size(); y++){
                        for(int i = 0; i<list.length(); i++){
                            JSONObject loopDay = list.getJSONObject(i);
                            String loopDate = loopDay.getString("dt_txt");
                            String loopDateString = loopDate.substring(0, 10);

                            // checking if date in the loop matches with today
                            if(loopDateString.contentEquals( (String)dateStringArray.get(y) )){
                                Log.d(TITLE, "DATE MATCH");

                                // checking if timing is equals to any of the timing in the ArrayList
                                for(int x = 0; x<timings.size(); x++){
                                    Log.e(TITLE, (String) timings.get(x));
                                    if (loopDate.substring(11).contentEquals( (String)timings.get(x)) ){
                                        Log.d(TITLE, "TIME MATCH AT" + i);
                                        Double temperature = loopDay.getJSONObject("main").getDouble("temp");
                                        String condition = loopDay.getJSONArray("weather").getJSONObject(0).getString("main");
                                        int icon = 0;
                                        if(condition == "Clouds"){
                                            icon = R.drawable.cloudy;
                                        } else if (condition == "Rain") {
                                            icon = R.drawable.rainy;
                                        }
                                        ArrayList tempArray = (ArrayList) allWeather.get(y);
                                        tempArray.add(new WeatherCondition(temperature, condition, icon));
                                    }
                                }
                            }
                        }

                        ArrayList tempArray = (ArrayList) allWeather.get(y);
                        if (tempArray.size()<5){
                            Integer difference = 5 - tempArray.size();
                            for (int i = 0; i<difference; i++){
                                tempArray.add(0, new WeatherCondition(0.0, "empty", R.drawable.smiley));
                            }
                            Log.v(TITLE, "SIZE OF THIS ARRAY = " + tempArray.size());
                        }
                    }

                    /** from this point onwards i am well aware that there is a much more efficient way to code this but
                     * this is how i will code it, since optimizing will take time
                     */

                    ImageView today9amImage = inflatedView.findViewById(R.id.am9DisplayImage);
                    ImageView today12pmImage = inflatedView.findViewById(R.id.pm12DisplayImage);
                    ImageView today3pmImage = inflatedView.findViewById(R.id.pm3DisplayImage);
                    ImageView today6pmImage = inflatedView.findViewById(R.id.pm6DisplayImage);
                    ImageView today9pmImage = inflatedView.findViewById(R.id.pm9DisplayImage);
                    ArrayList todayImageArray = new ArrayList();
                    todayImageArray.add(today9amImage);
                    todayImageArray.add(today12pmImage);
                    todayImageArray.add(today3pmImage);
                    todayImageArray.add(today6pmImage);




                    ImageView t92amImage = inflatedView.findViewById(R.id.am92DisplayImage);
                    ImageView t122pmImage = inflatedView.findViewById(R.id.pm122DisplayImage);
                    ImageView t32pmImage = inflatedView.findViewById(R.id.pm32DisplayImage);
                    ImageView t62pmImage = inflatedView.findViewById(R.id.pm62DisplayImage);
                    ImageView t92pmImage = inflatedView.findViewById(R.id.pm92DisplayImage);

                    ImageView t93amImage = inflatedView.findViewById(R.id.am93DisplayImage);
                    ImageView t123pmImage = inflatedView.findViewById(R.id.pm123DisplayImage);
                    ImageView t33pmImage = inflatedView.findViewById(R.id.pm33DisplayImage);
                    ImageView t63pmImage = inflatedView.findViewById(R.id.pm63DisplayImage);
                    ImageView t93pmImage = inflatedView.findViewById(R.id.pm93DisplayImage);

                    Log.e(TITLE, "SIZE OF TODAYARRAY = " + todayTimeWeather.size());


                }
                catch (JSONException e) {
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
