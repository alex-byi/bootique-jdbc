package com.nhl.bootique.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class LazyDataSourceFactoryIT {

	private static final String URL = "jdbc:derby:target/testdb;create=true";
	private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

	private Map<String, Map<String, String>> configs;
	private Map<String, String> derbyConfig;

	@Before
	public void before() {

		this.derbyConfig = new HashMap<>();
		derbyConfig.put("url", URL);
		derbyConfig.put("initialSize", "2");
		derbyConfig.put("driverClassName", DRIVER);

		this.configs = new HashMap<>();
		configs.put("c1", derbyConfig);
	}

	@Test
	public void testCreateDataSource() throws Exception {

		LazyDataSourceFactory factory = new LazyDataSourceFactory(configs);
		org.apache.tomcat.jdbc.pool.DataSource ds = factory.createDataSource("c1");
		assertNotNull(ds);

		try {
			assertEquals(URL, ds.getUrl());
			assertEquals(2, ds.getInitialSize());
		} finally {
			ds.close();
		}
	}

	@Test
	public void testCreateDataSource_DriverAutoDetected() throws Exception {

		derbyConfig.remove("driverClassName");

		LazyDataSourceFactory factory = new LazyDataSourceFactory(configs);
		org.apache.tomcat.jdbc.pool.DataSource ds = factory.createDataSource("c1");
		assertNotNull(ds);

		try {
			try (Connection c = ds.getConnection()) {
				assertTrue(c.getMetaData().getDriverName().toLowerCase().contains("derby"));
			}
		} finally {
			ds.close();
		}
	}

}
