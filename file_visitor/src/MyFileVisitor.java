

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class MyFileVisitor extends SimpleFileVisitor<Path> {

    private List<String> list=new ArrayList<>();


    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs){
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if(attrs.isRegularFile()){
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file.toString()), "cp1250"));
            String x;
            while((x = in.readLine()) != null){
                list.add(x);
            }
            in.close();
        }
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult visitFileFailed(Path file, IOException exc){
        return FileVisitResult.CONTINUE;
    }
    public List<String> getList(){
        return list;
    }

}
