package dev.pater.dao;


import dev.pater.annotations.Inject;
import dev.pater.entity.User;

import javax.persistence.EntityManager;

public class UserDAOImpl implements UserDAO {

	@Inject
	private EntityManager em;
	
	public void save(User user) {
		em.persist(user);
	}

}
