package com.pf.plat.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

/**
 * 功能描述：
 * 性能排序：fanout > direct >> topic。比例大约为11：10：6
 * RabbitMQ提供了6种模式，分别是HelloWorld，Work Queue，Publish/Subscribe，Routing，Topics，RPC Request/reply，
 * 其中Publish/Subscribe，Routing，Topics三种模式可以统一归为Exchange模式，只是创建时交换机的类型不一样，分别是fanout、direct、topic。
 * @修改日志：
 */
@Configuration
public class RabbitConfig implements RabbitListenerConfigurer {

    //=================== direct 模式 ：路由匹配上的就可以收到消息 ====================
    private final static String ROUTE_1 = "string";
    @Bean
    public Queue string() {
        return new Queue("string");
    }
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange", true, false);
    }
    @Bean
    public Binding bindingDirectExchangeWithString() {
        return BindingBuilder.bind(string()).to(directExchange()).with(ROUTE_1);
    }
    //=================== fanout 模式 ：广播消息，那个队列绑定该交换机，就可以收到消息 ====================
    @Bean
    public Queue fanoutA() {
        return new Queue("fanout.a");
    }
    @Bean
    public Queue fanoutB() {
        return new Queue("fanout.b");
    }
    @Bean
    public Queue fanoutC() {
        return new Queue("fanout.c");
    }
    @Bean
    FanoutExchange fanoutExchange() {
        // 定义一个名为fanoutExchange的fanout交换器
        return new FanoutExchange("fanoutExchange");
    }
    @Bean
    public Binding bindingExchangeWithA() {
        return BindingBuilder.bind(fanoutA()).to(fanoutExchange());
    }
    @Bean
    public Binding bindingExchangeWithB() {
        return BindingBuilder.bind(fanoutB()).to(fanoutExchange());
    }
    @Bean
    public Binding bindingExchangeWithC() {
        return BindingBuilder.bind(fanoutC()).to(fanoutExchange());
    }

  //=================== topic模式====================
    @Bean
    public Queue topiocA() {
        return new Queue("topic.a");
    }
    @Bean
    public Queue topicB() {
        return new Queue("topic.b");
    }
    @Bean
    public Queue topicC() {
        return new Queue("topic.c");
    }
//    topicA的key为topic.msg 那么他只会接收包含topic.msg的消息
//    topicB的key为topic.#那么他只会接收topic开头的消息
//    topicC的key为topic.*.Z那么他只会接收topic.B.z这样格式的消息
    @Bean
    TopicExchange topicExchange() {
        // 定义一个名为fanoutExchange的fanout交换器
        return new TopicExchange("topicExchange");
    }
    @Bean
    public Binding bindingTopicExchangeWithA() {
        return BindingBuilder.bind(topiocA()).to(topicExchange()).with("topic.msg");
    }
    @Bean
    public Binding bindingTopicExchangeWithB() {
        return BindingBuilder.bind(topicB()).to(topicExchange()).with("topic.#");
    }
    @Bean
    public Binding bindingTopicExchangeWithC() {
        return BindingBuilder.bind(topicC()).to(topicExchange()).with("topic.*.z");
    }
    
    
    @Bean
    @Primary
    @Scope("prototype")
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    @Primary
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
    
    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }
    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }
    
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }
}
