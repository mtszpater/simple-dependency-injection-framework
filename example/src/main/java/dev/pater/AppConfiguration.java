package dev.pater;

import javax.persistence.EntityManager;

import dev.pater.dao.UserDAO;
import dev.pater.dao.UserDAOImpl;
import dev.pater.service.UserService;
import dev.pater.service.UserServiceImpl;
import dev.pater.transaction.EMThreadLocalStorage;

public class AppConfiguration {
	
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
