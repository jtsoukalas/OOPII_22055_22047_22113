package gr.hua.oopii.travelAgency.comparators;

import gr.hua.oopii.travelAgency.City;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class TimestampCompare implements Comparator<City> {
    @Override
    public int compare(@NotNull City o1, @NotNull City o2) {
        return o1.getTimestamp().compareTo(o2.getTimestamp());
    }
}
