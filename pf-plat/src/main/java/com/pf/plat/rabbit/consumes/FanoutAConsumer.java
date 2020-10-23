package com.pf.plat.rabbit.consumes;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = "fanout.a")
public class FanoutAConsumer {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    /**
     * 消息消费
     * @RabbitHandler 代表此方法为接受到消息后的处理方法
     */
    @RabbitHandler
    public void recieved(Object msg, Channel channel, Message message) throws IOException {
        System.out.println("[fanout.a] recieved message:" + msg);
        //最终进行消息确认处理的确认，将消息从队列中进行移除，完成消费
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
