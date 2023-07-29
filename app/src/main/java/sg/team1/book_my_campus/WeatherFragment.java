package sg.team1.book_my_campus;

import androidx.fragment.app.Fragment;

import android.media.Image;
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

import org.checkerframework.checker.units.qual.A;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
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

                    /**
                     * Here I link some TextViews to edit ASAP
                     */

                    TextView tomorrowTimeText = inflatedView.findViewById(R.id.tomorrowDateText);
                    TextView tomorrow2TimeText = inflatedView.findViewById(R.id.tomorrow2DateText);
                    tomorrowTimeText.setText("VVV " +nextDateString +" VVV");
                    tomorrow2TimeText.setText("VVV " +next2DateString +" VVV");

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
                                    if (loopDate.substring(11).contentEquals( (String)timings.get(x)) ){
                                        Log.d(TITLE, "TIME MATCH AT" + i);
                                        Double temperature = loopDay.getJSONObject("main").getDouble("temp");
                                        String condition = loopDay.getJSONArray("weather").getJSONObject(0).getString("main");
                                        int icon = 0;
                                        if(condition.contentEquals("Clouds")){
                                            icon = R.drawable.cloudy;
                                        } else if (condition.contentEquals("Rain")) {
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
                     *
                     * Here I create 2 arrays each for each of the 3 days (today, tomorrow, and the day after). one contains the image
                     * and the other contains the text for temperature
                     *
                     * I then group the arrays into groups of 2 to match the 3 rows of dates for an easier for loop
                     *
                     * Then, I loop over the arrays to change the ImageResource and text displayed
                     */

                    // -----------------------------------------------------------------------------
                    ImageView t9amImage = inflatedView.findViewById(R.id.am9DisplayImage);
                    ImageView t12pmImage = inflatedView.findViewById(R.id.pm12DisplayImage);
                    ImageView t3pmImage = inflatedView.findViewById(R.id.pm3DisplayImage);
                    ImageView t6pmImage = inflatedView.findViewById(R.id.pm6DisplayImage);
                    ImageView t9pmImage = inflatedView.findViewById(R.id.pm9DisplayImage);
                    ArrayList tImageArray = new ArrayList();
                    tImageArray.add(t9amImage);
                    tImageArray.add(t12pmImage);
                    tImageArray.add(t3pmImage);
                    tImageArray.add(t6pmImage);
                    tImageArray.add(t9pmImage);

                            // i mistakenly named the id of the textviews to condition...
                    TextView t9amTemp = inflatedView.findViewById(R.id.am9DisplayCondition);
                    TextView t12pmTemp = inflatedView.findViewById(R.id.pm12DisplayCondition);
                    TextView t3pmTemp = inflatedView.findViewById(R.id.pm3DisplayCondition);
                    TextView t6pmTemp = inflatedView.findViewById(R.id.pm6DisplayCondition);
                    TextView t9pmTemp = inflatedView.findViewById(R.id.pm9DisplayCondition);
                    ArrayList tTextArray = new ArrayList();
                    tTextArray.add(t9amTemp);
                    tTextArray.add(t12pmTemp);
                    tTextArray.add(t3pmTemp);
                    tTextArray.add(t6pmTemp);
                    tTextArray.add(t9pmTemp);
                    // -----------------------------------------------------------------------------

                    // -----------------------------------------------------------------------------
                    ImageView t92amImage = inflatedView.findViewById(R.id.am92DisplayImage);
                    ImageView t122pmImage = inflatedView.findViewById(R.id.pm122DisplayImage);
                    ImageView t32pmImage = inflatedView.findViewById(R.id.pm32DisplayImage);
                    ImageView t62pmImage = inflatedView.findViewById(R.id.pm62DisplayImage);
                    ImageView t92pmImage = inflatedView.findViewById(R.id.pm92DisplayImage);
                    ArrayList t2ImageArray = new ArrayList();
                    t2ImageArray.add(t92amImage);
                    t2ImageArray.add(t122pmImage);
                    t2ImageArray.add(t32pmImage);
                    t2ImageArray.add(t62pmImage);
                    t2ImageArray.add(t92pmImage);

                    TextView t92amTemp = inflatedView.findViewById(R.id.am92DisplayCondition);
                    TextView t122pmTemp = inflatedView.findViewById(R.id.pm122DisplayCondition);
                    TextView t32pmTemp = inflatedView.findViewById(R.id.pm32DisplayCondition);
                    TextView t62pmTemp = inflatedView.findViewById(R.id.pm62DisplayCondition);
                    TextView t92pmTemp = inflatedView.findViewById(R.id.pm92DisplayCondition);
                    ArrayList t2TextArray = new ArrayList();
                    t2TextArray.add(t92amTemp);
                    t2TextArray.add(t122pmTemp);
                    t2TextArray.add(t32pmTemp);
                    t2TextArray.add(t62pmTemp);
                    t2TextArray.add(t92pmTemp);
                    // -----------------------------------------------------------------------------

                    // -----------------------------------------------------------------------------
                    ImageView t93amImage = inflatedView.findViewById(R.id.am93DisplayImage);
                    ImageView t123pmImage = inflatedView.findViewById(R.id.pm123DisplayImage);
                    ImageView t33pmImage = inflatedView.findViewById(R.id.pm33DisplayImage);
                    ImageView t63pmImage = inflatedView.findViewById(R.id.pm63DisplayImage);
                    ImageView t93pmImage = inflatedView.findViewById(R.id.pm93DisplayImage);
                    ArrayList t3ImageArray = new ArrayList();
                    t3ImageArray.add(t93amImage);
                    t3ImageArray.add(t123pmImage);
                    t3ImageArray.add(t33pmImage);
                    t3ImageArray.add(t63pmImage);
                    t3ImageArray.add(t93pmImage);

                    TextView t93amTemp = inflatedView.findViewById(R.id.am93DisplayCondition);
                    TextView t123pmTemp = inflatedView.findViewById(R.id.pm123DisplayCondition);
                    TextView t33pmTemp = inflatedView.findViewById(R.id.pm33DisplayCondition);
                    TextView t63pmTemp = inflatedView.findViewById(R.id.pm63DisplayCondition);
                    TextView t93pmTemp = inflatedView.findViewById(R.id.pm93DisplayCondition);
                    ArrayList t3TextArray = new ArrayList();
                    t3TextArray.add(t93amTemp);
                    t3TextArray.add(t123pmTemp);
                    t3TextArray.add(t33pmTemp);
                    t3TextArray.add(t63pmTemp);
                    t3TextArray.add(t93pmTemp);
                    // -----------------------------------------------------------------------------

                    ArrayList allImageArray = new ArrayList();
                    allImageArray.add(tImageArray);
                    allImageArray.add(t2ImageArray);
                    allImageArray.add(t3ImageArray);
                    ArrayList allTempArray = new ArrayList();
                    allTempArray.add(tTextArray);
                    allTempArray.add(t2TextArray);
                    allTempArray.add(t3TextArray);

                    // for each row
                    for (int i=0; i<allWeather.size(); i++){
                        ArrayList currentWeatherArray = (ArrayList) allWeather.get(i);

                        Log.d(TITLE, "LOOP " + i);

                        //for each card
                        for (int x=0; x<currentWeatherArray.size(); x++){

                            ArrayList tempImageArray = (ArrayList) allImageArray.get(i);
                            ImageView tempImageView = (ImageView) tempImageArray.get(x);
                            WeatherCondition tempWeatherCondition = (WeatherCondition) currentWeatherArray.get(x);
                            tempImageView.setImageResource(tempWeatherCondition.weatherIcon);

                            ArrayList tempTextArray = (ArrayList) allTempArray.get(i);
                            TextView tempTextView = (TextView) tempTextArray.get(x);
                            tempTextView.setText(tempWeatherCondition.getTemperature().toString() + "℃");
                        }

                    }
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
        requestQueue.add(stringRequestForecast);

        return inflatedView;
    }
}
