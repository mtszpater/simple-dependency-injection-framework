package dev.pater;

import java.util.function.Supplier;

import javax.persistence.EntityManager;

import dev.pater.transaction.EMThreadLocalStorage;

public class EntityManagerSupplier implements Supplier<EntityManager>{

	@Override
	public EntityManager get() {
		return EMThreadLocalStorage.getEntityManager();
	}
	
	
}
