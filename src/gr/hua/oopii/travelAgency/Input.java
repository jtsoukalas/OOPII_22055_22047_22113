package gr.hua.oopii.travelAgency;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Input {

    public static int readInt(String errorMessage){
        int answer=-1;
        boolean retry;
        Scanner scanner = new Scanner (System.in);

        do{
            try{
                answer= scanner.nextInt();
                retry=false;
            } catch (Exception e){
                System.err.print(errorMessage+"\nPlease try again:");
                scanner.next();
                retry=true;
            }
        } while (retry);

        scanner.close();
        return answer;
    }

    public static boolean readBoolean() {
        boolean retry;
        boolean answer = false;

        Scanner scanner = new Scanner(System.in);
        do {
            try {
                answer = scanner.nextBoolean();
                retry = false;
            } catch (InputMismatchException e) {
                System.err.println("Be careful true or false needed. Please try again: ");
                scanner.next();
                retry = true;
            }
        } while (retry);
        scanner.close();
        return answer;
    }

    public static int readAge() {
        boolean retry;
        int age;

        do {
            age = Input.readInt("Be careful age should be integer");
            retry = false;
            if (age < 15 || age > 115) {
                System.err.println("Error! The program accepts ages from 15 to 115. Please try again.");
                retry = true;
            }
        } while (retry);
        return age;
    }
}
