package dev.pater.context;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.persistence.EntityManager;

import dev.pater.annotations.Inject;
import dev.pater.transaction.*;

public class ApplicationContext<T> {

	Object appConfiguration; 
	
	public ApplicationContext(Class<T> appConfiguration) {
		super();
		try {
			this.appConfiguration = appConfiguration.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> clazz) {

		try {
			T obj = getBeanFromConfiguration(clazz);

			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(Inject.class)) {
					field.setAccessible(true);
					field.set(obj, getBean(field.getType()));
				}
			}

			if (clazz.isInterface()) {
				TransactionalHandler transactionalHandler = new TransactionalHandler(obj, new TransactionTemplateImpl());
				obj = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, transactionalHandler);
			}

			return obj;

		} catch (IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private <T> T getBeanFromConfiguration(Class<T> clazz) throws InstantiationException, IllegalAccessException {
		Method[] methods = appConfiguration.getClass().getMethods();

		for (Method method : methods) {
			if (clazz.equals(method.getReturnType())) {
				try {

					if (method.getReturnType().isAssignableFrom(EntityManager.class)) {
						EntityManagerHandler entityManagerHandler = new EntityManagerHandler(() -> {
							try {
								return (EntityManager) method.invoke(appConfiguration);
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								e.printStackTrace();
							}
							return null;
						});

						return (T) Proxy.newProxyInstance(EntityManager.class.getClassLoader(),
								new Class[]{EntityManager.class}, entityManagerHandler);

					}

					return (T) method.invoke(appConfiguration);

				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}

			}
		}

		return (T) clazz.newInstance();
	}
}
