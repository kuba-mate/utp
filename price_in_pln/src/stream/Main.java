/**
 *
 *  @author Matecki Jakub S24500
 *
 */

package stream;



import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    // Lista destynacji: port_wylotu port_przylotu cena_EUR 
    List<String> dest = Arrays.asList(
      "bleble bleble 2000",
      "WAW HAV 1200",
      "xxx yyy 789",
      "WAW DPS 2000",
      "WAW HKT 1000"
    );
    double ratePLNvsEUR = 4.30;
    List<String> result = dest.stream()
            .filter(x->x.substring(0,3).equals("WAW"))
            .map(x->{
                String[] arr=x.split(" ");
                double help=Integer.parseInt(arr[2])*ratePLNvsEUR;
                return "to " + arr[1] + " - price in PLN:" + "\t" + (int)help;
            }).collect(Collectors.toList());



    for (String r : result) System.out.println(r);
  }
}