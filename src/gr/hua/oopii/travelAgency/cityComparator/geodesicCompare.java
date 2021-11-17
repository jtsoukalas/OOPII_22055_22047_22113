package gr.hua.oopii.travelAgency.cityComparator;

import gr.hua.oopii.travelAgency.City;

import java.util.Comparator;

public class geodesicCompare implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {

        if(o1.getFeatures()[9] > o2.getFeatures()[9]){
            return 1;
        }else if(o1.getFeatures()[9] < o2.getFeatures()[9]){
            return -1;
        }else{
            return 0;
        }
    }
}
