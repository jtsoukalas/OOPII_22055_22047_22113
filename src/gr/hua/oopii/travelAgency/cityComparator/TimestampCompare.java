package gr.hua.oopii.travelAgency.cityComparator;

import gr.hua.oopii.travelAgency.City;

import java.util.Comparator;
import java.util.Date;

/**
 * @since secondDeliverable
 * @version 1.0
 */

public class TimestampCompare implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {
        return o1.getTimestamp().compareTo(o2.getTimestamp());
    }
}
