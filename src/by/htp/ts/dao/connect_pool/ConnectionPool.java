package by.htp.ts.dao.connect_pool;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public final class ConnectionPool {

	private BlockingQueue<Connection> connectionQueue;
	private BlockingQueue<Connection> givenAwayConQueue;

	private String driverName;
	private String url;
	private String user;
	private String password;
	private int poolSize;

	private static final ConnectionPool INSTANCE;

	static {

		try {
			INSTANCE = new ConnectionPool();
		} catch (ClassNotFoundException e) {
			// log
			throw new ConnectionPoolRuntimeException();
		} catch (SQLException e) {
			// log
			throw new ConnectionPoolRuntimeException();
		}
	}

	public static ConnectionPool getInstance() {
		return INSTANCE;
	}

	private ConnectionPool() throws ClassNotFoundException, SQLException {
		DBResourceManager dbResourceManager = DBResourceManager.getInstance();
		this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
		this.url = dbResourceManager.getValue(DBParameter.DB_URL);
		this.user = dbResourceManager.getValue(DBParameter.DB_USER);
		this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
		try {
			this.poolSize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POOLSIZE));
		} catch (NumberFormatException e) {
			poolSize = 10;
		}
		initPoolData();

	}

	private void initPoolData() throws ClassNotFoundException, SQLException {

		Class.forName(driverName);
		givenAwayConQueue = new ArrayBlockingQueue<Connection>(poolSize);
		connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);

		for (int i = 0; i < poolSize; i++) {
			Connection connection = DriverManager.getConnection(url, user, password);
			PooledConnection pooledConnection = new PooledConnection(connection);
			connectionQueue.add(pooledConnection);
		}

	}

	public void dispose() {
		clearConnectionQueue();
	}

	private void clearConnectionQueue() {
		try {
			closeConnectionsQueue(givenAwayConQueue);
			closeConnectionsQueue(connectionQueue);

		} catch (SQLException e) {
			// log
		}
	}

	public Connection takeConnection() {
		Connection connection = null;

		try {
			connection = connectionQueue.take();
			givenAwayConQueue.add(connection);
		} catch (InterruptedException e) {
			// log
		}
		return connection;

	}
	
	public void closeConnection( PreparedStatement ps, ResultSet rs) {

		try {
			if (rs != null) {
				rs.close();
			}

		} catch (SQLException e) {
			// log
		}
		try {

			if (ps != null) {
				ps.close();
			}

		} catch (SQLException e) {
			// log
		}
		

	}
	
	

	public void closeConnection(Connection con, Statement st, ResultSet rs) {

		try {
			if (rs != null) {
				rs.close();
			}

		} catch (SQLException e) {
			// log
		}
		try {

			if (st != null) {
				st.close();
			}

		} catch (SQLException e) {
			// log
		}
		try {
			if (con != null) {
				con.close();
			}

		} catch (SQLException e) {
			// log
		}

	}
	
	public void closeConnection(Connection con, PreparedStatement ps, ResultSet rs) {

		try {
			if (rs != null) {
				rs.close();
			}

		} catch (SQLException e) {
			// log
		}
		try {

			if (ps != null) {
				ps.close();
			}

		} catch (SQLException e) {
			// log
		}
		try {
			if (con != null) {
				con.close();
			}

		} catch (SQLException e) {
			// log
		}

	}

	public void closeConnection(Connection con, Statement st) {

		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException e) {
			// log
		}
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			// log
		}

	}

	public void closeConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			// log
		}
	}
	
	public void closeConnection(Connection con, PreparedStatement ps1, PreparedStatement ps2, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			// log
		}
		try {
			if (ps1 != null) {
				ps1.close();
			}
		} catch (SQLException e) {
			// log
		}
		try {
			if (ps2 != null) {
				ps2.close();
			}
		} catch (SQLException e) {
			// log
		}
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			// log
		}
	}

	private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
		Connection connection;
		while ((connection = queue.poll()) != null) {
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
			((PooledConnection) connection).reallyClose();
		}

	}

	private class PooledConnection implements Connection {

		private Connection connection;

		public PooledConnection(Connection c) throws SQLException {
			this.connection = c;
			this.connection.setAutoCommit(true);
		}

		public void reallyClose() throws SQLException {
			connection.close();
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {

			return connection.isWrapperFor(iface);
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {

			return connection.unwrap(iface);
		}

		@Override
		public void abort(Executor executor) throws SQLException {
			connection.abort(executor);

		}

		@Override
		public void clearWarnings() throws SQLException {
			connection.clearWarnings();

		}

		@Override
		public void close() throws SQLException {
			if (connection.isClosed()) {
				throw new SQLException("Attempting to close closed connection");
			}
			if (connection.isReadOnly()) {
				connection.setReadOnly(false);
			}
			if (!givenAwayConQueue.remove(this)) {
				throw new SQLException("Error deleting connection from the given away connection pool");
			}
			if (!connectionQueue.offer(this)) {
				throw new SQLException("Error allocating connection in the pool");
			}

		}

		@Override
		public void commit() throws SQLException {
			connection.commit();

		}

		@Override
		public Array createArrayOf(String typeName, Object[] elements) throws SQLException {

			return connection.createArrayOf(typeName, elements);
		}

		@Override
		public Blob createBlob() throws SQLException {

			return connection.createBlob();
		}

		@Override
		public Clob createClob() throws SQLException {

			return connection.createClob();
		}

		@Override
		public NClob createNClob() throws SQLException {

			return connection.createNClob();
		}

		@Override
		public SQLXML createSQLXML() throws SQLException {

			return connection.createSQLXML();
		}

		@Override
		public Statement createStatement() throws SQLException {

			return connection.createStatement();
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {

			return connection.createStatement(resultSetType, resultSetConcurrency);
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
				throws SQLException {

			return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public Struct createStruct(String typeName, Object[] attributes) throws SQLException {

			return connection.createStruct(typeName, attributes);
		}

		@Override
		public boolean getAutoCommit() throws SQLException {

			return connection.getAutoCommit();
		}

		@Override
		public String getCatalog() throws SQLException {

			return connection.getCatalog();
		}

		@Override
		public Properties getClientInfo() throws SQLException {

			return connection.getClientInfo();
		}

		@Override
		public String getClientInfo(String name) throws SQLException {

			return connection.getClientInfo(name);
		}

		@Override
		public int getHoldability() throws SQLException {

			return connection.getHoldability();
		}

		@Override
		public DatabaseMetaData getMetaData() throws SQLException {

			return connection.getMetaData();
		}

		@Override
		public int getNetworkTimeout() throws SQLException {

			return connection.getNetworkTimeout();
		}

		@Override
		public String getSchema() throws SQLException {

			return connection.getSchema();
		}

		@Override
		public int getTransactionIsolation() throws SQLException {

			return connection.getTransactionIsolation();
		}

		@Override
		public Map<String, Class<?>> getTypeMap() throws SQLException {

			return connection.getTypeMap();
		}

		@Override
		public SQLWarning getWarnings() throws SQLException {

			return connection.getWarnings();
		}

		@Override
		public boolean isClosed() throws SQLException {

			return connection.isClosed();
		}

		@Override
		public boolean isReadOnly() throws SQLException {

			return connection.isReadOnly();
		}

		@Override
		public boolean isValid(int timeout) throws SQLException {

			return connection.isValid(timeout);
		}

		@Override
		public String nativeSQL(String sql) throws SQLException {

			return connection.nativeSQL(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql) throws SQLException {

			return connection.prepareCall(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {

			return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {

			return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public PreparedStatement prepareStatement(String sql) throws SQLException {

			return connection.prepareStatement(sql);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {

			return connection.prepareStatement(sql, autoGeneratedKeys);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {

			return connection.prepareStatement(sql, columnIndexes);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {

			return connection.prepareStatement(sql, columnNames);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {

			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {

			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public void releaseSavepoint(Savepoint savepoint) throws SQLException {
			connection.releaseSavepoint(savepoint);

		}

		@Override
		public void rollback() throws SQLException {
			connection.rollback();

		}

		@Override
		public void rollback(Savepoint savepoint) throws SQLException {
			connection.rollback(savepoint);

		}

		@Override
		public void setAutoCommit(boolean autoCommit) throws SQLException {
			connection.setAutoCommit(autoCommit);

		}

		@Override
		public void setCatalog(String catalog) throws SQLException {
			connection.setCatalog(catalog);

		}

		@Override
		public void setClientInfo(Properties properties) throws SQLClientInfoException {
			connection.setClientInfo(properties);

		}

		@Override
		public void setClientInfo(String name, String value) throws SQLClientInfoException {
			connection.setClientInfo(name, value);

		}

		@Override
		public void setHoldability(int holdability) throws SQLException {
			connection.setHoldability(holdability);

		}

		@Override
		public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
			connection.setNetworkTimeout(executor, milliseconds);

		}

		@Override
		public void setReadOnly(boolean readOnly) throws SQLException {
			connection.setReadOnly(readOnly);

		}

		@Override
		public Savepoint setSavepoint() throws SQLException {

			return connection.setSavepoint();
		}

		@Override
		public Savepoint setSavepoint(String name) throws SQLException {

			return connection.setSavepoint(name);
		}

		@Override
		public void setSchema(String schema) throws SQLException {
			connection.setSchema(schema);

		}

		@Override
		public void setTransactionIsolation(int level) throws SQLException {
			connection.setTransactionIsolation(level);

		}

		@Override
		public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
			connection.setTypeMap(map);

		}
	}
}
