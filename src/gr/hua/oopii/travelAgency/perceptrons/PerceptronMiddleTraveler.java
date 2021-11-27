package gr.hua.oopii.travelAgency.perceptrons;

import gr.hua.oopii.travelAgency.City;
import gr.hua.oopii.travelAgency.comparators.TimestampCompare;
import gr.hua.oopii.travelAgency.exception.NoRecommendationException;

import java.util.ArrayList;

public class PerceptronMiddleTraveler extends PerceptronTraveler {
    public PerceptronMiddleTraveler() {
        super(0.8F, 0.4F, 0.7F, 0.1F, 0.1F,
                0.6F, 0.5F, 0.7F, 0.8F, -0.3F,-2.7F);
    }

    public ArrayList<City> sortRecommendation() throws NoRecommendationException {
        return sortRecommendation(new TimestampCompare());
    }
}