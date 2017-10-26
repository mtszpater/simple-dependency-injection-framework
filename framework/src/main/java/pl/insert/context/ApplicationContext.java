package pl.insert.context;

public interface ApplicationContext {

	<T> T getBean(Class<T> clazz);

}
