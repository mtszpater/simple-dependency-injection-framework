package use;

import pl.insert.context.ApplicationContext;
import pl.insert.context.ApplicationContextImpl;
import use.entity.User;
import use.service.UserService;

public class Application {
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		@SuppressWarnings("unchecked")
		ApplicationContext applicationContext = new ApplicationContextImpl(AppConfiguration.class);
		UserService service = applicationContext.getBean(UserService.class);

		User user = new User();
		user.setLastname("test");
		service.save(user);
		
		
	}
	
}
