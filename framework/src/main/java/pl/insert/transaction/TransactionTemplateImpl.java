package pl.insert.transaction;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import pl.insert.AppConfigurationInterface;
import pl.insert.DatabaseManager;
import pl.insert.annotations.Inject;
import pl.insert.configuration.AppConfiguration;

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