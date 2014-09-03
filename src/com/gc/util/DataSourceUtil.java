package com.gc.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DataSourceUtil {

	private DataSourceUtil() {
		
	}

	public static String getDataSourceInfo(DataSource ds) throws SQLException {
		return getDataSourceInfo(ds, null);
	}

	public static String getDataSourceInfo(DataSource ds, String name) throws SQLException {
		Connection conn = null;
		StringBuilder sb = new StringBuilder();
		try {
			conn = ds.getConnection();
			DatabaseMetaData dm = conn.getMetaData();
			sb.append("DataSource[");
			if (name != null) sb.append("name: ").append(name).append("\n\t");
			sb.append("class: ").append(ds.getClass().getName())
				.append("\n\tjdbc: ").append(dm.getJDBCMajorVersion()).append(".").append(dm.getJDBCMinorVersion())
				.append("\n\tdriver: ").append(dm.getDriverName()).append(" ").append(dm.getDriverVersion())
					// .append(" ").append(dm.getDriverMajorVersion()).append(".").append(dm.getDriverMinorVersion())
				.append("\n\tdatabase: ").append(dm.getDatabaseProductName()).append(" ").append(dm.getDatabaseProductVersion().replace('\n', ' '))
					// .append(" ").append(dm.getDatabaseMajorVersion()).append(".").append(dm.getDatabaseMinorVersion())
				.append("\n\turl: ").append(dm.getURL())
				.append("\n\tusername: ").append(dm.getUserName())
				.append("\n\tschema: ").append(conn.getCatalog())
				.append("]");
		} catch (SQLException e) {
			throw e;
		} finally {
			if (conn != null) try {conn.close();} catch(Exception e2) {}
		}
		return sb.toString();
	}

}
