package sg.team1.book_my_campus;

public class Variables {
    public static String convertTimeSlot(int slot){
        switch(slot)
        {
            case 0:
                return "9.00am-10.00am";
            case 1:
                return "10.00am-11.00am";
            case 2:
                return "11.00am-12.00pm";
            case 3:
                return "12.00pm-1.00pm";
            case 4:
                return "1.00pm-2.00pm";
            case 5:
                return "2.00pm-3.00pm";
            case 6:
                return "3.00pm-4.00pm";
            case 7:
                return "4.00pm-5.00pm";
            case 8:
                return "5.00pm-6.00pm";
            default:
                return "Closed";

        }

    }
}
