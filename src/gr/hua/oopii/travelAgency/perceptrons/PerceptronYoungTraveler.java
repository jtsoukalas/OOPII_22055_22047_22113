package gr.hua.oopii.travelAgency.perceptrons;

import gr.hua.oopii.travelAgency.City;
import gr.hua.oopii.travelAgency.cityComparator.geodesicCompare;

import java.util.ArrayList;
import java.util.Comparator;

public class PerceptronYoungTraveler extends PerceptronTraveler {
    public PerceptronYoungTraveler() {
        super(0.5F, 0.4F, 0.1F, 0F,
                0.6F, 1F, 0F, 0.1F, -0.3F, 0.7F,-1.7F);
    }


    public ArrayList<City> sortRecommendation(){
        return sortRecommendation(new geodesicCompare());
    }

}