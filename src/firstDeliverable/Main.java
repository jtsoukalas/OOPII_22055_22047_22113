package firstDeliverable;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        // Test control.dummyData
        Control crl = new Control();
        crl.makeDummyData();

        System.out.println("Printing dummy data:");
        System.out.println(crl.cityLibraryToString());
    }

}
