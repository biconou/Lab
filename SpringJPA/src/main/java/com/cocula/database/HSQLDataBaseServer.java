package com.cocula.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;

public abstract class HSQLDataBaseServer implements DataBaseServer {
	
	/**
	 * Instance du serveur HSQLDB sous jascent.
	 */
	private Server HSQLDBServer = null; 
	
	/**
	 * constructeur
	 */
	protected HSQLDataBaseServer() {
		super();
		HSQLDBServer = new Server();
		HSQLDBServer.setDatabasePath(0,getDataBasePath());  //l'adresse physique des fichiers
		HSQLDBServer.setDatabaseName(0,getDataBaseName());  //le nom de la base au sens server
		HSQLDBServer.setTrace(true);
	}

	abstract protected String getDataBasePath();
	
	abstract protected String getDataBaseName();

	protected String getJDBCConnectionURL() {
		StringBuilder sb = new StringBuilder("jdbc:hsqldb:hsql://localhost/").append(getDataBaseName());
		return sb.toString();
	}
	
	/**
	 * Démarre la base de données.
	 */
	@Override
	public void start() {
		HSQLDBServer.start();
	}

	/**
	 * Arrête la base de données.
	 */
	@Override
	public void stop() {
		//HSQLDBServer.shutdown();
		//HSQLDBServer.stop();
		try {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Connection c = DriverManager.getConnection(getJDBCConnectionURL(), "SA", "");
			Statement stmtshutdown = c.createStatement();
			stmtshutdown.execute("SHUTDOWN");
			c.close();
		} catch (SQLException e) {
			Exception e2 = new Exception("Exception lors de l'arrêt de la base",e);
			e2.printStackTrace();
		}
	}
}	

