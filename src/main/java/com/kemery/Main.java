package com.kemery;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) throws SQLException {
		
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ClubDBConfiguration.class);
		
		DataSource dataSource = applicationContext.getBean("dataSource", DataSource.class);
		
		MemberDao memberDao = applicationContext.getBean(MemberDao.class);
		
		Connection connection = dataSource.getConnection();
		
		System.out.println("Club connection closed " + connection.isClosed());
		
		Scanner sc = new Scanner(System.in);
		
		
		Member m = memberDao.find("B146");
		if(m == null) {
			System.out.println("B146 not found");
		} else {
			System.out.println(m.toString());
		}
		
		System.out.print("Lastnm: ");
		String lnm = sc.nextLine();
		m.setLastnm(lnm);
		
		System.out.print("Firstnm: ");
		String fnm = sc.nextLine();
		m.setFirstnm(fnm);
		
		System.out.println("Expdt: ");
		String edt = sc.nextLine();
		m.setExpdt(edt);
		
		
		memberDao.updateMember(m);
		
		// Demonstrate update()
/*		Member memberToUpdate = memberDao.find("U014");
		if(memberToUpdate == null) {
			System.out.println("U014 not found");
		} else {
			System.out.println(memberToUpdate.toString());
		}
		
		memberToUpdate.setFirstnm("Esmerelda");
		memberDao.update(memberToUpdate);
*/		
		
		// Demonstrate findByQuery
/*		m = memberDao.findByQuery("A043");
		if(m == null) {
			System.out.println("A043 not found");
		} else {
			System.out.println(m.toString());
		}
*/		
		
		// Demonstrate findByStatus()
		List<Member> members = memberDao.findByStatus("T");
		System.out.println("Number of inactive members = " + members.size());
		for(Member mem : members) {
			System.out.println(mem.toString());
		}
		
		// Create member to demonstrate insert()
		Member insertMember = new Member();
		
/*		insertMember.setMemid("S953");
		insertMember.setFirstnm("Adam");
		insertMember.setLastnm("Smith");
		insertMember.setMiddlenm("Joseph");
		insertMember.setStatus("C");
		insertMember.setMemdt("1776-09-03");
		insertMember.setPassword(0301);
*/
		// Uncomment the following line to insert Adam Smith
	//	memberDao.insert(insertMember);
	//	insertMember = memberDao.find(insertMember.getMemid());
		
		System.out.println(insertMember.getMemid());
		
		// Demonstrate delete()
//		String deleteMember = "S953";
//		memberDao.delete(deleteMember);		
		connection.close();

		//System.out.println("Club connection " + connection.isClosed());
		applicationContext.close();
	}

}
