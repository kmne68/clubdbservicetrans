package com.kemery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

public class MemberDaoJdbcImpl implements MemberDao {

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private MappingSqlQuery<Member> memberByIdQuery;
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		
		this.jdbcTemplate = jdbcTemplate;
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}
	
	
	public void setMemberByIdQuery(MappingSqlQuery<Member> memberByIdQuery) {
	
		this.memberByIdQuery = memberByIdQuery;
	}
	
	
	public Member findByQuery(String memid) {
		
		return memberByIdQuery.findObject(memid);
	}
	
	
	public Member find(String memid) {
		
		String sql = "SELECT memid, lastname, firstname, middlename, status, memdt, expdt, password FROM tblmembers WHERE memid = ?";
		return jdbcTemplate.queryForObject(sql, new RowMapper<Member>() {
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member m = new Member();
				m.setMemid(rs.getString("memid"));
				m.setLastnm(rs.getString("lastname"));
				m.setFirstnm(rs.getString("firstname"));
				m.setMiddlenm(rs.getString("middlename"));
				m.setStatus(rs.getString("status"));
				m.setMemdt(rs.getString("memdt"));
				m.setExpdt(rs.getString("expdt"));
				m.setPassword(rs.getInt("password"));
				return m;
			}
		}, memid);
	}
	
	public List<Member> findByStatus(String stat) {
		
		String sql = "SELECT memid, lastname, firstname, middlename, status, memdt, expdt, password FROM tblmembers WHERE status = :status";
		return namedParameterJdbcTemplate.query(sql, Collections.singletonMap("status", stat),
				new RowMapper<Member>() {
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member m = new Member();
				m.setMemid(rs.getString("memid"));
				m.setLastnm(rs.getString("lastname"));
				m.setFirstnm(rs.getString("firstname"));
				m.setMiddlenm(rs.getString("middlename"));
				m.setStatus(rs.getString("status"));
				m.setMemdt(rs.getString("memdt"));
				m.setExpdt(rs.getString("expdt"));
				m.setPassword(rs.getInt("password"));
				return m;
			}
		});
	}
	
	
	public void insert(Member member) {
		PreparedStatementCreatorFactory psCreatorFactory = new PreparedStatementCreatorFactory(
				"insert into tblmembers(memid, lastname, firstname, middlename, status, memdt, password) values(?, ?, ?, ?, ?, ?, ?)",
				new int[] {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.INTEGER });		
		
		
		KeyHolder keyHolder = new GeneratedKeyHolder();

		int count = jdbcTemplate.update(
				psCreatorFactory.newPreparedStatementCreator(new Object[] {
						member.getMemid(), member.getLastnm(), member.getFirstnm(), member.getMiddlenm(), member.getStatus(), member.getMemdt(), member.getPassword() }));
	//	if (count != 1)
	//		throw new InsertFailedException("Cannot insert account");
//		member.setId(keyHolder.getKey().longValue());
	}
	
	 	

	public void delete(String memberId) {
		int count = jdbcTemplate.update("DELETE FROM tblmembers WHERE memid = ? LIMIT 1", memberId);
	//	if (count != 1)
	//		throw new DeleteFailedException("Cannot delete member");
	}
	
	
	public void update(Member member) {
		int count = jdbcTemplate.update("update tblmembers set memid = ?, lastName = ?, firstName = ?, middleName = ?, status = ?, memdt = ?, expdt = ?, password = ? where memid = ?",
						member.getMemid(), member.getLastnm(), member.getFirstnm(), member.getMiddlenm(), member.getStatus(), member.getMemdt(), member.getExpdt(), member.getPassword(), member.getMemid());
		if (count != 1)
			throw new UpdateFailedException("Cannot update account");
	}

	
	public void updateMember(Member member) {
		String sql = "update tblMembers set lastname = ?, " +
					" firstname = ?, " +
					" middlename = ?, " +
					" status = ?, " +
					" memdt = ?, " +
					" expdt = ?, " +
					" password = ?, " + 
					" where memid = ?";
		try {
			int count = jdbcTemplate.update("update tblmembers set memid = ?, lastName = ?, firstName = ?, middleName = ?, status = ?, memdt = ?, expdt = ?,  password = ? where memid = ?",
					member.getMemid(), member.getLastnm(), member.getFirstnm(), member.getMiddlenm(), member.getStatus(), member.getMemdt(), member.getExpdt(), member.getPassword(), member.getMemid());
			if (count != 1)
				throw new UpdateFailedException("No member update for " + member.getMemid() + " count = " + count);
			else {
				System.out.println("Member " + member.getMemid() + " UPDATED.");
				
			}
		} catch (Exception e) {
			throw new UpdateFailedException(e.getMessage());
		}
	}


	@Override
	@Transactional
	public void addDuesPurchase(String memid, String purchasedt, String success) {
		// TODO Auto-generated method stub
		Connection connection = DataSourceUtils.getConnection(dataSource);
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE tblmembers SET ExpDt = '2018-01-01' WHERE memid = '" + memid + "'");
			
			if(success.equalsIgnoreCase("Y")) {
	//			statement.executeUpdate("INSERT INTO tblpurchases (memid, purchasedt, transtype, transcd, amount) VALUES ('\" + memid + \"','2018-05-01', 'D', '01', 100.00)");
//				PreparedStatementCreatorFactory psCreatorFactory = new PreparedStatementCreatorFactory(("INSERT INTO tblpurchases (memid, purchasedt, transtype, transcd, amount) VALUES ('" + memid + "','2018-04-23', 'D', '01', 100.00)" )); 
			} else {
	//			// failure (no success)
				statement.executeUpdate("INSET INTO tblpurchases (memid, purchasedt, transtype, transcd, amount) VALUES ('\" + memid + \"','2017-03-23', 'D', '01', 100.00)");
//				PreparedStatementCreatorFactory psCreatorFactory = new PreparedStatementCreatorFactory(("INSET INTO tblpurchases (memid, purchasedt, transtype, transcd, amount) VALUES ('" + memid + "','2017-03-23', 'D', '01', 100.00)" )); 
			}
			connection.commit();
			System.out.println("Transactions for " + memid + " committed.");
			
		} catch (Exception e) {
			
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} finally {
			DataSourceUtils.releaseConnection(connection, dataSource);
		}
		
	}


//	public void update(Member member) {
//		int count = jdbcTemplate.update("update tblmembers set (memid, lastname, firstname, middlename, status, memdt, password) = (?,?,?,?,?,?,?) where memid = ?",
//						member.getMemid(), member.getLastnm(), member.getFirstnm(), member.getMiddlenm(), member.getStatus(), member.getMemdt(), member.getPassword(), member.getMemid());
//		if (count != 1)
//			throw new UpdateFailedException("Cannot update account");
//	}	
	
}
