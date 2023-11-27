/**
 *
 *  @author Matecki Jakub S24500
 *
 */

package functions_1;





import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;


public class Main {
  public static void main(String[] args) {

    Function<String, List<String>> flines = (fileName) -> {
            List<String> list = new ArrayList<>();
            try (Stream<String> s = Files.lines(Paths.get(fileName))) {
                s.forEach(list::add);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        };

    Function<List<String>,String> join= (list) -> {
      String result="";
      for(String x:list){
          result=result+x;
      }
      return result;
    };

    Function<String, List<Integer>> collectInts=(result) -> {
      List<Integer> intList=new ArrayList<>();
      StringBuilder sb=new StringBuilder();
      char[] arr=result.toCharArray();
      int licznik=0;
      for (int i = 0; i < arr.length; i++) {
            if(arr[i] >= '0' && arr[i] <= '9'){
                  sb.append(arr[i]);
                  licznik++;
            } else if(licznik>0){
                  intList.add(Integer.parseInt(sb.toString()));
                  licznik=0;
                  sb.delete(0,sb.length());
            }
      }
      if(licznik>0)
        intList.add(Integer.parseInt(sb.toString()));
      return intList;
    };

    Function<List<Integer>, Integer> sum=(intList) -> {
        int suma=0;
        for (int x:intList){
            suma=suma+x;
        }
        return suma;
    };

    String fname = System.getProperty("user.home") + "/LamComFile.txt"; 
    InputConverter<String> fileConv = new InputConverter<>(fname);
    List<String> lines = fileConv.convertBy(flines);
    String text = fileConv.convertBy(flines, join);
    List<Integer> ints = fileConv.convertBy(flines, join, collectInts);
    Integer sumints = fileConv.convertBy(flines, join, collectInts, sum);

    System.out.println(lines);
    System.out.println(text);
    System.out.println(ints);
    System.out.println(sumints);

    List<String> arglist = Arrays.asList(args);
    InputConverter<List<String>> slistConv = new InputConverter<>(arglist);
    sumints = slistConv.convertBy(join, collectInts, sum);
    System.out.println(sumints);


  }
}
