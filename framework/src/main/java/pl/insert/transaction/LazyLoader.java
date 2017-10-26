package pl.insert.transaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.function.Supplier;

public class LazyLoader<T> implements InvocationHandler {

	private Supplier<T> supplier;
	private T relatedClass;

	public LazyLoader(Supplier<T> supplier) {
		super();
		this.supplier = supplier;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(getClazz(), args);
	}

	private T getClazz() {
		if (relatedClass == null)
			relatedClass = supplier.get();

		return relatedClass;
	}

}
