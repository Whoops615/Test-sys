package by.htp.ts.dao;

public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;

	public DAOException() {

	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Exception e) {
		super(e);
	}

	public DAOException(String message, Exception e) {
		super(message, e);
	}

}
