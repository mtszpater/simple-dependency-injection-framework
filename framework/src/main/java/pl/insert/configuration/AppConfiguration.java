package pl.insert.configuration;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import pl.insert.AppConfigurationInterface;
import pl.insert.DatabaseManager;
import pl.insert.annotations.Lazy;
import pl.insert.dao.UserDAO;
import pl.insert.dao.UserDAOImpl;
import pl.insert.services.LazyClassImpl;
import pl.insert.services.LazyClass;
import pl.insert.services.UserService;
import pl.insert.services.UserServiceImpl;
import pl.insert.transaction.EMThreadLocalStorage;

public class AppConfiguration implements AppConfigurationInterface {

	public UserService userService() {
		return new UserServiceImpl();
	}

	public UserDAO userDAO() {
		return new UserDAOImpl();
	}

	public EntityManager entityManager() {
		return EMThreadLocalStorage.getEntityManager();
	}
	
	@Lazy
	public LazyClass lazyClass() {
		return new LazyClassImpl();
	}
	
	public EntityManagerFactory entityManagerFactory() {
		return DatabaseManager.getConnection();
	}

}
