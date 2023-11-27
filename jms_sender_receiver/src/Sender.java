

import javax.naming.*;
import javax.jms.*;


public class Sender {

    public static void main(String[] args) {

        Connection connection = null;

        try {

            Context context = new InitialContext();
            ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

            String destFile = args[0];
            Destination destination = (Destination) context.lookup(destFile);

            connection = factory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // false - bez transakcyjnosci
            // AUTO_ACKNOWLEDGE - automatyczne potwierdzanie

            MessageProducer sender = session.createProducer(destination);

            connection.start();

            TextMessage textMessage = session.createTextMessage();

            textMessage.setText(args[1]);
            sender.send(textMessage);

            System.out.println("Sender is sending info: " + args[1]);


        } catch (Exception exc) {

            exc.printStackTrace();
            System.exit(1);

        } finally {

            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException exception) {
                    System.err.println(exception);
                }

            }
        }

        System.exit(0);

    }


}

