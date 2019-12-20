package by.htp.ts.dao.connect_pool;

import java.util.ResourceBundle;

public class DBResourceManager {

	public static final DBResourceManager INSTANCE = new DBResourceManager();

	private ResourceBundle bundle = ResourceBundle.getBundle("db");

	public static DBResourceManager getInstance() {
		return INSTANCE;
	}

	public String getValue(String key) {
		return bundle.getString(key);
	}

}
