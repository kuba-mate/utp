

public class StringTask
    implements Runnable{

    private String text;
    private int times;
    private volatile TaskState state;
    private Thread thread;
    private volatile String result="";

    public StringTask(String text, int times){
        state=TaskState.CREATED;
        this.text=text;
        this.times=times;
    }

    public void start(){
        state=TaskState.RUNNING;
        thread=new Thread(this);
        thread.start();
    }

    public void abort(){
        state=TaskState.ABORTED;
        thread.interrupt();
    }

    @Override
    public void run() {
        for (int i = 0; i < times; i++) {
            result=result+text;
            if(Thread.interrupted()){
                break;
            }
        }
        state=TaskState.READY;
    }

    public TaskState getState(){
        return state;
    }

    public String getResult(){
        return result;
    }

    public boolean isDone(){
        return state==TaskState.ABORTED || state==TaskState.READY;
    }

}