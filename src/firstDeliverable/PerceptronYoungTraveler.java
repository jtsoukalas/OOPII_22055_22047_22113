package firstDeliverable;

public class PerceptronYoungTraveler extends PerceptronTraveler {

    private final float weightsBias[] = new float[]{2.5F, 1.5F, 0.5F, 0F, 2.5F, 3F, 0F, 1F, 2F, 0.5F};      //||1.cafe,restaurant 2.sea, 3.museums, 4.wellness center, 5.stadium,
                                                                                                            // 6.bar/club, 7.parks,playgrounds, 8.temperature, 9.weather(how cloudy), 10.distance||
                                                                                                            // Convention of weightBias: min = 0 max = 3

    public float[] getWeightsBias() {
        return weightsBias;
    }


}

