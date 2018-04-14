package com.kemery.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.kemery.Member;
import com.kemery.MemberDao;
import com.kemery.UpdateFailedException;

public class MemberServiceImpl implements MemberService {

	private MemberDao memberDao;
	
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	
	@Transactional
	public void updatePassword(String memid, long password) {

		try {
			Member m = memberDao.find(memid);
			if(m.getPassword() == password) {
				System.out.println("No password change: password matched");
				return;
			}
			
			m.setPassword(password);
			memberDao.updateMember(m);
			//System.out.println("Member " + memid + " updated.");
		} catch(Exception e) {
			throw new UpdateFailedException("Update fail for " + memid + " " + e.getMessage());
		}
	}
	
	public boolean showMember(String memid) {
		
		try {
			Member m = memberDao.find(memid);
			System.out.println("Member found " + m.toString());
			return true;
		} catch (Exception e) {
			System.out.println("Member not found");
		}
		return false;
	}
	
	
	@Transactional
	public void renewMember(String memid, String success) {
		
		try {
			// find member
			// renew member expiration date
			// create purchase record for dues renewal amount
			// use underlying DAO object
			if (success == "N") {				
				
			}
			Member m = memberDao.find(memid);
			
			m.renew();
			memberDao.updateMember(m);
			
			String purchasedt = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			
			// add dues purchase
			// call memberDao.addDuesPurchase(...)
			memberDao.addDuesPurchase(memid, purchasedt, success);
			
		} catch (Exception e) {
			throw new UpdateFailedException(e.getMessage());
		}
	}	
}
