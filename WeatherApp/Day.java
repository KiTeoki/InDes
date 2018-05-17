package WeatherApp;
import java.util.Calendar;

public class Day{

    public static String decode(int n) {
        String[] temp = new String[]{"Error", "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat"};

        return temp[n];
    }

    public static String getDay(int n) {
        if (n == 0) {
            return "Today";
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_WEEK, n);
        String[] temp = cal.toString().split(",");
        String daynumber = temp[35].split("=")[1];
        return decode(Integer.parseInt(daynumber));
    }


}
