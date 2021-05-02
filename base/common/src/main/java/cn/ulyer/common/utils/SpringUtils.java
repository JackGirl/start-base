package cn.ulyer.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * spirng 容器获取bean
 * @author 万仁杰
 */
@Component
public class SpringUtils implements ApplicationContextAware {

	private static ApplicationContext context;


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public static <T> T getBean(Class<?> clazz) {
		Assert.notNull(context, "context is null");
		return (T) context.getBean(clazz);
	}

	public static <T> T getBean(String name) {
		Assert.notNull(context, "context is null");
		return (T) context.getBean(name);
	}

	public static void publishEvent(ApplicationEvent applicationEvent){
		Assert.notNull(context,"context is null cannot publish event");
		context.publishEvent(applicationEvent);
	}




}
