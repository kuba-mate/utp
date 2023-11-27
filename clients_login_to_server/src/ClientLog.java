

public class ClientLog {

    private String id;
    private StringBuilder log;

    public ClientLog(String id) {
        this.id = id;
        this.log = new StringBuilder();
    }

    public void buildString(String add){
        log.append(add);
    }

    public String getId(){
        return id;
    }

    public String getLog(){
        return log.toString();
    }

}