package gr.hua.oopii.travelAgency.perceptrons;

import gr.hua.oopii.travelAgency.City;
import gr.hua.oopii.travelAgency.comparators.GeodesicCompare;
import gr.hua.oopii.travelAgency.exception.NoRecommendationException;

import java.util.ArrayList;

public class PerceptronElderTraveler extends PerceptronTraveler {

    //Custom weights for elder traveler
    public PerceptronElderTraveler() {
        super(0.5F, 0.1F, 0.7F, 1F, 0F,
                -1F, 0.4F, 1F, 1F, -1F,-1.4F);
    }

    /**
     * {@inheritDoc}
     * <h3> Shorted by <u>revert geodesic distance</u>. </h3>
     * @return ArrayList <City> sorted
     * @throws NoRecommendationException if there is no recommendations
     */
    public ArrayList<City> sortRecommendation() throws NoRecommendationException {
        return sortRecommendation(new GeodesicCompare().reversed());
    }
}