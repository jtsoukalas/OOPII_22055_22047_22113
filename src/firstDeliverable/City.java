package firstDeliverable;

import java.lang.reflect.Array;

public class City {


    private float features[];
    private String name;

    public City(float[] features, String name) {

        this.features = features;
        this.name = name;
    }

    public float[] getFeatures() {
        return features;
    }

    public void setFeatures(float[] features) {
        this.features = features;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
