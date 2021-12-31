package gr.hua.oopii.travelAgency.perceptrons;

import gr.hua.oopii.travelAgency.City;
import gr.hua.oopii.travelAgency.comparators.GeodesicCompare;
import gr.hua.oopii.travelAgency.exception.NoRecommendationException;

import java.util.ArrayList;

public class PerceptronYoungTraveler extends PerceptronTraveler {

   //Custom weights for young traveler
    public PerceptronYoungTraveler() {
        super(0.5F, 0.4F, 0.1F, 0F,
                0.6F, 1F, 0F, 0.1F, -0.3F, 0.7F,-1.85F);
    }

    /**
     * {@inheritDoc}
     * <h3>Shorted by<u>geodesic distance</u>. </h3>
     * @return ArrayList<City> sorted
     * @throws NoRecommendationException if there is no recommendations
     */
    public ArrayList<City> sortRecommendation() throws NoRecommendationException {
        return sortRecommendation(new GeodesicCompare());
    }
}