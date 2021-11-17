package gr.hua.oopii.travelAgency.perceptrons;

import gr.hua.oopii.travelAgency.City;
import gr.hua.oopii.travelAgency.cityComparator.geodesicCompare;

import java.util.ArrayList;

public class PerceptronElderTraveler extends PerceptronTraveler {
    public PerceptronElderTraveler() {
        super(0.5F, 0.1F, 0.7F, 1F, 0F,
                -1F, 0.4F, 1F, 1F, -1F,-1.4F);
    }

    public ArrayList<City> sortRecommendation(){
        return sortRecommendation(new geodesicCompare().reversed());
    }
}