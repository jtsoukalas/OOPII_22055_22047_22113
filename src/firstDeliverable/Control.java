package firstDeliverable;

import java.lang.reflect.Array;
import java.util.Random;


public class Control {

    private City citiesLibrary[];

    public void makeDummyData() {
        int cityAmount = 15;
        Random rand = new Random();

        citiesLibrary = new City[cityAmount];
        String CityNames[] = new String[]{"Athens", "London", "Brussels", "Madrid", "New York", "Paris", "Berlin",
                                            "Stockholm", "Tokyo", "Rio", "Denver", "Rome", "Naples", "Constantinople", "Moscow"};

        for (int i = 0; i < cityAmount; i++) {
            citiesLibrary[i] = new City(new float[]{(float) rand.nextInt(10), (float) rand.nextInt(10),
                                                    (float) rand.nextInt(10), (float) rand.nextInt(10),
                                                    (float) rand.nextInt(10), (float) rand.nextInt(10),
                                                    (float) rand.nextInt(10),
                                                    rand.nextFloat() + rand.nextInt(146) + 184,                  //Temperature in Kelvin
                                                    rand.nextFloat() + rand.nextInt(99),                         //Weather Condition
                                                    rand.nextFloat() + rand.nextInt(9522)}, CityNames[i]);       //geodesic distance in miles
        }
    }

    public City[] getCitiesLibrary() {
        return citiesLibrary;
    }

    public void setCitiesLibrary(City[] citiesLibrary) {
        citiesLibrary = citiesLibrary;
    }

    public String cityLibraryToString(){
        String returnCityCatalogue="";
        for(int i = 0; i < citiesLibrary.length; i++){
            returnCityCatalogue+= citiesLibrary[i].toString() +"\n";
        }
        return returnCityCatalogue;
    }
}
