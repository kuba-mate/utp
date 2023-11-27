/**
 *
 *  @author Matecki Jakub S24500
 *
 */



import java.io.IOException;

public class Main {

  public static void main(String[] args) {

      Buffer buffer=new Buffer();
      Thread reader=new Thread(()->{
          while(!buffer.isDone()) {
              try {
                  buffer.read();
              } catch (IOException e) {
                  break;
              } catch (InterruptedException e) {
                  break;
              }
          }
      });

      Thread adder=new Thread(()->{
          while(!buffer.isDone()) {
              try {
                  buffer.sum();
              } catch (InterruptedException e) {
                  break;
              }
          }
          reader.interrupt();
      });

      reader.start();
      adder.start();




  }
}
