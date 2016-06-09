package com.cocula.database;


public class SpringJPADataBaseServer extends HSQLDataBaseServer {
	
	private static final String DATABASE_NAME = "SpringJPA";
	private static final String DATABASE_PATH = "./data/SpringJPA";
	
	
	private final static SpringJPADataBaseServer uniqueInstance = new SpringJPADataBaseServer();
	

	public static DataBaseServer getInstance() {
		return uniqueInstance;
	}

	@Override
	protected String getDataBasePath() {
		return DATABASE_PATH;
	}

	@Override
	protected String getDataBaseName() {
		return DATABASE_NAME;
	}
}
