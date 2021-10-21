package firstDeliverable;

import java.lang.reflect.Array;
import java.util.Random;


public class Control {

    private City CitiesLibrary[];

    public void dummyData() {
        int cityAmount = 15;
        Random rand = new Random();

        CitiesLibrary = new City[cityAmount];

        String CityNames[] = new String[]{"Athens", "London", "Brussels", "Madrid", "New York", "Paris", "Berlin", "Stockholm", "Tokyo", "Rio", "Denver", "Rome", "Naples", "Constantinople", "Moscow"};

        for (int i = 0; i < cityAmount; i++)

            CitiesLibrary[i] = new City(new float[]{(float) rand.nextInt(10), (float) rand.nextInt(10), (float) rand.nextInt(10), (float) rand.nextInt(10),
                    (float) rand.nextInt(10), (float) rand.nextInt(10), (float) rand.nextInt(10), rand.nextFloat() + rand.nextInt(146) + 184, rand.nextFloat() + rand.nextInt(99),
                    rand.nextFloat() + rand.nextInt(9522)}, CityNames[i]);       //in miles


    }


}
