package gr.hua.oopii.travelAgency.comparators;

import java.util.ArrayList;
import java.util.Comparator;

public class WeekDayCompare implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        //Adding days to an Arraylist
        enum Days {
            MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
        }
        ArrayList<String> week = new ArrayList<>(7);
        for (Days day : Days.values()) {
            week.add(day.toString());
        }

        //Getting the indexes of keys
        Integer key1 = week.indexOf(o1);
        Integer key2 = week.indexOf(o2);

        return key1.compareTo(key2);
    }
}
