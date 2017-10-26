package pl.insert.context;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.persistence.EntityManager;

import pl.insert.AppConfigurationInterface;
import pl.insert.annotations.Inject;
import pl.insert.annotations.Lazy;
import pl.insert.configuration.AppConfiguration;
import pl.insert.services.LazyClass;
import pl.insert.services.LazyClassImpl;
import pl.insert.transaction.*;

public class ApplicationContextImpl<T> implements ApplicationContext {

	Object appConfiguration; 
	
	public ApplicationContextImpl(Class<T> appConfiguration) {
		super();
		try {
			this.appConfiguration = appConfiguration.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(Class<T> clazz) {

		try {
			T obj = getBeanFromConfiguration(clazz);

			Field[] fields = obj.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; ++i) {
				if (fields[i].isAnnotationPresent(Inject.class)) {
					fields[i].setAccessible(true);
					fields[i].set(obj, getBean(fields[i].getType()));
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

		for (int i = 0; i < methods.length; ++i) {
			if (clazz.equals(methods[i].getReturnType())) {
				try {

					if (methods[i].isAnnotationPresent(Lazy.class)) {
						Method method_tmp = methods[i];

						@SuppressWarnings("rawtypes")
						LazyLoader lazyLoader = new LazyLoader(() -> {
							try {
								return method_tmp.invoke(appConfiguration);
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								e.printStackTrace();
							}
							return null;
						});

						return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
								new Class[] { clazz }, lazyLoader);

					}

					if (methods[i].getReturnType().isAssignableFrom(EntityManager.class)) {
						Method method_tmp = methods[i];
						EntityManagerHandler entityManagerHandler = new EntityManagerHandler(() -> {
							try {
								return (EntityManager) method_tmp.invoke(appConfiguration);
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								e.printStackTrace();
							}
							return null;
						});

						return (T) Proxy.newProxyInstance(EntityManager.class.getClassLoader(),
								new Class[] { EntityManager.class }, entityManagerHandler);

					}

					return (T) methods[i].invoke(appConfiguration);

				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}

			}
		}

		return (T) clazz.newInstance();
	}
}