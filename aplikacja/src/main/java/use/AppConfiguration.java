package use;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import use.service.UserService;
import pl.insert.AppConfigurationInterface;
import pl.insert.annotations.Lazy;
import pl.insert.transaction.EMThreadLocalStorage;
import use.dao.UserDAO;
import use.dao.UserDAOImpl;
import use.service.UserServiceImpl;

public class AppConfiguration {

	@Lazy
	public UserDAO userDAO() {
		return new UserDAOImpl();
	}
	
	public EntityManager entityManager() {
		return EMThreadLocalStorage.getEntityManager();
	}
	
	public UserService userService() {
		return new UserServiceImpl();
	}

}
