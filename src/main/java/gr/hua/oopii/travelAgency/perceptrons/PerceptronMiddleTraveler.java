package gr.hua.oopii.travelAgency.perceptrons;

import gr.hua.oopii.travelAgency.City;
import gr.hua.oopii.travelAgency.comparators.TimestampCompare;
import gr.hua.oopii.travelAgency.exception.NoRecommendationException;

import java.util.ArrayList;

public class PerceptronMiddleTraveler extends PerceptronTraveler {

    //Custom weights for middle traveler
    public PerceptronMiddleTraveler() {
        super(0.8F, 0.4F, 0.7F, 0.1F, 0.1F,
                0.6F, 0.5F, 0.7F, 0.8F, -0.3F,-1.5F);
    }

    /**
     * {@inheritDoc}
     * <h3>Shorted by<u>timestamp</u>. </h3>
     * @return ArrayList<City> sorted
     * @throws NoRecommendationException if there is no recommendations
     */
    public ArrayList<City> sortRecommendation() throws NoRecommendationException {
        return sortRecommendation(new TimestampCompare());
    }
}