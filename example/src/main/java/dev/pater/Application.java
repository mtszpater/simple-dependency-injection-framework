package dev.pater;

import dev.pater.context.ApplicationContext;
import dev.pater.entity.User;
import dev.pater.service.UserService;

public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext<AppConfiguration>(AppConfiguration.class);

        UserService service = (UserService) applicationContext.getBean(UserService.class);
        User user = new User();
        user.setLastName("test");
        service.save(user);
    }

}
