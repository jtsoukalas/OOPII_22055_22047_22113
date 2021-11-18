package gr.hua.oopii.travelAgency.cityComparator;

import gr.hua.oopii.travelAgency.City;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class GeodesicCompare implements Comparator<City> {
    @Override
    public int compare(@NotNull City o1, @NotNull City o2) {
        return Float.compare(o1.getFeatures()[9], o2.getFeatures()[9]);
    }
}
