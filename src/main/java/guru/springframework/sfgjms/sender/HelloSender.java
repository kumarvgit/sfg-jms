package guru.springframework.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage () {

        System.out.println("I am sending a message");

//        Prepare message
        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello World!")
                .build();
//        send to queue
        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
        System.out.println("Message sent");
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage () throws JMSException {

//        System.out.println("send and receive: I am sending and receiving message");
//
//        Prepare message
        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello World! send and receive")
                .build();
//        send to queue
        Message receivedMsg = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE,
                new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        Message helloMessage = null;
                        try {

                            helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                            helloMessage.setStringProperty("_type", "guru.springframework.sfgjms.model.HelloWorldMessage");
                            System.out.println("Sending... send and receive ");
                            return helloMessage;
                        } catch (JsonProcessingException e) {
//                            e.printStackTrace();
                            throw new JMSException("boom...");
                        }
                    }
                }
                );
        System.out.println("body:    " + receivedMsg.getBody(String.class));
    }
}
