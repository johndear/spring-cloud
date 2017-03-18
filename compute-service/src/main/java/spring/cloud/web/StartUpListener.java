package spring.cloud.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import spring.cloud.jobs.SyncCallbackResultJob;

@SuppressWarnings("rawtypes")
@Service
public class StartUpListener implements ApplicationListener {
	
	Logger logger = Logger.getLogger(StartUpListener.class);
	
	@Autowired
	SyncCallbackResultJob syncCallbackResultJob;

	@Override
	public void onApplicationEvent(ApplicationEvent event) {

		if (event instanceof ContextRefreshedEvent) {
			logger.info("======================= spring容器初始化完毕 =========================");
			
			// 启动定时job
			syncCallbackResultJob.excute();
		}

	}

}
