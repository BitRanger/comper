/*******************************************************************************
 * Copyright 2014 Cai Bowen Zhou Liangpeng
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.wangk.comper.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.wangk.comper.db.jdbc.collection.NoCaseMap;
import org.wangk.comper.db.jdbc.stmt.BaseBuilder;
import org.wangk.comper.db.jdbc.stmt.StatementCreator;
import org.wangk.comper.db.jdbc.stmt.UpdateStatementCreator;
import org.wangk.comper.db.orm.ColumnMapper;
import org.wangk.comper.db.orm.ObjectMapping;
import org.wangk.comper.db.orm.RowMapping;
import org.wangk.comper.db.orm.SingleColumMapper;

public class JdbcAux implements JdbcOperations {

	protected final Logger LOG = Logger.getLogger(JdbcAux.class.getName());
	
	public JdbcAux() {
		LOG.setLevel(Level.ALL);
	}
//-----------------------------------------------------------------------------

	private DataSource dataSource;
	private boolean lazyInit = true;
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public boolean isLazyInit() {
		return lazyInit;
	}

	public void setLazyInit(boolean lazyInit) {
		this.lazyInit = lazyInit;
	}
	
	public Connection getConnection() {
		try {
			return this.dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e.getSQLState() 
					+ "\nCaused by\n"
					+ e.getCause(), e);
		}
	}

//-----------------------------------------------------------------------------
//						execute
//-----------------------------------------------------------------------------

	/**
	 * the base function
	 */
	
	@Override
	public boolean execute(StatementCreator psc) {
		try {
			Connection connection = getConnection();
			PreparedStatement st = psc.createStatement(connection);
			if (LOG.isLoggable(Level.CONFIG)) {
				LOG.config("Executing SQL statement [" + st.toString() + "]");
			}
			boolean out = st.execute();
			JdbcUtil.closeStatement(st);
			JdbcUtil.closeConnection(connection);
			return out;
		} catch (SQLException e) {
			throw new RuntimeException(e.getSQLState() + "\nCaused by\n"
					+ e.getCause(), e);
		}
	}
	/**
	 *  below are  3 Wrappers
	 */
	@Override
	public boolean execute(final String sql) {
		return execute(getStatementCreator(sql));
	}

	@Override
	public boolean execute(final String sql, final Object... params) {
//System.out.println(sql);
		return execute(getStatementCreator(sql, params));
	}

	@Override
	public boolean execute(final BaseBuilder builder) {
		
		return execute(getStatementCreator(builder));
	}
//-----------------------------------------------------------------------------
//						update delete
//-----------------------------------------------------------------------------
	/**
	 * Base function
	 */
	
	@Override
	public int update(StatementCreator psc) {
		
		try {
			Connection connection = getConnection();
			PreparedStatement st = psc.createStatement(connection);
			int out = st.executeUpdate();
			JdbcUtil.closeStatement(st);
			JdbcUtil.closeConnection(connection);
			return out;
		} catch (SQLException e) {
			throw new RuntimeException(e.getSQLState() 
					+ "\nCaused by\n"
					+ e.getCause(), e);
		}
	}
	
	
	/**
	 *  and 4 Wrappers
	 */
	@Override
	public int update(final String sql) {
		
		return update(getStatementCreator(sql));
	}

	@Override
	public int update(final BaseBuilder builder) {
		
		return update(getStatementCreator(builder));
	}

	@Override
	public int update(final String sql, final Object... params) {
		
		return update(getStatementCreator(sql, params));
	}

	@Override
	public <T> int update(T obj, ObjectMapping<T> mapper) {
		
		return update(mapper.update(obj));
	}
	
	
	@Override
	public int[] batchUpdate(String... sql) {
		try {
			Connection connection = getConnection();
			Statement stmt = connection.createStatement();
			for (String string : sql) {
				stmt.addBatch(string + ';');
			}
			int[] out = stmt.executeBatch();
			JdbcUtil.closeStatement(stmt);
			JdbcUtil.closeConnection(connection);
			return out;
		} catch (SQLException e) {
			throw new RuntimeException(e.getSQLState() + "\nCaused by\n"
					+ e.getCause(), e);
		}
	}

//-----------------------------------------------------------------------------
//						insert
//-----------------------------------------------------------------------------
	@Override
	public <T> Map<String, Object> insert(final T obj, ObjectMapping<T> mapper, final String[] cols) {
		
		return insert(mapper.insert(obj), cols);
	}
	
	/**
	 * @return Map<String, T> is the getGenerated keys 
	 * 
	 *  example:
	 *  
	 *  keys =insert(entity, new String[]{"id", "time_created"})
	 *  
	 *  entityId = keys.get("id");
	 * 
	 */
	
	@Override
	public Map<String, Object> insert(UpdateStatementCreator psc, final String[] cols) {
		try {
			Connection connection = getConnection();
			
			PreparedStatement ps = psc.createUpdate(connection, cols);
			ps.execute();
			
			Map<String, Object> map = null;
			ResultSet rs = null;
			
			if (cols != null) {
				map = new NoCaseMap<Object>(cols.length);
				rs = ps.getGeneratedKeys();
				if (rs.next()) {

					for (String key : cols) {
						map.put(key, rs.getObject(key));
					}
					assert (rs.getMetaData().getColumnCount() != cols.length);
					assert (rs.isLast());
				}
			}
			
			JdbcUtil.closeResultSet(rs);
			JdbcUtil.closeStatement(ps);
			JdbcUtil.closeConnection(connection);
			
			return map;
		} catch (SQLException e) {
			throw new RuntimeException(e.getSQLState() 
					+ "\nCaused by\n"
					+ e.getCause(), e);
		}
	}
	
	/**
	 *  and 3 Wrappers
	 */
	@Override
	public Map<String, Object> insert(final String sql, String[] cols) {
		
		return insert(new UpdateStatementCreator() {
			
			@Override
			public PreparedStatement createStatement(Connection con)
					throws SQLException {return null;}
			
			@Override
			public PreparedStatement createUpdate(Connection con, String[] cols)
					throws SQLException {
				PreparedStatement ps =con.prepareStatement(sql, cols);
				return ps;
			}
		}, cols);
	}

	@Override
	public Map<String, Object> insert(final String sql, 
										String[] cols,
										final Object... params) {
		
		return insert(new UpdateStatementCreator() {
			
			@Override
			public PreparedStatement createStatement(Connection con)
					throws SQLException {return null;}
			
			@Override
			public PreparedStatement createUpdate(Connection con, String[] cols)
					throws SQLException {
				PreparedStatement ps =con.prepareStatement(sql, cols);
				setParams(ps, params);
				return ps;
			}
		}, cols);
	}

	@Override
	public Map<String, Object> insert(final BaseBuilder builder, final String[] cols) {
		
		return insert(new UpdateStatementCreator() {
			@Override
			public PreparedStatement createStatement(Connection con)
					throws SQLException {return null;}
			@Override
			public PreparedStatement createUpdate(Connection con, String[] cols)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(
											builder.getUnprepared()
											, cols);
				if (builder.hasParameters()) {
					setParams(ps, builder.getParameters());
				}
				return ps;
			}
		}, cols);
	}

//-----------------------------------------------------------------------------
//	select
//-----------------------------------------------------------------------------

//-----------------------------------------------
//	get object by appointed type
//-----------------------------------------------
	/**
	 * get object of requested type
	 */
	@Override
	public <T> T queryForObject(final String sql, Class<T> type) {
		return queryForObject(getStatementCreator(sql), type);
	}

	@Override
	public <T> T queryForObject(String sql, Class<T> type, Object... params) {
		return queryForObject(getStatementCreator(sql, params), type);
	}


	@Override
	public <T> T queryForObject(final BaseBuilder builder, Class<T> type) {
		
		return queryForObject(getStatementCreator(builder), type);
	}


	/**
	 * 	get single object of requested type
	 */
	@Override
	public <T> T queryForObject(StatementCreator psc, Class<T> type) {
		return queryForObject(psc, new SingleColumMapper<>(type));
	}
	
//-----------------------------------------------
//	get object by RowMapper
//-----------------------------------------------

	@Override
	public <T> T queryForObject(final String sql, RowMapping<T> mapper) {
		
		return queryForObject(getStatementCreator(sql), mapper);
	}

	@Override
	public <T> T queryForObject(final String sql, 
									RowMapping<T> mapper,
									final Object... params) {
		
		return queryForObject(getStatementCreator(sql, params), mapper);
	}
	

	@Override
	public <T> T queryForObject(final BaseBuilder builder, RowMapping<T>mapper) {
		
		return queryForObject(getStatementCreator(builder), mapper);
	}

	
	@Override
	public <T> T queryForObject(StatementCreator psc, RowMapping<T>mapper) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = psc.createStatement(connection);		
			ResultSet rs = ps.executeQuery();
			T o = null;
			if (rs.next()) {
				o = mapper.rowToObjec(rs);
assert(rs.isLast());
//System.out.println(ps);
			}
			JdbcUtil.closeResultSet(rs);
			JdbcUtil.closeStatement(ps);
			JdbcUtil.closeConnection(connection);
			return o;
		} catch (SQLException e) {
			throw new RuntimeException(e.getSQLState() 
					+ "\nCaused by\n"
					+ e.getCause(), e);
		}
	}
//-----------------------------------------------------------------------------
//						just like queryForObject 
//-----------------------------------------------------------------------------

	@Override
	public <T> List<T> queryForList(final String sql, RowMapping<T> mapper) {
		
		return queryForList(getStatementCreator(sql), mapper);
	}

	@Override
	public <T> List<T> queryForList(final String sql, 
									RowMapping<T> mapper, 
									final Object... params) {
		
		return queryForList(getStatementCreator(sql, params), mapper);
	}


	@Override
	public <T> List<T> queryForList(final BaseBuilder builder, RowMapping<T> mapper) {
		
		return queryForList(getStatementCreator(builder), mapper);
	}

	
	@Override
	public <T> List<T> queryForList(StatementCreator psc, RowMapping<T> mapper) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = psc.createStatement(connection);
			ResultSet rs = ps.executeQuery();
			List<T> ls = new ArrayList<T>(8);
			while (rs.next()) {
				ls.add(mapper.rowToObjec(rs));
			}
			JdbcUtil.closeResultSet(rs);
			JdbcUtil.closeStatement(ps);
			JdbcUtil.closeConnection(connection);
			return ls;
		} catch (SQLException e) {
			throw new RuntimeException(e.getSQLState() + "\nCaused by\n"
					+ e.getCause(), e);
		}
	}
//-----------------------------------------------
//	get object by type
//-----------------------------------------------

	@Override
	public <T> List<T> queryForList(final String sql, Class<T> type) {
		
		return queryForList(getStatementCreator(sql), type);
	}
	
	@Override
	public <T> List<T> queryForList(String sql, Class<T> type, Object... params) {
		
		return queryForList(getStatementCreator(sql, params), type);
	}
	
	@Override
	public <T> List<T> queryForList(BaseBuilder builder, Class<T> type) {
		
		return queryForList(getStatementCreator(builder), type);
	}

	@Override
	public <T> List<T> queryForList(StatementCreator psc, Class<T> type) {
		
		return queryForList(psc, new SingleColumMapper<T>(type));
	}


//-----------------------------------------------------------------------------
//						Map
//-----------------------------------------------------------------------------

	/**
	 * store the result in a map
	 * 
	 * example:
	 * 
	 * 	JdbcAux jdbcAux = new JdbcAux(testBase.cpds);
		
		Map<String, Object> map = jdbcAux.queryForMap(
				
				new SQLQuery().select("name, age, gender")
								.from("student")
										.innerJoin("takes").on("takes.studentId").eq("student.id")
										.innerJoin("classes").on("classes.id").eq("takes.classId")
								.where("class.id").eq(5)
								.and("student.address = ").var("HuBei")
								.having("student.age").greaterThan(20)
								.offset(0)
								.limit(25)
				);
		
		int age = (int) map.get("AGE"); // case insensitive
		boolean gender = (boolean)map.get("gendeR");
	 */
	@Override
	public Map<String, Object> queryForMap(String sql) {
		ColumnMapper mapper = new ColumnMapper();
		return queryForObject(sql, mapper);
	}

	@Override
	public Map<String, Object> queryForMap(String sql, Object... params) {
		ColumnMapper mapper = new ColumnMapper();
		return queryForObject(sql, mapper, params);
	}

	@Override
	public Map<String, Object> queryForMap(BaseBuilder builder) {
		ColumnMapper mapper = new ColumnMapper();
		return queryForObject(builder, mapper);
	}

//	return (Map<String, T>) queryForObject(sql, columnMapper);
	@Override
	public  Map<String, Object> queryForMap(StatementCreator psc) {
		ColumnMapper mapper = new ColumnMapper();
		return queryForObject(psc, mapper);
	}
	
	
	@Override
	public ResultSet queryForResultSet(String sql) {
		
		Connection connection = getConnection();
		ResultSet rs;
		try {
			rs = connection.createStatement().executeQuery(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e.getSQLState() + "\nCaused by\n"
					+ e.getCause(), e);
		}
		return rs;
	}

	@Override
	public ResultSet queryForResultSet(final String sql, final Object... params) {
		
		return queryForResultSet(new StatementCreator() {
			
			@Override
			public PreparedStatement createStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql);
				setParams(ps, params);
				return ps;
			}
		});
	}


	@Override
	public ResultSet queryForResultSet(BaseBuilder builder) {
		Connection connection = getConnection();
		ResultSet rs;
		try {
			rs = builder.build(connection).executeQuery();
		} catch (SQLException e) {
			throw new RuntimeException(e.getSQLState() + "\nCaused by\n"
					+ e.getCause(), e);
		}
		return rs;
	}
	
	@Override
	public ResultSet queryForResultSet(StatementCreator psc) {
		
		Connection connection = getConnection();
		ResultSet rs;
		try {
			rs = psc.createStatement(connection).executeQuery();
		} catch (SQLException e) {
			throw new RuntimeException(e.getSQLState() + "\nCaused by\n"
					+ e.getCause(), e);
		}
		return rs;
	}


	//-----------------------------------------------------------------------------
		public static void setParams(PreparedStatement ps, Object... params) throws SQLException {
			if (ps != null && params != null) {
				for (int i = 0; i != params.length; ++i) {
					ps.setObject(i + 1, params[i]);
				}
			}
		}
		
	//-----------------------------------------------------------------------------
//						private functions
	//-----------------------------------------------------------------------------
		
		
		/**
		 *  create a StatementCreator out of a String
		 * @param sql
		 * @return
		 */
		protected static StatementCreator getStatementCreator (final String sql) {
			
			return new StatementCreator() {
				
				@Override
				public PreparedStatement createStatement(Connection con)
						throws SQLException {
					
					return con.prepareStatement(sql);
				}
			};
		}
		/**
		 *  create a StatementCreator out of a String and its parameters
		 * @param sql
		 * @param params
		 * @return
		 */
		protected static StatementCreator getStatementCreator (final String sql, final Object...params) {
			
			return new StatementCreator() {
				
				@Override
				public PreparedStatement createStatement(Connection con)
						throws SQLException {
					PreparedStatement ps = con.prepareStatement(sql);
					setParams(ps, params);
//System.out.println(ps.toString());
					return ps;
				}
			};
		}
		/**
		 *  create a StatementCreator out of a SQLBuilder
		 * @param sql
		 * @param params
		 * @return
		 */
		protected static StatementCreator getStatementCreator (final BaseBuilder builder) {
			
			return new StatementCreator() {
				
				@Override
				public PreparedStatement createStatement(Connection con)
						throws SQLException {
					PreparedStatement ps = con.prepareStatement(builder.getUnprepared());
					if (builder.hasParameters()) {
						setParams(ps, builder.getParameters());
					}
					return ps;
				}
			};
		}
		
}


