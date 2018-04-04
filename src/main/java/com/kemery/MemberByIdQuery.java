package com.kemery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

public class MemberByIdQuery extends MappingSqlQuery<Member> {

	public MemberByIdQuery(DataSource dataSource) {
		super(dataSource, "SELECT memid, lastname, firstname, middlename, status, memdt, expdt, password FROM tblmembers where memid = ?");
		
		declareParameter(new SqlParameter(Types.VARCHAR));
		compile();
	}
	
	protected Member mapRow(ResultSet rs, int rowNum) throws SQLException {
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
}
