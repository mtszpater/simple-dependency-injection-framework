package pl.insert.transaction;

import javax.persistence.EntityManager;


public class EMThreadLocalStorage {
	
	private static final ThreadLocal<EntityManager> threadLocal = new ThreadLocal<>();

	public static EntityManager getEntityManager() {
		return threadLocal.get();
	}
	
	public static void setEntityManager(EntityManager entityManager) {
		threadLocal.set(entityManager);
	}

}
