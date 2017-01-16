package spring.cloud.web;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import spring.cloud.jobs.SyncCallbackResultJob;

@Component
public class SpringUtil implements ApplicationContextAware {
	
	@Autowired
	SyncCallbackResultJob syncCallbackResultJob;

	private static ApplicationContext applicationContext = null;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {

		if (SpringUtil.applicationContext == null) {

			SpringUtil.applicationContext = applicationContext;

		}
		
		syncCallbackResultJob.get();

	}

	// 获取applicationContext
	public static ApplicationContext getApplicationContext() {
		return applicationContext;

	}

	// 通过name获取 Bean.
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	// 通过class获取Bean.
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	// 通过name,以及Clazz返回指定的Bean
	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}

	public static List<String> getServiceAliases() {
		List<String> aliases = new ArrayList<String>();
		Map<String, Object> beansWithAnnotationMap = SpringUtil.getApplicationContext().getBeansWithAnnotation(org.springframework.stereotype.Service.class);  
		for(Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()){  
			Class<? extends Object> clazz = entry.getValue().getClass();//获取到实例对象的class信息
			Service anno = clazz.getAnnotation(Service.class);
			aliases.add(anno.value());
		}
		return aliases;
	}

}
