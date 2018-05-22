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
     * 查询货道状态
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
     * 整体出货
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
     * 获取出货结果
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

    /**
     * 获取出货日志
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "shipmentLogInputChannel")
    public MessageHandler shipmentLogHandler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("shipmentLogInputChannel:" + message.getPayload());
            }

        };
    }

    /**
     * 前置货梯测试
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "preposeMotorCaseInputChannel")
    public MessageHandler preposeMotorCaseHandler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("preposeMotorCaseInputChannel:" + message.getPayload());
            }

        };
    }

    /**
     * 前置货梯归位
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "preposeMotorHomeInputChannel")
    public MessageHandler preposeMotorHomeHandler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("preposeMotorHomeInputChannel:" + message.getPayload());
            }

        };
    }

    /**
     * 后置推手测试
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "coorDinateCaseGoodInputChannel")
    public MessageHandler coorDinateCaseGoodHandler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("coorDinateCaseGoodInputChannel:" + message.getPayload());
            }

        };
    }

    /**
     * 后置推手归位
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "coorDinateHomeInputChannel")
    public MessageHandler coorDinateHomeHandler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("coorDinateHomeInputChannel:" + message.getPayload());
            }
        };
    }

    /**
     * 出货测试
     */
    @Bean
    @ServiceActivator(inputChannel = "goodsSellCaseInputChannel")
    public MessageHandler goodsSellCaseHandler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("goodsSellCaseInputChannel:" + message.getPayload());
            }
        };
    }

    /**
     * 复位
     */
    @Bean
    @ServiceActivator(inputChannel = "goodsResetInputChannel")
    public MessageHandler goodsResetHandler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("goodsResetInputChannel:" + message.getPayload());
            }
        };
    }

}
