package A1;

import java.util.Random;

public class Payload {


   public static int GetTheRandomNumeber() {

      int max = 25000;
      int min = 1;

      Random rand = new Random();
      return rand.nextInt();
      // int radomNumber = rand.nextInt((max - min) + 1) + min;
      //
      // return radomNumber;

   }

}
