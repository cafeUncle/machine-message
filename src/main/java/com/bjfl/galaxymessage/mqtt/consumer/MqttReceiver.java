package com.bjfl.galaxymessage.mqtt.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class MqttReceiver {

    /**
     * 测试通信服务mq连接
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("test:" + message.getPayload());
            }

        };
    }

    /**
     * 货道查询
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "cellStatusInputChannel")
    public MessageHandler cellStatusHandler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("cellStatusInputChannel:" + message.getPayload());
            }

        };
    }

    /**
     * 整体出货   要求先查询，再出货
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "shipmentInputChannel")
    public MessageHandler shipmentHandler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("shipmentInputChannel:" + message.getPayload());
                Object payload = message.getPayload();

            }

        };
    }

    /**
     * 出货结果
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "shipmentResultInputChannel")
    public MessageHandler shipmentResultHandler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("shipmentResultInputChannel:" + message.getPayload());
            }

        };
    }


}
