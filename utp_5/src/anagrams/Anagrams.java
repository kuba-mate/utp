/**
 *
 *  @author Matecki Jakub S24500
 *
 */

package anagrams;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public
    class Anagrams {

    private String fileName;
    private List<String> words=new ArrayList<>();
    List<List<String>> result=new ArrayList<>();

    public Anagrams(String fileName){
        this.fileName=fileName;
        try {
            readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFile() throws IOException {
        File file=new File(fileName);
        FileReader fr=new FileReader(file);
        StringBuilder sb=new StringBuilder();
        int x;
        while((x=fr.read())!=-1){
            sb.append((char)x);
        }
        String[] arr=sb.toString().split("\\s+");
        words= Arrays.asList(arr);
    }

    public boolean areWordsSimilar(String x, String y){
        TreeSet<String> setX=new TreeSet<>();
        TreeSet<String> setY=new TreeSet<>();
        setX.addAll(Arrays.asList(x.split("")));
        setY.addAll(Arrays.asList(y.split("")));
        return setX.equals(setY);
    }

    public List<List<String>> getSortedByAnQty(){
        List<String> tmp=words;

        while(tmp.size()>0){
            String pivot=tmp.get(0);
            List<String> newlist=new ArrayList<>();
            List<String> help=new ArrayList<>();
            for (int i = 0; i < tmp.size(); i++) {
                if(areWordsSimilar(pivot,tmp.get(i)))
                    newlist.add(tmp.get(i));
                 else
                    help.add(tmp.get(i));
            }
            tmp=help;
            result.add(newlist);
        }

        return result.stream().sorted((x, y) -> {
                    int difference = y.size() - x.size();
                    if (difference == 0)
                        return x.get(0).compareTo(y.get(0));
                        return difference;
                }).collect(Collectors.toList());
    }


    public String getAnagramsFor(String text) {
        boolean found = false;
        List<String> rightList=new ArrayList<>();
        List<String> tmp=new ArrayList<>();
        for (List<String> lists : result) {
            for (int i = 0; i < lists.size(); i++) {
                if (text.equals(lists.get(i))) {
                    found = true;
                    rightList = lists;
                }
            }
        }
        for(String element:rightList){
            if(!element.equals(text))
                tmp.add(element);
        }

        if(found)
            return text + ": " + tmp;
        else
            return null;
    }
}
