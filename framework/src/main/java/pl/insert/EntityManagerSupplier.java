package pl.insert;

import java.util.function.Supplier;

import javax.persistence.EntityManager;

import pl.insert.transaction.EMThreadLocalStorage;

public class EntityManagerSupplier implements Supplier<EntityManager>{

	@Override
	public EntityManager get() {
		return EMThreadLocalStorage.getEntityManager();
	}
	
	
}
