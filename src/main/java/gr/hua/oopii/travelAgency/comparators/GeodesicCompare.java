package gr.hua.oopii.travelAgency.comparators;

import gr.hua.oopii.travelAgency.City;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * Comparator that compares city objects's geodistic distance
 * @version 0
 * @since Second Deliverable
 * @author
 */

public class GeodesicCompare implements Comparator<City> {

    /**
     * Compare method takes two city objects and gets
     * the 9th feature from the features[]->geodesic distance
     * @param o1 City Object
     * @param o2 City Object
     * @return the value {@code 0} if {@code o1} is
     *             numerically equal to {@code o2}; a value less than
     *            {@code 0} if {@code o1} is numerically less than
     *            {@code o2}; and a value greater than {@code 0}
     *             if {@code f1} is numerically greater than
     *           {@code o2}.
     * @since secondDeliverable
     * @author
     */
    @Override
    public int compare(@NotNull City o1, @NotNull City o2) {
        return Float.compare(o1.getFeatures()[9], o2.getFeatures()[9]);
    }
}
