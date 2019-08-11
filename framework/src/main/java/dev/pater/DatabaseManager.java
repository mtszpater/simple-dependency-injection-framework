package dev.pater;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseManager {
	private static EntityManagerFactory sessionFactory = null;

	protected DatabaseManager() {}

	public static EntityManagerFactory getConnection() {
		if (sessionFactory == null) {
			sessionFactory = Persistence.createEntityManagerFactory("first_persistence");
		}
		return sessionFactory;
	}

}
