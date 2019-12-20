package by.htp.ts.bean.creator;

import java.util.Map;

import by.htp.ts.bean.User;
import by.htp.ts.bean.UserInfo;
import by.htp.ts.bean.UserParameter;

public class Creator {

	private static final Creator INSTANCE = new Creator();

	private Creator() {

	}

	public static Creator getInstance() {
		return INSTANCE;
	}

	public UserInfo createUserInfo(Map<String, Object> param) {

		UserInfo userInfo = new UserInfo();

		userInfo.setLogin((String) param.get(UserParameter.LOGIN.name()));
		userInfo.setPassword((String) param.get(UserParameter.PASSWORD.name()));
		userInfo.setEmail((String) param.get(UserParameter.EMAIL.name()));
		userInfo.setRole((String) param.get(UserParameter.ROLE.name()));
		userInfo.setName((String) param.get(UserParameter.NAME.name()));
		userInfo.setSurname((String) param.get(UserParameter.SURNAME.name()));

		return userInfo;
	}
	
	public User createUser(Map<String, Object> param) {
		
		User user = new User();
		
		user.setId((int) param.get(UserParameter.ID.name()));
		user.setEmail((String) param.get(UserParameter.EMAIL.name()));
		user.setRole((String) param.get(UserParameter.ROLE.name()));
		user.setName((String) param.get(UserParameter.NAME.name()));
		user.setSurname((String) param.get(UserParameter.SURNAME.name()));
		
		return user;
	}

}
