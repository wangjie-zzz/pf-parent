//package com.pf.system.rabbit;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class QueueMessageServiceImpl implements RabbitTemplate.ReturnCallback
//{
//    /**
//     * 消息队列模板
//     */
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//
//    /**
//    * @Title: exchange模式
//    * @Param:
//    * @description:
//    * @author: wangjie
//    * @date: 2020/10/15 11:45
//    * @return:
//    * @throws:
//    */
//    public void send(Object message, String exchange, String routingKey) throws Exception {
//        this.rabbitTemplate.setReturnCallback(this);
//        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
//            if (!ack) {
//                System.out.println("HelloSender消息消费失败" + cause + correlationData.toString());
//            } else {
//                System.out.println("HelloSender 消息消费成功 ");
//            }
//        });
//        //发送消息到消息队列
//        rabbitTemplate.convertAndSend(exchange,routingKey,message);
//    }
//
//    /**
//    * @Title: 简单/工作队列模式
//    * @Param:
//    * @description:
//    * @author: wangjie
//    * @date: 2020/10/15 11:45
//    * @return:
//    * @throws:
//    */
//    public void send(Object message, String routeKey) {
//        this.rabbitTemplate.setReturnCallback(this);
//        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
//            if (!ack) {
//                System.out.println("HelloSender消息消费失败" + cause + correlationData.toString());
//            } else {
//                System.out.println("HelloSender 消息消费成功 ");
//            }
//        });
//        rabbitTemplate.convertAndSend(routeKey, message);
//    }
//
//    @Override
//    public void returnedMessage(Message message, int i, String arg2, String arg3, String arg4) {
//        log.info("sender return success" + message.toString()+"==i=="+i+"==3=="+arg2+"===3="+arg3+"==4=="+arg4);
//    }
//}
