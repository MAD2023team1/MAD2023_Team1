package sg.team1.book_my_campus;

import android.util.Log;

import java.util.Calendar;

public class Variables {


    int currentTime= Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    public static String convertTimeSlot(int slot){
        int currentTime= Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        switch(slot)
        {
            case 0:

                return "09.00-10.00";

            case 1:


                return "10.00-11.00";

            case 2:


                return "11.00-12.00";

            case 3:


                return "12.00-13.00";

            case 4:


                return "13.00-14.00";

            case 5:


                return "14.00-15.00";
            case 6:


                return "15.00-16.00";

            case 7:


                return "16.00-17.00";

            case 8:


                return "17.00-18.00";

            default:
                return "Closed";

        }

    }
}
