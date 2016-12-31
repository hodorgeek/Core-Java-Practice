package com.hodor.corejava.jdbc.common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The utility class which loads the database from configuration files, loads
 * the driver and provides methods related to database connection
 * 
 * @author sham
 *
 */
public final class DatabaseUtils implements DatabaseProperty {

	private static final Logger LOGGER = Logger.getLogger(DatabaseUtils.class.getName());

	private Connection conn;

	private static DatabaseUtils databaseUtil;

	private static String dbUser;
	private static String dbPassword;
	private static String dbDriver;
	private static String dbName;
	private static String dbHost;
	private static String dbPort;
	private static String dbVendor;

	static {
		Properties properties = null;
		String passwordFile = "";
		try {
			properties = PropertyLoaderUtils.readPropertyFromFile(CONFIG_FILE_PATH);
			dbUser = properties.getProperty(USER_NAME);
			dbDriver = properties.getProperty(DRIVER_NAME);
			dbName = properties.getProperty(DATABASE_NAME);
			dbPort = properties.getProperty(DB_PORT);
			dbHost = properties.getProperty(HOST_NAME);
			dbVendor = properties.getProperty(DATABASE_VENDOR);
			passwordFile = properties.getProperty(PASSWORD);
			dbPassword = PropertyLoaderUtils.readFromFile(passwordFile);

			// loading the driver
			Class.forName(dbDriver);
		} catch (IOException e) {
			final String fileName = passwordFile != null ? passwordFile : CONFIG_FILE_PATH;
			LOGGER.log(Level.SEVERE, "Unable to load config file or password file : " + fileName, e);
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE, "Unable to load driver class : " + dbDriver, e);
		}
	}

	private DatabaseUtils() {
		// Exists only to defeat instantiation.
	}

	/**
	 * getInstance() is used to get the singleton instance of the
	 * DatabasePropertyHolder class
	 * 
	 * @return the daabasePropertyHolder Instance
	 */
	public static DatabaseUtils getInstance() {
		if (databaseUtil == null) {
			databaseUtil = new DatabaseUtils();
		}
		return databaseUtil;
	}

	/**
	 * getConnection() is used to get the data base connection object.
	 * 
	 * @return the connection object
	 * @throws SQLException
	 *             if the connection to database is not successful
	 */
	public Connection getConnection() throws SQLException {
		final StringBuilder connectionUrl = new StringBuilder("jdbc:");
		if (dbVendor.equals("mysql")) {
			connectionUrl.append(dbVendor).append("://").append(dbHost).append(":").append(dbPort).append("/");
			LOGGER.info("The connection url is : " + connectionUrl.toString());
			conn = DriverManager.getConnection(connectionUrl.toString(), dbUser, dbPassword);
			conn.setCatalog(dbName);
		} else if (dbVendor.equals("derby")) {
			connectionUrl.append(dbVendor).append(":").append(dbName).append(";create=true");
			LOGGER.info("The connection url is : " + connectionUrl.toString());
			conn = DriverManager.getConnection(connectionUrl.toString(), dbUser, dbPassword);
		}
		LOGGER.info("The database connection is created successfully, is Open : " + conn.isClosed());
		return conn;
	}

	/**
	 * closeConnection() is used to close the database connection
	 * 
	 * @throws SQLException
	 *             if any failure in closing the connection.
	 */
	public void closeConnection() throws SQLException {
		if (!conn.isClosed()) {
			conn.close();
			LOGGER.info("The cnnection to database get closed successfully");
		}
	}

	public static void alternatePrintSQLException(SQLException ex) {
		while (ex != null) {
			LOGGER.log(Level.SEVERE, "SQLState: " + ex.getSQLState());
			LOGGER.log(Level.SEVERE, "Error Code: " + ex.getErrorCode());
			LOGGER.log(Level.SEVERE, "Message: " + ex.getMessage());
			Throwable t = ex.getCause();
			while (t != null) {
				LOGGER.log(Level.SEVERE, "Cause: ", t);
				t = t.getCause();
			}
			ex = ex.getNextException();
		}
	}

}
