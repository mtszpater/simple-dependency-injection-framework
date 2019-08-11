package dev.pater.service;

import dev.pater.annotations.Inject;
import dev.pater.annotations.Transactional;
import dev.pater.dao.UserDAO;
import dev.pater.entity.User;

@Transactional
public class UserServiceImpl implements UserService {

	@Inject
	private UserDAO userDAO;
	
	public void save(User user) {
		userDAO.save(user);	
	}


}
