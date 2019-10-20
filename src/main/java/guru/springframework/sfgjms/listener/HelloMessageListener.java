package guru.springframework.sfgjms.listener;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
public class HelloMessageListener {

    /**
     * The method name can be anything.
     * @param helloWorldMessage
     * @param headers
     * @param message message is of type javax.message this import can be anything as long as it obeys the protocol
     *
     * Here is this example we are sending the spring type message but receiving javax type of message, that's okay
     */
    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers,
                       Message message) {
        System.out.println("I have got a message");
//        investigate the properties
        System.out.println(helloWorldMessage);

//        This is going to increase redelivery count and this is more like the transactional where
//        if message is not delivered we are going to come to know
//        throw new RuntimeException();

    }
}
