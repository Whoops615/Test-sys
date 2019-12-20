package by.htp.ts.service;

import by.htp.ts.service.impl.TestServiceImpl;
import by.htp.ts.service.impl.UserServiceImpl;

public final class ServiceProvider {

	private final static ServiceProvider INSTANCE = new ServiceProvider();

	private final UserService userService = new UserServiceImpl();
	private final TestService testService = new TestServiceImpl();

	public static ServiceProvider getInstance() {
		return INSTANCE;
	}

	public UserService getUserService() {
		return userService;
	}

	public TestService getTestService() {
		return testService;
	}

}
