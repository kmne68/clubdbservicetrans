package com.kemery.services;

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
			memberDao.update(m);
			System.out.println("Member " + memid + " updated.");
		} catch(Exception e) {
			throw new UpdateFailedException("Update fail for " + memid + " " + e.getMessage());
		}
	}
	
}
