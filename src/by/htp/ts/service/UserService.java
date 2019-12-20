package by.htp.ts.service;



import by.htp.ts.bean.User;
import by.htp.ts.bean.UserInfo;
import by.htp.ts.service.validator.ValidatorException;

public interface UserService {

	User authorization(String login, String password) throws ServiceException, ValidatorException;
	boolean registration(UserInfo userInfo) throws ServiceException, ValidatorException;

}
