package com.mprzybylak.minefields.jpa.id.common;

public class QueryGenerator {
	
	private static final String E = " e";
	private static final String SELECT = "SELECT e FROM ";

	public static String select(Class<?> clazz) {
		return new StringBuffer().append(SELECT).append(clazz.getName()).append(E).toString();
	}

}
