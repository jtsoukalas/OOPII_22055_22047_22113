package firstDeliverable;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        // Test control.dummyData
        Control control = new Control();
        control.makeDummyData();

        System.out.println("-Start printing dummy data-\n"+control.cityLibraryToString()+"-End printing dummy data-\n");

        //Testing runPerceptron + recommend + retrieveCompatibleCities
        System.out.println("-Start printing dummy recommendation-");
        ArrayList temp;
        if ((temp = control.runPerceptron(16))!=null){
            System.out.println(temp);
        } else {
            System.out.println("There are no recommendations at the time.");
        }
        System.out.println("-End printing dummy recommendation-\n");
    }
}
