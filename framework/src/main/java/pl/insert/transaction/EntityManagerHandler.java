package pl.insert.transaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.function.Supplier;

import javax.persistence.EntityManager;

public class EntityManagerHandler  implements InvocationHandler {

	private Supplier<EntityManager> supplier;
	
	public EntityManagerHandler(Supplier<EntityManager> supplier) {
		super();
		this.supplier = supplier;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(supplier.get(), args);
	}

}


