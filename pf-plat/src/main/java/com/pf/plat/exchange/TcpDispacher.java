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

	@Resource(name = "platformGeneralService")
	private IPlatformGeneralService platformGeneralService;

    /**
     *
     * @Title: messageRecived
     * @Description: 处理分发
     * @modifyLog：
     */
    public void messageRecived(String data) {
    	log.info("接受应答的data:::::"+data);
    	if( StringUtils.isBlank(data)) {
    		log.info("\r\n非json格式数据{}", data); return;
    	}
    	this.platformGeneralService.receiptNotice(data);
    }

}
