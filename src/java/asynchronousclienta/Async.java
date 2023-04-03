package asynchronousclienta;

import com.listener.Listener;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class Async {
     

    @Resource(lookup = "jms/myConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = "jms/myTopic")
    private static Topic topic;
    private static Listener listener;

    public static void main(String[] args) throws JMSException {
        int count=0;

        Connection connection = connectionFactory.createConnection();
        connection.start();
        try {

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(topic);
            //TextMessage  message =session.createTextMessage();
            //Message message = consumer.receive();
            System.out.println("Listening..........");
//            listener = new Listener();
//            consumer.setMessageListener(listener);
            while (true) {
                Message m = consumer.receive(1);
                if (m != null) {
                    if (m instanceof TextMessage) {
                      TextMessage  message = (TextMessage) m;
                      count++;
                        
                        System.out.println("Book produced on: " + message.getText());
                        System.out.println("Total number of books"+ count);
                        
                        
                        
                    } else {
                        break;
                       
                    }
                }
            }

        } catch (JMSException e) {
            System.out.println(e.getMessage());
        }

    }

}
