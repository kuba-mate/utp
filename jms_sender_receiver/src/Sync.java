

import javax.naming.*;
import javax.jms.*;
import javax.swing.*;
import java.awt.event.*;



public class Sync
        extends JFrame
        implements MessageListener {


    private Connection connection;

    private JTextArea area = new JTextArea(10, 20);

    int index =0;
    public void onMessage(Message message) {

        this.setTitle("Received message " + ++index);
        try {

            area.append(((TextMessage) message).getText() + "\\\\n");

        } catch(JMSException exception) {
            System.err.println(exception);
        }
    }

    public Sync(String destination) {


        try {

            Context context = new InitialContext();
            ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

            Destination dest = (Destination) context.lookup(destination);
            connection = factory.createConnection();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageConsumer receiver = session.createConsumer(dest);
            receiver.setMessageListener(this);

            connection.start();

        } catch (Exception exc) {


            exc.printStackTrace();
            System.exit(1);
        }


        this.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                try {
                    connection.close();
                } catch (Exception exception) {

                }

                dispose();
                System.exit(0);
            }

        }
        );


        this.setTitle("Waiting...");
        this.pack();
        this.setLocationRelativeTo(null);
    }



    public static void main(String[] args) {

        new Sync("kolejka");

    }




}

