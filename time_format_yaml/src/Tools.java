/**
 *
 *  @author Matecki Jakub S24500
 *
 */




import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;


public class Tools {



    public static Options createOptionsFromYaml(String path) throws FileNotFoundException {

        Yaml yaml = new Yaml();
        Map<String, Object> loadingMap = yaml.load(new FileInputStream(path));

        return new Options(
                loadingMap.get("host").toString(),
                Integer.parseInt(loadingMap.get("port").toString()),
                Boolean.parseBoolean(loadingMap.get("concurMode").toString()),
                Boolean.parseBoolean(loadingMap.get("showSendRes").toString()),
                (Map<String, List<String>>) loadingMap.get("clientsMap")
        );


    }


}
