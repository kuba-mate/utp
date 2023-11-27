

import javax.naming.*;
import javax.jms.*;

public class Receiver {

    public static void main(String[] args) {

        Connection connection = null;

        try {

            Context context = new InitialContext();
            ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

            String admDestName = args[0];
            Destination destination = (Destination) context.lookup(admDestName);

            connection = factory.createConnection();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer receiver = session.createConsumer(destination);

            connection.start();

            System.out.println("Receiver is waiting...");
            Message message = receiver.receive();

            if (message instanceof TextMessage) {

                TextMessage text = (TextMessage) message;
                System.out.println("Received: " + text.getText());

            } else if (message != null) {
                System.out.println("No text");
            }


        } catch (Exception exc) {

            exc.printStackTrace();
            System.exit(1);

        } finally {

            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException exc) {
                    System.err.println(exc);
                }
            }

        }

        System.exit(0);

    }
}
