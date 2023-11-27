/**
 *
 *  @author Matecki Jakub S24500
 *
 */



import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Service {

    private String countryName;
    private String cityName="Warsaw";
    private Currency currencyName;

    private final String weatherKey = "e42e4ff55be0b9988d09cbae95ac3aad";
    private final String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?lat=";
    private final String exchangeUrl = "https://api.exchangerate.host/convert?from=%s&to=%s";

    public Service(String countryName, String cityName){
        this.countryName=countryName;
        this.cityName=cityName;
        this.currencyName=getCurrency();

    }

    public Service(String countryName){
        this.countryName=countryName;
        this.currencyName=getCurrency();

    }

    public String getWeather(String city){

        String api=null;
        try {
            api = new Scanner(new URL("https://api.openweathermap.org/geo/1.0/direct?q="+city+"&limit=1&appid=" + weatherKey).openStream(),
                    String.valueOf(StandardCharsets.UTF_8)).useDelimiter("\\A").next();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject obj=null;
        try {
            JSONParser jparserer= new JSONParser();
            obj = (JSONObject) ((JSONArray)jparserer.parse(api)).get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String jsonWeather=null;
        try {
            jsonWeather = new Scanner(new URL(weatherUrl+(obj.get("lat"))+"&lon="+(obj.get("lon"))+"&appid=" + weatherKey).openStream(), "UTF-8").useDelimiter("\\A").next();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonWeather;
    }


    public JSONObject getWeatherToObject(String city) {
        String data = this.getWeather(city);
        JSONParser parser=new JSONParser();
        JSONObject weatherObj=null;
        try {
            weatherObj = (JSONObject) (parser.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return weatherObj;
    }

    public Double getRateFor(String currency) {


        String url=String.format(exchangeUrl, currency, currencyName.getCurrencyCode());
        String data=null;

        try {
            data=makeRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }


        StringBuilder sb=new StringBuilder();
        boolean go=true;
        boolean bylaCyfra=true;
        boolean bylaJedynka=false;

        for (int i = 0; i < data.length() && go; i++) {
            bylaCyfra=false;
            if(data.charAt(i)=='1' && !bylaJedynka)
                bylaJedynka=true;
            else if((data.charAt(i)>='0' && data.charAt(i)<='9') || (sb.toString().length() > 0 && data.charAt(i)=='.')){
                sb.append(data.charAt(i));
                bylaCyfra=true;
            }
            if(!bylaCyfra && sb.toString().length()>0)
                go=false;
        }
        return Double.parseDouble(sb.toString());

    }

    public Double getNBPRate(){
        return 1/getRateFor("PLN");
    }

    private Currency getCurrency(){
        Locale langEnglish  = new Locale.Builder().setLanguage("en"/*English*/).build();
        for (Locale locale: Locale.getAvailableLocales()) {
            if (locale.getDisplayCountry(langEnglish).equals(countryName)) {
                return Currency.getInstance(locale);
            }
        }
        return null;
    }


     private String makeRequest(String requestUrl) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = connection.getInputStream();
        String text = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));

        connection.disconnect();

        return text;
    }


    public Currency getCurrencyName() {
        return currencyName;
    }

    public String getCityName(){
        return cityName;
    }

    public String getCountryName(){
        return countryName;
    }



}
