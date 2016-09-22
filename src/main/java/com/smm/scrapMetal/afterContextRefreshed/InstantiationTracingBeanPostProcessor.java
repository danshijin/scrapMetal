package com.smm.scrapMetal.afterContextRefreshed;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.smm.scrapMetal.bo.IWebSocketBO;
import com.smm.scrapMetal.dao.IChatRecordsDao;

/**
* @author  zhaoyutao
* @version 2016年2月29日 上午9:19:45
* 所有的bean初始化完成后的监听类
*/
@Service
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent>{
	
	private Logger logger = Logger.getLogger(InstantiationTracingBeanPostProcessor.class);
	
	@Resource
	IChatRecordsDao iChatRecordsDao;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		IWebSocketBO.notReadVector.addAll(iChatRecordsDao.countIsNotReadGroupByChatId());
		logger.info("程序启动了，未读列表为：" + IWebSocketBO.notReadVector);
	}
}
