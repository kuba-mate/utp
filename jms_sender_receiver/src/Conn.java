

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;

public class Conn {

    public static void connect(Connection connection) throws NamingException, JMSException {

        Context context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        String[] arr = {"Sender"};
        String admDestName = arr[0];
        Destination destination = (Destination) context.lookup(admDestName);

        connection = (Connection) factory.createConnection();

        Session session = ((javax.jms.Connection) connection).createSession(false, Session.AUTO_ACKNOWLEDGE);

    }

}
