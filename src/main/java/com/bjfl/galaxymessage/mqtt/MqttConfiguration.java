package com.bjfl.galaxymessage.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;

@Configuration
@PropertySource(value = "classpath:mqtt.properties")
public class MqttConfiguration {

    @Value("${serverUri}")
    private String serverUri;
    @Value("${mq.username}")
    private String username;
    @Value("${mq.password}")
    private String password;
    @Value("${qos}")
    private Integer qos;
    @Value("${completionTimeout}")
    private Integer completionTimeout;


    @Value("${testInBound.clientId}")
    private String testInBoundClientId;
    @Value("${testInBound.topic}")
    private String testInBoundTopic;

    @Value("${cellStatusInBound.clientId}")
    private String cellStatusInBoundClientId;
    @Value("${cellStatusInBound.topic}")
    private String cellStatusInBoundTopic;

    @Value("${shipmentInBound.clientId}")
    private String shipmentInBoundClientId;
    @Value("${shipmentInBound.topic}")
    private String shipmentInBoundTopic;

    @Value("${shipmentResultInBound.clientId}")
    private String shipmentResultInBoundClientId;
    @Value("${shipmentResultInBound.topic}")
    private String shipmentResultInBoundTopic;

    @Value("${mqtt.sender.clientId}")
    private String sendClientId;

    /**
     * 消息通道
     *
     * @return
     */
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    /**
     * mqtt服务器配置
     *
     * @return
     */
    @Bean
    public MqttPahoClientFactory clientFactory() {
        DefaultMqttPahoClientFactory clientFactory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setServerURIs(new String[]{serverUri});
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());
        mqttConnectOptions.setAutomaticReconnect(true);
        clientFactory.setConnectionOptions(mqttConnectOptions);
        return clientFactory;
    }

    @Bean
    public MqttClient mqttClient() throws MqttException {
        MqttClient mqttClient = new MqttClient(serverUri, sendClientId);
        MqttConnectOptions connOptions = new MqttConnectOptions();
        connOptions.setUserName(username);
        connOptions.setPassword(password.toCharArray());
        connOptions.setAutomaticReconnect(true);
        mqttClient.connect(connOptions);
        return mqttClient;
    }

    /**
     * 通道适配器（给inputChannel注册通道适配器）
     *
     * @param clientFactory
     * @param mqttInputChannel
     * @return
     */
    private MqttPahoMessageDrivenChannelAdapter createAdapter(MqttPahoClientFactory clientFactory, MessageChannel mqttInputChannel, String clientId, String topic) {
        //clientId 客户端ID，生产端和消费端的客户端ID需不同，不然服务器会认为是同一个客户端，会接收不到信息
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(clientId, clientFactory, topic);
        //超时时间
        adapter.setCompletionTimeout(completionTimeout);
        //转换器
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(qos);
        adapter.setOutputChannel(mqttInputChannel);
        return adapter;
    }

    @Bean
    public MessageProducer inbound(MqttPahoClientFactory clientFactory, MessageChannel mqttInputChannel) {
        return createAdapter(clientFactory, mqttInputChannel, testInBoundClientId, testInBoundTopic);
    }

    @Bean
    public MessageProducer cellStatusInBound(MqttPahoClientFactory clientFactory, @Qualifier("cellStatusInputChannel") MessageChannel mqttInputChannel) {
        return createAdapter(clientFactory, mqttInputChannel, cellStatusInBoundClientId, cellStatusInBoundTopic);
    }

    @Bean
    public MessageProducer shipmentInBound(MqttPahoClientFactory clientFactory, @Qualifier("shipmentInputChannel") MessageChannel mqttInputChannel) {
        return createAdapter(clientFactory, mqttInputChannel, shipmentInBoundClientId, shipmentInBoundTopic);
    }

    @Bean
    public MessageProducer shipmentResultInBound(MqttPahoClientFactory clientFactory, @Qualifier("shipmentResultInputChannel") MessageChannel mqttInputChannel) {
        return createAdapter(clientFactory, mqttInputChannel, shipmentResultInBoundClientId, shipmentResultInBoundTopic);
    }


}
