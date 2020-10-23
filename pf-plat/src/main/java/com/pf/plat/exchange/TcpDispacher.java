package com.pf.plat.exchange;

import com.pf.plat.service.IPlatformGeneralService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class TcpDispacher {

	/*@Autowired
    private AmqpTemplate amqpTemplate;*/

	@Resource(name = "platformGeneralService")
	private IPlatformGeneralService platformGeneralService;

    /**
     *
     * @Title: messageRecived
     * @Description: 处理分发
     * @modifyLog：
     */
    public void messageRecived(List<String> extendNo, String matchingRule,String data) {
    	log.info("接受应答的data:::::"+data);
    	if( CollectionUtils.isEmpty(extendNo) ) {
    		log.info("\r\n当前socket客户端未设置通知对象标识"); return;
    	}
    	if( StringUtils.isBlank(data)) {
    		log.info("\r\n非json格式数据{}", data); return;
    	}
    	this.platformGeneralService.receiptNotice(extendNo, matchingRule, data);
    	/*MessageNotice messageNotice = new MessageNotice(extendNo,data);
    	this.amqpTemplate.convertAndSend(RabbitDirectConfig.PLAT_NOTICE_EXCHANGE ,RabbitDirectConfig.PLAT_NOTICE_KEY , messageNotice);*/
    }

    /**
     *
     * @Title: receiverDirectFieldLogsQueue
     * @Description: 消息通知
     * @modifyLog：
     */
    /*@RabbitListener(queues = RabbitDirectConfig.PLAT_NOTICE_QUEUE)
    public void receiverDirectPlatNoticeQueue(MessageNotice messageNotice ) {
    	log.info("\r\n成功接收代理消息：{}", messageNotice);
    	this.receiptNotice(messageNotice);
    }*/

    /**
     *
     * @Title: receiptNotice
     * @Description:
     * @modifyLog：
     */
    /*private void receiptNotice(MessageNotice messageNotice ) {
    	this.platformGeneralService.receiptNotice(messageNotice.getExtendNo(), messageNotice.getData());
    }*/

}
