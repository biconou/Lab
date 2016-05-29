package com.cocula.database;


public class TghDataBaseServer extends HSQLDataBaseServer {
	
	private static final String DATABASE_NAME = "tgh";
	private static final String DATABASE_PATH = "./data/tgh";
	
	
	private final static TghDataBaseServer uniqueInstance = new TghDataBaseServer();
	

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
