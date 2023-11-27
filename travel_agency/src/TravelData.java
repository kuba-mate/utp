


import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;


public
    class TravelData {

    List<Travel> list;

    public TravelData(File dataDir){

        list=new ArrayList<>();
        File[] files=dataDir.listFiles();
        for(File file : files)
            readFromFile(file);


    }

    public void readFromFile(File dataDir){
        try {
            boolean go=true;
            FileReader fr=new FileReader(dataDir);
            StringBuilder sb=new StringBuilder();
            String[] arr;
            int x;
            while((x=fr.read())!=-1 || go){
                if(x==10 || x==-1){
                    arr=sb.toString().split("\t");
                    list.add(new Travel(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5],arr[6]));
                    sb.delete(0,sb.toString().length());
                } else {
                    sb.append((char)x);
                }
                if(x==-1){
                    go=false;
                }
            }
        } catch (IOException | ParseException e) {
            return;
        }
    }

    List<String> getOffersDescriptionsList(String loc, String dateFormat) {
        List<String> result=new ArrayList<>();
        Locale locale=Locale.forLanguageTag(loc.replace("_","-"));
        NumberFormat numberFormat=NumberFormat.getInstance(locale);
        SimpleDateFormat format=new SimpleDateFormat(dateFormat);
        String wynik;

        for (Travel travel : list){

            wynik = Dictionary.translateCountryName(travel.getLoc(), locale, travel.getCountry()) + " " +
                    format.format(travel.getDateFrom()) + " " +
                    format.format(travel.getDateTo()) + " " +
                    Dictionary.translateWord(travel.getLoc(),locale,travel.getPlace()) + " " +
                    numberFormat.format(travel.getPrice()) + " " +
                    travel.getCurrency();
            result.add(wynik);
        }
        return result;
    }

    public List<Travel> getList() {
        return list;
    }

}
