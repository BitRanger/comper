package test.comper.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.comper.jdbc.DBConnection;

public class TestSQLite2 extends DBConnection {

	@Before
	public void setUp() throws Exception {
		connect();

		Connection connection = jdbc.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rSet = statement.executeQuery("select count(*) from wk_paper");
		while (rSet.next()) {
			System.out.println(rSet.getInt(1));
		}
		System.out.println("TestSQLite.setUp()");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
	}

}
