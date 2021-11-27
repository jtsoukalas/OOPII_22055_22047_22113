package gr.hua.oopii.travelAgency.perceptrons;

import gr.hua.oopii.travelAgency.City;
import gr.hua.oopii.travelAgency.comparators.GeodesicCompare;
import gr.hua.oopii.travelAgency.exception.NoRecommendationException;

import java.util.ArrayList;

public class PerceptronYoungTraveler extends PerceptronTraveler {
    public PerceptronYoungTraveler() {
        super(0.5F, 0.4F, 0.1F, 0F,
                0.6F, 1F, 0F, 0.1F, -0.3F, 0.7F,-1.7F);
    }

    public ArrayList<City> sortRecommendation() throws NoRecommendationException {
        return sortRecommendation(new GeodesicCompare());
    }
}