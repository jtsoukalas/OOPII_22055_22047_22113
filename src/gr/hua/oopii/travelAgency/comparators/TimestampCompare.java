package gr.hua.oopii.travelAgency.comparators;

import gr.hua.oopii.travelAgency.City;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * Comparator for City objects and especially for timestamp field
 *
 * @version 0
 * @since secondDeliverable
 * @author
 */
public class TimestampCompare implements Comparator<City> {

    /**
     * Compares two city objects about the timestamps
     *
     * @param o1 City object
     * @param o2 City object
     * @return the value 0 if the argument o1 is equal to o2
     * a value less than 0 if o1 is before the o2
     * and a value greater than 0 if o1 is after the o2.
     * @since secondDeliverable
     * @author
     */

    @Override
    public int compare(@NotNull City o1, @NotNull City o2) {
        return o1.getTimestamp().compareTo(o2.getTimestamp());
    }
}
