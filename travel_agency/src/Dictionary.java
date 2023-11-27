

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

public
    class Dictionary {

    static Properties dictionary;

    public static void createDictionary(){
        try {
            FileInputStream input=new FileInputStream("dictionary.properties");
            dictionary=new Properties();
            dictionary.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String translateCountryName(Locale input, Locale output, String word) {
        createDictionary();
        for(Locale locale : Locale.getAvailableLocales()){
            if(locale.getDisplayCountry(input).equals(word))
                return locale.getDisplayCountry(output);
        }
        return null;
    }

    public static String translateWord(Locale input, Locale output, String word){
        createDictionary();
        return dictionary.getProperty(input.getLanguage() + "->" + output.getLanguage() + "." + word,word);
    }
}
