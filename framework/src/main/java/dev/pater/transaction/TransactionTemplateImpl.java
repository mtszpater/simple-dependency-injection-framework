package dev.pater.transaction;

import java.sql.SQLException;

import javax.persistence.EntityManager;

import dev.pater.DatabaseManager;

public class TransactionTemplateImpl implements TransactionTemplate {
	@Override
	public <T> T execute(TransactionalOperation<T> action) throws SQLException {
		EntityManager entityManager = DatabaseManager.getConnection().createEntityManager();
		EMThreadLocalStorage.setEntityManager(entityManager);
		entityManager.getTransaction().begin();
		try {
			return action.run();
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
	}
}