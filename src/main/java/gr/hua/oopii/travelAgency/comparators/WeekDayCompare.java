package gr.hua.oopii.travelAgency.comparators;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Compares two days (strings)
 * @since secondDeliverable
 * @version 0
 */
public class WeekDayCompare implements Comparator<String> {

    /**
     * Compares two strings - days of week. Week starting day: Monday.
     * @param d1 Day1
     * @param d2 Day2
     * @return  the value 0 if d1 is equal to the d2;
     *          a value less than 0 if d1 is before d2;
     *          and a value greater than 0 if d1 is after d2;
     * @since secondDeliverable
     * @author
     */
    @Override
    public int compare(@NotNull String d1, @NotNull String d2) {
        //Adding days to an Arraylist
        enum Days {
            MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
        }
        ArrayList<String> week = new ArrayList<>(7);
        for (Days day : Days.values()) {
            week.add(day.toString());
        }

        //Getting the indexes of keys
        Integer key1 = week.indexOf(d1.toUpperCase());
        Integer key2 = week.indexOf(d2.toUpperCase());

        return key1.compareTo(key2);
    }
}
