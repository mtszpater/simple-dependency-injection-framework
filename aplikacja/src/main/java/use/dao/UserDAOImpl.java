package use.dao;

import javax.persistence.EntityManager;

import pl.insert.annotations.Inject;
import use.entity.User;

public class UserDAOImpl implements UserDAO {

	@Inject
	private EntityManager em;
	
	
	public UserDAOImpl() {
		/* dodane chwilowo dla @Lazy */
		try {
			System.out.println("Zasypiam");
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void save(User user) {
		em.persist(user);
	}

}
