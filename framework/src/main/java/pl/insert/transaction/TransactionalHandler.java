package pl.insert.transaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import pl.insert.annotations.Transactional;
import pl.insert.transaction.TransactionTemplate;

public class TransactionalHandler implements InvocationHandler {

	private Object object;
	private TransactionTemplate transactionTemplate;

	public TransactionalHandler(Object object, TransactionTemplate transactionTemplate) {
		super();
		this.transactionTemplate = transactionTemplate;
		this.object = object;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable, InvocationTargetException, IllegalAccessException, IllegalArgumentException {

		if (object.getClass().isAnnotationPresent(Transactional.class) || object.getClass()
				.getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(Transactional.class)) {

			return transactionTemplate.execute(() -> {
				try {
					return method.invoke(object, args);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				return null;
			});

		} else {
			return method.invoke(object, args);
		}
	}

}
