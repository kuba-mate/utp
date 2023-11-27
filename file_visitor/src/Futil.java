

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Futil {

    public static void processDir(String dirName, String resultName){

        Path fileDir= Paths.get(dirName);
        MyFileVisitor mfv=new MyFileVisitor();
        try {
            Files.walkFileTree(fileDir,mfv);
            List<String> data=mfv.getList();
            System.out.println(data);
            createFile(data,resultName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFile(List<String> data, String resultName) throws IOException {
        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(resultName), StandardCharsets.UTF_8));
            for ( String line : data){
                bw.write(line + "\n");
            }
            bw.close();
    }

}
