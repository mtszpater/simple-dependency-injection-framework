package use.service;

import pl.insert.annotations.Inject;
import pl.insert.annotations.Transactional;
import use.dao.UserDAO;
import use.entity.User;

@Transactional
public class UserServiceImpl implements UserService {

	@Inject
	private UserDAO userDAO;

	public void save(User user) {
		userDAO.save(user);	
	}


}
