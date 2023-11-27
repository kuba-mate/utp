

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public
    class Buffer {

    private int przeczytaneTowary = 0;
    private int sumowaneTowary = 0;
    private double suma = 0;
    private List<Towar> list = new ArrayList<>();
    private final String filename  =  "../Towary.txt";
    private StringBuilder sb=new StringBuilder();
    private String[] arr;
    private boolean done=false;
    private boolean thatsall=false;

    public synchronized void read() throws IOException, InterruptedException {

        FileReader fr=new FileReader(filename);
        int x;
        while((x=fr.read())!=-1) {
            if (x != 10) {
                sb.append((char) x);
            } else {
                while(sumowaneTowary != przeczytaneTowary)
                    wait();
                dodajTowar();
            }
        }
        dodajTowar();
        thatsall=true;
    }

    public synchronized void sum() throws InterruptedException {
        while(sumowaneTowary == przeczytaneTowary)
            wait();

        suma=suma+list.get(przeczytaneTowary-1).getWaga();
        sumowaneTowary++;

        if(sumowaneTowary%100==0)
            System.out.println("policzono wage " + sumowaneTowary + " towarów");
        if(thatsall && sumowaneTowary==przeczytaneTowary) {
            System.out.println(suma);
            done=true;
        }

        notify();

    }

    public boolean isDone(){
        return done;
    }

    public void dodajTowar(){
        arr = sb.toString().split(" ");
        list.add(new Towar(Integer.parseInt(arr[0]), Double.parseDouble(arr[1])));
        sb.delete(0, sb.toString().length());
        przeczytaneTowary++;
        if (przeczytaneTowary % 200 == 0)
            System.out.println("utworzono " + przeczytaneTowary + " obiektów");
        notify();
    }

}
