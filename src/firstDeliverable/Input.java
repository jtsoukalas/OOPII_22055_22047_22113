package firstDeliverable;

import java.util.Scanner;

public abstract class Input {

    public static int readInt(String errorMessage){
        int answer=-1;
        boolean retry;
        Scanner input = new Scanner (System.in);

        do{
            try{
                answer=input.nextInt();
                retry=false;
            } catch (Exception e){
                System.err.print(errorMessage+"\nPlease try again:");
                input.next();
                retry=true;
            }
        } while (retry);

        return answer;
    }
}
