

import javax.naming.*;
import javax.jms.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Connection connection = null;

        try {

            Context context = new InitialContext();
            ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

            String admTopicName = args[0];
            String subscriptionName = args[1];

            Topic topic = (Topic) context.lookup(admTopicName);
            connection = factory.createConnection();


            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            TopicSubscriber subs = session.createDurableSubscriber(topic, subscriptionName);

            connection.start();

            while (true) {

                Message message = subs.receiveNoWait();
                if (message == null) {
                    System.out.println("Press ctrl-c to stop!");

                    Thread.sleep(8000);
                }

                else printMessage(message);
            }


        } catch (Exception exception) {


            exception.printStackTrace();
            System.exit(1);

        } finally {

            if (connection != null) {
                try {

                    connection.close();

                } catch (JMSException excexption) {
                    System.err.println(excexption);
                }
            }

        }
        System.exit(0);
    }



    private static void printMessage(Message message) {
        if (message instanceof TextMessage) {

            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println("Received: " + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }

        } else if (message != null) {

            System.out.println("There is no text");

        }
    }












}








