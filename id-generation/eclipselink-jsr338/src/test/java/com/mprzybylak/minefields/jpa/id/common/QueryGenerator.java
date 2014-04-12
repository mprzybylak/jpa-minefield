package com.mprzybylak.minefields.jpa.id.common;

/**
 * Helper class for JPQL query generation
 * @author Michal Przybylak
 */
public class QueryGenerator {
	
	private static final String E = " e";
	private static final String SELECT = "SELECT e FROM ";

	/**
	 * Generates select (all) query for given class
	 * @param clazz 
	 * @return
	 */
	public static String select(Class<?> clazz) {
		return new StringBuffer().append(SELECT).append(clazz.getName()).append(E).toString();
	}

}
